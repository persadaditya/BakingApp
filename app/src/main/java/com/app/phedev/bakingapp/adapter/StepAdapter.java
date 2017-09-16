package com.app.phedev.bakingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.phedev.bakingapp.R;
import com.app.phedev.bakingapp.activity.DetailStepActivity;
import com.app.phedev.bakingapp.activity.DetailStepFragment;
import com.app.phedev.bakingapp.pojo.Step;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.app.phedev.bakingapp.activity.DetailStepActivity.ARG_ITEM_lIST;
import static com.app.phedev.bakingapp.activity.DetailStepFragment.ARG_DESC;
import static com.app.phedev.bakingapp.activity.DetailStepFragment.ARG_ITEM_ID;
import static com.app.phedev.bakingapp.activity.DetailStepFragment.ARG_SHORT_DESC;
import static com.app.phedev.bakingapp.activity.DetailStepFragment.ARG_THUMBNAIL;
import static com.app.phedev.bakingapp.activity.DetailStepFragment.ARG_VIDEO;

/**
 * Created by phedev in 2017.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder> {
    private Context ctx;
    private List<Step> stepList;
    private boolean mTwoPane;
    private FragmentManager supportFragmentManager;

    public StepAdapter(Context context, List<Step> steps, boolean twoPane){
        this.ctx = context;
        this.stepList = steps;
        this.mTwoPane = twoPane;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_liststep,parent,false);
        return new StepAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Step step = stepList.get(position);

        String imageUrl = step.getThumbnailURL();

        holder.tv_step.setText(step.getShortDescription());

        Glide.with(ctx)
                .load(imageUrl)
                .error(R.drawable.recipe)
                .placeholder(R.drawable.recipe)
                .into(holder.img_step);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    //arguments.putParcelableArrayList(DetailStepActivity.ARG_ITEM_lIST, (ArrayList<? extends Parcelable>) stepList);
                    arguments.putInt(ARG_ITEM_ID, step.getId());
                    arguments.putString(ARG_DESC, step.getDescription());
                    arguments.putString(ARG_SHORT_DESC, step.getShortDescription());
                    arguments.putString(ARG_VIDEO, step.getVideoURL());
                    arguments.putString(ARG_THUMBNAIL, step.getThumbnailURL());
                    DetailStepFragment fragment = new DetailStepFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.step_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, DetailStepActivity.class);

                    intent.putParcelableArrayListExtra(ARG_ITEM_lIST, (ArrayList<? extends Parcelable>) stepList);
                    intent.putExtra(ARG_ITEM_ID, step.getId());
                    intent.putExtra(ARG_DESC, step.getDescription());
                    intent.putExtra(ARG_SHORT_DESC, step.getShortDescription());
                    intent.putExtra(ARG_VIDEO, step.getVideoURL());
                    intent.putExtra(ARG_THUMBNAIL, step.getThumbnailURL());

                    context.startActivity(intent);

                    Toast.makeText(ctx, "Swipe left/right to next/prev step ", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return stepList.size();
    }

    private FragmentManager getSupportFragmentManager() {
        return supportFragmentManager;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.step_title_card)
        TextView tv_step;
        @BindView(R.id.step_thumbnail_img)
        ImageView img_step;
        final View mView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mView = itemView;
        }

    }
}
