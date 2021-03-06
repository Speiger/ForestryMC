/*******************************************************************************
 * Copyright (c) 2011-2014 SirSengir.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Various Contributors including, but not limited to:
 * SirSengir (original work), CovertJaguar, Player, Binnie, MysteriousAges
 ******************************************************************************/
package forestry.arboriculture;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.arboriculture.IAlleleFruit;
import forestry.api.arboriculture.ITreeGenome;
import forestry.api.arboriculture.TreeManager;
import forestry.api.genetics.IFruitFamily;
import forestry.core.utils.BlockUtil;

public class FruitProviderPod extends FruitProviderNone {

	public enum EnumPodType {
		COCOA, DATES, PAPAYA;//, COCONUT;

		public String getModelName() {
			return toString().toLowerCase(Locale.ENGLISH);
		}
	}

	private final EnumPodType type;
	@Nonnull
	private final Map<ItemStack, Float> drops;

	public FruitProviderPod(String unlocalizedDescription, IFruitFamily family, EnumPodType type, ItemStack... dropOnMature) {
		super(unlocalizedDescription, family);
		this.type = type;
		this.drops = new HashMap<>();
		for (ItemStack drop : dropOnMature) {
			this.drops.put(drop, 1.0f);
		}
	}

	@Override
	public boolean requiresFruitBlocks() {
		return true;
	}

	@Nonnull
	@Override
	public List<ItemStack> getFruits(ITreeGenome genome, World world, BlockPos pos, int ripeningTime) {
		if (drops.isEmpty()) {
			return Collections.emptyList();
		}

		if (ripeningTime >= 2) {
			List<ItemStack> drops = new ArrayList<>();
			for (ItemStack aDrop : this.drops.keySet()) {
				drops.add(aDrop.copy());
			}
			return drops;
		}

		return Collections.emptyList();
	}

	@Override
	public boolean trySpawnFruitBlock(ITreeGenome genome, World world, Random rand, BlockPos pos) {

		if (rand.nextFloat() > genome.getSappiness()) {
			return false;
		}

		if (type == EnumPodType.COCOA) {
			return BlockUtil.tryPlantCocoaPod(world, pos);
		} else {
			IAlleleFruit activeAllele = (IAlleleFruit) genome.getActiveAllele(EnumTreeChromosome.FRUITS);
			return TreeManager.treeRoot.setFruitBlock(world, activeAllele, genome.getSappiness(), pos);
		}
	}
	
	@Override
	public ResourceLocation getSprite(ITreeGenome genome, IBlockAccess world, BlockPos pos, int ripeningTime) {
		return null;
	}

	@Override
	public ResourceLocation getDecorativeSprite() {
		return null;
	}

	@Nonnull
	@Override
	public Map<ItemStack, Float> getProducts() {
		return Collections.unmodifiableMap(drops);
	}

	@Override
	public void registerSprites() {
	}

	@Nonnull
	@Override
	public String getModelName() {
		return type.getModelName();
	}

}
