package bakingtime.maxgaj.udacity.com.bakingtime;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import bakingtime.maxgaj.udacity.com.bakingtime.Model.Recipe;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class StepActivityScreenTest {

    @Rule
    public ActivityTestRule<StepActivity> mActivityRule = new ActivityTestRule(StepActivity.class){
        @Override
        protected Intent getActivityIntent() {
            Intent intent = new Intent();
            Recipe recipe = Recipe.getDumbRecipe();
            intent.putExtra("Recipe", recipe);
            intent.putExtra("Position", 1);
            return intent;
        }
    };

    @Test
    public void clickNextButtonOnSecondStep_getsThirdSTep(){
        onView(withId(R.id.step_text_view)).check(matches(withText(Recipe.SECOND_DUMB_STEP_DESCRIPTION)));
        onView(withId(R.id.step_next_button)).perform(click());
        onView(withId(R.id.step_text_view)).check(matches(withText(Recipe.THIRD_DUMB_STEP_DESCRIPTION)));
    }

    @Test
    public void clickPreviousButtonOnSecondStep_getsFirstSTep(){
        onView(withId(R.id.step_text_view)).check(matches(withText(Recipe.SECOND_DUMB_STEP_DESCRIPTION)));
        onView(withId(R.id.step_previous_button)).perform(click());
        onView(withId(R.id.step_text_view)).check(matches(withText(Recipe.FIRST_DUMB_STEP_DESCRIPTION)));
    }

}
