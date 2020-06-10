package com.example.android.bakingtime.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.android.bakingtime.Model.BakingFood;
import com.example.android.bakingtime.R;
import com.example.android.bakingtime.Utils.ApiClient;
import com.example.android.bakingtime.Utils.ApiInterface;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BakingFoodTagAdapter.BakingFoodTagAdapterOnClickHandler{

    private static final String TAG = "MainActivity";
    ApiInterface apiInterface;
    private RecyclerView mRecyclerView;
    private BakingFoodTagAdapter foodTagAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Context context;
    private BakingFoodTagAdapter.BakingFoodTagAdapterOnClickHandler handler;
    List<BakingFood> list = new ArrayList<>();

    /*Puts extra name strings*/
    private static final String EXTRA_FOOD_LIST = "BAKING_FOOD_LIST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_bakingFoodTags);

        mRecyclerView.setHasFixedSize(true);

        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
        if (tabletSize) {
            // use a Grid layout manager
            layoutManager = new GridLayoutManager(this, 3);
        } else {
            // use a linear layout manager
            layoutManager = new LinearLayoutManager(this);
        }
        mRecyclerView.setLayoutManager(layoutManager);

        context = this;
        handler = this;

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<List<BakingFood>> call = apiInterface.getBakingFoods();
        call.enqueue(new Callback<List<BakingFood>>() {
            @Override
            public void onResponse(Call<List<BakingFood>> call, Response<List<BakingFood>> response) {

                list = response.body();
                // specify an adapter (see also next example)
                foodTagAdapter = new BakingFoodTagAdapter(list, context, handler);
                mRecyclerView.setAdapter(foodTagAdapter);
            }

            @Override
            public void onFailure(Call<List<BakingFood>> call, Throwable t) {
                Log.e(TAG, "onFailure "+ t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void onClick(BakingFood foodTagName) {

        int foodID = foodTagName.getId() - 1; //minus 1 because the index list starts from 0
        //Log.e("BakingAppRecipeTag", list.get(foodID).toString());

        List<BakingFood> tempList = new ArrayList<>();
        tempList.add(list.get(foodID));

        Context context = this;
        Class destinationClass = RecipeName.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        //intentToStartDetailActivity.putExtra(EXTRA_FOOD_TAG_ID, foodID);
        intentToStartDetailActivity.putExtra(EXTRA_FOOD_LIST, (Serializable) tempList);
        startActivity(intentToStartDetailActivity);
    }
}
