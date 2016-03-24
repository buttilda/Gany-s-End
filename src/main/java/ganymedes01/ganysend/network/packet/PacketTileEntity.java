package ganymedes01.ganysend.network.packet;

import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.network.IPacketHandlingTile;
import ganymedes01.ganysend.network.PacketHandler.PacketType;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

public class PacketTileEntity extends CustomPacket {

	private BlockPos pos;
	private TileData dataWriter;
	private ByteBuf buffer;

	public interface TileData {

		public void writeData(ByteBuf buffer);
	}

	public PacketTileEntity() {
		super(PacketType.TILE_ENTITY);
	}

	public PacketTileEntity(TileEntity tile, TileData dataWriter) {
		this(tile.getPos(), dataWriter);
	}

	public PacketTileEntity(BlockPos pos, TileData dataWriter) {
		super(PacketType.TILE_ENTITY);
		this.pos = pos;
		this.dataWriter = dataWriter;
	}

	@Override
	public void writeData(ByteBuf buffer) {
		buffer.writeInt(pos.getX());
		buffer.writeInt(pos.getY());
		buffer.writeInt(pos.getZ());
		dataWriter.writeData(buffer);
	}

	@Override
	public void readData(ByteBuf buffer) {
		int x = buffer.readInt();
		int y = buffer.readInt();
		int z = buffer.readInt();
		pos = new BlockPos(x, y, z);
		this.buffer = buffer;
	}

	@Override
	public void handleClientSide(World world, EntityPlayer player) {
		IPacketHandlingTile tile = Utils.getTileEntity(world, pos, IPacketHandlingTile.class);
		if (tile != null)
			tile.readPacketData(buffer);
	}

	@Override
	public void handleServerSide(World world, EntityPlayer player) {
		IPacketHandlingTile tile = Utils.getTileEntity(world, pos, IPacketHandlingTile.class);
		if (tile != null)
			tile.readPacketData(buffer);
	}
}