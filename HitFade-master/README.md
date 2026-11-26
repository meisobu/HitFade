# HitFade (Minecraft Forge 1.8.9)

Client-side mod that reduces vanilla combat critical particles after consecutive confirmed hits.

## Behavior

The mod counts a hit only after the server sends the target's hurt-status update for an entity the local player recently attacked. It does not count a click or an attack packet by itself.

| Confirmed hit in the sequence | Combat particles kept |
|---|---:|
| 1st | 100% |
| 2nd | 100% |
| 3rd | 65% |
| 4th | 30% |
| 5th and later | 0% |

After 3 seconds without another confirmed hit, the sequence resets to 100%.

## Particle scope

Minecraft 1.8.9's vanilla combat particle IDs are `CRIT` (9) and `CRIT_MAGIC` (10). HitFade filters those two particle types at the particle factory. Other world particles are left unchanged. Normal hits are still counted even when they do not produce a critical-particle burst.

## Build

Requires the Gradle wrapper and Java 17 to run Gradle; the project compiles Minecraft mod classes for Java 8.

```bash
./gradlew build
```

The distributable mod JAR is created in `build/libs/`.

## Install

Copy the built `hitfade-1.0.1.jar` into the `mods` folder of a Minecraft 1.8.9 Forge instance.
