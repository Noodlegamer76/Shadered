package com.noodlegamer76.shadered.datagen;

import com.noodlegamer76.shadered.ShaderedMod;
import com.noodlegamer76.shadered.block.InitBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, ShaderedMod.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(InitBlocks.SPACE_BLOCK);
        blockWithItem(InitBlocks.STORMY_BLOCK);
        blockWithItem(InitBlocks.OCEAN_BLOCK);
        blockWithItem(InitBlocks.DARKNESS_BLOCK);
        blockWithItem(InitBlocks.END_BLOCK);
        blockWithItem(InitBlocks.END_SKY_BLOCK);
        blockWithItem(InitBlocks.ECLIPSE_BLOCK);

    }

    private void cubeBottomTop(RegistryObject<Block> block, ResourceLocation top, ResourceLocation side, ResourceLocation bottom) {
        axisBlock((RotatedPillarBlock) block.get(),
                models().cubeBottomTop(name(block.get()), side, bottom, top),
                models().cubeBottomTop(name(block.get()) + "_horizontal", side, bottom, top));
    }

    private void cutout(RegistryObject<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(),
                models().cubeAll(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    private void saplingBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(),
                models().cross(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    private void carpetBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(),
                models().carpet(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), blockTexture(blockRegistryObject.get())));
    }

    private String name(Block block) {
        return key(block).getPath();
    }

    private ResourceLocation key(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }
}
