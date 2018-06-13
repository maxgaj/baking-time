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
import butterknife.BindView;
import butterknife.ButterKnife;

import java.util.ArrayList;

public class IngredientActivity extends AppCompatActivity {
    private Recipe recipe;

//    @BindView(R.id.ingredient_previous_button) Button previousButton;
//    @BindView(R.id.ingredient_next_button) Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("Recipe"))
            this.recipe = intent.getParcelableExtra("Recipe");
//
//        previousButton.setEnabled(false);
//
//        nextButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getNext();
//            }
//        });

        if (savedInstanceState == null){
            IngredientFragment ingredientFragment = new IngredientFragment();
            ArrayList<Ingredient> ingredientList = (ArrayList<Ingredient>) recipe.getIngredients();
            ingredientFragment.setIngredientlist(ingredientList);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.ingredients_container, ingredientFragment).commit();
        }
    }

//    private void getNext(){
//        Context context = getApplicationContext();
//        Class destination = StepActivity.class;
//        Intent intent = new Intent(context, destination);
//        intent.putExtra("Recipe", recipe);
//        intent.putExtra("Position", 0);
//        context.startActivity(intent);
//    }
}
