package com.app.phedev.bakingapp.retrofit;

import com.app.phedev.bakingapp.pojo.Ingredient;
import com.app.phedev.bakingapp.pojo.Result;
import com.app.phedev.bakingapp.pojo.Step;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by phedev in 2017.
 */

public interface Service {
    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<Result>> getResultInfo();

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<Ingredient> getIngredientInfo();

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<Step> getStepInfo();
}
