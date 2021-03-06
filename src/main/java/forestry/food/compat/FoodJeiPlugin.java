package forestry.food.compat;

import javax.annotation.Nonnull;

import forestry.core.utils.JeiUtil;
import forestry.food.PluginFood;
import forestry.food.items.ItemRegistryFood;
import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;

@JEIPlugin
public class FoodJeiPlugin extends BlankModPlugin {
	@Override
	public void register(@Nonnull IModRegistry registry) {
		ItemRegistryFood items = PluginFood.items;
		JeiUtil.addDescription(registry,
				items.infuser
		);
	}
}
