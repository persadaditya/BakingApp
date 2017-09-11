package com.app.phedev.bakingapp.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.app.phedev.bakingapp.R;
import com.app.phedev.bakingapp.adapter.RecipeAdapter;
import com.app.phedev.bakingapp.pojo.Result;
import com.app.phedev.bakingapp.retrofit.Client;
import com.app.phedev.bakingapp.retrofit.Service;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{

    private RecyclerView mRecyclerView;
    private RecipeAdapter recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView)findViewById(R.id.rv_recipe);

        //initViews();
        new getDataRecipe().execute();
    }


    private void initViews(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(recipeAdapter);
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
                    recipeAdapter = new RecipeAdapter(getApplicationContext(), recipeList);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setAdapter(recipeAdapter);
                    recipeAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(@NonNull Call<List<Result>> call, @NonNull Throwable t) {
                    Log.d("ERROR RETROFIT", t.getMessage());
                }
            });
        }catch (Exception e){
            Log.d("ERROR FETCH:", e.getMessage());
        }
        return null;
    }
    }
}
