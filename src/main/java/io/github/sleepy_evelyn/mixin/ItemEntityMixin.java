package io.github.sleepy_evelyn.mixin;

import io.github.sleepy_evelyn.PortalCrafting;
import io.github.sleepy_evelyn.access.ItemEntityAccess;
import io.github.sleepy_evelyn.api.PortalCraftEvents;
import io.github.sleepy_evelyn.api.recipe.PortalRecipeHandler;
import io.github.sleepy_evelyn.api.recipe.SimplePortalRecipe;
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

    @Unique
    private boolean portalcrafting$inEndPortal = false;

    public ItemEntityMixin(EntityType<?> variant, World world) {
        super(variant, world);
    }

    @Shadow public abstract ItemStack getStack();

    @Inject(method= "moveToWorld(Lnet/minecraft/server/world/ServerWorld;)Lnet/minecraft/entity/Entity;", at=@At("HEAD"))
    public void moveToWorld(ServerWorld destination, CallbackInfoReturnable<Entity> cir) {
        var ingredients = PortalCraftEvents.COLLECT_INGREDIENTS.invoker().collectIngredients((ItemEntity) (Object) this);
        int itemContainerLimit = (int) PortalCrafting.CONFIG.getDouble("itemContainerLimit", PortalCrafting.ITEM_CONTAINER_LIMIT_DEFAULT);

        if (ingredients.size() == 1) {
            if (SimplePortalRecipe.hasRecipe(getStack().withCount(1))) {
                PortalRecipeHandler.addIngredient
            }
        } else if (ingredients.size() > 1 && ingredients.size() < itemContainerLimit) { // Cap how many ingredients we search for
            if(ingredients.stream().anyMatch(itemStack -> SimplePortalRecipe.hasRecipe(itemStack.withCount(1)))) {

            }
        }
    }

    @Unique @Override
    public void portalcrafting$setInEndPortal(boolean inEndPortal) {
        this.portalcrafting$inEndPortal = inEndPortal;
    }
}
