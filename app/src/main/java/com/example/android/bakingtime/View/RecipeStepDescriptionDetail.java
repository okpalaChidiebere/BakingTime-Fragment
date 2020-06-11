package com.example.android.bakingtime.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.android.bakingtime.Model.BakingStep;
import com.example.android.bakingtime.R;

import java.io.Serializable;
import java.util.List;

public class RecipeStepDescriptionDetail extends AppCompatActivity {

    private static final String TAG = "StepDescriptionDetail";

    public static final String BAKING_RECIPE_STEPS_LIST = "baking_recipe_steps_list";
    public static final String LIST_INDEX = "list_index";

    private FragmentRecipePlayerView playerViewFragment;
    private List<BakingStep> bakingStep;
    private int currentListIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_description_detail);

        if (savedInstanceState == null) {
            // Create a new head BodyPartFragment
            playerViewFragment = new FragmentRecipePlayerView();
            currentListIndex = getIntent().getIntExtra(LIST_INDEX, 0);
            bakingStep = (List<BakingStep>) getIntent().getSerializableExtra(BAKING_RECIPE_STEPS_LIST);

            //Log.e(TAG, "Position clicked: "+ currentListIndex);
            playerViewFragment.setRecipeStepsList(bakingStep);
            playerViewFragment.setListIndex(currentListIndex);

            // Add the fragment to its container using a FragmentManager and a Transaction
            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.player_view_and_description_body, playerViewFragment)
                    .commit();
        }else{

            /*to keep track of the list and index when the user uses the button to go through steps
            even after screen configuration to watch video in full screen*/

            bakingStep = (List<BakingStep>) savedInstanceState.getSerializable(BAKING_RECIPE_STEPS_LIST);
            currentListIndex = savedInstanceState.getInt(LIST_INDEX);
        }


    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        outState.putSerializable(BAKING_RECIPE_STEPS_LIST, (Serializable) bakingStep);
        outState.putInt(LIST_INDEX, currentListIndex);

        super.onSaveInstanceState(outState);
    }

    // The "Next" button launches a new fragment with previous steps
    public void previousStep(View view) {

        if (currentListIndex != 0) {
            currentListIndex = currentListIndex - 1;

            playerViewFragment = new FragmentRecipePlayerView();
            playerViewFragment.setRecipeStepsList(bakingStep);
            playerViewFragment.setListIndex(currentListIndex);

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.player_view_and_description_body, playerViewFragment)
                    .commit();

        }
        else{
            // Show an error message as a toast
            Toast.makeText(this, "You are at the first step", Toast.LENGTH_SHORT).show();
        }

    }

    // The "Next" button launches a new fragment with next steps
    public void nextStep(View view) {

        if(currentListIndex < bakingStep.size() - 1) {
            currentListIndex = currentListIndex + 1;

            playerViewFragment = new FragmentRecipePlayerView();
            playerViewFragment.setRecipeStepsList(bakingStep);
            playerViewFragment.setListIndex(currentListIndex);

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.player_view_and_description_body, playerViewFragment)
                    .commit();
        }
        else{
            // Show an error message as a toast
            Toast.makeText(this, "This is the last step", Toast.LENGTH_SHORT).show();
        }
    }
}
