package com.example.android.bakingtime;

import android.widget.TextView;

import com.example.android.bakingtime.View.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withResourceName;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class MainActivityScreenTest {

    public static final String BAKING_FOOD_TAG = "Yellow Cake";

    /*Testing the AdapterViews for the RecyclerView in the MainActivity class*/
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    /**
     * Clicks on a RecyclerView item and checks it opens up the RecipeName that loads the master
     * fragment with the correct ingredient and recipe step details.
     */
    @Test
    public void clickRecyclerViewItem_OpensRecipeNameActivity() {

        // Get a reference to a specific RecyclerView item and clicks it.
        onView(withId(R.id.recyclerView_bakingFoodTags))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText(BAKING_FOOD_TAG)), click()));

        // Checks that the RecipeNameActivity opens with the ActionBar having the correct baking food tag name displayed
        //FYI: the 'RecipeNameMasterListFragment' populates the 'RecipeName'(Host Activity) action bar title programatically
        onView(allOf(instanceOf(TextView.class),
                withParent(withResourceName("action_bar"))))
                .check(matches(withText(BAKING_FOOD_TAG)));

    }

}
