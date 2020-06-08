package com.example.android.bakingtime.View;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.bakingtime.Model.BakingFood;
import com.example.android.bakingtime.Model.BakingStep;
import com.example.android.bakingtime.Model.Ingredient;
import com.example.android.bakingtime.R;
import com.example.android.bakingtime.Utils.ApiClient;
import com.example.android.bakingtime.Utils.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeNameMasterListFragment extends Fragment implements RecipeStepAdapter.RecipeStepAdapterOnClickHandler{

    private static final String TAG = "MasterListFragment";
    ApiInterface apiInterface;
    private RecyclerView mRecyclerView_ingredients;
    private RecyclerView mRecyclerView_recipeSteps;
    private FoodIngredientAdapter foodIngredientAdapter;
    private RecipeStepAdapter recipeStepAdapter;
    private RecyclerView.LayoutManager layoutManager_Ingredients;
    private RecyclerView.LayoutManager layoutManager_recipeSteps;
    private Context context;
    private RecipeStepAdapter.RecipeStepAdapterOnClickHandler handler;

    private int mFoodTagID;
    List<BakingFood> list = new ArrayList<>();
    List<BakingStep> steplist = new ArrayList<>();
    List<Ingredient> ingredient = new ArrayList<>();


    // Define a new interface OnRecipeStepDescriptionClickListener that triggers a callback in the host activity
    OnRecipeStepDescriptionClickListener mCallback;

    // OnRecipeStepDescriptionClickListener interface, calls a method in the host activity named onRecipeStepDescriptionSelected
    public interface OnRecipeStepDescriptionClickListener {
        void onRecipeStepDescriptionSelected(int position, List<BakingStep> recipeSteps);
    }


    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (OnRecipeStepDescriptionClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnRecipeStepDescriptionClickListener");
        }
    }


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the fragment
     */
    public RecipeNameMasterListFragment(){

    }

    /**
     * Inflates the fragment layout file and sets the correct resource for the image to display
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the recipe-name static master list  fragment layout

        View rootView = inflater.inflate(R.layout.fragment_master_list_recipe_name, container, false);

        mRecyclerView_ingredients = (RecyclerView) rootView.findViewById(R.id.tv_recyclerView_step_ingredients);
        mRecyclerView_recipeSteps = (RecyclerView) rootView.findViewById(R.id.tv_recyclerView_step_description);

        mRecyclerView_ingredients.setHasFixedSize(true);
        mRecyclerView_recipeSteps.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager_Ingredients = new LinearLayoutManager(getContext());
        layoutManager_recipeSteps = new LinearLayoutManager(getContext());

        mRecyclerView_ingredients.setLayoutManager(layoutManager_Ingredients);
        mRecyclerView_recipeSteps.setLayoutManager(layoutManager_recipeSteps);

        context = getContext();
        handler = this;

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<List<BakingFood>> call = apiInterface.getBakingFoods();
        call.enqueue(new Callback<List<BakingFood>>() {
            @Override
            public void onResponse(Call<List<BakingFood>> call, Response<List<BakingFood>> response) {

                list = response.body();
                steplist = list.get(0).getSteps();
                ingredient = list.get(0).getIngredients();
                foodIngredientAdapter = new FoodIngredientAdapter(ingredient);
                recipeStepAdapter = new RecipeStepAdapter(steplist, handler);

                mRecyclerView_ingredients.setAdapter(foodIngredientAdapter);
                mRecyclerView_recipeSteps.setAdapter(recipeStepAdapter);
            }

            @Override
            public void onFailure(Call<List<BakingFood>> call, Throwable t) {
                Log.e(TAG, "onFailure "+ t.getLocalizedMessage());
            }
        });

        // Return the rootView
        return rootView;
    }

    @Override
    public void onClick(BakingStep recipeSteps) {

        int bakingStepID = recipeSteps.getId();

        //BakingStep temRecipeSteps = new BakingStep(recipeSteps.getDescription(), recipeSteps.getVideoURL(), recipeSteps.getThumbnailURL());

        // Trigger the callback method and pass in the position that was clicked
        mCallback.onRecipeStepDescriptionSelected(bakingStepID, steplist);
    }
}
