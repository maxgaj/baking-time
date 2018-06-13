package bakingtime.maxgaj.udacity.com.bakingtime;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import bakingtime.maxgaj.udacity.com.bakingtime.Model.Recipe;
import bakingtime.maxgaj.udacity.com.bakingtime.Model.Step;

public class MasterListActivity extends AppCompatActivity implements MasterListFragment.OnStepClickListener {
    public static final String TWO_PANE = "twopane";

    private Recipe recipe;
    private boolean twoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_list);

        if (savedInstanceState != null){
            twoPane = savedInstanceState.getBoolean(TWO_PANE);
        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("Recipe"))
            this.recipe = intent.getParcelableExtra("Recipe");

        if (savedInstanceState == null) {
            MasterListFragment masterListFragment = new MasterListFragment();
            masterListFragment.setStepList(recipe.getSteps());
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.master_list_container, masterListFragment).commit();

            if (findViewById(R.id.master_list_divider) != null) {
                twoPane = true;
                StepFragment stepFragment = new StepFragment();
                stepFragment.setStep(recipe.getSteps().get(0));
                fragmentManager.beginTransaction().add(R.id.step_container, stepFragment).commit();
            } else {
                twoPane = false;
            }
        }
    }

    @Override
    public void onStepSelected(int position) {
        Step step = recipe.getSteps().get(position);
        Log.d("TEST", "onStepSelected: twopane = " +twoPane);
        if (twoPane){
            StepFragment newStepFragment = new StepFragment();
            newStepFragment.setStep(step);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_container, newStepFragment).commit();
        }
        else {
            Context context = getApplicationContext();
            Class destination = StepActivity.class;
            Intent intent = new Intent(context, destination);
            intent.putExtra("Step", step);
            context.startActivity(intent);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(TWO_PANE, twoPane);
    }
}
