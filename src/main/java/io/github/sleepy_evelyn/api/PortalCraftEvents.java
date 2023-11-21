package io.github.sleepy_evelyn.api;

import io.github.sleepy_evelyn.api.recipe.PortalRecipe;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public final class PortalCraftEvents {

    public static final Event<Crafted<PortalRecipe>> CRAFTED = EventFactory.createArrayBacked(
            Crafted.class, callbacks -> (recipe, result, crafter) -> {
                for (Crafted<PortalRecipe> callback : callbacks)
                    callback.crafted(recipe, result, crafter);
            });

    public static final Event<Matches<PortalRecipe>> MATCHES = EventFactory.createArrayBacked(
            Matches.class, callbacks -> (recipe, craftingInventory, finalIngredientEntity, targetWorld) -> {
                for (Matches<PortalRecipe> callback : callbacks)
                    return callback.matches(recipe, craftingInventory, finalIngredientEntity, targetWorld);
                return true;
            });

    public static final Event<Cancelled<PortalRecipe>> CANCELLED = EventFactory.createArrayBacked(
            Cancelled.class, callbacks -> (recipe, craftingInventory, finalIngredientEntity, targetWorld) -> {
                for (Cancelled<PortalRecipe> callback : callbacks)
                    callback.cancelled(recipe, craftingInventory, finalIngredientEntity, targetWorld);
            });

    public static final Event<IngredientAdded<PortalRecipe>> INGREDIENT_ADDED = EventFactory.createArrayBacked(
            IngredientAdded.class, (callbacks) -> (recipe, inventory, world, ingredientEntity) -> {
                for (IngredientAdded<PortalRecipe> callback : callbacks)
                    return callback.ingredientAdded(recipe, inventory, world, ingredientEntity);
                return true;
            });

    public static final Event<CollectIngredients> COLLECT_INGREDIENTS = EventFactory.createArrayBacked(
            CollectIngredients.class, callbacks -> itemEntity -> {
                for (CollectIngredients callback : callbacks)
                    return callback.collectIngredients(itemEntity);
                return DefaultedList.of();
            });

    public interface Crafted<T extends PortalRecipe> {
        /**
         * Called after a portal has finished crafting and the result is returned.
         *
         * @param result The item entity produced from the recipes result. Gives context about the world and the
         *      results item stack, position and velocity.
         * @param crafter Will try to resolve who crafted the recipe. If automation is enabled the crafter may be null.
         */
        void crafted(T portalRecipe, ItemEntity result, @Nullable Entity crafter);
    }

    public interface Matches<T extends PortalRecipe> {
        /**
         * Calls after a recipe has been matched and just before it is about to be crafted.
         *
         * @param craftingInventory Inventory containing the crafting ingredients.
         * @param finalIngredientEntity Entity of the final ingredient added to a recipe. For recipes with multiple
         *      ingredients this may be an item container such as a bundle.
         * @param targetWorld World the crafting result will be sent to if crafting is set to continue. A null value
         *      signifies the result does not need to move between dimensions.
         *
         * @return True if crafting should continue. Otherwise, crafting is cancelled and the ingredients returned.
         */
        boolean matches(T portalRecipe, SimpleInventory craftingInventory, ItemEntity finalIngredientEntity, @Nullable World targetWorld);
    }

    public interface Cancelled<T extends PortalRecipe> {
        /**
         * Define what happens if crafting is cancelled for a given recipe.
         *
         * @param craftingInventory Inventory containing the crafting ingredients.
         * @param finalIngredientEntity Entity of the final ingredient added to a recipe. For recipes with multiple
         *      ingredients this may be an item container such as a bundle.
         * @param targetWorld World the crafting result would have been sent to. A null value signifies the result
         *      did not need to move between dimensions.
         */
        void cancelled(T portalRecipe, SimpleInventory craftingInventory, ItemEntity finalIngredientEntity, @Nullable World targetWorld);
    }

    public interface IngredientAdded<T extends PortalRecipe> {
        /**
         * Called after an ingredient is added to a recipe.
         *
         * @param craftingInventory Inventory containing the crafting ingredients provided already.
         * @param ingredientEntity Entity of the ingredient thrown into the portal. Gives context about the world
         *      and the ingredients item stack, position and velocity.
         *
         * @return True if the ingredient should be accepted into the recipe. Otherwise, the ingredient will be returned.
         */
        boolean ingredientAdded(T portalRecipe, SimpleInventory craftingInventory, World world, ItemEntity ingredientEntity);
    }

    public interface CollectIngredients {
        /**
         * Called after an item is thrown into a nether / end portal or any other portal that uses 'moveToWorld' (QM, Yarn).
         * Used to collect ingredients (items) from containers that store other items. Such as a Bundle or Shulker box.
         *
         * @param itemEntity The item entity thrown into the portal.
         * @return The items (ingredients) extracted from the container. Will be air if no container item is found.
         */
        DefaultedList<ItemStack> collectIngredients(ItemEntity itemEntity);
    }

    private PortalCraftEvents() {}
}
