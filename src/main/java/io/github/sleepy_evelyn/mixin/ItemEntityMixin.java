package io.github.sleepy_evelyn.mixin;

import io.github.sleepy_evelyn.access.ItemEntityAccess;
import io.github.sleepy_evelyn.api.recipe.SimplePortalRecipe;
import io.github.sleepy_evelyn.api.recipe.PortalRecipeHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity implements ItemEntityAccess {

    private boolean inEndPortal = false;

    public ItemEntityMixin(EntityType<?> variant, World world) {
        super(variant, world);
    }

    @Shadow public abstract ItemStack getStack();

    @Inject(method= "moveToWorld(Lnet/minecraft/server/world/ServerWorld;)Lnet/minecraft/entity/Entity;", at=@At("HEAD"))
    public void moveToWorld(ServerWorld destination, CallbackInfoReturnable<Entity> cir) {
        // Entities item stack exists as an ingredient for a portal recipe. Decide what we want to do with it.
        if (SimplePortalRecipe.hasRecipe(getStack().withCount(1))) {
            if (inNetherPortal) {
                PortalRecipeHandler.addIngredient()
            } else if (inEndPortal) {

            } else {
                // TODO - Custom Portals
            }

        }
    }

    @Unique @Override
    public void portalcrafting$setInEndPortal(boolean inEndPortal) {
        this.inEndPortal = inEndPortal;
    }
}
