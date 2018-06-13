package bakingtime.maxgaj.udacity.com.bakingtime;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import bakingtime.maxgaj.udacity.com.bakingtime.Model.Recipe;
import butterknife.BindView;
import butterknife.ButterKnife;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
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
        @BindView(R.id.recipe_name) TextView nameTextView;
        @BindView(R.id.recipe_thumbnail) ImageView thumbnailImageView;

        public RecipeViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Class destination = MasterListActivity.class;
                    Recipe recipe = recipesList.get(getAdapterPosition());
                    Intent intent = new Intent(context, destination);
                    intent.putExtra("Recipe", recipe);
                    context.startActivity(intent);
                }
            });
        }
    }

}
