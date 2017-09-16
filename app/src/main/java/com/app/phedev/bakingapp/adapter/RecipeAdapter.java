package com.app.phedev.bakingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.phedev.bakingapp.R;
import com.app.phedev.bakingapp.activity.StepListActivity;
import com.app.phedev.bakingapp.pojo.Ingredient;
import com.app.phedev.bakingapp.pojo.Result;
import com.app.phedev.bakingapp.pojo.Step;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.app.phedev.bakingapp.activity.StepListActivity.INGREDIENT_DATA;
import static com.app.phedev.bakingapp.activity.StepListActivity.STEP_DATA;

/**
 * Created by phedev in 2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder>{
    private Context context;
    private List<Result> recipeList;

    public RecipeAdapter(Context ctx, List<Result> recipeList){
        this.context = ctx;
        this.recipeList = recipeList;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_recipe,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Integer serve = recipeList.get(position).getServings();
        String serves = String.valueOf(serve);

        holder.titles.setText(recipeList.get(position).getName());
        holder.serving.setText(serves);
        String poster = recipeList.get(position).getImage();


        Glide.with(context)
                .load(poster)
                .placeholder(R.drawable.recipe)
                .error(R.drawable.recipe)
                .into(holder.imgRecipe);
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.recipe_title)
        TextView titles;
        @BindView(R.id.recipe_servings)
        TextView serving;
        @BindView(R.id.recipe_image)
        ImageView imgRecipe;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            final Context context = view.getContext();
            int pos = getAdapterPosition();
            Result recipe = recipeList.get(pos);
            Intent detailAct = new Intent(context, StepListActivity.class);
            detailAct.putExtra(StepListActivity.TITLE_RECIPE, recipe.getName());

            ArrayList<Ingredient> ingredientsData = new ArrayList<>(recipe.getIngredients());
            detailAct.putParcelableArrayListExtra(INGREDIENT_DATA, ingredientsData);

            ArrayList<Step> stepData = new ArrayList<>(recipe.getSteps());
            detailAct.putParcelableArrayListExtra(STEP_DATA, stepData);

            context.startActivity(detailAct);
        }
    }
}
