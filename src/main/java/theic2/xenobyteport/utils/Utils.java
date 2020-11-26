package theic2.xenobyteport.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import it.unimi.dsi.fastutil.bytes.Byte2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ByteMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.multiplayer.ChunkProviderClient;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketCreativeInventoryAction;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentKeybind;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.FMLIndexedMessageToMessageCodec;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleIndexedCodec;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import org.apache.commons.lang3.StringUtils;
import theic2.xenobyteport.api.Xeno;
import theic2.xenobyteport.modules.modded.GiveSelect;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;
import java.util.stream.Stream;

public class Utils {

    private BlockPos lastCoord = new BlockPos(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);

    public Minecraft mc() {
        return Minecraft.getMinecraft();
    }

    public BlockPos mop() {
        return mc().getRenderViewEntity().rayTrace(200, 1.0F).getBlockPos();
    }

    public BlockPos myCoords() {
        return player().getPosition();
    }

    public BlockPos coords(Entity entity) {
        return entity.getPosition();
    }

    public EntityPlayer player(String nick) {
        return world().getPlayerEntityByName(nick);
    }

    public EntityPlayer player() {
        return mc().player;
    }

    public boolean isInCreative() {
        return mc().player.isCreative();
    }

    public boolean isInCreative(EntityPlayer pl) {
        return pl.isCreative();
    }

    public String uuid(Entity entity) {
        return entity.getUniqueID().toString();
    }

    public int id(Entity entity) {
        return entity.getEntityId();
    }

    public int myId() {
        return id(player());
    }

    public String myUUID() {
        return uuid(player());
    }

    public String name(Entity entity) {
        return entity.getName();
    }

    public String myName() {
        return player().getName();
    }

    //TODO
    //мб не работает
    public String stringID(ItemStack item) {
        return item.getItem().getRegistryName().toString();
    }

    public WorldClient world() {
        return mc().world;
    }

    public int worldID() {
        return world().provider.getDimension();
    }

    public void reloadWorld() {
        world().provider.setWorld(world());
    }

    public void disconnect() {
        NetworkManager netHandler = mc().getConnection().getNetworkManager();
        if (netHandler != null) {
            netHandler.closeChannel(new TextComponentKeybind("Тупа бан"));
        }
    }

    public NBTTagCompound jsonToNbt(String json) {
        NBTBase base = null;
        try {
            base = JsonToNBT.getTagFromJson(json);
        } catch (NBTException err) {
            XenoLogger.info(err.getMessage());
        }
        return (NBTTagCompound) base;
    }

    public String nbtToJson(NBTTagCompound nbt) {
        return nbt.toString().replaceAll("\\s+", " ").replaceAll("\"", "");
    }

    public ItemStack item() {
        return player().inventory.getCurrentItem();
    }

    public ItemStack hoveredItem() {
        return player().inventory.getItemStack();
    }

    public ItemStack item(int id, int meta, int count) {
        Item item = Item.getItemById(id);
        ItemStack stack = item != null ? new ItemStack(item) : null;
        if (stack != null) {
            stack.setItemDamage(meta);
            stack.setCount(count);
        }
        return stack;
    }

    public ItemStack item(int id, int meta) {
        return item(id, meta, 1);
    }

    public ItemStack item(int id) {
        return item(id, 0, 1);
    }

    public ItemStack item(ItemStack toItem, String json) {
        return item(toItem, jsonToNbt(json));
    }

    public ItemStack item(ItemStack toItem, NBTTagCompound tag) {
        if (toItem != null && tag != null) {
            toItem.setTagCompound(collideNbt(toItem.hasTagCompound() ? toItem.getTagCompound() : new NBTTagCompound(), tag));
        }
        return toItem;
    }

    public ItemStack heldItem() {
        return player().inventory.getItemStack();
    }

    public ItemStack rename(ItemStack toItem, String name) {
        return toItem.setStackDisplayName(name);
    }

    public void setHardness(Block block, int hardness) {
        block.setHardness(hardness);
    }

    public void setHardness(int hardness) {
        setHardness(block(), hardness);
    }

    public IBlockState blockState(BlockPos pos) {
        return world().getBlockState(pos);
    }

    public int blockMeta(BlockPos pos) {
        return block(pos).getMetaFromState(blockState(pos));
    }

    public Block block() {
        return block(mop());
    }

    public Block block(BlockPos pos) {
        return blockState(pos).getBlock();
    }

    public Block block(String id) {
        return Block.REGISTRY.getObject(new ResourceLocation(id));
    }

    public TileEntity tile() {
        return tile(mop());
    }

    public TileEntity tile(BlockPos pos) {
        return world().getTileEntity(pos);
    }

    public TileEntity tile(NBTTagCompound tag) {
        return tile(tile(), tag);
    }

    public TileEntity tile(TileEntity toTile, NBTTagCompound tag) {
        if (toTile != null && tag != null) {
            NBTTagCompound tileTag = new NBTTagCompound();
            toTile.writeToNBT(tileTag);
            toTile.readFromNBT(collideNbt(tileTag, tag));
        }
        return toTile;
    }

    public int pointedEntity() {
        Entity entity = mc().objectMouseOver.entityHit;
        return (entity != null) ? entity.getEntityId() : -1;
    }

    public Entity entity() {
        return entity(null);
    }

    public void singlePlayerGui() {
        mc().displayGuiScreen(new GuiWorldSelection(currentScreen()));
    }

    public Entity entity(NBTTagCompound tag) {
        return entity(entity(pointedEntity()), tag);
    }

    public Entity entity(Entity toEntity, NBTTagCompound tag) {
        if (toEntity != null && tag != null) {
            toEntity.readFromNBT(tag);
        }
        return toEntity;
    }

    public Entity entity(int id) {
        return world().getEntityByID(id);
    }

    public Stream<Entity> nearEntityes() {
        return nearEntityes(50);
    }

    public Stream<Entity> nearEntityes(int radius) {
        return world().loadedEntityList.stream().filter(e -> player().getDistance(e) <= radius && e != player());
    }

    public String longString(String text, int len) {
        return StringUtils.repeat(text, len);
    }

    public void clipboardMessage(Object clipboardTextObj) {
        String clipboardText = nullHelper(clipboardTextObj);
        if (!clipboardText.isEmpty()) {
            StringSelection clipText = new StringSelection(clipboardText.replaceAll("§.", ""));
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(clipText, null);
        }
    }

    public void creativeGive(int slot, int id, int meta, int count) {
        creativeGive(slot, item(id, meta, count));
    }

    public void creativeGive(int id, int meta, int count) {
        creativeGive(activeSlot(), item(id, meta, count));
    }

    public void creativeGive(int id, int meta) {
        creativeGive(activeSlot(), item(id, meta));
    }

    public void creativeGive(int id) {
        creativeGive(activeSlot(), item(id));
    }

    public void creativeGive(ItemStack item) {
        creativeGive(activeSlot(), item);
    }

    public void creativeGive(int slot, ItemStack item) {
        sendPacket(new CPacketCreativeInventoryAction(slot, item));
    }

    public void windowMessage(Object messageTextObj) {
        JOptionPane.showMessageDialog(null, nullHelper(messageTextObj));
    }

    public boolean confirmDialog(Object messageTextObj) {
        return JOptionPane.showConfirmDialog(null, nullHelper(messageTextObj)) == JOptionPane.YES_OPTION;
    }

    public void serverChatMessage(Object serverChatMessageText) {
        mc().player.sendChatMessage(nullHelper(serverChatMessageText));
    }

    public void chatMessage(Object chatMessageText) {
        mc().player.sendMessage(new TextComponentString(Xeno.format_prefix + nullHelper(chatMessageText)));
    }

    public void chatMessage(Object... data) {
        chatMessage(Arrays.asList(data));
    }

    public void debugMessage() {
        chatMessage(formatTime().concat(" ").concat(Rand.str()));
    }

    public void playSound(SoundEvent soundEvent) {
        mc().player.playSound(soundEvent, 1, 1);
    }

    public String nullHelper(Object o) {
        return "" + o;
    }

    public String separator(String text) {
        return "==================== " + text + " ====================";
    }

    public int windowID() {
        return player().openContainer.windowId;
    }

    public GuiContainer guiContainer() {
        return guiContainer(currentScreen());
    }

    public GuiContainer guiContainer(GuiScreen screen) {
        return screen instanceof GuiContainer ? (GuiContainer) screen : null;
    }

    public void shutDown() {
        mc().shutdown();
    }

    public void dropSlots(int range) {
        for (int slot = 0; slot < range; slot++) {
            dropSlot(slot, true);
        }
    }

    public void dropSlot(int slot, boolean allStacks) {
        clickSlot(slot, allStacks ? 1 : 0, ClickType.THROW);
    }

    public void swapSlot(int from, int to) {
        clickSlot(from, to, ClickType.SWAP);
    }

    public void clickSlot(int slot, int shift, ClickType action) {
        mc().playerController.windowClick(windowID(), slot, shift, action, player());
    }

    public int activeSlot() {
        return mySlotsCount() + player().inventory.currentItem;
    }

    public boolean isInGame() {
        return player() != null && world() != null;
    }

    public int mySlotsCount() {
        return player().inventory.mainInventory.size();
    }

    public int slots(TileEntity tile) {
        if (tile instanceof IInventory) {
            IInventory inventory = (IInventory) tile;
            return inventory.isUsableByPlayer(player()) ? inventory.getSizeInventory() : 0;
        }
        return 0;
    }

    public String entityInfo(Entity entity) {
        return "[" + entity.getCommandSenderEntity() + ": UUID(" + uuid(entity) + "), ID(" + id(entity) + ")]";
    }

    public boolean isPlayer(Entity e) {
        return e instanceof EntityPlayer;
    }

    public boolean isMonster(Entity e) {
        return e instanceof EntityMob || e instanceof IMob;
    }

    public boolean isAnimal(Entity e) {
        return e instanceof EntityAnimal || e instanceof EntityAmbientCreature || e instanceof EntityWaterMob || e instanceof EntityGolem;
    }

    @SuppressWarnings("unchecked")
    public List<TileEntity> nearTiles() {
        List<TileEntity> out = new ArrayList<TileEntity>();
        IChunkProvider chunkProvider = world().getChunkProvider();
        if (chunkProvider instanceof ChunkProviderClient) {
            for (Chunk chunk : ((Long2ObjectMap<Chunk>) Reflections.getPrivateValue(ChunkProviderClient.class, (ChunkProviderClient) chunkProvider, "field_73236_b", "loadedChunks", "c")).values()) {
                for (Object entityObj : chunk.getTileEntityMap().values()) {
                    if (entityObj instanceof TileEntity) {
                        out.add((TileEntity) entityObj);
                    }
                }
            }
        }
        return out;
    }

    public List<String> playersList() {
        List<String> out = new ArrayList<String>();
        for (NetworkPlayerInfo info : mc().getConnection().getPlayerInfoMap()) {
            out.add(info.getDisplayName().toString());
        }
        return out;
    }

    public NBTTagCompound nbtItem(ItemStack item) {
        return item.writeToNBT(new NBTTagCompound());
    }

    public NBTTagCompound nbtItem(ItemStack item, int slot, String slotName) {
        NBTTagCompound tag = nbtItem(item);
        tag.setByte(slotName, (byte) slot);
        return tag;
    }

    public String formatTime() {
        return new SimpleDateFormat("'['HH:mm:ss']'").format(new Date());
    }

    public boolean hasTag(Object obj, String key) {
        NBTTagCompound tag = nbt(obj);
        if (tag != null) {
            return tag.hasKey(key);
        }
        return false;
    }

    public NBTTagCompound nbt(Object obj) {
        NBTTagCompound tag = new NBTTagCompound();
        if (obj instanceof ItemStack) {
            tag = ((ItemStack) obj).getTagCompound();
        } else if (obj instanceof TileEntity) {
            ((TileEntity) obj).writeToNBT(tag);
        } else if (obj instanceof Entity) {
            ((Entity) obj).writeToNBT(tag);
        }
        return tag;
    }

    public NBTTagCompound collideNbt(NBTTagCompound toCollide, NBTTagCompound in) {
        Map<String, NBTBase> outMap = nbtMap(toCollide);
        outMap.putAll(nbtMap(in));
        setNbtMap(toCollide, outMap);
        return toCollide;
    }

    public Map<String, NBTBase> nbtMap(NBTTagCompound tag) {
        return Reflections.getPrivateValue(NBTTagCompound.class, tag, "field_74784_a", "tagMap", "d");
    }

    public void setNbtMap(NBTTagCompound tag, Map map) {
        Reflections.setPrivateValue(NBTTagCompound.class, tag, map, "field_74784_a", "tagMap", "d");
    }

    //private double boxHeight;
    //public void verticalTeleport(int yPos, boolean sound) {
    //    if (boxHeight == 0) {
    //        boxHeight = player().getEntityBoundingBox().maxY - player().getEntityBoundingBox().minY;
    //    }
    //    player().boundingBox.minY = yPos;
    //    player().boundingBox.maxY = yPos + boxHeight;
    //    if (sound) {
    //        playSound(SoundEvents.BLOCK_PORTAL_TRAVEL);
    //    }
    //}

    public boolean isDoubleChest(TileEntity tile) {
        if (tile instanceof TileEntityChest) {
            BlockPos pos = tile.getPos();
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();
            return tile(new BlockPos(x - 1, y, z)) instanceof TileEntityChest || tile(new BlockPos(x + 1, y, z)) instanceof TileEntityChest || tile(new BlockPos(x, y, z - 1)) instanceof TileEntityChest || tile(new BlockPos(x, y, z + 1)) instanceof TileEntityChest;
        }
        return false;
    }

    public boolean isAfk(EntityLivingBase e) {
        BlockPos playerPos = e.getPosition();
        if (playerPos == lastCoord) {
            return true;
        } else {
            lastCoord = playerPos;
            return false;
        }
    }

    public NBTTagCompound chestGiveHelper(TileEntityChest chest, GiveSelect selector) {
        NBTTagCompound root = new NBTTagCompound();
        NBTTagList list = new NBTTagList();
        root.setInteger("x", chest.getPos().getX());
        root.setInteger("y", chest.getPos().getY());
        root.setInteger("z", chest.getPos().getZ());
        root.setString("id", "Chest");
        int slots = selector.fillAllSlots() ? slots(chest) : 1;
        for (int i = 0; i < slots; i++) {
            list.appendTag(nbtItem(selector.givedItem(), i, "Slot"));
        }
        root.setTag("Items", list);
        return root;
    }

    //TODO говнецо
    public List<BlockPos> nukerList(BlockPos c, int r) {
        r--;
        List<BlockPos> list = new ArrayList<BlockPos>();
        for (int i = r; i >= -r; i--) {
            for (int k = r; k >= -r; k--) {
                for (int j = -r; j <= r; j++) {
                    int x = c.getX() + i;
                    int y = c.getY() + j;
                    int z = c.getZ() + k;
                    Block block = block(new BlockPos(x, y, z));
                    if (block instanceof BlockAir) {
                        continue;
                    }
                    list.add(new BlockPos(x, y, z));
                }
            }
        }
        return list;
    }

    public void runWorldSave(String saveName) {
        try {
            mc().launchIntegratedServer(saveName, saveName, null);
        } catch (Exception e) {
        }
    }

    @SuppressWarnings("unchecked")
    public boolean isGuiOpen(String className) {
        try {
            return isGuiOpen((Class<? extends GuiScreen>) Class.forName(className));
        } catch (Exception e) {
            return false;
        }
    }

    public String getObfuscated(String obf, String mcp) {
        return isObfuscated() ? obf : mcp;
    }

    public boolean isObfuscated() {
        try {
            Minecraft.class.getDeclaredMethod("getMinecraft");
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    public boolean isGuiOpen(Class<? extends GuiScreen> guiClass) {
        return currentScreen() != null && currentScreen().getClass() == guiClass;
    }

    public GuiScreen currentScreen() {
        return mc().currentScreen;
    }

    public void openGui(GuiScreen gui) {
        openGui(gui, false);
    }

    public void openGui(GuiScreen gui, boolean silent) {
        if (silent) {
            ScaledResolution sr = new ScaledResolution(mc());
            mc().currentScreen = gui;
            mc().setIngameNotInFocus();
            gui.width = sr.getScaledWidth();
            gui.height = sr.getScaledHeight();
            gui.mc = mc();
            gui.mc.skipRenderWorld = false;
            gui.initGui();
        } else {
            mc().displayGuiScreen(gui);
        }
    }

    public void closeGuis() {
        openGui(null);
        mc().setIngameFocus();
    }

    public boolean isInGameGui() {
        return isInGame() && currentScreen() == null;
    }

    public ByteBuf itemWriter(int id) {
        return itemWriter(id, 0, 1, null);
    }

    public ByteBuf itemWriter(int id, int meta) {
        return itemWriter(id, meta, 1, null);
    }

    public ByteBuf itemWriter(int id, int meta, int count) {
        return itemWriter(id, meta, count, null);
    }

    public ByteBuf itemWriter(int id, int meta, int count, NBTTagCompound nbt) {
        ByteBuf buf = Unpooled.buffer(0).writeShort(id).writeByte(count).writeShort(meta);
        ByteBufUtils.writeTag(buf, nbt);
        return buf;
    }

    public Class<?> getPacket(SimpleNetworkWrapper wrapper, int id) {
        SimpleIndexedCodec codec = Reflections.getPrivateValue(SimpleNetworkWrapper.class, wrapper, "packetCodec");
        Byte2ObjectMap<Class<?>> desc = Reflections.getPrivateValue(FMLIndexedMessageToMessageCodec.class, codec, "discriminators");
        return desc.get((byte) id);
    }

    public int getPacket(SimpleNetworkWrapper wrapper, Class packet) {
        SimpleIndexedCodec codec = Reflections.getPrivateValue(SimpleNetworkWrapper.class, wrapper, "packetCodec");
        Object2ByteMap<?> types = Reflections.getPrivateValue(FMLIndexedMessageToMessageCodec.class, codec, "types");
        return types.get(packet);
    }

    public void sendPacket(Packet packet) {
        mc().getConnection().sendPacket(packet);
    }

    public void sendPacket(String channel, PacketBuffer data) {
        sendPacket(new FMLProxyPacket(data, channel));
    }

    public void sendPacket(String channel, Object... data) {
        sendPacket(channel, bufWriter(data));
    }

    public void sendPacket(String channel, ByteBuf data) {
        sendPacket(channel, new PacketBuffer(data));
    }

    public ByteBuf bufWriter(Object... data) {
        return bufWriter(Unpooled.buffer(0), data);
    }

    public ByteBuf bufWriter(ByteBuf buf, Object... data) {
        for (Object o : data) {
            if (o instanceof Integer) {
                buf.writeInt((Integer) o);
            } else if (o instanceof Byte) {
                buf.writeByte((Byte) o);
            } else if (o instanceof Short) {
                buf.writeShort((Short) o);
            } else if (o instanceof Float) {
                buf.writeFloat((Float) o);
            } else if (o instanceof String) {
                ByteBufUtils.writeUTF8String(buf, (String) o);
            } else if (o instanceof ItemStack) {
                ByteBufUtils.writeItemStack(buf, (ItemStack) o);
            } else if (o instanceof NBTTagCompound) {
                ByteBufUtils.writeTag(buf, (NBTTagCompound) o);
            } else if (o instanceof ByteBuf) {
                buf.writeBytes((ByteBuf) o);
            } else if (o instanceof Double) {
                buf.writeDouble((Double) o);
            } else if (o instanceof Boolean) {
                buf.writeBoolean((Boolean) o);
            } else if (o instanceof byte[]) {
                buf.writeBytes((byte[]) o);
            } else if (o instanceof Long) {
                buf.writeLong((Long) o);
            } else if (o instanceof int[]) {
                for (int i : (int[]) o) {
                    bufWriter(buf, i);
                }
            } else throw new UnsupportedOperationException("Cannot serialize: " + o.getClass().getName());
        }
        return buf;
    }


    public ItemStack getStackMouseOver() {
        ItemStack stack = ItemStack.EMPTY;
        if (Reflections.exists("mezz.jei.JustEnoughItems")) {
            try {
                Object jeiRuntime = Class.forName("mezz.jei.Internal").getMethod("getRuntime").invoke(null);
                stack = (ItemStack) Class.forName("mezz.jei.gui.overlay.ItemListOverlay").getMethod("getStackUnderMouse").invoke(jeiRuntime.getClass().getMethod("getItemListOverlay").invoke(jeiRuntime));
            } catch (Exception e) {
                return null;
            }
        }
        if (currentScreen() instanceof GuiContainer) {
            if (stack == null) {
                stack = ((GuiContainer) currentScreen()).getSlotUnderMouse().getStack();
            }
        }

        return stack;
    }


}