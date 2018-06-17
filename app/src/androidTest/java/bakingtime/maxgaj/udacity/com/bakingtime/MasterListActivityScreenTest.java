package bakingtime.maxgaj.udacity.com.bakingtime;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import bakingtime.maxgaj.udacity.com.bakingtime.Model.Recipe;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4.class)
public class MasterListActivityScreenTest {

    @Rule
    public ActivityTestRule<MasterListActivity> mActivityRule = new ActivityTestRule(MasterListActivity.class){
        @Override
        protected Intent getActivityIntent() {
            Intent intent = new Intent();
            Recipe recipe = Recipe.getDumbRecipe();
            intent.putExtra("Recipe", recipe);
            return intent;
        }
    };

    @Test
    public void clickGridViewItem_opensStepActivity(){
        onData(anything()).inAdapterView(withId(R.id.master_grid_view)).atPosition(0).perform(click());
        onView(withId(R.id.step_text_view)).check(matches(withText(Recipe.FIRST_DUMB_STEP_DESCRIPTION)));
    }

    @Test
    public void clickIngredientCardView_opensIngredientActivity(){
        onView(withId(R.id.ingredient_cardview)).perform(click());
        String firstDumbIngredient = Recipe.DUMB_INGREDIENT + " : " + Double.toString(Recipe.DUMB_INGREDIENT_QUANTITY) + " " +Recipe.DUMB_INGREDIENT_MEASURE;
        onData(anything()).inAdapterView(withId(R.id.ingredient_listview)).atPosition(0).check(matches(withText(firstDumbIngredient)));
        //onView(withId(R.id.ingredient_title)).check(matches(withText(Recipe.DUMB_RECIPE_NAME)));

    }

}
