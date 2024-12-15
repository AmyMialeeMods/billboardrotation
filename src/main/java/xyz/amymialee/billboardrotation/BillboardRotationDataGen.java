package xyz.amymialee.billboardrotation;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;
import org.jetbrains.annotations.NotNull;
import xyz.amymialee.mialib.templates.MDataGen;

public class BillboardRotationDataGen extends MDataGen {
    @Override
    protected void generateTranslations(MLanguageProvider provider, RegistryWrapper.WrapperLookup registryLookup, FabricLanguageProvider.@NotNull TranslationBuilder builder) {
        builder.add(BillboardRotation.BILLBOARD_ROTATION.getTranslationKey(), "Billboard Rotation");
        builder.add(BillboardRotation.HEAD_ROTATION_INTERVAL.getTranslationKey(), "Head Rotation Interval");
        builder.add(BillboardRotation.HEAD_ROTATION_INTERVAL.getDescriptionTranslationKey(), "The interval at which the entity heads should rotate");
        builder.add(BillboardRotation.BODY_ROTATION_INTERVAL.getTranslationKey(), "Body Rotation Interval");
        builder.add(BillboardRotation.BODY_ROTATION_INTERVAL.getDescriptionTranslationKey(), "The interval at which the entity bodies should rotate");
        builder.add(BillboardRotation.ADJUST_TO_PLAYER_ROTATION.getTranslationKey(), "Adjust to Player Rotation");
        builder.add(BillboardRotation.ADJUST_TO_PLAYER_ROTATION.getDescriptionTranslationKey(), "Whether the entity should adjust to the player's rotation");
        builder.add(BillboardRotation.ANIMATION_DELAY.getTranslationKey(), "Animation Delay");
        builder.add(BillboardRotation.ANIMATION_DELAY.getDescriptionTranslationKey(), "The delay between each animation frame");
        builder.add(BillboardRotation.ROTATE_VERTICALLY.getTranslationKey(), "Rotate Vertically");
        builder.add(BillboardRotation.ROTATE_VERTICALLY.getDescriptionTranslationKey(), "Whether the entity should rotate vertically");
    }
}