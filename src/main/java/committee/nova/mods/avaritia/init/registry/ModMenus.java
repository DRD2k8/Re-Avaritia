package committee.nova.mods.avaritia.init.registry;

import committee.nova.mods.avaritia.client.screen.CompressorScreen;
import committee.nova.mods.avaritia.client.screen.ExtremeCraftingScreen;
import committee.nova.mods.avaritia.client.screen.NeutronCollectorScreen;
import committee.nova.mods.avaritia.common.menu.CompressorMenu;
import committee.nova.mods.avaritia.common.menu.ExtremeCraftingMenu;
import committee.nova.mods.avaritia.common.menu.NeutronCollectorMenu;
import committee.nova.mods.avaritia.util.registry.RegistryUtil;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.IContainerFactory;

public class ModMenus {

    public static MenuType<ExtremeCraftingMenu> extreme_crafting_table = RegistryUtil.menu("extreme_crafting_table", () -> new MenuType<>((IContainerFactory<ExtremeCraftingMenu>) ExtremeCraftingMenu::create, FeatureFlagSet.of())).get();
    public static MenuType<NeutronCollectorMenu> neutron_collector = RegistryUtil.menu("neutron_collector", () -> new MenuType<>((IContainerFactory<NeutronCollectorMenu>) NeutronCollectorMenu::create, FeatureFlagSet.of())).get();
    public static MenuType<CompressorMenu> compressor = RegistryUtil.menu("compressor", () -> new MenuType<>((IContainerFactory<CompressorMenu>) CompressorMenu::create, FeatureFlagSet.of())).get();


    @OnlyIn(Dist.CLIENT)
    public static void onClientSetup() {
        MenuScreens.register(extreme_crafting_table, ExtremeCraftingScreen::new);
        MenuScreens.register(neutron_collector, NeutronCollectorScreen::new);
        MenuScreens.register(compressor, CompressorScreen::new);

    }

}