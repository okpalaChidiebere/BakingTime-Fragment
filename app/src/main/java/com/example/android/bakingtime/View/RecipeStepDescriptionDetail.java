package com.example.android.bakingtime.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.android.bakingtime.R;

public class RecipeStepDescriptionDetail extends AppCompatActivity {
//<!-- android:visibility="invisible" -->
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_description_detail);

        // Create a new head BodyPartFragment
        FragmentRecipePlayerView playerViewFragment = new FragmentRecipePlayerView();

        // Add the fragment to its container using a FragmentManager and a Transaction
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.player_view_body, playerViewFragment)
                .commit();
    }

}
