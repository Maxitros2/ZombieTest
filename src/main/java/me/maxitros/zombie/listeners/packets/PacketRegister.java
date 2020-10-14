package me.maxitros.zombie.listeners.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import me.maxitros.zombie.ZombieTest;

public class PacketRegister
{
    private ZombieTest plugin;
    public void enable(ZombieTest plugin)
    {
        this.plugin = plugin;
        PacketAdapter.AdapterParameteters params = PacketAdapter
                .params()
                .plugin(plugin)
                .types(PacketType.Play.Server.ENTITY_METADATA)
                .serverSide()
                .listenerPriority(ListenerPriority.NORMAL);
        ProtocolLibrary.getProtocolManager().addPacketListener(new ItemNamePacket(params));
    }

    public void disable() {
        ProtocolLibrary.getProtocolManager().removePacketListeners(plugin);
    }
}
