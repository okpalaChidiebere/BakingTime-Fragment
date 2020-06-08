package com.example.android.bakingtime.View;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingtime.Model.BakingFood;
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

public class RecipeNameMasterListFragment extends Fragment {

    private static final String TAG = "MasterListFragment";
    ApiInterface apiInterface;
    private RecyclerView mRecyclerView_ingredients;
    private RecyclerView mRecyclerView_recipeSteps;
    private FoodIngredientAdapter foodIngredientAdapter;
    private RecipeStepAdapter recipeStepAdapter;
    private RecyclerView.LayoutManager layoutManager_Ingredients;
    private RecyclerView.LayoutManager layoutManager_recipeSteps;
    private Context context;
    //private RecipeStepAdapter.RecipeStepAdapterOnClickHandler handler;

    private int mFoodTagID;
    List<BakingFood> list = new ArrayList<>();


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
        //handler = getContext();

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<List<BakingFood>> call = apiInterface.getBakingFoods();
        call.enqueue(new Callback<List<BakingFood>>() {
            @Override
            public void onResponse(Call<List<BakingFood>> call, Response<List<BakingFood>> response) {

                list = response.body();
                foodIngredientAdapter = new FoodIngredientAdapter(list.get(0).getIngredients());
                recipeStepAdapter = new RecipeStepAdapter(list.get(0).getSteps());

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
}
