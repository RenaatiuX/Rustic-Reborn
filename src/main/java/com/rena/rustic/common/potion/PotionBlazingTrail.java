package com.rena.rustic.common.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class PotionBlazingTrail extends MobEffect {

    public PotionBlazingTrail() {
        super(MobEffectCategory.BENEFICIAL, 16738816);
    }

    /*@Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (!pLivingEntity.getEntityWorld().isRemote && pLivingEntity.isOnGround() && pLivingEntity.getEntityWorld().isAirBlock(pLivingEntity.getPosition()) && pLivingEntity.getEntityWorld().getBlockState(pLivingEntity.getPosition().down()).isNormalCube()) {
            pLivingEntity.getEntityWorld().setBlockState(pLivingEntity.getPosition(), pLivingEntity.FIRE.getDefaultState(), 3);
        }
    }*/

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return (pDuration % 10) == 0;
    }
}
