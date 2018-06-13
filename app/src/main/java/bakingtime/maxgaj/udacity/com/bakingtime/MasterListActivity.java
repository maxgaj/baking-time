package bakingtime.maxgaj.udacity.com.bakingtime;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import bakingtime.maxgaj.udacity.com.bakingtime.Model.Ingredient;
import bakingtime.maxgaj.udacity.com.bakingtime.Model.Recipe;
import bakingtime.maxgaj.udacity.com.bakingtime.Model.Step;
import butterknife.BindView;
import butterknife.ButterKnife;

import java.util.ArrayList;

public class MasterListActivity extends AppCompatActivity implements MasterListFragment.OnStepClickListener {
    public static final String TWO_PANE = "twopane";

    private Recipe recipe;
    private boolean twoPane;

    @BindView(R.id.ingredient_cardview) CardView ingredientsCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_list);
        ButterKnife.bind(this);

        if (savedInstanceState != null){
            twoPane = savedInstanceState.getBoolean(TWO_PANE);
        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("Recipe"))
            this.recipe = intent.getParcelableExtra("Recipe");

        ingredientsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onIngredientsSelected();
            }
        });

        if (savedInstanceState == null) {
            MasterListFragment masterListFragment = new MasterListFragment();
            masterListFragment.setStepList(recipe.getSteps());
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.master_list_container, masterListFragment).commit();

            if (findViewById(R.id.master_list_divider) != null) {
                twoPane = true;
                ArrayList<Ingredient> ingredientList = (ArrayList<Ingredient>) recipe.getIngredients();
                IngredientFragment newIngredientFragment = new IngredientFragment();
                newIngredientFragment.setIngredientlist(ingredientList);
                getSupportFragmentManager().beginTransaction().add(R.id.step_container, newIngredientFragment).commit();
            } else {
                twoPane = false;
            }
        }
    }

    @Override
    public void onStepSelected(int position) {
        Step step = recipe.getSteps().get(position);
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
            intent.putExtra("Recipe", recipe);
            intent.putExtra("Position", position);
            context.startActivity(intent);
        }
    }

    public void onIngredientsSelected(){
        ArrayList<Ingredient> ingredientList = (ArrayList<Ingredient>) recipe.getIngredients();
        if (twoPane){
            IngredientFragment newIngredientFragment = new IngredientFragment();
            newIngredientFragment.setIngredientlist(ingredientList);
            getSupportFragmentManager().beginTransaction().replace(R.id.step_container, newIngredientFragment).commit();
        }
        else {
            Context context = getApplicationContext();
            Class destination = IngredientActivity.class;
            Intent intent = new Intent(context, destination);
            intent.putExtra("Recipe", recipe);
            context.startActivity(intent);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(TWO_PANE, twoPane);
    }
}
