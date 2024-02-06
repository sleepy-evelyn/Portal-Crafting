package io.github.sleepy_evelyn.api.impl.recipe;

import io.github.sleepy_evelyn.api.PortalCraftEvents;
import io.github.sleepy_evelyn.api.PortalCraftingAPI;
import io.github.sleepy_evelyn.api.exception.PortalCraftException;
import io.github.sleepy_evelyn.api.recipe.PortalRecipe;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static io.github.sleepy_evelyn.api.recipe.PortalRecipeHandler.AddIngredientResult;

public final class PortalRecipeHandlerImpl {

    private static long lastCraftTicks = 0;

    public static <T extends PortalRecipe> AddIngredientResult addIngredient(RecipeType<T> recipeType, ItemEntity ingredientEntity, @Nullable World targetWorld) {
        return addIngredients(recipeType, new SimpleInventory(ingredientEntity.getStack()), ingredientEntity, targetWorld);
    }

    public static <T extends PortalRecipe> AddIngredientResult addIngredients(RecipeType<T> recipeType, DefaultedList<ItemStack> ingredients, ItemEntity ingredientsHolder, @Nullable World targetWorld) {
        var craftingInventory = new SimpleInventory(ingredients.size());
        ingredients.forEach(craftingInventory::addStack);
        return addIngredients(recipeType, craftingInventory, ingredientsHolder, targetWorld);
    }

    public static <T extends PortalRecipe> AddIngredientResult addIngredients(RecipeType<T> recipeType, SimpleInventory craftingInventory, ItemEntity newIngredientEntity, @Nullable World targetWorld) {
        var globalRules = PortalCraftingAPI.globalCraftingRules();
        String errorPrefix = "Failed to add an ingredient to a Portal Recipe. ";

        if (newIngredientEntity == null || newIngredientEntity.getStack().isEmpty()) {
            PortalCraftingAPI.LOGGER.error(errorPrefix + " The ingredient Item Entity or it's holder does not exist.");
            return AddIngredientResult.GENERIC_FAIL;
        } else if (craftingInventory == null || craftingInventory.isEmpty()) {
            PortalCraftingAPI.LOGGER.error(errorPrefix + "A Crafting Inventory was not provided.");
            return AddIngredientResult.GENERIC_FAIL;
        } else if (!globalRules.automaticCraftingEnabled() && newIngredientEntity.getOwner() == null)
            return AddIngredientResult.NO_OWNER_AUTOMATION_DISABLED;

        // TODO - Tick rate limit



        Set<ItemStack> collectedStacks = new LinkedHashSet<>();
        PortalCraftEvents.COLLECT_STACKS.invoker().collectStacks(newIngredientEntity, collectedStacks);
        if (!globalRules.allowMultiInputCrafting() && collectedStacks.size() > 1)
            return Add

        if (newIngredientEntity == null || newIngredientEntity.getStack().isEmpty())
            PortalCraftingAPI.LOGGER.error
        else if (craftingInventory == null)
            throw new AddIngredientException("A crafting inventory was not provided.", new NullPointerException());
            Set<ItemStack> collectedStacks = new LinkedHashSet<>();
            PortalCraftEvents.COLLECT_STACKS.invoker().collectStacks(newIngredientEntity, collectedStacks);

            if ()

            PortalCraftingAPI.globalCraftingRules().

                    newIngredientEntity.getWorld().getRecipeManager()
                    .getFirstMatch(recipeType, craftingInventory, newIngredientEntity.getWorld())
                    .flatMap(portalRecipe -> Optional.of(portalRecipe)
                            .filter(recipe -> PortalCraftEvents.MATCHES.invoker().matches(recipe, craftingInventory, newIngredientEntity, targetWorld)))
                    .ifPresent(recipe -> {
                        try {
                            recipe.craft(craftingInventory, newIngredientEntity, targetWorld);

                        } catch (PortalCraftException e) {
                            e.printStackTrace();
                        }
                    });
        }
    }

    public static boolean itemMatchesWithoutTag(RecipeType<>)

    public static boolean hasPortalRecipe(RecipeType<PortalRecipe> recipeType, ItemStack itemStack) {
        var itemWithAmount = new ItemWithAmount(itemStack.getItem(), itemStack.getCount());

        if (!ALL_INGREDIENT_ITEMS.contains(itemWithAmount))
            return false;
        else if (!ALL_INGREDIENTS.containsKey(recipeType))
            return false;
        else {
            var ingredients = ALL_INGREDIENTS.get(recipeType);
            if (ingredients == null || ingredients.isEmpty())
                return false;

            ingredients.forEach(ingredient -> {
                if (ingredient.test(itemStack)) {

                }
            });
            return true;
        }
    }

    private record ItemWithAmount(Item item, int amount) {};
}
