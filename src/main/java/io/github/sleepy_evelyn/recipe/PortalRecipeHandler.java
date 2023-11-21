package io.github.sleepy_evelyn.recipe;

import io.github.sleepy_evelyn.api.PortalCraftEvents;
import io.github.sleepy_evelyn.api.exception.PortalCraftException;
import io.github.sleepy_evelyn.api.recipe.PortalRecipe;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BundleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.RecipeType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PortalRecipeHandler {

    public static void initialize() {
        PortalCraftEvents.COLLECT_INGREDIENTS.register(PortalRecipeHandler::collectIngredients);
    }

    static <T extends PortalRecipe> void addIngredients(RecipeType<T> recipeType, DefaultedList<ItemStack> ingredients, ItemEntity , @Nullable World targetWorld) {
        var craftingInventory = new SimpleInventory(ingredients.size());
        var currentWorld = containerEntity.getWorld();



        ingredients.forEach(craftingInventory::addStack);
        if (currentWorld instanceof ServerWorld) {
            currentWorld.getRecipeManager()
                    .getFirstMatch(recipeType, craftingInventory, currentWorld)
                    .ifPresent(match -> match.craft(craftingInventory, containerEntity, targetWorld));
        }
    }

    static <T extends PortalRecipe> void addIngredients(RecipeType<T> recipeType, )

    private static DefaultedList<ItemStack> collectIngredients(ItemEntity itemEntity) {
        var itemStack = itemEntity.getStack();
        var item = itemStack.getItem();

        if (item instanceof BundleItem)
            return collectFromBundle(itemStack);
        else if (item instanceof BlockItem blockItem) {
            if (blockItem.getBlock() instanceof ShulkerBoxBlock)
                return collectFromShulkerBox(itemStack);
        }
        return DefaultedList.of();
    }

    private static DefaultedList<ItemStack> collectFromBundle(ItemStack itemStack) {
        var nbt = itemStack.getNbt();
        if (nbt != null && nbt.contains("Items", 10)) {
            var itemsNbtList = nbt.getList("Items", 10);
            DefaultedList<ItemStack> itemStacks = DefaultedList.ofSize(itemsNbtList.size(), ItemStack.EMPTY);

            itemsNbtList.stream()
                    .filter(element -> element instanceof NbtCompound)
                    .map(NbtCompound.class::cast)
                    .map(ItemStack::fromNbt)
                    .forEach(itemStacks::add);
            return itemStacks;
        }
        return DefaultedList.of();
    }

    private static DefaultedList<ItemStack> collectFromShulkerBox(ItemStack itemStack) {
        var nbt = BlockItem.getBlockEntityNbtFromStack(itemStack);
        if (nbt != null && nbt.contains("Items", 10)) {
            int itemsSize = nbt.getList("Items", 10).size();
            DefaultedList<ItemStack> itemStacks = DefaultedList.ofSize(itemsSize, ItemStack.EMPTY);

            Inventories.readNbt(nbt, itemStacks);
            return itemStacks;
        }
        return DefaultedList.of();
    }
}
