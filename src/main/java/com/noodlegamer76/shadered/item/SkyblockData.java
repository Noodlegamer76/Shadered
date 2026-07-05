package com.noodlegamer76.shadered.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.noodlegamer76.shadered.client.util.SkyblockType;
import com.noodlegamer76.shadered.client.util.skyblock.SkyblockPass;

public record SkyblockData(SkyblockType type, SkyblockPass pass) {
    private static Codec<String> validatedString(final int minSize, final int maxSize) {
        return Codec.STRING.validate(value -> {
            final int length = value.length();
            if (length < minSize) {
                return DataResult.error(() -> "String \"" + value + "\" is too short: " + length + ", expected range [" + minSize + "-" + maxSize + "]");
            }
            if (length > maxSize) {
                return DataResult.error(() -> "String \"" + value + "\" is too long: " + length + ", expected range [" + minSize + "-" + maxSize + "]");
            }
            return DataResult.success(value);
        });
    }

    private static final Codec<SkyblockType> TYPE_CODEC = validatedString(1, 64).xmap(
            name -> {
                try {
                    return SkyblockType.valueOf(name);
                } catch (IllegalArgumentException e) {
                    return SkyblockType.STORMY;
                }
            },
            SkyblockType::name
    );

    private static final Codec<SkyblockPass> PASS_CODEC = validatedString(1, 64).xmap(
            name -> {
                try {
                    return SkyblockPass.valueOf(name);
                } catch (IllegalArgumentException e) {
                    return SkyblockPass.NORMAL;
                }
            },
            SkyblockPass::name
    );

    public static final Codec<SkyblockData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            TYPE_CODEC.fieldOf("blockType").forGetter(SkyblockData::type),
            PASS_CODEC.fieldOf("pass").forGetter(SkyblockData::pass)
    ).apply(instance, SkyblockData::new));
}