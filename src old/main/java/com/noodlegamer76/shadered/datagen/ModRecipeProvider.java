package com.noodlegamer76.shadered.datagen;

import com.noodlegamer76.shadered.item.InitItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        surroundWith8(pWriter, Items.GLOW_INK_SAC, Items.WHITE_WOOL, InitItems.LIGHT_BLOCK.get());
        surroundWith8(pWriter, Items.INK_SAC, Items.BLACK_WOOL, InitItems.DARKNESS_BLOCK.get());
    }

    protected static void surroundWith8(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike middle, ItemLike surround, ItemLike result) {
        ShapedRecipeBuilder
                .shaped(RecipeCategory.BUILDING_BLOCKS, result, 8)
                .define('#', Ingredient.of(surround))
                .define('-', Ingredient.of(middle))
                .pattern("###")
                .pattern("#-#")
                .pattern("###")
                .unlockedBy(getHasName(surround), has(surround))
                .save(pFinishedRecipeConsumer);
    }
}
