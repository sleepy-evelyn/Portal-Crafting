package io.github.sleepy_evelyn.api.recipe;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class RecipeViewHolder {

    private final Block portalBlock;
    @Nullable private final CategoryTexture categoryTexture;
    @Nullable private final ItemStack categoryStack;

    private RecipeViewHolder(Builder builder) {
        this.categoryTexture = builder.categoryTexture;
        this.portalBlock = builder.portalBlock;
        this.categoryStack = builder.categoryStack;
    }

    public static class Builder {
        private final Block portalBlock;
        private CategoryTexture categoryTexture;
        private ItemStack categoryStack;

        public Builder(Block portalBlock) {
            this.portalBlock = portalBlock;
        }

        public Builder categoryTexture(Identifier texture, int u, int v, int width, int height, int regionWidth, int regionHeight, int textureWidth, int textureHeight) {
            categoryTexture = new CategoryTexture(texture, u, v, width, height, regionWidth, regionHeight, textureWidth, textureHeight);
            return this;
        }

        public Builder categoryTexture(Identifier texture, int u, int v, int width, int height, int regionWidth, int regionHeight) {
            return this.categoryTexture(texture, u, v, width, height, regionWidth, regionHeight, 256, 256);
        }

        public Builder categoryStack(ItemStack categoryStack) {
            this.categoryStack = categoryStack;
            return this;
        }

        public RecipeViewHolder build() {
            return new RecipeViewHolder(this);
        }
    }

    private record CategoryTexture(Identifier texture, int u, int v, int width, int height, int regionWidth, int regionHeight, int textureWidth, int textureHeight) {}
}
