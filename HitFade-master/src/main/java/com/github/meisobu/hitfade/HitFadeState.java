package com.github.meisobu.hitfade;

import java.util.Random;

/** Client-side state for the hit particle sequence. */
public final class HitFadeState {
    private static final long RESET_AFTER_NANOS = 3_000_000_000L;
    private static final long CONFIRM_WINDOW_NANOS = 1_500_000_000L;
    private static final Random RANDOM = new Random();

    private static int pendingEntityId = Integer.MIN_VALUE;
    private static long pendingAttackAt;
    private static long lastConfirmedHitAt;
    private static int hitNumber;
    private static float currentChance = 1.0F;

    private HitFadeState() {
    }

    /** Called when the local player sends an attack against an entity. */
    public static void markAttack(int entityId) {
        pendingEntityId = entityId;
        pendingAttackAt = System.nanoTime();
    }

    /** Called only when the server confirms the target's hurt status. */
    public static void confirmHit(int entityId) {
        long now = System.nanoTime();
        if (entityId != pendingEntityId || now - pendingAttackAt > CONFIRM_WINDOW_NANOS) {
            return;
        }

        pendingEntityId = Integer.MIN_VALUE;

        if (lastConfirmedHitAt == 0L || now - lastConfirmedHitAt >= RESET_AFTER_NANOS) {
            hitNumber = 0;
        }

        hitNumber++;
        lastConfirmedHitAt = now;

        if (hitNumber <= 2) {
            currentChance = 1.0F;
        } else if (hitNumber == 3) {
            currentChance = 0.65F;
        } else if (hitNumber == 4) {
            currentChance = 0.30F;
        } else {
            currentChance = 0.0F;
        }
    }

    /** Returns true when this hit particle should be kept. */
    public static boolean shouldKeepHitParticle() {
        long now = System.nanoTime();
        if (lastConfirmedHitAt != 0L && now - lastConfirmedHitAt >= RESET_AFTER_NANOS) {
            hitNumber = 0;
            currentChance = 1.0F;
            lastConfirmedHitAt = 0L;
        }
        return currentChance >= 1.0F || (currentChance > 0.0F && RANDOM.nextFloat() < currentChance);
    }
}
