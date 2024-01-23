package hk.edu.shape;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class RouteListActivityTest {

    @Rule
    public ActivityTestRule<RouteListActivity> activityRule = new ActivityTestRule<>(RouteListActivity.class);

    private RouteListActivity activity;

    @Before
    public void setUp() {
        activity = activityRule.getActivity();
    }

    @Test
    public void testListViewIsDisplayed() {
        onView(withId(R.id.listView)).check(matches(isDisplayed()));
    }


}
