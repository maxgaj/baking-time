package bakingtime.maxgaj.udacity.com.bakingtime;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import bakingtime.maxgaj.udacity.com.bakingtime.Model.Recipe;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private List<Recipe> recipesList;

    public RecipeAdapter(List<Recipe> recipesList) {
        this.recipesList = recipesList;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_row, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        Recipe recipe = recipesList.get(position);
        holder.title.setText(recipe.getName());
    }

    @Override
    public int getItemCount() {
        return recipesList.size();
    }

    public void swapData(List<Recipe> recipesList){
        this.recipesList =  recipesList;
        notifyDataSetChanged();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public RecipeViewHolder(View view){
            super(view);
            title = (TextView) view.findViewById(R.id.title);
        }
    }

}
