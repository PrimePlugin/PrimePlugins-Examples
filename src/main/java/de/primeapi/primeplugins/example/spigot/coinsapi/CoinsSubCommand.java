package de.primeapi.primeplugins.example.spigot.coinsapi;

import de.primeapi.primeplugins.example.spigot.Example;
import de.primeapi.primeplugins.spigotapi.api.PrimePlayer;
import de.primeapi.primeplugins.spigotapi.api.command.SubCommand;
import de.primeapi.primeplugins.spigotapi.api.plugins.coins.CoinsAPI;
import de.primeapi.primeplugins.spigotapi.gui.GUIBuilder;
import de.primeapi.primeplugins.spigotapi.gui.itembuilder.ItemBuilder;
import org.bukkit.Material;

/**
 * @author Jonas D. Exceptionpilot
 * created on 27.02.2022
 * created for PrimePlugins-Exampless
 */

public class CoinsSubCommand extends SubCommand {
    public CoinsSubCommand() {
        super(null);
    }

    @Override
    public boolean execute(PrimePlayer primePlayer, String[] strings) {
        CoinsAPI.getInstance().getCoins(primePlayer.getUniqueId()).submit(coins -> {
            GUIBuilder guiBuilder = new GUIBuilder(9, "§8»§e §eCoinsAPI");
            for (int i = 0; i < 9; i++) {
                guiBuilder.addItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, (byte) 8).setDisplayName(" ").build());
            }
            guiBuilder.addItem(4, new ItemBuilder(Material.GOLD_NUGGET)
                            .setDisplayName("§8»§e §eKlick mich!")
                            .addLore("§e§8»§a " + coins.intValue()).build(),
                    (player, itemStack) -> {
                        player.closeInventory();
                        CoinsAPI.getInstance().addCoins(player.getUniqueId(), 1);
                        player.sendMessage("§eCoinsAPI §7● §7Du hast einen §eFree-Coin §7erhalten!");
                        player.sendMessage("§eCoinsAPI §7● §7Deine Coins §e" + coins.intValue());
                    });
            primePlayer.thePlayer().openInventory(guiBuilder.build(Example.getInstance()));
            primePlayer.thePlayer().updateInventory();
        });
        return true;
    }
}
