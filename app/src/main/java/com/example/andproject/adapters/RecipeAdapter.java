package com.example.andproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.andproject.R;
import com.example.andproject.models.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecyclerViewHolder> {

    private List<Recipe> mRecipes;
    private LayoutInflater layoutInflater;

    public RecipeAdapter(List<Recipe> mRecipes) {
        this.mRecipes = mRecipes;
    }


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_textview, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        Recipe recp = mRecipes.get(position);
        holder.recipeText.setText(recp.getRecipe());

    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView recipeText;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeText = itemView.findViewById(R.id.recipeText);

        }

    }

}
