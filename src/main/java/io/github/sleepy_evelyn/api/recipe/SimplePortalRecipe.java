package io.github.sleepy_evelyn.api.recipe;

import io.github.sleepy_evelyn.api.exception.PortalCraftException;
import net.minecraft.block.Block;
import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A basic portal recipe.
 */
public abstract class SimplePortalRecipe implements PortalRecipe {

    private static final Map<RecipeType<?>, Set<Item>> ALL_INGREDIENT_ITEMS = new HashMap<>();

    private final RecipeType<?> recipeType;
    private final Identifier id;
    private final DefaultedList<Ingredient> ingredients;
    private final CraftAction craftAction;
    private final boolean closePortal;
    private final ItemStack result;

    public SimplePortalRecipe(RecipeType<?> recipeType, Identifier id, DefaultedList<Ingredient> ingredients, CraftAction craftAction, boolean closePortal, ItemStack result) {
        this.recipeType = recipeType;
        this.id = id;
        this.ingredients = ingredients;
        this.craftAction = craftAction;
        this.closePortal = closePortal;
        this.result = result;

        // Add to the global list of
        ALL_INGREDIENT_ITEMS.computeIfAbsent(recipeType, k -> new HashSet<>())
                .addAll(ingredients.stream()
                .flatMap(ingredient -> Arrays.stream(ingredient.getMatchingStacks()))
                .map(ItemStack::getItem)
                .collect(Collectors.toSet()));
    }

    @Override
    public boolean matches(SimpleInventory recipeInventory, World world) {
        var recipeMatcher = new RecipeMatcher();
        long itemCount = recipeInventory.stacks.stream()
                .filter(itemStack -> !itemStack.isEmpty())
                .peek(itemStack -> recipeMatcher.addInput(itemStack, 1))
                .count();
        return itemCount == ingredients.size() && recipeMatcher.match(this, null);
    }

    @Override
    public void craft(SimpleInventory craftingInventory, ItemEntity ingredientsHolder, @Nullable World targetWorld) throws PortalCraftException {
        if (craftingInventory.isEmpty())
            throw new PortalCraftException(id, "Cannot craft from an empty inventory.");

        switch (getCraftAction()) {
            case MERGE -> {
                Block blockResult = Block.getBlockFromItem(result.getItem());
                if (blockResult.getDefaultState().isAir())
                    throw new PortalCraftException(id, "Failed to merge the result into the portals frame. The result is not a block item.");
                else
                    merge(ingredientsHolder, blockResult, targetWorld);
            }
            case RETURN -> returnFromPortal(ingredientsHolder, targetWorld);
            case PASS_THROUGH -> {
                if (targetWorld != null)
                    passThrough(ingredientsHolder, targetWorld);
            }
        }
    }

    abstract protected void returnFromPortal(ItemEntity itemEntity, @Nullable World targetWorld) throws PortalCraftException;

    abstract protected void merge(ItemEntity ingredientsHolder, Block blockResult, @Nullable World targetWorld) throws PortalCraftException;

    abstract protected void passThrough(ItemEntity ingredientsHolder, @NotNull World targetWorld) throws PortalCraftException;

    @Override
    public CraftAction getCraftAction() { return craftAction; }

    @Override
    public boolean closesPortal() { return closePortal; }

    @Override
    public DefaultedList<Ingredient> getIngredients() { return ingredients; }

    @Override
    public ItemStack getResult(DynamicRegistryManager registryManager) { return result; }

    @Override
    public RecipeType<?> getType() { return recipeType; }

    @Override
    public Identifier getId() { return id; }

    public static boolean hasItemIngredient(RecipeType<?> recipeType, Item itemIngredient) {
        if (ALL_INGREDIENT_ITEMS.containsKey(recipeType)) {
            Set<Item> allRecipeItems = ALL_INGREDIENT_ITEMS.get(recipeType);
            if (allRecipeItems != null && !allRecipeItems.isEmpty())
                return allRecipeItems.contains(itemIngredient);
        }
        return false;
    }
}