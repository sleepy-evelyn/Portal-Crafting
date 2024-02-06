package io.github.sleepy_evelyn.recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.sleepy_evelyn.PortalCrafting;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.SynchronousResourceReloader;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

import java.io.IOException;

public class RecipeReloadListener implements IdentifiableResourceReloadListener, SynchronousResourceReloader {

    @Override
    public void reload(ResourceManager manager) {
        var portalRecipes = manager.findResources("recipes/portal", id -> id.getPath().endsWith(".json"));
        portalRecipes.forEach((id, recipeResource) -> {
            try (var recipeReader = recipeResource.openBufferedReader()) {
                var recipeJsonObject = (JsonObject) JsonParser.parseReader(recipeReader);
                var resultObj = JsonHelper.getObject(recipeJsonObject, "result");
                var resultStack = ShapedRecipe.outputFromJson(resultObj);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public Identifier getFabricId() {
        return PortalCrafting.id("data_reload_listener");
    }
}
