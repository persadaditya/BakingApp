package com.app.phedev.bakingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.app.phedev.bakingapp.R;
import com.app.phedev.bakingapp.adapter.IngredientAdapter;
import com.app.phedev.bakingapp.adapter.StepAdapter;
import com.app.phedev.bakingapp.pojo.Ingredient;
import com.app.phedev.bakingapp.pojo.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * An activity representing a list of Steps. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class StepListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    @BindView(R.id.ingredient_list)
    RecyclerView rv_ingredient;
    @BindView(R.id.step_list)
    RecyclerView rv_step;

    public static final String TITLE_RECIPE = "title";
    public static final String INGREDIENT_DATA = "ingredient";
    public static final String STEP_DATA = "step";
    private boolean mTwoPane;
    private IngredientAdapter adapter_ing;
    private StepAdapter adapter_step;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_list);

        ButterKnife.bind(this);

        String title = getIntent().getStringExtra(TITLE_RECIPE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //toolbar.setTitle(title);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(title);
        }


        if (findViewById(R.id.step_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        LinearLayoutManager linear = new LinearLayoutManager(getApplicationContext());
        adapter_ing = new IngredientAdapter(getApplicationContext(),getDataIngredient());
        rv_ingredient.setLayoutManager(linear);
        rv_ingredient.setAdapter(adapter_ing);
        adapter_ing.notifyDataSetChanged();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        adapter_step = new StepAdapter(getApplicationContext(),getDataStep(), mTwoPane);
        rv_step.setLayoutManager(layoutManager);
        rv_step.setAdapter(adapter_step);
        adapter_step.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.widget, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                navigateUpTo(new Intent(this, MainActivity.class));
                return true;
            case R.id.Settings:
                //not implemented yet
                //when click it add ingredient needs to widget;

                Toast.makeText(this, "Added to Widget", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private List<Ingredient> getDataIngredient(){
        return getIntent().getParcelableArrayListExtra(INGREDIENT_DATA);
    }

    private List<Step> getDataStep(){
        return getIntent().getParcelableArrayListExtra(STEP_DATA);
    }

}
