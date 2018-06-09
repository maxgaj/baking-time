package bakingtime.maxgaj.udacity.com.bakingtime;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import bakingtime.maxgaj.udacity.com.bakingtime.Model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeAdapter extends android.support.v7.widget.RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private List<Recipe> recipesList;
    private Context context;

    public RecipeAdapter(Context context, List<Recipe> recipesList) {
        this.context = context;
        this.recipesList = recipesList;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_card, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        Recipe recipe = recipesList.get(position);
        holder.nameTextView.setText(recipe.getName());

        //TODO custom image
        // Sources
        // Icons made by Freepik (http://www.freepik.com) from www.flaticon.com is licensed by CC 3.0 BY
        String thumbnailPath = recipe.getImage();
        if (thumbnailPath != null && !thumbnailPath.equals("")){
            Picasso.with(context)
                .load(thumbnailPath)
                .placeholder(R.drawable.ic_recipe_default)
                .error(R.drawable.ic_recipe_default)
                .into(holder.thumbnailImageView);
        }
        else
            holder.thumbnailImageView.setImageResource(R.drawable.ic_recipe_default);
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
        public TextView nameTextView;
        public ImageView thumbnailImageView;

        public RecipeViewHolder(View view){
            super(view);
            nameTextView = (TextView) view.findViewById(R.id.recipe_name);
            thumbnailImageView = (ImageView) view.findViewById(R.id.recipe_thumbnail);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO Intent
                    Toast.makeText(context, recipesList.get(getAdapterPosition()).getName(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
