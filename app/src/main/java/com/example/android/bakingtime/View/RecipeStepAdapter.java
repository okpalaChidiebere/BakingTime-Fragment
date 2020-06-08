package com.example.android.bakingtime.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingtime.Model.BakingStep;
import com.example.android.bakingtime.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecipeStepAdapter extends RecyclerView.Adapter<RecipeStepAdapter.RecipeStepAdapterViewHolder>{

    private List<BakingStep> mSteps;
    //private final RecipeStepAdapterOnClickHandler mClickHandler;

    /*public interface RecipeStepAdapterOnClickHandler {
        void onClick(BakingStep recipeSteps);
    }*/

    /*public RecipeStepAdapter(List<BakingStep> steps, RecipeStepAdapterOnClickHandler clickHandler){
        this.mSteps = steps;
        this.mClickHandler = clickHandler;
    }*/

    public RecipeStepAdapter(List<BakingStep> steps){
        this.mSteps = steps;
    }

    /*public void setAdapter(List<Steps> data){
        this.mSteps = data;
    }*/

    //public class RecipeStepAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public class RecipeStepAdapterViewHolder extends RecyclerView.ViewHolder{
        public final TextView mRecipeSteps;

        public RecipeStepAdapterViewHolder(View view){
            super(view);

            mRecipeSteps = (TextView) view.findViewById(R.id.tv_foodRecipeStep);

            //view.setOnClickListener(this);
        }

       /* @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            BakingStep recipeSteps = mSteps.get(adapterPosition);
            mClickHandler.onClick(recipeSteps);
        }*/
    }

    @NonNull
    @Override
    public RecipeStepAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.recipe_step_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new RecipeStepAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeStepAdapterViewHolder holder, int position) {

        holder.mRecipeSteps.setText(mSteps.get(position).getShortDescription());
    }

    @Override
    public int getItemCount() {
        return mSteps.size();
    }
}
