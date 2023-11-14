package io.github.sleepy_evelyn.api.recipe;

import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.recipe.RecipeType;
import net.minecraft.world.World;

public interface PortalRecipeHandler {

    static <T extends SimplePortalRecipe> void addIngredient(RecipeType<T> recipeType, SimpleInventory inventory, World currentWorld, World targetWorld, ItemEntity itemEntity) {
        currentWorld.getRecipeManager()
                .getFirstMatch(recipeType, inventory, currentWorld)
                .ifPresent(match -> match.craft(inventory, targetWorld, itemEntity));
    }

    static <T extends SimplePortalRecipe> void addIngredient(RecipeType<T> recipeType, SimpleInventory inventory, World currentWorld, ItemEntity itemEntity) {
        addIngredient(recipeType, inventory, currentWorld, currentWorld, itemEntity);
    }

    static <T extends SimplePortalRecipe> void addIngredient(RecipeType<T> recipeType, World currentWorld, ItemEntity itemEntity) {
        var inventory = new SimpleInventory(itemEntity.getStack());
        addIngredient(recipeType, inventory, currentWorld, itemEntity);
    }
}
