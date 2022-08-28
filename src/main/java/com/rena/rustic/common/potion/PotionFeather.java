package com.rena.rustic.common.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class PotionFeather extends MobEffect {

    public PotionFeather() {
        super(MobEffectCategory.BENEFICIAL, 14474460);
    }

    /*@Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (!pLivingEntity.isOnGround() && pLivingEntity.motionY < -0.4) {
            pLivingEntity.motionY += 0.1;
            pLivingEntity.velocityChanged = true;
            pLivingEntity.fallDistance = 0;
        }
    }*/

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
