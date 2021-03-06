package bakingtime.maxgaj.udacity.com.bakingtime;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import bakingtime.maxgaj.udacity.com.bakingtime.Model.Ingredient;
import bakingtime.maxgaj.udacity.com.bakingtime.Model.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

import java.util.ArrayList;

public class IngredientActivity extends AppCompatActivity {
    private Recipe recipe;

    @BindView(R.id.ingredient_title) TextView recipeNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("Recipe"))
            this.recipe = intent.getParcelableExtra("Recipe");

        recipeNameTextView.setText(this.recipe.getName());

        if (savedInstanceState == null){
            IngredientFragment ingredientFragment = new IngredientFragment();
            ArrayList<Ingredient> ingredientList = (ArrayList<Ingredient>) recipe.getIngredients();
            ingredientFragment.setIngredientlist(ingredientList);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.ingredients_container, ingredientFragment).commit();
        }
    }
}
