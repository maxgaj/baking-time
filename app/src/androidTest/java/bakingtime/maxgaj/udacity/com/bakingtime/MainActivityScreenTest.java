package bakingtime.maxgaj.udacity.com.bakingtime;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityScreenTest {
    public static final String RECIPE_NAME = "Brownies";

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);


    @Test
    public void clickRecyclerViewItem_OpensMasterListActivity(){
        onView(ViewMatchers.withId(R.id.recipe_recyclerview)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.master_list_title)).check(matches(withText(RECIPE_NAME)));
    }


}
