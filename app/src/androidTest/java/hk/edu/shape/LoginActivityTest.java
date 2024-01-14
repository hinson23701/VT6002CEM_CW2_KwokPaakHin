package hk.edu.shape;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class LoginActivityTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule = new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void loginTest() {
        ActivityScenario<LoginActivity> activityScenario = activityRule.getScenario();

        // Type the email and password
        Espresso.onView(withId(R.id.editTextUsername)).perform(typeText("test2@test.com"));
        Espresso.onView(withId(R.id.editTextPassword)).perform(typeText("123456"));

        // Click the login button
        Espresso.onView(withId(R.id.btnLogin)).perform(click());

        // Verify that the progress dialog is displayed
        // Verify that the MapActivity is launched
        ActivityScenario<MapActivity> mapActivityScenario = ActivityScenario.launch(MapActivity.class);
        mapActivityScenario.onActivity(activity -> {

        });

    }
}