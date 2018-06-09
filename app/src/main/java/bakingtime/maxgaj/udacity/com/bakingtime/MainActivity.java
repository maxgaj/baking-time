package bakingtime.maxgaj.udacity.com.bakingtime;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;
import bakingtime.maxgaj.udacity.com.bakingtime.Model.Recipe;
import bakingtime.maxgaj.udacity.com.bakingtime.network.BakingRetrofitClient;
import bakingtime.maxgaj.udacity.com.bakingtime.network.BakingRetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Recipe> recipesList = new ArrayList<>();
    private RecyclerView recipeRecyclerView;
    private RecipeAdapter recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recipeRecyclerView = (RecyclerView) findViewById(R.id.recipe_recyclerview);
        recipeAdapter = new RecipeAdapter(recipesList);
        RecyclerView.LayoutManager recipeLayoutManager = new LinearLayoutManager(getApplicationContext());
        recipeRecyclerView.setLayoutManager(recipeLayoutManager);
        recipeRecyclerView.setAdapter(recipeAdapter);

        getRecipeData();


    }

    public void getRecipeData(){
        Retrofit retrofit = BakingRetrofitInstance.getRetrofit();
        BakingRetrofitClient retrofitClient = retrofit.create(BakingRetrofitClient.class);
        Call<List<Recipe>> call = retrofitClient.listRecipe();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                recipesList = response.body();
                recipeAdapter.swapData(recipesList);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}
