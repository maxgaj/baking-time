package bakingtime.maxgaj.udacity.com.bakingtime;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import bakingtime.maxgaj.udacity.com.bakingtime.Model.Ingredient;
import bakingtime.maxgaj.udacity.com.bakingtime.Model.Recipe;
import bakingtime.maxgaj.udacity.com.bakingtime.network.BakingRetrofitClient;
import bakingtime.maxgaj.udacity.com.bakingtime.network.BakingRetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class BakingTimeWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, Recipe recipe, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_provider);

        String title = recipe.getName() + " (" + Integer.toString(recipe.getServings()) + "p.)";
        views.setTextViewText(R.id.widget_title, title);

        String ingredientString ="";
        for(Ingredient ingredient : recipe.getIngredients()){
            ingredientString += "- "+ingredient.getIngredient() + " : " +ingredient.getQuantity()+" "+ingredient.getMeasure();
            ingredientString += "\n";
        }
        views.setTextViewText(R.id.widget_ingredients, ingredientString);

        Intent activityIntent = new Intent(context, MasterListActivity.class);
        activityIntent.putExtra("Recipe", recipe);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, activityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_see_button, pendingIntent);

        Intent nextIntent = new Intent(context, BakingTimeWidgetService.class);
        nextIntent.setAction(BakingTimeWidgetService.ACTION_NEXT_RECIPE);
        PendingIntent nextPendingIntent = PendingIntent.getService(context, 0, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_next_button, nextPendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        BakingTimeWidgetService.startActionGetRecipe(context);
    }

    public static void updateRecipeWidget(Context context, AppWidgetManager appWidgetManager, Recipe recipe, int[] appWidgetIds){
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, recipe, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

