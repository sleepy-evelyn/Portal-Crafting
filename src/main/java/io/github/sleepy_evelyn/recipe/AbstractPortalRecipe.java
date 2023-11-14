package io.github.sleepy_evelyn.recipe;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class AbstractPortalRecipe implements Recipe<SimpleInventory> {

    protected static final HashSet<Block> PORTAL_BLOCKS = new HashSet<>();
    private static final int FRAME_BLOCKS_LIMIT = 88; // Big end portal size
    private static final RandomGenerator RANDOM_GEN = RandomGenerator.createThreaded();

    private final RecipeType<?> recipeType;
    private final Identifier id;
    private final DefaultedList<Ingredient> ingredients;
    protected final RecipeContext recipeContext;
    private final ItemStack result;

    public enum Action { MERGE, RETURN, PASS_THROUGH,  }
    public record RecipeContext(Block portalBlock, Block frameBlock, Action action) {}

    public AbstractPortalRecipe(RecipeType<?> recipeType, Identifier id, DefaultedList<Ingredient> ingredients, RecipeContext recipeContext, ItemStack result) {
        this.recipeType = recipeType;
        this.id = id;
        this.ingredients = ingredients;
        this.recipeContext = recipeContext;
        this.result = result;
    }

    @Override
    public boolean matches(SimpleInventory recipeInventory, World world) {
        // Using a hashset since we are not expecting multiple of the same ingredient
        Set<Ingredient> foundIngredients = new HashSet<>();
        ingredients.stream()
                .filter(ingredient -> recipeInventory.stacks.stream().anyMatch(ingredient::test))
                .forEach(foundIngredients::add);

        return foundIngredients.size() == this.ingredients.size();
    }

    public void tryActivate(World currentWorld, @Nullable World destinationWorld, ItemEntity itemEntity, @Nullable List<BlockPos> frameBlocks) {
        if (recipeContext.action() == Action.MERGE)
            mergeIntoFrame(currentWorld, frameBlocks);
        else if(recipeContext.action() == Action.RETURN)
            returnFromPortal(currentWorld, itemEntity);
    }

    protected void mergeIntoFrame(World currentWorld, List<BlockPos> frameBlocks) {
        var resultBlock = Block.getBlockFromItem(result.getItem());
        if (frameBlocks == null || recipeContext.frameBlock == Blocks.AIR || resultBlock == null || frameBlocks.size() > FRAME_BLOCKS_LIMIT)
            return;

        int frameBlocksConverted = 0, attempts = 0;
        while (frameBlocksConverted < result.getCount()) {
            if (attempts > frameBlocks.size() || frameBlocks.isEmpty())
                break;

            var randomFrameBlockPos = frameBlocks.get(RANDOM_GEN.range(0, frameBlocks.size() - 1));
            var frameBlockState = currentWorld.getBlockState(randomFrameBlockPos);
            if (frameBlockState.getBlock().equals(recipeContext.frameBlock)) {
                currentWorld.setBlockState(randomFrameBlockPos, resultBlock.getDefaultState());
                frameBlocksConverted++;
            }
            attempts++;
        }
    }

    protected void returnFromPortal(World currentWorld, ItemEntity itemEntity) {
        var velocity = itemEntity.getVelocity();
        currentWorld.spawnEntity(new ItemEntity(
                currentWorld, itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(), result,
                -velocity.x, -velocity.y, -velocity.z
        ));
        itemEntity.remove(Entity.RemovalReason.DISCARDED);
    }

    @Override
    public ItemStack craft(SimpleInventory inventory, DynamicRegistryManager registryManager) {
        return null;
    }

    @Override
    public boolean fits(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getResult(DynamicRegistryManager registryManager) {
        return result;
    }

    @Override
    public Identifier getId() { return id; }

    @Override
    public RecipeType<?> getType() {
        return recipeType;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() { return ingredients; }

    public static boolean isSupported(Block block) {
        return PORTAL_BLOCKS.contains(block);
    }
}
