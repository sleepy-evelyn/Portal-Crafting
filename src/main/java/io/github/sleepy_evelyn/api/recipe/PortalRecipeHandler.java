package io.github.sleepy_evelyn.api.recipe;

import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public interface PortalRecipeHandler {

    static <T extends PortalRecipe> void addIngredients(RecipeType<T> recipeType, DefaultedList<ItemStack> ingredients, ItemEntity ingredientEntity, @Nullable World targetWorld) {
        var craftingInventory = new SimpleInventory(ingredients.size());
        ingredients.forEach(craftingInventory::addStack);

        if (ingredientEntity.getWorld() != null) {

        }
    }

    static <T extends PortalRecipe> void addIngredient(RecipeType<T> recipeType, SimpleInventory inventory, @Nullable World targetWorld) {
        currentWorld.getRecipeManager()
                .getFirstMatch(recipeType, inventory, currentWorld)
                .ifPresent(match -> match.craft(inventory, targetWorld, ingredientEntity));
    }

    static <T extends SimplePortalRecipe> void addIngredient(RecipeType<T> recipeType, SimpleInventory inventory, World currentWorld, ItemEntity itemEntity) {
        addIngredient(recipeType, inventory, currentWorld, currentWorld, itemEntity);
    }

    static <T extends SimplePortalRecipe> void addIngredient(RecipeType<T> recipeType, World currentWorld, ItemEntity itemEntity) {
        var inventory = new SimpleInventory(itemEntity.getStack());



        addIngredient(recipeType, inventory, currentWorld, itemEntity);
    }
}
