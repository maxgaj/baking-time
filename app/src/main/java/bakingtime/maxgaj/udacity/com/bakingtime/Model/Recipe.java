package bakingtime.maxgaj.udacity.com.bakingtime.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Recipe implements Parcelable {
    public static final String DUMB_RECIPE_NAME = "Dumb Recipe";
    public static final double DUMB_INGREDIENT_QUANTITY = 2.0;
    public static final String DUMB_INGREDIENT_MEASURE = "Kg";
    public static final String DUMB_INGREDIENT = "Flour";
    public static final String FIRST_DUMB_STEP_DESCRIPTION = "first step";
    public static final String SECOND_DUMB_STEP_DESCRIPTION = "second step";
    public static final String THIRD_DUMB_STEP_DESCRIPTION = "third step";

    private int id;
    private String name;
    private List<Ingredient> ingredients = new ArrayList<>();
    private List<Step> steps = new ArrayList<>();
    private int servings;
    private String image;

    public Recipe(int id, String name, List<Ingredient> ingredients, List<Step> steps, int servings, String image){
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }

    private Recipe(Parcel in){
        this.id = in.readInt();
        this.name = in.readString();
        in.readList(this.ingredients, Ingredient.class.getClassLoader());
        in.readList(this.steps, Step.class.getClassLoader());
        this.servings = in.readInt();
        this.image = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeList(ingredients);
        parcel.writeList(steps);
        parcel.writeInt(servings);
        parcel.writeString(image);
    }

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>(){
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        public Recipe[] newArray(int size){
            return new Recipe[size];
        }
    };

    public static Recipe getDumbRecipe(){
        List<Ingredient> dumbIngredientList = new ArrayList<>();
        dumbIngredientList.add(new Ingredient(DUMB_INGREDIENT_QUANTITY, DUMB_INGREDIENT_MEASURE, DUMB_INGREDIENT));
        List<Step> dumbStepList = new ArrayList<>();
        dumbStepList.add(new Step(0, "first step", FIRST_DUMB_STEP_DESCRIPTION, "", ""));
        dumbStepList.add(new Step(1, "second step", SECOND_DUMB_STEP_DESCRIPTION, "", ""));
        dumbStepList.add(new Step(2, "third step",THIRD_DUMB_STEP_DESCRIPTION, "", ""));
        return new Recipe(0, DUMB_RECIPE_NAME, dumbIngredientList, dumbStepList, 8, "");
    }
}
