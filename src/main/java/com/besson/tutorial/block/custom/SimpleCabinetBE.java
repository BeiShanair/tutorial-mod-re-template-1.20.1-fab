package com.besson.tutorial.block.custom;

import com.besson.tutorial.block.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class SimpleCabinetBE extends LootableContainerBlockEntity {
    // 创建一个物品栏（方块实体的）
    private DefaultedList<ItemStack> inv = createInventory();

    protected SimpleCabinetBE(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }
    // 重新写的构造函数，用于方块创建方块实体的实例化
    public SimpleCabinetBE(BlockPos blockPos, BlockState blockState) {
        this(ModBlockEntities.SIMPLE_CABINET, blockPos, blockState);
    }

    // 创建物品栏，返回27个（9*3）
    private DefaultedList<ItemStack> createInventory() {
        return DefaultedList.ofSize(27, ItemStack.EMPTY);
    }

    // 获取物品栏
    @Override
    protected DefaultedList<ItemStack> getInvStackList() {
        return this.inv;
    }

    // 设置物品栏
    @Override
    protected void setInvStackList(DefaultedList<ItemStack> list) {
        this.inv = list;
    }

    // 获取GUI标题
    @Override
    protected Text getContainerName() {
        return Text.translatable("container.simple_cabinet");
    }

    // 创建GUI的屏幕及屏幕处理程序，这里采用原版自带的
    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return GenericContainerScreenHandler.createGeneric9x3(syncId, playerInventory, this);
    }

    // 获取物品栏数量
    @Override
    public int size() {
        return 27;
    }

    // 读取物品栏数据
    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.inv = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        if (!this.deserializeLootTable(nbt)) {
            Inventories.readNbt(nbt, this.inv);
        }
    }

    // 写入物品栏数据
    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if (!this.serializeLootTable(nbt)) {
            Inventories.writeNbt(nbt, this.inv);
        }
    }
}
