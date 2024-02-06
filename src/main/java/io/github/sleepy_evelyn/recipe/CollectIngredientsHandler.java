package io.github.sleepy_evelyn.recipe;

import io.github.sleepy_evelyn.api.PortalCraftEvents;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.BundleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

import java.util.LinkedHashSet;
import java.util.Set;

public final class CollectIngredientsHandler {

    public static void initialize() {
        PortalCraftEvents.COLLECT_STACKS.register(CollectIngredientsHandler::collectStacks);
    }

    private static void collectStacks(ItemEntity itemEntity, Set<ItemStack> collectedStacks) {
        var itemStack = itemEntity.getStack();
        var item = itemStack.getItem();

        if (item instanceof BundleItem)
            collectedStacks.addAll(collectFromBundle(itemStack));
    }

    private static Set<ItemStack> collectFromBundle(ItemStack itemStack) {
        var nbt = itemStack.getNbt();
        Set<ItemStack> itemStacks = new LinkedHashSet<>();

        if (nbt != null && nbt.contains("Items", 10)) {
            var itemsNbtList = nbt.getList("Items", 10);

            itemsNbtList.stream()
                    .filter(element -> element instanceof NbtCompound)
                    .map(NbtCompound.class::cast)
                    .map(ItemStack::fromNbt)
                    .forEach(itemStacks::add);
            return itemStacks;
        }
        return itemStacks;
    }

    private CollectIngredientsHandler() {
        throw new AssertionError("Collect Ingredients Handler should not be instantiated.");
    }
}
