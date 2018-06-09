package bakingtime.maxgaj.udacity.com.bakingtime.network;

import bakingtime.maxgaj.udacity.com.bakingtime.Model.Recipe;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface BakingRetrofitClient {
    @GET("baking.json")
    Call<List<Recipe>> listRecipe();
}
