package com.github.meisobu.hitfade.mixin;

import com.github.meisobu.hitfade.HitFadeState;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFX;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EffectRenderer.class)
public class MixinEffectRenderer {
    /**
     * In 1.8.9, vanilla's two combat particle types are CRIT (9) and CRIT_MAGIC (10).
     * Filtering at the factory keeps unrelated world particles untouched.
     */
    @Inject(method = "spawnEffectParticle", at = @At("HEAD"), cancellable = true)
    private void hitfade$filterCombatParticles(int particleId,
                                                double x, double y, double z,
                                                double xSpeed, double ySpeed, double zSpeed,
                                                int[] arguments,
                                                CallbackInfoReturnable<EntityFX> cir) {
        if ((particleId == 9 || particleId == 10) && !HitFadeState.shouldKeepHitParticle()) {
            cir.setReturnValue(null);
        }
    }
}
