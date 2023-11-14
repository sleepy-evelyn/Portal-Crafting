package io.github.sleepy_evelyn.api.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;

import java.util.Locale;

public class SimplePortalRecipeSerializer<T extends SimplePortalRecipe> implements RecipeSerializer<T> {

    private final RecipeFactory<T> recipeFactory;

    public SimplePortalRecipeSerializer(RecipeFactory<T> recipeFactory) {
        this.recipeFactory = recipeFactory;
    }

    @Override
    public T read(Identifier id, JsonObject json) {
        DefaultedList<Ingredient> ingredients = getIngredients(json);
        if (ingredients.isEmpty())
            throw new JsonParseException("Recipe " + id + " failed to load. No Ingredients where provided");

        String craftActionString = JsonHelper.getString(json,"action", "return");
        var craftAction = Enum.valueOf(CraftAction.class, craftActionString.toUpperCase(Locale.ENGLISH));
        var resultObj = JsonHelper.getObject(json, "result"); // Result
        var resultStack = ShapedRecipe.outputFromJson(resultObj);
        boolean closePortal = JsonHelper.getBoolean(json, "close", false); // Whether to close the portal or not

        return recipeFactory.create(id, ingredients, craftAction, closePortal, resultStack);
    }

    private DefaultedList<Ingredient> getIngredients(JsonObject json) {
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

    @Override
    public T read(Identifier id, PacketByteBuf buf) {
        int numIngredients = buf.readVarInt();
        var ingredients = DefaultedList.ofSize(numIngredients, Ingredient.EMPTY);
        ingredients.replaceAll(ignored -> Ingredient.fromPacket(buf));

        var craftAction = buf.readEnumConstant(CraftAction.class);
        var resultStack = buf.readItemStack();
        boolean closePortal = buf.readBoolean();
        return recipeFactory.create(id, ingredients, craftAction, closePortal, resultStack);
    }

    @Override
    public void write(PacketByteBuf buf, T recipe) {
        for (var ingredient : recipe.getIngredients())
            ingredient.write(buf);
        buf.writeVarInt(recipe.getIngredients().size());
        buf.writeEnumConstant(recipe.getCraftAction());
        buf.writeItemStack(recipe.getResult(DynamicRegistryManager.EMPTY));
        buf.writeBoolean(recipe.closesPortal());
    }

    public interface RecipeFactory<T extends SimplePortalRecipe> {
        T create(Identifier id, DefaultedList<Ingredient> ingredients, CraftAction craftAction, boolean closePortal, ItemStack result);
    }
}
