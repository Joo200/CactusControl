package de.joo.cactuscontrol;

import de.joo.cactuscontrol.helper.AntiSpam;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;

/**
 * Created by Johannes on 03.02.2018.
 */
public class CactusEventListener implements Listener {
    private AntiSpam<Location> cactusLookup = new AntiSpam<>(100);
    private CactusControlPlugin plugin;

    public CactusEventListener(CactusControlPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockPhysics(BlockPhysicsEvent event) {
        if(event.getBlock().getType() != Material.CACTUS) return;
        if(event.getChangedType() == Material.AIR) {
            cactusLookup.setBlocked(event.getBlock().getLocation());
        } else if (event.getChangedType() == Material.CACTUS && cactusLookup.isBlocked(event.getBlock().getLocation())) {
            plugin.incrementLocation(event.getBlock().getLocation());
        }
    }
}
