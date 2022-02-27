package de.primeapi.primeplugins.example.spigot.permsapi;

import de.primeapi.primeplugins.spigotapi.api.info.Info;
import de.primeapi.primeplugins.spigotapi.api.plugins.perms.PermsAPI;
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
        PermsAPI.getInstance().getHighestGroup(player.getUniqueId()).submit(sqlGroup -> {
            event.setFormat(player.getDisplayName() + "§8[§e" + sqlGroup.getName().complete() + "§8]" + event.getMessage());
        });
    }
}
