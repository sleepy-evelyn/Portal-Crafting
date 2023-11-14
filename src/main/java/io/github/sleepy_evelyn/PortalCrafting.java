package io.github.sleepy_evelyn;

import io.github.sleepy_evelyn.api.PortalCraftingAPI;
import io.github.sleepy_evelyn.init.PortalCraftingRecipes;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PortalCrafting implements ModInitializer {

	public static final Logger LOGGER = LoggerFactory.getLogger(PortalCraftingAPI.MOD_ID);
	public static final PortalCraftingConfig CONFIG = new PortalCraftingConfig();

	@Override
	public void onInitialize() {
		PortalCraftingRecipes.initialize();

		if (CONFIG.getBoolean("bundlesEnabled"))
			ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS_AND_UTILITIES)
					.register((entries) -> entries.addItem(Items.BUNDLE));
	}

	public static Identifier id(String path) {
		return new Identifier(PortalCraftingAPI.MOD_ID, path);
	}
}