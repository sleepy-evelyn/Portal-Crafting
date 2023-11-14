package io.github.sleepy_evelyn.api.event;

import io.github.sleepy_evelyn.api.recipe.PortalRecipe;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public final class PortalCraftEvents {

    public static final Event<IngredientAdded<PortalRecipe>> INGREDIENT_ADDED = EventFactory.createArrayBacked(
            IngredientAdded.class, (callbacks) -> (recipe, inventory, currentWorld, targetWorld, itemEntity) -> {
               for (IngredientAdded<PortalRecipe> callback : callbacks)
                   callback.ingredientAdded(recipe, inventory, currentWorld, targetWorld, itemEntity);
            });

    public static final Event<Crafted<PortalRecipe>> CRAFTED = EventFactory.createArrayBacked(
            Crafted.class, callbacks -> (recipe, world, result) -> {
                for (Crafted<PortalRecipe> callback : callbacks)
                    callback.crafted(recipe, world, result);
            });

    public interface Crafted<T extends PortalRecipe> {
        /**
         * Called after a portal has finished crafting and the result is returned
         *
         * @param portalRecipe The associated recipe
         * @param world World the result is crafted in
         * @param result The item entity produced from the recipes result
         */
        void crafted(T portalRecipe, World world, ItemEntity result);
    }

    public interface IngredientAdded<T extends PortalRecipe> {
        /**
         * Called after an ingredient is added to a portal recipe
         *
         * @param portalRecipe The associated recipe
         * @param inventory Inventory containing all the currently stored crafting ingredients for a given portal
         * @param currentWorld The world crafting occurred in
         * @param targetWorld The world the ingredient will move to after crafting
         * @param ingredientEntity The ingredient added as an entity. Can be used to gather context about
         *
         * @returns
         */
        boolean ingredientAdded(T portalRecipe, SimpleInventory inventory, World currentWorld, @Nullable World targetWorld, ItemEntity ingredientEntity);
    }

    private PortalCraftEvents() {}
}
