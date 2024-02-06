package io.github.sleepy_evelyn.api;

import io.github.sleepy_evelyn.api.recipe.PortalRecipe;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

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

    public static final Event<CollectStacks> COLLECT_STACKS = EventFactory.createArrayBacked(
            CollectStacks.class, callbacks -> (itemEntity, collectedStacks) -> {
                for (CollectStacks callback : callbacks)
                    callback.collectStacks(itemEntity, collectedStacks);

                if (collectedStacks.isEmpty()) // No item containers where found so use the item entities stack instead
                    collectedStacks.add(itemEntity.getStack());
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
         * @return True if crafting should continue or false if crafting should be abandoned.
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

    public interface CollectStacks {
        /**
         * Called just before ingredients are added to a recipe. Used to collect item stacks from containers such as
         * Bundles or Shulker Boxes that store other items in them. Will return a single stack if no container is found.
         *
         * @param itemEntity The item entity thrown into the portal.
         * @param collectedStacks Collected stacks from the containers prior. Add to this set if any new stacks are found.
         */
        void collectStacks(ItemEntity itemEntity, Set<ItemStack> collectedStacks);
    }

    private PortalCraftEvents() {}
}
