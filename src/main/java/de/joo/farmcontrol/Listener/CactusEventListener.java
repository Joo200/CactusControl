package de.joo.farmcontrol.Listener;

import de.joo.farmcontrol.FarmControlPlugin;
import de.joo.farmcontrol.helper.AntiSpam;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;

public class CactusEventListener implements Listener {
    private AntiSpam<Location> cactusLookup = new AntiSpam<>(100);
    private FarmControlPlugin plugin;

    public CactusEventListener(FarmControlPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockPhysics(BlockPhysicsEvent event) {
        if(event.getSourceBlock().getType() != Material.CACTUS) return;
        if(event.getChangedType() == Material.AIR) {
            cactusLookup.setBlocked(event.getSourceBlock().getLocation());
        } else if (event.getChangedType() == Material.CACTUS && cactusLookup.isBlocked(event.getSourceBlock().getLocation())) {
            plugin.incrementCactusLocation(event.getSourceBlock().getLocation());
        }
    }
}
