package io.github.sleepy_evelyn.api;

import io.github.sleepy_evelyn.api.recipe.SimplePortalRecipe;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;

public class PortalCraftingAPI {

    public static final String MOD_ID = "portalcrafting";
    public static final Object2ObjectMap<Identifier, RecipeType<SimplePortalRecipe>> RECIPE_TYPES_MAP = new Object2ObjectArrayMap<>();


    /**
     * @param recipeTypeId The associated ID for the unique portal recipe type. Takes the following format:
     *                     'portalcrafting:[Mod ID]/[Path]'. For example 'portalcrafting:aether/glowstone_portal'
     * @param portalRecipe The container for our portal
     */
    /*public static void register(Identifier recipeTypeId, PortalRecipe portalRecipe) {
        String recipeTypePath = recipeTypeId.toString().replaceFirst(":", "/");

        var recipeType = Registry.register(
                Registries.RECIPE_TYPE,
                new Identifier(MOD_ID, recipeTypePath),
                new RecipeType<PortalRecipe>() {
            @Override
            public String toString() {
                return recipeTypePath;
            }
        });
    }*/
}
