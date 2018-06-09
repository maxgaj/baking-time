package bakingtime.maxgaj.udacity.com.bakingtime;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import bakingtime.maxgaj.udacity.com.bakingtime.Model.Recipe;
import bakingtime.maxgaj.udacity.com.bakingtime.network.BakingRetrofitClient;
import bakingtime.maxgaj.udacity.com.bakingtime.network.BakingRetrofitInstance;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Recipe> recipesList = new ArrayList<>();

    @BindView(R.id.recipe_recyclerview) RecyclerView recipeRecyclerView;
    @BindView(R.id.error_view) View errorView;
    @BindView(R.id.error_button) Button errorButton;
    @BindView(R.id.pb_loading) ProgressBar loadingProgressBar;

    private RecipeAdapter recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        errorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRecipeData();
            }
        });

        recipeAdapter = new RecipeAdapter(this, recipesList);
        RecyclerView.LayoutManager recipeLayoutManager;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            recipeLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
        else
            recipeLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recipeRecyclerView.setLayoutManager(recipeLayoutManager);
        recipeRecyclerView.setAdapter(recipeAdapter);

        getRecipeData();


    }

    public void getRecipeData(){
        displayLoading();
        Retrofit retrofit = BakingRetrofitInstance.getRetrofit();
        BakingRetrofitClient retrofitClient = retrofit.create(BakingRetrofitClient.class);
        Call<List<Recipe>> call = retrofitClient.listRecipe();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                recipesList = response.body();
                recipeAdapter.swapData(recipesList);
                displayContent();
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                displayError();
                t.printStackTrace();
            }
        });
    }

    private void displayContent() {
        recipeRecyclerView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.INVISIBLE);
        loadingProgressBar.setVisibility(View.INVISIBLE);
    }

    private void displayError() {
        recipeRecyclerView.setVisibility(View.INVISIBLE);
        errorView.setVisibility(View.VISIBLE);
        loadingProgressBar.setVisibility(View.INVISIBLE);
    }

    private void displayLoading() {
        recipeRecyclerView.setVisibility(View.INVISIBLE);
        errorView.setVisibility(View.INVISIBLE);
        loadingProgressBar.setVisibility(View.VISIBLE);
    }
}
