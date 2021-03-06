package de.primeapi.primeplugins.example.spigot.permsapi;

import de.primeapi.primeplugins.spigotapi.api.info.Info;
import de.primeapi.primeplugins.spigotapi.api.plugins.perms.PermsAPI;
import de.primeapi.primeplugins.spigotapi.sql.permissions.SQLGroup;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * @author Jonas D. Exceptionpilot
 * created on 27.02.2022
 * created for PrimePlugins-Exampless
 */

public class PermsExamples implements Listener {

    @Info(info = "Sync Methode!")
    @Deprecated
    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String highestGroup = PermsAPI.getInstance().getHighestGroup(player.getUniqueId()).complete().getName().complete();
        event.setFormat(player.getDisplayName() + "§8[§e" + highestGroup + "§8]" + event.getMessage());
    }

    @Info(info = "Async Methode!")
    @EventHandler
    public void onAsyncPlayer(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (event.getMessage().equalsIgnoreCase("#givedefault")) {
            SQLGroup.fromName("default").submit(sqlGroup -> {
                if (sqlGroup == null) {
                    throw new NullPointerException("group is null!");
                }
                try {
                    PermsAPI.getInstance().addGroup(player.getUniqueId(), "default", -1L, 1);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                player.sendMessage("§cPermsAPI §7● §7Du hast nun den Rang §8'§7default§8'§7!");
            });
            event.setCancelled(true);
            return;
        }
        PermsAPI.getInstance().getHighestGroup(player.getUniqueId()).submit(sqlGroup -> {
            event.setFormat(player.getDisplayName() + "§8[§e" + sqlGroup.getName().complete() + "§8]" + event.getMessage());
        });
    }
}
