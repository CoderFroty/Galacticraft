/*
 * Copyright (c) 2019-2025 Team Galacticraft
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.galacticraft.mod.data.tag;

import dev.galacticraft.api.gas.Gases;
import dev.galacticraft.mod.content.GCFluids;
import dev.galacticraft.mod.tag.GCFluidTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.FluidTags;

import java.util.concurrent.CompletableFuture;

public class GCFluidTagProvider extends FabricTagProvider.FluidTagProvider {
    public GCFluidTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        this.getOrCreateTagBuilder(GCFluidTags.OIL)
                .add(GCFluids.CRUDE_OIL)
                .add(GCFluids.FLOWING_CRUDE_OIL);
        this.getOrCreateTagBuilder(GCFluidTags.OIL_COMMON)
                .addTag(GCFluidTags.OIL);

        this.getOrCreateTagBuilder(GCFluidTags.FUEL)
                .add(GCFluids.FUEL)
                .add(GCFluids.FLOWING_FUEL);
        this.getOrCreateTagBuilder(GCFluidTags.FUEL_COMMON)
                .addTag(GCFluidTags.FUEL);

        this.getOrCreateTagBuilder(GCFluidTags.SULFURIC_ACID)
                .add(GCFluids.SULFURIC_ACID)
                .add(GCFluids.FLOWING_SULFURIC_ACID);
        this.getOrCreateTagBuilder(GCFluidTags.SULFURIC_ACID_COMMON)
                .addTag(GCFluidTags.SULFURIC_ACID);

        this.getOrCreateTagBuilder(GCFluidTags.LIQUID_OXYGEN)
                .add(GCFluids.LIQUID_OXYGEN);
        this.getOrCreateTagBuilder(GCFluidTags.OXYGEN)
                .add(Gases.OXYGEN);

        this.getOrCreateTagBuilder(GCFluidTags.NON_BREATHABLE)
                .addTag(GCFluidTags.OIL)
                .addTag(GCFluidTags.FUEL)
                .addTag(GCFluidTags.SULFURIC_ACID)
                .addOptionalTag(FluidTags.WATER);
    }
}
