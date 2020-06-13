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
    public static final String SHORT_RECIPE_STEP_DESCRIPTION = "Recipe Introduction";
    public static final String BAKING_INGREDIENT_NAME = "sifted cake flour";
    public static final String BAKING_INGREDIENT_MEASUREMENT = "233.0G";

    /*Testing the AdapterViews for the RecyclerView in the MainActivity class*/
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    /**
     * Clicks on a RecyclerView item and checks it opens up the RecipeName that loads the master
     * fragment with the correct ingredient and recipe step details.
     *
     * The we select a specific recipe baking step(instruction summary) and checks it opens up the RecipeStepDescription(full step instruction)
     */
    @Test
    public void clickRecyclerViewItem_OpensRecipeNameActivity_ClickAStepInstruction_OpensRecipeStepDescriptionDetailWithVideoAndInstruction() {

        // Get a reference to a specific RecyclerView item and clicks it.
        onView(withId(R.id.recyclerView_bakingFoodTags))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText(BAKING_FOOD_TAG)), click()));

        // Checks that the RecipeNameActivity opens with the ActionBar having the correct baking food tag name displayed
        //FYI: the 'RecipeNameMasterListFragment' populates the 'RecipeName'(Host Activity) action bar title programatically
        onView(allOf(instanceOf(TextView.class),
                withParent(withResourceName("action_bar"))))
                .check(matches(withText(BAKING_FOOD_TAG)));

        // Checks that the RecipeStepDescriptionDetail opens showing the correct baking ingredient displayed
        onView(withId(R.id.tv_recyclerView_step_ingredients)).check(matches(hasDescendant(withText(BAKING_INGREDIENT_NAME))));
        //onView(withId(R.id.tv_recyclerView_step_ingredients)).check(matches(hasDescendant(withText(BAKING_INGREDIENT_MEASUREMENT))));


        // Get a reference to a specific RecyclerView instruction item and clicks it.
        onView(withId(R.id.tv_recyclerView_step_description))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText(SHORT_RECIPE_STEP_DESCRIPTION)), click()));

        // Checks that the RecipeStepDescriptionDetail opens showing the correct step instruction displayed
        onView(withId(R.id.tv_recipe_step_instruction)).check(matches(withText(SHORT_RECIPE_STEP_DESCRIPTION)));

    }

}
