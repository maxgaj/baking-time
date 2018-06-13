package bakingtime.maxgaj.udacity.com.bakingtime;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import bakingtime.maxgaj.udacity.com.bakingtime.Model.Step;
import butterknife.BindView;
import butterknife.ButterKnife;
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

public class StepFragment extends Fragment {
    private static final String STEP  = "step";
    private static final String TAG = "StepFragment";

    private Step step;
    private SimpleExoPlayer exoPlayer;

    @BindView(R.id.step_text_view) TextView textView;
    @BindView(R.id.step_playerView) SimpleExoPlayerView playerView;

    public StepFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null){
            this.step = savedInstanceState.getParcelable(STEP);
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
            initializePlayer(Uri.parse(step.getVideoURL()));
        }
        else {
            playerView.setVisibility(View.GONE);
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
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        if (exoPlayer != null) {
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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }
}
