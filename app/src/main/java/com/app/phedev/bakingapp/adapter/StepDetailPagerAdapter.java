package com.app.phedev.bakingapp.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.app.phedev.bakingapp.activity.DetailStepFragment;
import com.app.phedev.bakingapp.pojo.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phedev in 2017.
 */

public class StepDetailPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private List<Step> steps;

    public StepDetailPagerAdapter(FragmentManager fm, Context ctx, List<Step> list) {
        super(fm);
        this.context = ctx;
        this.steps = new ArrayList<>();
        this.steps = list;
        //this.steps.addAll(steps);
    }

    @Override
    public Fragment getItem(int position) {
        Step step = steps.get(position);
        String desc = step.getDescription();
        String vid = step.getVideoURL();
        String thumbnail = step.getThumbnailURL();
        return DetailStepFragment.newInstance(desc, vid, thumbnail, position);
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public int getCount() {
        return steps.size();
    }
}
