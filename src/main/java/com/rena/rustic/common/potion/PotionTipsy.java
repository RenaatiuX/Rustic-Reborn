package com.rena.rustic.common.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PotionTipsy extends MobEffect {

    public PotionTipsy() {
        super(MobEffectCategory.HARMFUL, 7900290);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (pLivingEntity != null && !pLivingEntity.level.isClientSide && pAmplifier > 1) {
            if (pAmplifier > 2) {
                pLivingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 400, 1, false, false));
                pLivingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 400, 1, false, false));
                pLivingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 400, 0, false, false));
            } else {
                pLivingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 400, 0, false, false));
                pLivingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 400, 0, false, false));
            }
            // remove curative items after, in case the effects were already active before
            List<ItemStack> cureItems = new ArrayList<>();
            MobEffectInstance nausea = pLivingEntity.getEffect(MobEffects.CONFUSION);
            MobEffectInstance slowness = pLivingEntity.getEffect(MobEffects.MOVEMENT_SLOWDOWN);
            MobEffectInstance blindness = pLivingEntity.getEffect(MobEffects.BLINDNESS);
            MobEffectInstance tipsy = pLivingEntity.getEffect(this);
            if (nausea != null) nausea.setCurativeItems(cureItems);
            if (slowness != null) slowness.setCurativeItems(cureItems);
            if (blindness != null) blindness.setCurativeItems(cureItems);
            if (tipsy != null) tipsy.setCurativeItems(cureItems);
        }
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return pAmplifier > 1 && pDuration % 100 == 0;
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        return new ArrayList<>();
    }
}
