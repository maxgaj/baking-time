package bakingtime.maxgaj.udacity.com.bakingtime;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.util.Log;
import bakingtime.maxgaj.udacity.com.bakingtime.Model.Recipe;
import bakingtime.maxgaj.udacity.com.bakingtime.network.BakingRetrofitClient;
import bakingtime.maxgaj.udacity.com.bakingtime.network.BakingRetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.util.List;

public class BakingTimeWidgetService extends IntentService {
    public static final String ACTION_GET_RECIPE = "bakingtime.maxgaj.udacity.com.bakingtime.action.get_recipe";
    public static final String ACTION_NEXT_RECIPE = "bakingtime.maxgaj.udacity.com.bakingtime.action.next_recipe";

    public BakingTimeWidgetService() { super("BakingTimeWidgetService"); }

    public static void startActionNextRecipe(Context context){
        Intent intent = new Intent(context, BakingTimeWidgetService.class);
        intent.setAction(ACTION_NEXT_RECIPE);
        context.startService(intent);
    }

    public static void startActionGetRecipe(Context context){
        Intent intent = new Intent(context, BakingTimeWidgetService.class);
        intent.setAction(ACTION_GET_RECIPE);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_GET_RECIPE.equals(action)){
                handleActionGetRecipe();
            } else if (ACTION_NEXT_RECIPE.equals(action)){
                handleActionNextRecipe();
            }
        }
    }

    private void handleActionGetRecipe(){
        final Context context = this;
        Retrofit retrofit = BakingRetrofitInstance.getRetrofit();
        BakingRetrofitClient retrofitClient = retrofit.create(BakingRetrofitClient.class);
        Call<List<Recipe>> call = retrofitClient.listRecipe();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                List<Recipe> recipesList = response.body();
                SharedPreferences sharedPreferences = context.getSharedPreferences(getString(R.string.preference_recipe), context.MODE_PRIVATE);
                int recipeId = sharedPreferences.getInt(getString(R.string.preference_recipe_id), 0);
                if (recipeId >= recipesList.size()){
                    recipeId = 0;
                    SharedPreferences.Editor editor =  sharedPreferences.edit();
                    editor.putInt(getString(R.string.preference_recipe_id), recipeId);
                    editor.apply();
                }

                Recipe recipe = recipesList.get(recipeId);

                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, BakingTimeWidgetProvider.class));
                BakingTimeWidgetProvider.updateRecipeWidget(context, appWidgetManager, recipe, appWidgetIds);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {

                t.printStackTrace();
            }
        });
    }

    private void handleActionNextRecipe(){
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_recipe), 0);
        int recipeId = sharedPreferences.getInt(getString(R.string.preference_recipe_id), 0);
        recipeId += 1;
        SharedPreferences.Editor editor =  sharedPreferences.edit();
        editor.putInt(getString(R.string.preference_recipe_id), recipeId);
        editor.apply();
        startActionGetRecipe(this);
    }
}
