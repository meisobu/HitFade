package com.github.meisobu.hitfade.mixin;

import com.github.meisobu.hitfade.HitFadeState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerControllerMP.class)
public class MixinPlayerControllerMP {
    @Inject(method = "attackEntity", at = @At("HEAD"))
    private void hitfade$rememberTarget(EntityPlayer player, Entity target, CallbackInfo ci) {
        if (player != null && player == Minecraft.getMinecraft().thePlayer && target != null) {
            HitFadeState.markAttack(target.getEntityId());
        }
    }
}
