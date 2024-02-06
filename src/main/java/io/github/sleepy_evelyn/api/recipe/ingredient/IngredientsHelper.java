package io.github.sleepy_evelyn.api.recipe.ingredient;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;

public final class IngredientsHelper {

    public static DefaultedList<Ingredient> getIngredients(JsonObject json) {
        DefaultedList<Ingredient> ingredients = DefaultedList.of();

        // Check for ingredients either as an array or single input
        if (JsonHelper.hasArray(json, "ingredients")) {
            JsonArray ingredientArray = JsonHelper.getArray(json, "ingredients");
            for (var ingredientObj : ingredientArray) {
                var ingredient = Ingredient.fromJson(ingredientObj, false);
                if (!ingredient.isEmpty())
                    ingredients.add(ingredient);
            }
        } else {
            var ingredient = Ingredient.fromJson(JsonHelper.getObject(json, "ingredient"), false);
            if (!ingredient.isEmpty())
                ingredients.add(ingredient);
        }
        return ingredients;
    }

    private IngredientsHelper() {
        throw new AssertionError("Ingredients Helper should not be instantiated.");
    }

}
