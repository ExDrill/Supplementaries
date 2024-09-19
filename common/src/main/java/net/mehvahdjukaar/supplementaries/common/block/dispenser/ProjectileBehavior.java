package net.mehvahdjukaar.supplementaries.common.block.dispenser;

import net.mehvahdjukaar.moonlight.api.util.DispenserHelper;
import net.mehvahdjukaar.supplementaries.common.block.fire_behaviors.ProjectileStats;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

abstract class ProjectileBehavior extends DispenserHelper.AdditionalDispenserBehavior {

    protected ProjectileBehavior(Item item) {
        super(item);
    }

    @Override
    protected InteractionResultHolder<ItemStack> customBehavior(BlockSource source, ItemStack stack) {
        Level world = source.level();
        Position dispensePosition = DispenserBlock.getDispensePosition(source);
        Direction direction = source.state().getValue(DispenserBlock.FACING);
        BlockPos frontPos = source.pos().relative(direction);
        //this will make it so stuff can only shoot when no collision block is in front so we can run other behaviors too
        if (!world.getBlockState(frontPos).getCollisionShape(world, frontPos).isEmpty()) {
            return InteractionResultHolder.fail(stack);
        }
        Projectile projectileEntity = this.getProjectileEntity(source, dispensePosition,  stack);
        projectileEntity.shoot(direction.getStepX(), direction.getStepY() + 0.1F, direction.getStepZ(), this.getProjectileVelocity(), this.getProjectileInaccuracy());
        world.addFreshEntity(projectileEntity);
        stack.shrink(1);
        return InteractionResultHolder.success(stack);
    }

    @Override
    protected void playSound(BlockSource source, boolean success) {
        var center = source.center();
        source.level().playSound(null, center.x() + 0.5, center.y() + 0.5, center.z() + 0.5,
                getSound(),
                SoundSource.NEUTRAL, 0.5F,
                0.4F / (source.level().getRandom().nextFloat() * 0.4F + 0.8F));
    }

    protected abstract SoundEvent getSound();

    protected abstract Projectile getProjectileEntity(BlockSource source, Position position, ItemStack stackIn);

    // same as snowball
    protected float getProjectileInaccuracy() {
        return ProjectileStats.SNOWBALL_DISPENSER_INACCURACY;
    }

    protected float getProjectileVelocity() {
        return ProjectileStats.SNOWBALL_DISPENSER_SPEED;
    }

}
