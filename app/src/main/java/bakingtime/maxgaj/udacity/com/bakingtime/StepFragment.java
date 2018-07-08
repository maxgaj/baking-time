package bakingtime.maxgaj.udacity.com.bakingtime;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import bakingtime.maxgaj.udacity.com.bakingtime.Model.Step;
import butterknife.BindView;
import butterknife.ButterKnife;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

public class StepFragment extends Fragment {
    private static final String STEP  = "step";
    private static final String POSITION  = "position";
    private static final String PLAYSTATE = "playstate";
    private static final String TAG = "StepFragment";

    private Step step;
    private SimpleExoPlayer exoPlayer;
    private long playerPosition;
    private boolean isPlayWhenReady;
    private Uri videoUri;

    @BindView(R.id.step_text_view) TextView textView;
    @BindView(R.id.step_playerView) SimpleExoPlayerView playerView;
    @BindView(R.id.step_imageView) ImageView thumbnailImageView;

    public StepFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.playerPosition = C.TIME_UNSET;
        this.isPlayWhenReady = true;
        if (savedInstanceState != null){
            this.step = savedInstanceState.getParcelable(STEP);
            this.playerPosition = savedInstanceState.getLong(POSITION, C.TIME_UNSET);
            this.isPlayWhenReady = savedInstanceState.getBoolean(PLAYSTATE, true);
        }

        View rootView = inflater.inflate(R.layout.fragment_step, container, false);
        ButterKnife.bind(this, rootView);
        if (step != null){
            textView.setText(step.getDescription());
        }
        else {
            Log.v(TAG, "Step is null");
        }

        String videoUrl = step.getVideoURL();
        if (videoUrl != null && !videoUrl.equals("")) {
            videoUri = Uri.parse(step.getVideoURL());
            initializePlayer(this.videoUri);
            thumbnailImageView.setVisibility(View.GONE);
        }
        else {
            playerView.setVisibility(View.GONE);
            String thumbnailUrl = step.getThumbnailURL();
            if (thumbnailUrl != null && !thumbnailUrl.equals("")){
                String thumbnailExtension = thumbnailUrl.substring(thumbnailUrl.lastIndexOf(".")+1);
                if (!thumbnailExtension.equals("mp4")) {
                    Uri thumbnailUri = Uri.parse(thumbnailUrl);
                    Picasso.with(getContext()).load(thumbnailUri).into(thumbnailImageView);
                }
                else {
                    thumbnailImageView.setVisibility(View.GONE);
                }
            }
            else {
                thumbnailImageView.setVisibility(View.GONE);
            }

        }

        return rootView;
    }

    private void initializePlayer(Uri uri){
        if (exoPlayer == null){
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            playerView.setPlayer(exoPlayer);
            String userAgent = Util.getUserAgent(getContext(), "BakingTime");
            MediaSource mediaSource = new ExtractorMediaSource(uri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            if (this.playerPosition != C.TIME_UNSET)
                exoPlayer.seekTo(this.playerPosition);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(this.isPlayWhenReady);
        }
    }

    private void releasePlayer() {
        if (exoPlayer != null) {
            this.playerPosition = exoPlayer.getCurrentPosition();
            this.isPlayWhenReady = exoPlayer.getPlayWhenReady();
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }


    public void setStep(Step step){
        this.step = step;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(STEP, step);
        outState.putLong(POSITION, this.playerPosition);
        outState.putBoolean(PLAYSTATE, this.isPlayWhenReady);
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }


    // based on discussion suggested by my first reviewer
    // https://stackoverflow.com/questions/45481775/exoplayer-restore-state-when-resumed/45482017#
    @Override
    public void onResume() {
        super.onResume();
        if (videoUri != null)
            initializePlayer(this.videoUri);
    }
}
