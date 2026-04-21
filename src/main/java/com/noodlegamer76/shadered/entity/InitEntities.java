package com.noodlegamer76.shadered.entity;

import com.noodlegamer76.shadered.ShaderedMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class InitEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, ShaderedMod.MODID);

    public static final RegistryObject<EntityType<GameObject>> GAME_OBJECT = ENTITY_TYPES.register("game_object",
            () -> EntityType.Builder.of(GameObject::new, MobCategory.MISC).sized(0.5F, 0.5F).build("game_object"));
}
