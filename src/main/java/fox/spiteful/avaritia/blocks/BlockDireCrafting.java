package fox.spiteful.avaritia.blocks;

import fox.spiteful.avaritia.Avaritia;
import fox.spiteful.avaritia.tile.TileEntityDireCrafting;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockDireCrafting extends BlockContainer {
    private Random randy = new Random();

    public BlockDireCrafting(){
        super(Material.IRON);
        setSoundType(SoundType.GLASS);
        setHardness(50.0F);
        setResistance(2000.0F);
        setUnlocalizedName("dire_crafting");
        setCreativeTab(Avaritia.tab);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (world.isRemote)
        {
            return true;
        }
        else
        {
            player.openGui(Avaritia.instance, 1, world, pos.getX(), pos.getY(), pos.getZ());
            return true;
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityDireCrafting();
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity tile = worldIn.getTileEntity(pos);

        if (tile instanceof TileEntityDireCrafting) {
            for (int i = 1; i < ((TileEntityDireCrafting) tile).getSizeInventory(); i++) {
                ItemStack stack = ((TileEntityDireCrafting) tile).getStackInSlot(i);
                if (stack != null) {
                    EntityItem item = new EntityItem(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, stack);
                    worldIn.spawnEntityInWorld(item);
                    ((TileEntityDireCrafting) tile).setInventorySlotContents(i, null);
                }
            }
        }

        super.breakBlock(worldIn, pos, state);
    }
}
