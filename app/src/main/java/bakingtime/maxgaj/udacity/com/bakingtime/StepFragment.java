package bakingtime.maxgaj.udacity.com.bakingtime;

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

public class StepFragment extends Fragment {
    private static final String STEP  = "step";
    private static final String TAG = "StepFragment";

    private Step step;

    @BindView(R.id.step_text_view) TextView textView;

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

        return rootView;
    }

    public void setStep(Step step){
        this.step = step;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(STEP, step);
    }
}
