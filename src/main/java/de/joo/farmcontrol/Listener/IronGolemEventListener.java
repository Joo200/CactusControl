package de.joo.farmcontrol.Listener;

import de.joo.farmcontrol.FarmControlPlugin;
import de.joo.farmcontrol.helper.AntiSpam;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

public class IronGolemEventListener implements Listener {
    private AntiSpam<Location> ironGolemLookup = new AntiSpam<>(100);
    private FarmControlPlugin plugin;

    public IronGolemEventListener(FarmControlPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        if(event.getEntity().getType() != EntityType.IRON_GOLEM) return;

        if (!ironGolemLookup.isBlocked(event.getEntity().getLocation())) {
            plugin.incrementIronGolemLocation(event.getEntity().getLocation());
            ironGolemLookup.setBlocked(event.getEntity().getLocation());
        }
    }
}
