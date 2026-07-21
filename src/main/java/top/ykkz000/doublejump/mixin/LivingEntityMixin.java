package top.ykkz000.doublejump.mixin;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.ykkz000.doublejump.DoubleJumpConfig;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Shadow
    protected abstract float getJumpPower(float multiplier);

    @Unique
    private int jumpingTimes = 0;

    @Redirect(method = "aiStep()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;onGround()Z", ordinal = 2))
    protected boolean disableOnGroundCheck(LivingEntity instance) {
        return instance instanceof Player || instance.onGround();
    }

    @Inject(method = "jumpFromGround()V", at = @At("HEAD"), cancellable = true)
    protected void checkJumpFromGround(CallbackInfo ci) {
        DoubleJumpConfig config = AutoConfig.getConfigHolder(DoubleJumpConfig.class).getConfig();
        if (((LivingEntity) (Object) this).onGround()) {
            jumpingTimes = 0;
        }
        if (jumpingTimes >= config.maxJumpTimes) {
            ci.cancel();
        }
        jumpingTimes++;
    }

    @Redirect(method = "getJumpPower()F", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getJumpPower(F)F"))
    protected float weakJump(LivingEntity instance, float multiplier) {
        DoubleJumpConfig config = AutoConfig.getConfigHolder(DoubleJumpConfig.class).getConfig();
        return getJumpPower((float) (multiplier * Math.pow(config.weakenRate, jumpingTimes - 1)));
    }
}
