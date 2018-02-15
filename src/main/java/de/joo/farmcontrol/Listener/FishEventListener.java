package de.joo.farmcontrol.Listener;

import de.joo.farmcontrol.FarmControlPlugin;
import de.joo.farmcontrol.helper.AntiSpam;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

public class FishEventListener implements Listener {
    private AntiSpam<Location> fishLookup = new AntiSpam<>(10);
    private FarmControlPlugin plugin;

    public FishEventListener(FarmControlPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerFish(PlayerFishEvent event) {
        Location locPlayer = event.getPlayer().getLocation();
        Location locFarm = new Location(event.getPlayer().getWorld(), locPlayer.getBlockX(), locPlayer.getBlockY(), locPlayer.getBlockZ());
        if (!fishLookup.isBlocked(locFarm)) {
            plugin.incrementFishLocation(locFarm);
            fishLookup.setBlocked(locFarm);
        }
    }
}
