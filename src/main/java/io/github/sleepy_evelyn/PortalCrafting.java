package io.github.sleepy_evelyn;

import io.github.sleepy_evelyn.init.PortalCraftingRecipes;
import io.github.sleepy_evelyn.recipe.CollectIngredientsHandler;
import io.github.sleepy_evelyn.recipe.RecipeReloadListener;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;
import net.minecraft.world.GameRules;

public class PortalCrafting implements ModInitializer {

	public static final CraftingRulesProvider CONFIG = new CraftingRulesProvider();

	public static final GameRules.Key<GameRules.BooleanRule> BUNDLES_ENABLED
			= GameRuleRegistry.register("bundlesEnabled", GameRules.Category.MISC, GameRuleFactory.createBooleanRule(true));

	@Override
	public void onInitialize() {
		PortalCraftingRecipes.initialize();
		CollectIngredientsHandler.initialize();

		/*if (CONFIG.getBoolean("bundlesEnabled"))
			ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS_AND_UTILITIES)
					.register((entries) -> entries.addItem(Items.BUNDLE));*/

		ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new RecipeReloadListener());
	}
}