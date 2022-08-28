package com.rena.rustic.common.block.fluid;

import com.rena.rustic.core.EffectInit;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

public class FluidBooze extends FluidDrinkable{

    public static final String QUALITY_NBT_KEY = "Quality";

    private float inebriationChance = 0.5F;

    protected FluidBooze(Properties properties) {
        super(properties);
    }

    public FluidBooze setInebriationChance(float chance) {
        this.inebriationChance = chance;
        return this;
    }

    public float getInebriationChance() {
        return inebriationChance;
    }

    @Override
    public void onDrank(@NotNull Level world, @NotNull Player player, @NotNull ItemStack stack, @NotNull FluidStack fluid) {
        //float quality = getQuality(fluid);

        //inebriate(world, player, quality);
        //affectPlayer(world, player, quality);
    }

    protected void affectPlayer(Level world, Player player, float quality) {

    }

    /*public float getQuality(FluidStack fluid) {
        float quality = 0F;
        if (fluid.tag != null && fluid.tag.hasKey(QUALITY_NBT_KEY, 5)) {
            quality = fluid.tag.getFloat(QUALITY_NBT_KEY);
        }
        return Math.max(Math.min(quality, 1), 0);
    }
*/
    protected void inebriate(Level world, Player player, float quality) {
        int duration = (int) (12000 * (Math.max(1 - Math.abs(quality - 0.75F), 0F)));
        float inebriationChanceMod = Math.max(Math.min(1 - Math.abs(0.67F * (quality - 0.75F)), 1), 0);
        MobEffectInstance tipsyEffect = player.getEffect(EffectInit.TIPSY.get());
        if (world.random.nextFloat() < this.inebriationChance * inebriationChanceMod) {
            if (tipsyEffect == null) {
                player.addEffect(new MobEffectInstance(EffectInit.TIPSY.get(), duration, 0, false, false));
            } else if (tipsyEffect.getAmplifier() < 3) {
                player.addEffect(new MobEffectInstance(EffectInit.TIPSY.get(), duration, tipsyEffect.getAmplifier() + 1, false, false));
            }
        }
    }

    @Override
    public boolean isSource(FluidState pState) {
        return false;
    }

    @Override
    public int getAmount(FluidState pState) {
        return 0;
    }
}
