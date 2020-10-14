package me.maxitros.zombie.listeners.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;

import java.util.List;
import java.util.Optional;

public class ItemNamePacket extends PacketAdapter
{
    public ItemNamePacket(AdapterParameteters params)
    {
        super(params);
    }
    @Override
    public void onPacketSending(PacketEvent e)
    {
        PacketContainer packet = e.getPacket();
        if (packet.getType() == PacketType.Play.Server.ENTITY_METADATA)
        {
            WrapperPlayServerEntityMetadata entityMetadataPacket = new WrapperPlayServerEntityMetadata(packet.deepClone());
            List<WrappedWatchableObject> dataWatcherValues = entityMetadataPacket.getEntityMetadata();
            if (dataWatcherValues == null)
            {
                return;
            }
            for (WrappedWatchableObject watchableObject : dataWatcherValues)
            {
                if (watchableObject.getIndex() == 2)
                {
                    String customName;
                    Object value = watchableObject.getValue();
                    if (!(value instanceof Optional))
                    {
                        continue;
                    }
                    Optional<?> customNameOptional = (Optional<?>) value;
                    if (!customNameOptional.isPresent())
                    {
                        continue;
                    }
                    WrappedChatComponent componentWrapper = WrappedChatComponent.fromHandle(customNameOptional.get());
                    customName = componentWrapper.getJson();
                    customName = customName.replace("%name%", e.getPlayer().getDisplayName());
                    watchableObject.setValue(Optional.of(WrappedChatComponent.fromJson(customName).getHandle()));
                    e.setPacket(entityMetadataPacket.getHandle());
                    return;
                }
            }
        }
    }
}
