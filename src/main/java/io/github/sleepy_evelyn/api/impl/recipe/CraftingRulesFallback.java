package io.github.sleepy_evelyn.api.impl.recipe;

import io.github.sleepy_evelyn.api.PortalCraftingAPI;
import io.github.sleepy_evelyn.api.recipe.CraftingRules;
import net.minecraft.util.Identifier;

public final class CraftingRulesFallback implements CraftingRules {

    @Override
    public Identifier id() {
        return PortalCraftingAPI.id("fallback_rules_provider");
    }

    @Override
    public boolean automaticCraftingEnabled() {
        return true;
    }

    @Override
    public boolean allowMultiInputCrafting() {
        return true;
    }

    @Override
    public int craftingInventoryLimit() {
        return 6;
    }

    @Override
    public int ingredientsLimit() {
        return 27;
    }

    @Override
    public int inputRateLimit() {
        return 4;
    }
}
