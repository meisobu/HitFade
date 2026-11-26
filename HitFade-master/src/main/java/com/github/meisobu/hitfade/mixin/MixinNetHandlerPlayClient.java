package com.github.meisobu.hitfade.mixin;

import com.github.meisobu.hitfade.HitFadeState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayClient.class)
public class MixinNetHandlerPlayClient {
    @Inject(method = "handleEntityStatus", at = @At("HEAD"))
    private void hitfade$confirmServerHit(S19PacketEntityStatus packet, CallbackInfo ci) {
        Minecraft minecraft = Minecraft.getMinecraft();
        // Netty's first invocation only schedules the packet on the client thread.
        if (minecraft == null || !minecraft.isCallingFromMinecraftThread() || minecraft.theWorld == null) {
            return;
        }
        if (packet.getOpCode() != 2) {
            return;
        }
        Entity target = packet.getEntity(minecraft.theWorld);
        if (target != null) {
            HitFadeState.confirmHit(target.getEntityId());
        }
    }
}
