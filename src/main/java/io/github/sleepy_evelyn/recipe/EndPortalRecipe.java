package io.github.sleepy_evelyn.recipe;

import io.github.sleepy_evelyn.api.exception.PortalCraftException;
import io.github.sleepy_evelyn.api.recipe.CraftAction;
import io.github.sleepy_evelyn.api.recipe.SimplePortalRecipe;
import io.github.sleepy_evelyn.init.PortalCraftingRecipes;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.minecraft.block.Block;
import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EndPortalRecipe extends SimplePortalRecipe {

    private static final ObjectSet<Ingredient> ALL_INGREDIENTS = new ObjectArraySet<>();

    public EndPortalRecipe(Identifier id, DefaultedList<Ingredient> ingredients, CraftAction craftAction, boolean closePortal, ItemStack result) {
        super(PortalCraftingRecipes.END_PORTAL_RECIPE_TYPE, id, ingredients, craftAction, closePortal, result);
        ALL_INGREDIENTS.addAll(ingredients);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return PortalCraftingRecipes.END_PORTAL_RECIPE_SERIALIZER;
    }

    @Override
    public void craft(SimpleInventory craftingInventory, ItemEntity finalIngredientEntity, @Nullable World targetWorld) {

    }

    @Override
    protected void returnFromPortal(ItemEntity itemEntity, @Nullable World targetWorld) throws PortalCraftException {

    }

    @Override
    protected void merge(ItemEntity ingredientsHolder, Block blockResult, @Nullable World targetWorld) throws PortalCraftException {

    }

    @Override
    protected void passThrough(ItemEntity ingredientsHolder, @NotNull World targetWorld) throws PortalCraftException {

    }

}
