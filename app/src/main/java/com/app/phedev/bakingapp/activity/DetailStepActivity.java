package com.app.phedev.bakingapp.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.app.phedev.bakingapp.R;
import com.app.phedev.bakingapp.adapter.StepDetailPagerAdapter;
import com.app.phedev.bakingapp.pojo.Step;

import java.util.List;

import static com.app.phedev.bakingapp.activity.DetailStepFragment.ARG_DESC;
import static com.app.phedev.bakingapp.activity.DetailStepFragment.ARG_ITEM_ID;
import static com.app.phedev.bakingapp.activity.DetailStepFragment.ARG_SHORT_DESC;
import static com.app.phedev.bakingapp.activity.DetailStepFragment.ARG_THUMBNAIL;
import static com.app.phedev.bakingapp.activity.DetailStepFragment.ARG_VIDEO;

public class DetailStepActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private StepDetailPagerAdapter mPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    public static final String ARG_ITEM_lIST = "arg_item";
    private List<Step> stepList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_step);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        //mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), getStepData());
        mPagerAdapter = new StepDetailPagerAdapter(getSupportFragmentManager(), getApplicationContext(), getStepData());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setClipToPadding(false);
        mViewPager.setPageMargin(12);
        mViewPager.setAdapter(mPagerAdapter);
        mPagerAdapter.notifyDataSetChanged();
        setSelectedTab();

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putInt(ARG_ITEM_ID, getIntent().getIntExtra(ARG_ITEM_ID,0));
            arguments.putString(ARG_DESC,
                    getIntent().getStringExtra(ARG_DESC));
            arguments.putString(ARG_SHORT_DESC, getIntent().getStringExtra(ARG_SHORT_DESC));
            arguments.putParcelableArrayList(ARG_ITEM_lIST,
                    getIntent().getParcelableArrayListExtra(ARG_ITEM_lIST));
            arguments.putString(ARG_VIDEO,
                    getIntent().getStringExtra(ARG_VIDEO));
            arguments.putString(ARG_THUMBNAIL,
                    getIntent().getStringExtra(ARG_THUMBNAIL));
            DetailStepFragment fragment = new DetailStepFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }

    }

    public void setSelectedTab (){
        int selectedTab = getIntent().getIntExtra(ARG_ITEM_ID,0);
        mViewPager.setCurrentItem(selectedTab);
    }

    private List<Step> getStepData() {
        return getIntent().getParcelableArrayListExtra(ARG_ITEM_lIST);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
