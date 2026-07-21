package top.ykkz000.doublejump;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = DoubleJump.MOD_ID)
public class DoubleJumpConfig implements ConfigData {
    @ConfigEntry.Gui.Excluded
    private static final int MAX_JUMP_TIMES_MIN = 1;
    @ConfigEntry.Gui.Excluded
    private static final int MAX_JUMP_TIMES_MAX = 10;
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = MAX_JUMP_TIMES_MIN , max = MAX_JUMP_TIMES_MAX)
    public int maxJumpTimes = 2;
    @ConfigEntry.Gui.Tooltip
    public double weakenRate = 0.8;

    @Override
    public void validatePostLoad() throws ValidationException {
        ConfigData.super.validatePostLoad();
        if (maxJumpTimes < MAX_JUMP_TIMES_MIN ) {
            maxJumpTimes = MAX_JUMP_TIMES_MIN ;
        }
        if (maxJumpTimes > MAX_JUMP_TIMES_MAX) {
            maxJumpTimes = MAX_JUMP_TIMES_MAX;
        }
        if (weakenRate < 0.0) {
            weakenRate = 0.0;
        }
    }
}
