package io.github.sleepy_evelyn.api.recipe;

import io.github.sleepy_evelyn.api.exception.PortalCraftException;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public abstract class SimplePortalRecipe implements PortalRecipe {

    private static final ObjectSet<ItemStack> ALL_INGREDIENT_STACKS = new ObjectArraySet<>();

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
    public void craft(SimpleInventory inventory, World targetWorld, @NotNull ItemEntity finalIngredientEntity) throws PortalCraftException {
        if (inventory.isEmpty())
            throw new PortalCraftException(id, "Cannot craft from an empty inventory.");
        else if (targetWorld == null)
            throw new PortalCraftException(id, "Destination world does not exist");

        boolean isResultBlock = !Block.getBlockFromItem(result.getItem()).getDefaultState().isAir();
        switch (getCraftAction()) {
            case MERGE -> {
                if (isResultBlock) merge(targetWorld, finalIngredientEntity);
            }
            case RETURN -> returnFromPortal(targetWorld, finalIngredientEntity);
            case PASS_THROUGH -> passThrough(targetWorld, finalIngredientEntity);
            default -> defaults(targetWorld, finalIngredientEntity);
        }
    }

    protected void returnFromPortal(World targetWorld, ItemEntity itemEntity) {
        var velocity = itemEntity.getVelocity();
        targetWorld.spawnEntity(new ItemEntity(
                targetWorld, itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(),
                getResult(DynamicRegistryManager.EMPTY), -velocity.x, -velocity.y, -velocity.z
        ));
        itemEntity.remove(Entity.RemovalReason.DISCARDED);
    }

    protected void merge(World targetWorld, ItemEntity finalIngredientEntity) {

    }

    protected void passThrough(World targetWorld, ItemEntity itemEntity) {

    }

    protected void defaults(World targetWorld, ItemEntity finalIngredientEntity) {
        returnFromPortal(targetWorld, finalIngredientEntity);
    }

    @Override
    public CraftAction getCraftAction() { return craftAction; };

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

    public static boolean hasRecipe(ItemStack itemStack) {
        return ALL_INGREDIENT_STACKS.contains(itemStack);
    }
}
