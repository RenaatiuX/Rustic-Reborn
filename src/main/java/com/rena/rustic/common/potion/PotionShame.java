package com.rena.rustic.common.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class PotionShame extends MobEffect {

    public PotionShame() {
        super(MobEffectCategory.HARMFUL, 16409650);
    }

    /*@Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (pLivingEntity != null && !pLivingEntity.level.isClientSide) {
            PacketHandler.INSTANCE.sendToAll(new MessageShameFX(pLivingEntity.level.random.nextInt(6) + 6, pLivingEntity.posX, pLivingEntity.posY,
                    pLivingEntity.posZ, pLivingEntity.motionX, pLivingEntity.motionY, pLivingEntity.motionZ, pLivingEntity.width, pLivingEntity.height));
        }
    }*/

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return (pDuration % 5) == 0;
    }
}
