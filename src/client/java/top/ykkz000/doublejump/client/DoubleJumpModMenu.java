package top.ykkz000.doublejump.client;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfigClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import top.ykkz000.doublejump.DoubleJumpConfig;

@Environment(EnvType.CLIENT)
public class DoubleJumpModMenu implements ModMenuApi {
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return parent -> AutoConfigClient
				.getConfigScreen(DoubleJumpConfig.class, parent)
				.get();
	}
}
