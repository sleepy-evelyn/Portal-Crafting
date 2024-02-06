package io.github.sleepy_evelyn.api;

import io.github.sleepy_evelyn.api.impl.recipe.CraftingRulesFallback;
import io.github.sleepy_evelyn.api.recipe.CraftingRules;
import io.github.sleepy_evelyn.api.recipe.PortalRecipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PortalCraftingAPI {

    public static final String MOD_ID = "portalcrafting";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    // Rules which all portal crafting recipes have to adhere to
    private static CraftingRules globalCraftingRules = new CraftingRulesFallback();

    /** Convenience method for registering a new portal recipe serializer */
    public static <T extends RecipeSerializer<PortalRecipe>> T registerSerializer(Identifier id, T recipeSerializer) {
        return Registry.register(Registries.RECIPE_SERIALIZER, id, recipeSerializer);
    }

    /** Convenience method for registering a new portal recipe type */
    public static <T extends PortalRecipe> RecipeType<T> registerType(Identifier id) {
        return Registry.register(Registries.RECIPE_TYPE, id, new RecipeType<T>() {
            public String toString() {
                return id.getPath();
            }
        });
    }

    /**
     * Assign a different global crafting rules provider. Used to replace the default provider which simply grabs the
     * rules from the portalcrafting.json config file. Note that only one provider can exist at any time.
     *
     * @param craftingRulesProvider Provider for rules which all portal crafting recipes have to adhere to.
     */
    public static void setGlobalRulesProvider(CraftingRules craftingRulesProvider) {
        if (craftingRulesProvider == null) return;

        LOGGER.info("Global Crafting rules provider has changed from: " + PortalCraftingAPI.globalCraftingRules.id()
                + " to: " + craftingRulesProvider.id());
        PortalCraftingAPI.globalCraftingRules = craftingRulesProvider;
    }

    public static @NotNull CraftingRules globalCraftingRules() {
        return globalCraftingRules;
    }

    public static Identifier id(String path) {
        return new Identifier(PortalCraftingAPI.MOD_ID, path);
    }
}
