package bakingtime.maxgaj.udacity.com.bakingtime;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import bakingtime.maxgaj.udacity.com.bakingtime.Model.Ingredient;
import bakingtime.maxgaj.udacity.com.bakingtime.Model.Recipe;
import bakingtime.maxgaj.udacity.com.bakingtime.Model.Step;
import butterknife.BindView;
import butterknife.ButterKnife;

public class StepActivity extends AppCompatActivity {
    private static final String POSITION = "position";

    private Recipe recipe;
    private int position;

    @BindView(R.id.step_previous_button) Button previousButton;
    @BindView(R.id.step_next_button) Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("Recipe")) {
            this.recipe = intent.getParcelableExtra("Recipe");
            this.position = intent.getIntExtra("Position", 0);
        }

        if (savedInstanceState != null){
            this.position = savedInstanceState.getInt(POSITION);
        }

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPrevious();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNext();
            }
        });

        updateButton();

        if (savedInstanceState == null) {
            StepFragment stepFragment = new StepFragment();
            stepFragment.setStep(recipe.getSteps().get(position));
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.step_container, stepFragment).commit();
        }
    }

    private void getPrevious(){
        if (position > 0){
            this.position-=1;
            StepFragment stepFragment = new StepFragment();
            stepFragment.setStep(recipe.getSteps().get(position));
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.step_container, stepFragment).commit();
            updateButton();
        }
    }

    private void getNext(){
        if (position < recipe.getSteps().size()-1){
            this.position+=1;
            StepFragment stepFragment = new StepFragment();
            stepFragment.setStep(recipe.getSteps().get(position));
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.step_container, stepFragment).commit();
            updateButton();
        }
    }

    private void updateButton(){
        if (position <= 0) {
            previousButton.setEnabled(false);
        } else {
            previousButton.setEnabled(true);
        }

        if (position >= recipe.getSteps().size()-1){
            nextButton.setEnabled(false);
        } else {
            nextButton.setEnabled(true);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POSITION, position);
    }
}
