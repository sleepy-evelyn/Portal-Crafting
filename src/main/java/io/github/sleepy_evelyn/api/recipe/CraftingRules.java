package io.github.sleepy_evelyn.api.recipe;

import net.minecraft.util.Identifier;

/**
 * A set of rules that can be used to limit portal crafting behaviour either universally across all recipes
 * using the 'global' recipe rules or individually as defined by individual recipes. Global crafting rules
 * will override recipe specific rules if they are stricter.
 */
public interface CraftingRules {
    // Unique ID for the provider to distinguish it from the default provider
    Identifier id();

    // Whether portal crafting ingredients have to be thrown by a player or not
    boolean automaticCraftingEnabled();

    // Whether portal recipes can receive multiple different input ingredients for a recipe
    boolean allowMultiInputCrafting();

    // Limits the amount of items an item container can have before it gets rejected as an ingredient
    int craftingInventoryLimit();

    // Limits the amount of individual crafting ingredients a portal recipe can have
    int ingredientsLimit();

    // Limits the rate at which items can be added into a portal crafting recipe in ticks
    int inputRateLimit();
}