package io.github.sleepy_evelyn;

import io.github.sleepy_evelyn.api.PortalCraftingAPI;
import io.github.sleepy_evelyn.api.recipe.CraftingRules;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import net.minecraft.util.Identifier;

@Config.Gui.Background("minecraft:textures/block/nether_portal.png")
@Config(name = PortalCraftingAPI.MOD_ID)
public class CraftingRulesProvider implements ConfigData, CraftingRules {

    @Comment("Whether portal crafting ingredients have to be thrown by a player or not")
    boolean automaticCraftingEnabled = true;

    @Comment("Allow portal recipes to receive multiple inputs")
    boolean allowMultiInputCrafting = true;

    @Comment("Limits the amount of ingredients a portal recipe can have")
    @ConfigEntry.BoundedDiscrete(min = 1, max = 6)
    int ingredientsLimit = 6; // Any larger and EMI / REI entries get borked

    @Comment("""
        Limits the amount of items an item container can have before it gets rejected as an ingredient
        Useful for items such as Bundles which can carry other items
        """)
    @ConfigEntry.BoundedDiscrete(min = 1, max = 216)
    int craftingInventoryLimit = 27; // Size of a double chest

    @Comment("Limits the rate at which items can be added into a portal crafting recipe in ticks")
    @ConfigEntry.BoundedDiscrete(min = 4, max = 1200)
    int inputRateLimit = 4; // Default = 2 redstone ticks. Max = 1 minute

    @Override
    public Identifier id() {
        return PortalCraftingAPI.id("autoconfig_rules_provider");
    }

    @Override
    public boolean automaticCraftingEnabled() {
        return automaticCraftingEnabled;
    }

    @Override
    public boolean allowMultiInputCrafting() {
        return allowMultiInputCrafting;
    }

    @Override
    public int craftingInventoryLimit() {
        return craftingInventoryLimit;
    }

    @Override
    public int ingredientsLimit() {
        return ingredientsLimit;
    }

    @Override
    public int inputRateLimit() {
        return 0;
    }

    @Override
    public void validatePostLoad() throws ValidationException {
        ConfigData.super.validatePostLoad();
        PortalCraftingAPI.setGlobalRulesProvider(this);
    }
}
