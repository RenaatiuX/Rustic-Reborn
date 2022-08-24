package com.rena.rustic.core;

import com.rena.rustic.RusticReborn;
import com.rena.rustic.common.potion.PotionBlazingTrail;
import com.rena.rustic.common.potion.PotionFeather;
import com.rena.rustic.common.potion.PotionShame;
import com.rena.rustic.common.potion.PotionTipsy;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EffectInit {

    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, RusticReborn.MOD_ID);

    public static final RegistryObject<MobEffect> TIPSY = EFFECTS.register("tipsy", PotionTipsy::new);
    public static final RegistryObject<MobEffect> SHAME_POTION = EFFECTS.register("shame", PotionShame::new);
    public static final RegistryObject<MobEffect> BLAZING_TRAIL_POTION = EFFECTS.register("blazing_trail", PotionBlazingTrail::new);
    public static final RegistryObject<MobEffect> FEATHER_POTION = EFFECTS.register("feather", PotionFeather::new);
}
