package com.example.android.bakingtime.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.android.bakingtime.Model.BakingStep;
import com.example.android.bakingtime.R;

import java.io.Serializable;
import java.util.List;

public class RecipeName extends AppCompatActivity implements RecipeNameMasterListFragment.OnRecipeStepDescriptionClickListener{

    public static final String BAKING_RECIPE_STEPS_LIST = "baking_recipe_steps_list";
    public static final String LIST_INDEX = "list_index";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_name);
    }

    @Override
    public void onRecipeStepDescriptionSelected(int position, List<BakingStep> recipeSteps) {
        // Create a Toast that displays the position that was clicked
        //Toast.makeText(this, "Position clicked = " + recipeSteps.getDescription(), Toast.LENGTH_SHORT).show();

        Bundle b = new Bundle();
        b.putInt(LIST_INDEX, position);
        b.putSerializable(BAKING_RECIPE_STEPS_LIST, (Serializable) recipeSteps);

        // Attach the Bundle to an intent
        final Intent intent = new Intent(this, RecipeStepDescriptionDetail.class);
        intent.putExtras(b);

        startActivity(intent);
    }
}
