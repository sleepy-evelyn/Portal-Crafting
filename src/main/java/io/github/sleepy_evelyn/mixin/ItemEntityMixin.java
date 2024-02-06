package io.github.sleepy_evelyn.mixin;

import io.github.sleepy_evelyn.PortalCrafting;
import io.github.sleepy_evelyn.access.ItemEntityAccess;
import io.github.sleepy_evelyn.api.PortalCraftEvents;
import io.github.sleepy_evelyn.api.recipe.PortalRecipeHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.LinkedHashSet;
import java.util.Set;

import static io.github.sleepy_evelyn.init.PortalCraftingRecipes.END_PORTAL_RECIPE_TYPE;
import static io.github.sleepy_evelyn.init.PortalCraftingRecipes.NETHER_PORTAL_RECIPE_TYPE;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity implements ItemEntityAccess {

    @Unique
    private boolean portalcrafting$inEndPortal = false;

    public ItemEntityMixin(EntityType<?> variant, World world) {
        super(variant, world);
    }

    @Inject(method= "moveToWorld(Lnet/minecraft/server/world/ServerWorld;)Lnet/minecraft/entity/Entity;", at=@At("HEAD"))
    public void moveToWorld(ServerWorld destination, CallbackInfoReturnable<Entity> cir) {
        var itemEntity = (ItemEntity) (Object) this;

        Set<ItemStack> collectedStacks = new LinkedHashSet<>();
        PortalCraftEvents.COLLECT_INGREDIENTS.invoker().collectIngredients(itemEntity);
        int itemContainerLimit = (int) PortalCrafting.CONFIG.getDouble("itemContainerLimit", PortalCrafting.ITEM_CONTAINER_LIMIT_DEFAULT);

        if (ingredientStacks.size() == 1) {
            if (inNetherPortal)
                PortalRecipeHandler.addIngredient(NETHER_PORTAL_RECIPE_TYPE, itemEntity, destination);
            else if (portalcrafting$inEndPortal)
                PortalRecipeHandler.addIngredient(END_PORTAL_RECIPE_TYPE, itemEntity, destination);
        } else if (ingredientStacks.size() > 1 && ingredientStacks.size() <= itemContainerLimit) {
            PortalRecipeHandler.addIngredients(NETHER_PORTAL_RECIPE_TYPE, ingredientStacks, itemEntity, destination);

            /*
            if (inNetherPortal && hasItemIngredient(NETHER_PORTAL_RECIPE_TYPE, ingredientItem))
                PortalRecipeHandler.addIngredient(NETHER_PORTAL_RECIPE_TYPE, itemEntity, destination);
            else if (portalcrafting$inEndPortal && hasItemIngredient(END_PORTAL_RECIPE_TYPE, ingredientItem))
                PortalRecipeHandler.addIngredient(END_PORTAL_RECIPE_TYPE, itemEntity, destination);
                */
        }
    }

    @Unique @Override
    public void portalcrafting$setInEndPortal(boolean inEndPortal) {
        this.portalcrafting$inEndPortal = inEndPortal;
    }
}
