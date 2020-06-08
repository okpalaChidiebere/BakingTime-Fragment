package com.example.android.bakingtime.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.android.bakingtime.R;

public class RecipeName extends AppCompatActivity implements RecipeNameMasterListFragment.OnRecipeStepDescriptionClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_name);
    }

    @Override
    public void onRecipeStepDescriptionSelected(int position) {
        // Create a Toast that displays the position that was clicked
        Toast.makeText(this, "Position clicked = " + position, Toast.LENGTH_SHORT).show();
    }
}
