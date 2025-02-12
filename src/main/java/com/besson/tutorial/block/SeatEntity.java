package com.besson.tutorial.block;

import net.minecraft.entity.Dismounting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

import java.util.List;

public class SeatEntity extends Entity {
    // 此类来自ArknightsFurniture

    public SeatEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    // 这个构造函数是用在create方法中的
    private SeatEntity(World world, BlockPos pos, double yOffset, Direction direction) {
        this(ModBlockEntities.SEAT, world);
        // 设置位置和朝向（可变量是Y轴偏移量，朝向）
        this.setPosition(pos.getX() + 0.5, pos.getY() + yOffset, pos.getZ() + 0.5);
        this.setRotation(direction.asRotation(), 0.0F);
    }

    // 以下三个方法是用于实体的，但我们这里创建的实体除了坐就没什么，所以都可空着
    @Override
    protected void initDataTracker() {

    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {

    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {

    }

    // 重写tick，检测座位是否被破坏，如果没有乘客或者座位被破坏了，就删除座位（移除实体）
    @Override
    public void tick() {
        super.tick();
        if (!this.getWorld().isClient()) {
            if (this.getPassengerList().isEmpty() || this.getWorld().isAir(getBlockPos())) {
                this.remove(RemovalReason.DISCARDED);
                this.getWorld().updateComparators(getBlockPos(), this.getWorld().getBlockState(getBlockPos()).getBlock());
            }
        }
    }

    // 重写getMountedHeightOffset，并返回为0（原方法用于不同大小的实体返回的Y轴偏移量，这里我们创建的实体没有大小，所以返回0）
    @Override
    public double getMountedHeightOffset() {
        return 0.0;
    }

    // 重写canBeRiddenInWater，并返回为true（原方法用于实体是否被骑乘，相对而言，也就是可坐），参见马等实体
    @Override
    protected boolean canStartRiding(Entity entity) {
        return true;
    }

    // 重写createSpawnPacket，创建S2C数据包，用于客户端同步数据
    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return super.createSpawnPacket();
    }

    // 自定义create方法，用于创建座位实体，其传入参数为世界、方块坐标、Y轴偏移量、玩家、朝向
    public static ActionResult create(World world, BlockPos pos, double yOffset, PlayerEntity player, Direction direction) {
        if(!world.isClient()) {
            // 检测座位是否已经存在，如果存在则不创建
            List<SeatEntity> seats = world.getNonSpectatingEntities(
                    SeatEntity.class, new Box(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1.0, pos.getY() + 1.0, pos.getZ() + 1.0));

            if(seats.isEmpty()) {
                // 创建座位实体，并在世界中生成，玩家开始骑乘
                SeatEntity seat = new SeatEntity(world, pos, yOffset, direction);
                world.spawnEntity(seat);
                player.startRiding(seat, false);
            }
        }
        return ActionResult.SUCCESS;
    }

    // 重写updatePassengerForDismount，用于玩家下座位时，找到合适的位置（可参见猪的相关内容）
    @Override
    public Vec3d updatePassengerForDismount(LivingEntity passenger) {
        // 获取位置
        Direction direction = this.getMovementDirection();
        // 获取四个方向的偏移量，并尝试找到合适的位置，如果找到则返回（给予一个偏移量，防止玩家卡在某些地方）
        Direction[] offsets = {direction, direction.rotateYClockwise(), direction.rotateYCounterclockwise(), direction};
        for (Direction offset : offsets) {
            Vec3d vec3d = Dismounting.findRespawnPos(passenger.getType(), this.getWorld(), this.getBlockPos().offset(offset), false);
            if (vec3d != null) {
                return vec3d.add(0, 0.25, 0);
            }
        }
        return super.updatePassengerForDismount(passenger);
    }

    // 重写addPassenger和updatePassengerPosition，用于同步乘客的朝向，使其与座位朝向一致
    @Override
    protected void addPassenger(Entity passenger) {
        super.addPassenger(passenger);
        passenger.setYaw(this.getYaw());
    }

    // 重写updatePassengerPosition，用于同步玩家的位置，使玩家的位置与座位位置一致
    @Override
    protected void updatePassengerPosition(Entity passenger, PositionUpdater positionUpdater) {
        super.updatePassengerPosition(passenger, positionUpdater);
        this.clampYaw(passenger);
    }

    // 自定义方法，限制玩家头部可旋转的角度（参见船的实体相关内容（在updatePassengerPosition中））
    private void clampYaw(Entity passenger) {
        passenger.setBodyYaw(this.getYaw());
        float wrappedYaw = MathHelper.wrapDegrees(passenger.getYaw() - this.getYaw());
        float clampedYaw = MathHelper.clamp(wrappedYaw, -120.0F, 120.0F);
        passenger.prevYaw += clampedYaw - wrappedYaw;
        passenger.setYaw(passenger.getYaw() + clampedYaw - wrappedYaw);
        passenger.setHeadYaw(passenger.getYaw());
    }

}
