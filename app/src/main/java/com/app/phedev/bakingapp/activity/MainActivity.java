package com.app.phedev.bakingapp.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.phedev.bakingapp.R;
import com.app.phedev.bakingapp.adapter.RecipeAdapter;
import com.app.phedev.bakingapp.pojo.Result;
import com.app.phedev.bakingapp.retrofit.Client;
import com.app.phedev.bakingapp.retrofit.Service;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity{

    private RecipeAdapter recipeAdapter;
    @BindView(R.id.rv_recipe)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_error)
    TextView errorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        new getDataRecipe().execute();
    }

    class getDataRecipe extends AsyncTask<Void,Void,Result>{

    public getDataRecipe(){
    }

    @Override
    protected Result doInBackground(Void... voids) {
        try {
            Client client = new Client();
            Service apiService = Client.createService(Service.class);
            Call<List<Result>> call = apiService.getResultInfo();
            call.enqueue(new Callback<List<Result>>() {
                @Override
                public void onResponse(@NonNull Call<List<Result>> call, @NonNull Response<List<Result>> response) {
                    List<Result> recipeList = response.body();
                    hideError();
                    recipeAdapter = new RecipeAdapter(getApplicationContext(), recipeList);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setAdapter(recipeAdapter);
                    recipeAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(@NonNull Call<List<Result>> call, @NonNull Throwable t) {
                    Log.d("ERROR RETROFIT", t.getMessage());
                    showError();
                }
            });
        }catch (Exception e){
            Log.d("ERROR FETCH:", e.getMessage());
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return null;
    }
    }

    public void showError(){
        errorText.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }

    public void hideError(){
        errorText.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }
}
