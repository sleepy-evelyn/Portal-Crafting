package io.github.sleepy_evelyn.api.recipe;

import io.github.sleepy_evelyn.api.PortalCraftingAPI;
import io.github.sleepy_evelyn.api.exception.PortalCraftException;
import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public interface PortalRecipe extends Recipe<SimpleInventory> {

    /**
     * Called when a recipe is found and able to be crafted
     *
     * @param craftingInventory Inventory containing the crafting ingredients.
     * @param finalIngredientEntity The entity of last ingredient thrown into the portal before crafting occurred
     * @param targetWorld World the crafting result will be sent to. A null value signifies the result does not
     *      need to move between dimensions.
     */
    void craft(SimpleInventory craftingInventory, ItemEntity finalIngredientEntity, @Nullable World targetWorld) throws PortalCraftException;

    /** Whether upon being crafted successfully the portal should close */
    boolean closesPortal();

    /** The action to perform after crafting has completed. See {@link CraftAction} For the different types of actions possible */
    default CraftAction getCraftAction() {
        return CraftAction.NONE;
    }

    /** Define recipe specific crafting rules. These will be overridden by global crafting rules if they are stricter */
    @Nullable
    default CraftingRules getCraftingRules() {
        return PortalCraftingAPI.globalCraftingRules(); // Defaults to global crafting rules if no recipe specific rules are provided
    }

    // Recipe Boilerplate...
    default boolean fits(int width, int height) {
        return false;
    }

    default boolean isIgnoredInRecipeBook() {
        return true;
    }

    default ItemStack craft(SimpleInventory inventory, DynamicRegistryManager registryManager) {
        return ItemStack.EMPTY;
    }
}
