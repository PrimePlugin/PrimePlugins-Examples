package de.primeapi.primeplugins.example.spigot.coinsapi;

import de.primeapi.primeplugins.spigotapi.api.info.Info;
import de.primeapi.primeplugins.spigotapi.api.plugins.coins.CoinsAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;


/**
 * @author Jonas D. Exceptionpilot
 * created on 27.02.2022
 * created for PrimePlugins-Exampless
 */

public class CoinsAPIExamples implements Listener {


    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getInventory() == null) return;
        if (event.getInventory().getName() == null) return;
        if (event.getInventory().getName().equalsIgnoreCase("YourInventory")) {
            CoinsAPI.getInstance().getCoins(player.getUniqueId()).submit(coins -> {
                if (coins >= 100) {
                    CoinsAPI.getInstance().setCoins(player.getUniqueId(), coins - 100);
                    player.sendMessage("§aDir wurden 100 Coins abgezogen!");
                } else {
                    player.sendMessage("§cDu hast nicht genügend Coins!");
                }
            });
        }
    }

    @Info(info = "Sync Methode!")
    @Deprecated
    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        // getCoins sync from UUID
        int coins = CoinsAPI.getInstance().getCoins(player.getUniqueId()).complete();
        event.setFormat(player.getDisplayName() + "§8[§e" + coins + "§8]" + event.getMessage());
    }

    @Info(info = "Async Methode!")
    @EventHandler
    public void onAsyncPlayer(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        CoinsAPI.getInstance().getCoins(player.getUniqueId()).submit(coins -> {
            event.setFormat(player.getDisplayName() + "§8[§e" + coins + "§8]" + event.getMessage());
        });
    }
}
