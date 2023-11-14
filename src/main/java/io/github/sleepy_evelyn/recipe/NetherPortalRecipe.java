package io.github.sleepy_evelyn.recipe;

import io.github.sleepy_evelyn.api.recipe.CraftAction;
import io.github.sleepy_evelyn.api.recipe.SimplePortalRecipe;
import io.github.sleepy_evelyn.init.PortalCraftingRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;

public class NetherPortalRecipe extends SimplePortalRecipe {

    public NetherPortalRecipe(Identifier id, DefaultedList<Ingredient> ingredients, CraftAction craftAction, boolean closePortal, ItemStack result) {
        super(PortalCraftingRecipes.NETHER_PORTAL_RECIPE_TYPE, id, ingredients, craftAction, closePortal, result);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return PortalCraftingRecipes.NETHER_PORTAL_RECIPE_SERIALIZER;
    }
}
