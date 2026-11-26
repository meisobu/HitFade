
package com.beast.hitfade;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(
    modid = "hitfade",
    name = "HitFade",
    version = "1.0",
    clientSideOnly = true
)
public class HitFadeMod {

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        System.out.println("[HitFade] Loaded!");
    }
}
