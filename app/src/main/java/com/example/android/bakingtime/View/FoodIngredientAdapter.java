package com.example.android.bakingtime.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingtime.Model.Ingredient;
import com.example.android.bakingtime.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FoodIngredientAdapter extends RecyclerView.Adapter<FoodIngredientAdapter.FoodIngredientAdapterViewHolder>{

    private List<Ingredient> mIngredients;

    public FoodIngredientAdapter(List<Ingredient> ingredients){
        this.mIngredients = ingredients;
    }

    /*public void setAdapter(List<Ingredients> data) {
        this.mIngredients = data;
    }*/

    public class FoodIngredientAdapterViewHolder extends RecyclerView.ViewHolder{
        public final TextView mFoodIngredient;
        public final TextView mFoodMeasurement;

        public FoodIngredientAdapterViewHolder(View view){
            super(view);

            mFoodIngredient = (TextView) view.findViewById(R.id.tv_foodIngredient);
            mFoodMeasurement = (TextView) view.findViewById(R.id.tv_IngredientMeasurement);

        }
    }

    @NonNull
    @Override
    public FoodIngredientAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.baking_ingredients_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new FoodIngredientAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodIngredientAdapterViewHolder holder, int position) {

        holder.mFoodIngredient.setText(mIngredients.get(position).getIngredient());

        String measurement = mIngredients.get(position).getQuantity() +" "+mIngredients.get(position).getMeasure();
        holder.mFoodMeasurement.setText(measurement);

    }

    @Override
    public int getItemCount() {
        return mIngredients.size();
    }
}
