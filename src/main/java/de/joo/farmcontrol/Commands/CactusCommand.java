package de.joo.farmcontrol.Commands;

import de.joo.farmcontrol.FarmControlPlugin;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Comparator;
import java.util.Map;

public class CactusCommand implements CommandExecutor {
    private FarmControlPlugin plugin;

    public CactusCommand(FarmControlPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage("Nicht als Konsole ausführbar!");
            return true;
        }
        Player player = (Player)commandSender;
        if(!player.hasPermission("farmcontrol.see")) {
            player.sendMessage(ChatColor.RED + "Unzureichende Berechtigungen.");
            return true;
        }
        Map<Location, Integer> counterMap = plugin.getCactusCounterMap();
        if(counterMap.size() == 0) {
            player.sendMessage(ChatColor.RED + "Es wurden keine Kakteenfarmen gefunden.");
            return true;
        }
        int page = 1;
        if(strings.length == 1) {
            try {
                page = Integer.parseInt(strings[0]);
            } catch (NumberFormatException e) {
                player.sendMessage(ChatColor.RED + "Bitte eine Nummer übergeben.");
                return true;
            }
        }
        if(page < 1) return true;
        int maxPage = ((int)counterMap.size()/15)+1;
        if(maxPage < page) {
            player.sendMessage(ChatColor.RED + "Höchste Seitenzahl: " + maxPage);
            return true;
        }
        player.sendMessage(ChatColor.BLUE + "Kakteenfarmen. Gesamt: " + counterMap.size() + " Seite " + page + " von " + (((int)counterMap.size()/15)+1) +":");
        plugin.getCactusCounterMap().entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .skip(15*(page-1)).limit(15).forEach(entry -> {
            player.spigot().sendMessage(new ComponentBuilder(ChatColor.GRAY + "" + entry.getKey().getBlockX()
                    + ", " + entry.getKey().getBlockY()
                    + ", " + entry.getKey().getBlockZ() + " --> " + entry.getValue()).
                    event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp " + player.getName()
                            + " " + entry.getKey().getBlockX()
                            + " " + entry.getKey().getBlockY()
                            + " " + entry.getKey().getBlockZ())).create());
        });
        return true;
    }
}
