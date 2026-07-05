package com.noodlegamer76.shadered.worldgen.features;

import com.mojang.serialization.Codec;
import com.noodlegamer76.shadered.client.util.SkyblockType;
import com.noodlegamer76.shadered.entity.block.IllusoriteOreBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;

import java.util.BitSet;
import java.util.HashSet;
import java.util.Set;

public class IllusoriteOreFeature extends OreFeature {

    private final Set<BlockPos> placed = new HashSet<>();

    public IllusoriteOreFeature(Codec<OreConfiguration> codec) {
        super(codec);
    }

    @Override
    protected boolean doPlace(
            WorldGenLevel level,
            RandomSource random,
            OreConfiguration config,
            double minX, double maxX,
            double minZ, double maxZ,
            double minY, double maxY,
            int x, int y, int z,
            int width, int height
    ) {
        placed.clear();

        SkyblockType veinType = SkyblockType.values()[random.nextInt(SkyblockType.values().length)];

        int count = 0;
        BitSet bitset = new BitSet(width * height * width);
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

        int size = config.size;
        double[] data = new double[size * 4];

        for (int i = 0; i < size; ++i) {
            float f = (float) i / (float) size;

            double d0 = Mth.lerp(f, minX, maxX);
            double d1 = Mth.lerp(f, minY, maxY);
            double d2 = Mth.lerp(f, minZ, maxZ);

            double d3 = random.nextDouble() * size / 16.0D;
            double d4 = ((Mth.sin((float) Math.PI * f) + 1.0F) * d3 + 1.0D) / 2.0D;

            data[i * 4] = d0;
            data[i * 4 + 1] = d1;
            data[i * 4 + 2] = d2;
            data[i * 4 + 3] = d4;
        }

        for (int a = 0; a < size - 1; ++a) {
            if (data[a * 4 + 3] <= 0.0D) continue;

            for (int b = a + 1; b < size; ++b) {
                if (data[b * 4 + 3] <= 0.0D) continue;

                double dx = data[a * 4] - data[b * 4];
                double dy = data[a * 4 + 1] - data[b * 4 + 1];
                double dz = data[a * 4 + 2] - data[b * 4 + 2];
                double dr = data[a * 4 + 3] - data[b * 4 + 3];

                if (dr * dr > dx * dx + dy * dy + dz * dz) {
                    if (dr > 0.0D) data[b * 4 + 3] = -1.0D;
                    else data[a * 4 + 3] = -1.0D;
                }
            }
        }

        for (int i = 0; i < size; ++i) {
            double r = data[i * 4 + 3];
            if (r < 0.0D) continue;

            double px = data[i * 4];
            double py = data[i * 4 + 1];
            double pz = data[i * 4 + 2];

            int minX2 = Math.max(Mth.floor(px - r), x);
            int minY2 = Math.max(Mth.floor(py - r), y);
            int minZ2 = Math.max(Mth.floor(pz - r), z);

            int maxX2 = Math.max(Mth.floor(px + r), minX2);
            int maxY2 = Math.max(Mth.floor(py + r), minY2);
            int maxZ2 = Math.max(Mth.floor(pz + r), minZ2);

            for (int ix = minX2; ix <= maxX2; ++ix) {
                double dx = ((ix + 0.5D) - px) / r;
                if (dx * dx >= 1.0D) continue;

                for (int iy = minY2; iy <= maxY2; ++iy) {
                    double dy = ((iy + 0.5D) - py) / r;
                    if (dx * dx + dy * dy >= 1.0D) continue;

                    for (int iz = minZ2; iz <= maxZ2; ++iz) {
                        double dz = ((iz + 0.5D) - pz) / r;
                        if (dx * dx + dy * dy + dz * dz >= 1.0D) continue;

                        if (level.isOutsideBuildHeight(iy)) continue;

                        int idx = ix - x + (iy - y) * width + (iz - z) * width * height;
                        if (bitset.get(idx)) continue;

                        bitset.set(idx);
                        pos.set(ix, iy, iz);

                        if (!level.ensureCanWrite(pos)) continue;

                        BlockState state = level.getBlockState(pos);

                        for (OreConfiguration.TargetBlockState target : config.targetStates) {
                            if (canPlaceOre(state, level::getBlockState, random, config, target, pos)) {
                                boolean placedBlock = level.setBlock(pos, target.state, 2);

                                if (placedBlock) {
                                    BlockPos immutable = pos.immutable();

                                    if (level.getBlockEntity(immutable) instanceof IllusoriteOreBlockEntity be) {
                                        be.setBlockType(veinType);
                                    }

                                    placed.add(immutable);
                                    count++;
                                }

                                break;
                            }
                        }
                    }
                }
            }
        }

        return count > 0;
    }
}