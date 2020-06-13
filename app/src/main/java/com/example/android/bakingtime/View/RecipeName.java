package com.example.android.bakingtime.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.android.bakingtime.Model.BakingFood;
import com.example.android.bakingtime.Model.BakingStep;
import com.example.android.bakingtime.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RecipeName extends AppCompatActivity implements RecipeNameMasterListFragment.OnRecipeStepDescriptionClickListener{

    public static final String BAKING_RECIPE_STEPS_LIST = "baking_recipe_steps_list";
    public static final String LIST_INDEX = "list_index";
    public static final String EXTRA_FOOD_LIST = "BAKING_FOOD_LIST";

    /*Keys used for widgets*/
    public static final String EXTRA_FOOD_LIST_INGREDIENT = "BAKING_FOOD_LIST_INGREDIENT";
    public static final String BUNDLE_FOOD_LIST_INGREDIENT = "BUNDLE_FOOD_LIST_INGREDIENT";

    private FragmentRecipePlayerView playerViewFragment;
    private List<BakingStep> bakingStep;

    // Track whether to display a two-pane or single-pane UI
    // A single-pane display refers to phone screens, and two-pane to larger tablet screens
    private boolean mTwoPane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_name);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true); //back Button

        // Determine if you're creating a two-pane or single-pane display
        if(findViewById(R.id.android_me_linear_layout) != null) {
            // This LinearLayout will only initially exist in the two-pane tablet case

            Log.e("DoublePane", "yes");
            mTwoPane = true;

            if(savedInstanceState == null) {

                // Create a new head BodyPartFragment
                playerViewFragment = new FragmentRecipePlayerView();
                List<BakingFood> bakingFood = (List<BakingFood>) getIntent().getSerializableExtra(EXTRA_FOOD_LIST);
                bakingStep = bakingFood.get(0).getSteps();

                //Log.e(TAG, "Position clicked: "+ currentListIndex);
                playerViewFragment.setRecipeStepsList(bakingStep);

                // Add the fragment to its container using a FragmentManager and a Transaction
                FragmentManager fragmentManager = getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .add(R.id.player_view_and_description_body, playerViewFragment)
                        .commit();
            }
        }else {
            // We're in single-pane mode and displaying fragments on a phone in separate activities
            mTwoPane = false;
            Log.e("DoublePane", "No");
        }
    }

    @Override
    public void onRecipeStepDescriptionSelected(int position, List<BakingStep> recipeSteps) {
        // Create a Toast that displays the position that was clicked
        //Toast.makeText(this, "Position clicked = " + recipeSteps.getDescription(), Toast.LENGTH_SHORT).show();

        // Handle the two-pane case and replace existing fragments right when a new image is selected from the master list
        if (mTwoPane) {
            // Create two=pane interaction

            // Create a new head BodyPartFragment
            playerViewFragment = new FragmentRecipePlayerView();

            //Log.e(TAG, "Position clicked: "+ currentListIndex);
            playerViewFragment.setRecipeStepsList(recipeSteps);
            playerViewFragment.setListIndex(position);

            // Add the fragment to its container using a FragmentManager and a Transaction
            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.player_view_and_description_body, playerViewFragment)
                    .commit();

        }else {

            Bundle b = new Bundle();
            b.putInt(LIST_INDEX, position);
            b.putSerializable(BAKING_RECIPE_STEPS_LIST, (Serializable) recipeSteps);

            // Attach the Bundle to an intent
            final Intent intent = new Intent(this, RecipeStepDescriptionDetail.class);
            intent.putExtras(b);

            startActivity(intent);
        }
    }

    public List<BakingFood> getBakingFoodData() {

        return (List<BakingFood>) getIntent().getSerializableExtra(EXTRA_FOOD_LIST);
    }
}
