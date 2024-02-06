package io.github.sleepy_evelyn.mixin;

import io.github.sleepy_evelyn.recipe.AbstractPortalRecipe;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractBlock.class)
public class AbstractBlockMixin {

    @Inject(method = "onEntityCollision", at=@At("HEAD"))
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity, CallbackInfo ci) {
        var block = state.getBlock();

        /*
        if (AbstractPortalRecipe.isSupported(block)) {
            if (entity instanceof ItemEntity itemEntity) {
                var itemStack = itemEntity.getStack();
                var inventory = new SimpleInventory(itemStack);

                if (block == Blocks.NETHER_PORTAL)
                    territorial$tryActivateRecipe(PortalCraftingRecipes.NETHER_PORTAL_RECIPE_TYPE, inventory, world, itemEntity);
                else if (block == Blocks.END_PORTAL)
                    territorial$tryActivateRecipe(PortalCraftingRecipes.END_PORTAL_RECIPE_TYPE, inventory, world, itemEntity);
                else
                    territorial$tryActivateRecipe(PortalCraftingRecipes.CUSTOM_PORTAL_RECIPE_TYPE, inventory, world, itemEntity);

            }
        }
        ((ItemEntity) entity).setOwner()
        */
    }

    @Unique
    private <T extends AbstractPortalRecipe> void territorial$tryActivateRecipe(RecipeType<T> recipeType, SimpleInventory inventory, World world, ItemEntity itemEntity) {
        world.getRecipeManager()
                .getFirstMatch(recipeType, inventory, world)
                .ifPresent(match -> match.tryActivate(world, null, itemEntity, null));
    }
}
