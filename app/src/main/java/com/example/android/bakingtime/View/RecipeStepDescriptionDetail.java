package com.example.android.bakingtime.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.android.bakingtime.R;

public class RecipeStepDescriptionDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_description_detail);

        if (savedInstanceState == null) {
            // Create a new head BodyPartFragment
            FragmentRecipePlayerView playerViewFragment = new FragmentRecipePlayerView();
            //playerViewFragment.setRecipeStepsList();
            //playerViewFragment.setListIndex();

            // Add the fragment to its container using a FragmentManager and a Transaction
            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.player_view_and_description_body, playerViewFragment)
                    .commit();
        }
    }

}
