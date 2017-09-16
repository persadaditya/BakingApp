package com.app.phedev.bakingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.phedev.bakingapp.R;
import com.app.phedev.bakingapp.pojo.Ingredient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by phedev in 2017.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder>{
    private Context ctx;
    private List<Ingredient> ingredientList;

    public IngredientAdapter(Context context, List<Ingredient> list){
        this.ctx = context;
        this.ingredientList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_list_ingredient,parent,false);
        return new IngredientAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientAdapter.ViewHolder holder, int position) {
        Ingredient ingredient = ingredientList.get(position);
        Double quan = ingredient.getQuantity();

        String quantity = String.valueOf(quan);
        String measure = ingredient.getMeasure();

        holder.tv_ingredient.setText(ingredient.getIngredient());
        holder.tv_quantity.setText(quantity + " " + measure);
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_ingredient)
        TextView tv_ingredient;
        @BindView(R.id.tv_quantity)
        TextView tv_quantity;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
