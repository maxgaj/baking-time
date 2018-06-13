package bakingtime.maxgaj.udacity.com.bakingtime;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import bakingtime.maxgaj.udacity.com.bakingtime.Model.Step;

public class StepActivity extends AppCompatActivity {
    private Step step;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("Step"))
            this.step = intent.getParcelableExtra("Step");

        if (savedInstanceState == null) {
            StepFragment stepFragment = new StepFragment();
            stepFragment.setStep(step);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.step_container, stepFragment).commit();
        }
    }
}
