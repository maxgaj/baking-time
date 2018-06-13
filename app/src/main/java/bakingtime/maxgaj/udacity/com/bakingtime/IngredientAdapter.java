package bakingtime.maxgaj.udacity.com.bakingtime;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import bakingtime.maxgaj.udacity.com.bakingtime.Model.Ingredient;
import butterknife.BindView;
import butterknife.ButterKnife;

import java.util.ArrayList;

public class IngredientAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Ingredient> ingredientList;

    @BindView(R.id.item_ingredient_description) TextView ingredientTextView;

    public IngredientAdapter(Context context,  ArrayList<Ingredient> ingredientList){
        this.context = context;
        this.ingredientList = ingredientList;
    }

    @Override
    public int getCount() {
        return ingredientList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Ingredient ingredient = ingredientList.get(i);
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_ingredient, viewGroup, false);
        }
        ButterKnife.bind(this, view);
        String ingredientText = ingredient.getIngredient() + " : " + Double.toString(ingredient.getQuantity()) + " " +ingredient.getMeasure();
        ingredientTextView.setText(ingredientText);
        return view;
    }
}
