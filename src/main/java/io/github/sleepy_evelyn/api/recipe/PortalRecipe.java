package io.github.sleepy_evelyn.api.recipe;

import io.github.sleepy_evelyn.api.exception.PortalCraftException;
import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.world.World;

public interface PortalRecipe extends Recipe<SimpleInventory> {

    /**
     * Called when a recipe is found and able to be crafted
     *
     * @param inventory The attached inventory for the recipe
     * @param targetWorld The world the resultant item stack should travel to. Can be the same world
     * @param finalIngredientEntity The entity of last ingredient thrown into the portal before crafting occurred
     */
    void craft(SimpleInventory inventory, World targetWorld, ItemEntity finalIngredientEntity) throws PortalCraftException;

    /** The action to perform after crafting has completed. See {@link CraftAction} For the different types of actions possible */
    default CraftAction getCraftAction() { return CraftAction.NONE; }

    /** Whether upon being crafted successfully the portal should close */
    boolean closesPortal();

    // Recipe Boilerplate
    default boolean fits(int width, int height) { return false; }

    default boolean isIgnoredInRecipeBook() { return true; }

    default ItemStack craft(SimpleInventory inventory, DynamicRegistryManager registryManager) { return ItemStack.EMPTY; }
}
