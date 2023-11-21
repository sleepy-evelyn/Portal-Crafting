package io.github.sleepy_evelyn.init;

import io.github.sleepy_evelyn.PortalCrafting;
import io.github.sleepy_evelyn.api.recipe.PortalRecipeSerializer;
import io.github.sleepy_evelyn.recipe.EndPortalRecipe;
import io.github.sleepy_evelyn.recipe.NetherPortalRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class PortalCraftingRecipes {

    public static final PortalRecipeSerializer<NetherPortalRecipe> NETHER_PORTAL_RECIPE_SERIALIZER
            = registerSerializer("nether", new PortalRecipeSerializer<>(NetherPortalRecipe::new));
    public static final PortalRecipeSerializer<EndPortalRecipe> END_PORTAL_RECIPE_SERIALIZER
            = registerSerializer("end", new PortalRecipeSerializer<>(EndPortalRecipe::new));

    public static final RecipeType<NetherPortalRecipe> NETHER_PORTAL_RECIPE_TYPE = registerType("nether");
    public static final RecipeType<EndPortalRecipe> END_PORTAL_RECIPE_TYPE = registerType("end");

    public static void initialize() {}

    private static <T extends RecipeSerializer<?>> T registerSerializer(String id, T recipeSerializer) {
        return Registry.register(Registries.RECIPE_SERIALIZER, PortalCrafting.id(id), recipeSerializer);
    }

    private static <T extends Recipe<?>> RecipeType<T> registerType(String path) {
        return Registry.register(Registries.RECIPE_TYPE, PortalCrafting.id(path), new RecipeType<T>() {
            public String toString() {
                return path;
            }
        });
    }
}
