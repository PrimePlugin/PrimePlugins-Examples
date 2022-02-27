package de.primeapi.primeplugins.example.spigot.permsapi;

import de.primeapi.primeplugins.example.spigot.Example;
import de.primeapi.primeplugins.spigotapi.api.PrimePlayer;
import de.primeapi.primeplugins.spigotapi.api.command.SubCommand;
import de.primeapi.primeplugins.spigotapi.api.plugins.perms.PermsAPI;
import de.primeapi.primeplugins.spigotapi.gui.GUIBuilder;
import de.primeapi.primeplugins.spigotapi.gui.itembuilder.ItemBuilder;
import de.primeapi.primeplugins.spigotapi.sql.permissions.SQLGroup;
import org.bukkit.Material;

/**
 * @author Jonas D. Exceptionpilot
 * created on 27.02.2022
 * created for PrimePlugins-Exampless
 */

public class PermsSubCommand extends SubCommand {

    public PermsSubCommand() {
        super(null);
    }

    @Override
    public boolean execute(PrimePlayer primePlayer, String[] strings) {
        GUIBuilder guiBuilder = new GUIBuilder(3 * 9, "§8»§e §cPermsAPI");
        PermsAPI.getInstance().getGroups(primePlayer.getUniqueId()).submit(sqlGroups -> {
            for (int i = 0; i < 3 * 9; i++) {
                guiBuilder.addItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, (byte) 8).setDisplayName(" ").build());
            }
            int i = 0;
            for (SQLGroup sqlGroup : sqlGroups) {
                guiBuilder.addItem(i++, new ItemBuilder(Material.WOOL)
                                .setDisplayName("§8»§e " + sqlGroup.getColor().complete() + sqlGroup.getName().complete())
                                .addLore("§8»§e §eColor §8:§e " + sqlGroup.getColor().complete() + sqlGroup.getColor().complete().replace("§", ""))
                                .addLore("§8»§e §ePrefix §8:§e " + sqlGroup.getPrefix().complete())
                                .addLore("§8»§e §eSuffix §8:§e " + sqlGroup.getSuffix().complete())
                                .addLore("§8»§e §eWeight §8:§e " + sqlGroup.getWeight().complete())
                                .addLore("§8»§e §eDisplayName §8:§e " + sqlGroup.getDisplayName().complete())
                                .build(),
                        (player, itemStack) -> {
                            player.closeInventory();
                            player.chat("/perm group " + sqlGroup.getName().complete() + " info");
                        }
                );
            }
            primePlayer.thePlayer().openInventory(guiBuilder.build(Example.getInstance()));
            primePlayer.thePlayer().updateInventory();
        });
        return true;
    }
}
