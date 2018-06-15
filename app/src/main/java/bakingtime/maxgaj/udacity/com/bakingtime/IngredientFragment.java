package bakingtime.maxgaj.udacity.com.bakingtime;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import bakingtime.maxgaj.udacity.com.bakingtime.Model.Ingredient;
import butterknife.BindView;
import butterknife.ButterKnife;

import java.util.ArrayList;

public class IngredientFragment extends Fragment {
    private static final String INGREDIENTS_LIST = "ingredients_list";

    private ArrayList<Ingredient> ingredientlist;

    @BindView(R.id.ingredient_listview) ListView ingredientListView;

    public IngredientFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null){
            this.ingredientlist = savedInstanceState.getParcelableArrayList(INGREDIENTS_LIST);
        }

        View rootView = inflater.inflate(R.layout.fragment_ingredient, container, false);
        ButterKnife.bind(this, rootView);

        IngredientAdapter adapter = new IngredientAdapter(getContext(), ingredientlist);
        ingredientListView.setAdapter(adapter);

        return rootView;
    }

    public void setIngredientlist(ArrayList<Ingredient> ingredientlist) {
        this.ingredientlist = ingredientlist;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(INGREDIENTS_LIST, ingredientlist);
    }
}
