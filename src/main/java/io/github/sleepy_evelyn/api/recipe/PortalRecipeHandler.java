package io.github.sleepy_evelyn.api.recipe;

import io.github.sleepy_evelyn.api.impl.recipe.PortalRecipeHandlerImpl;
import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public interface PortalRecipeHandler {

    /**
     * Enum values to allow handling of different scenarios that may occur after an ingredient is added
     */
    enum AddIngredientResult {
        SUCCESS,                        // Ingredient added successfully. All other results will destroy or return the ingredient.
        NO_OWNER_AUTOMATION_DISABLED,   // Ingredient doesn't have an owner and automation is disabled
        EXCEEDS_INVENTORY_LIMIT,        // Input crafting inventory exceeds the inventory limit
        EXCEEDS_INPUT_RATE_LIMIT,       // Exceeded the rate at which ingredients are allowed to be added.
        EXCEEDS_INGREDIENT_LIMIT,       // Added more ingredients than there are recipe inputs.
        GENERIC_FAIL,                   // Adding an ingredient failed but no result is provided.
    }

    /**
     * Add an ingredient for a single input > single output recipe.
     *
     * @param ingredientEntity The ingredient thrown into a portal for crafting.
     * @param targetWorld World the crafting result will be sent to. A null value signifies the result will not
     *      need to move between dimensions.
     **/
    static <T extends PortalRecipe> AddIngredientResult addIngredient(RecipeType<T> recipeType, ItemEntity ingredientEntity, @Nullable World targetWorld) {
        return PortalRecipeHandlerImpl.addIngredient(recipeType, ingredientEntity, targetWorld);
    }

    /**
     * Add ingredients from an item that holds other items such as a bundle.
     *
     * @param ingredients A list of all the ingredients contained within the ingredientsHolder.
     * @param ingredientsHolder The item entity thrown into a portal that holds the other ingredients, such as
     *      a bundle or shulker.
     * @param targetWorld World the crafting result will be sent to. A null value signifies the result will not
     *      need to move between dimensions.
     **/
    static <T extends PortalRecipe> AddIngredientResult addIngredients(RecipeType<T> recipeType, DefaultedList<ItemStack> ingredients, ItemEntity ingredientsHolder, @Nullable World targetWorld) {
        return PortalRecipeHandlerImpl.addIngredients(recipeType, ingredients, ingredientsHolder, targetWorld);
    }

    /**
     * Add ingredients for a multi-input recipe. Used primarily for modded portals that use a block entity
     * to keep track of a recipes current crafting inventory.
     *
     * @param craftingInventory The current crafting inventory.
     * @param newIngredientEntity The new ingredient thrown into a portal.
     * @param targetWorld World the crafting result will be sent to. A null value signifies the result will not
     *      need to move between dimensions.
     */
    static <T extends PortalRecipe> AddIngredientResult addIngredients(RecipeType<T> recipeType, SimpleInventory craftingInventory, ItemEntity newIngredientEntity, @Nullable World targetWorld) {
        return PortalRecipeHandlerImpl.addIngredients(recipeType, craftingInventory, newIngredientEntity, targetWorld);
    }
}
