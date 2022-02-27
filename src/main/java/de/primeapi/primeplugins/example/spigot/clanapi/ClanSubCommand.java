package de.primeapi.primeplugins.example.spigot.clanapi;

import de.primeapi.primeplugins.example.spigot.Example;
import de.primeapi.primeplugins.spigotapi.api.PrimePlayer;
import de.primeapi.primeplugins.spigotapi.api.command.SubCommand;
import de.primeapi.primeplugins.spigotapi.api.info.Info;
import de.primeapi.primeplugins.spigotapi.api.plugins.clan.ClanAPI;
import de.primeapi.primeplugins.spigotapi.gui.GUIBuilder;
import de.primeapi.primeplugins.spigotapi.gui.itembuilder.ItemBuilder;
import de.primeapi.primeplugins.spigotapi.sql.SQLPlayer;
import de.primeapi.primeplugins.spigotapi.sql.clan.SQLPlayerAllocation;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Jonas D. Exceptionpilot
 * created on 27.02.2022
 * created for PrimePlugins-Examples
 */

public class ClanSubCommand extends SubCommand {

    public ClanSubCommand() {
        super(null);
    }

    @Info(info = "/examples <Clan>")
    @Override
    public boolean execute(PrimePlayer primePlayer, String[] strings) {
        SQLPlayer sqlPlayer = SQLPlayer.loadPlayerByName(primePlayer.getPlayer().getName()).complete();
        ClanAPI.getInstance().getClanFromPlayer(sqlPlayer).submit(sqlClan -> {
            GUIBuilder guiBuilder = new GUIBuilder(3 * 9, "§8»§e §aClan-Example");
            for (int i = 0; i < 3 * 9; i++) {
                guiBuilder.addItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, (byte) 8).setDisplayName(" ").build());
            }
            guiBuilder.addItem(10, new ItemBuilder(Material.GOLD_NUGGET)
                            .setDisplayName("§8»§e §cClan Tag")
                            .addLore("§e§8»§a " + sqlClan.getRealname().complete()).build(),
                    ((player, itemStack) -> {
                        player.sendMessage("§5");
                        player.closeInventory();
                        player.sendMessage("§aClanAPI §7● §eTag §8»§e " + sqlClan.getTag().complete());
                    })
            );
            guiBuilder.addItem(13, new ItemBuilder(Material.GOLD_NUGGET)
                    .setDisplayName("§8»§e §eDein Clan")
                    .addLore("§e§8»§a " + sqlClan.getRealname().complete()).build()
            );
            guiBuilder.addItem(16, new ItemBuilder(Material.PAPER)
                    .setDisplayName("§8»§e §aInformation")
                    .build(),
                    ((player, itemStack) -> {
                        player.sendMessage("§5");
                        player.closeInventory();
                        player.sendMessage("§aClanAPI §7● §cDu bist im Clan §8» [§e" + sqlClan.getName().complete() + "§8]");
                        player.sendMessage("§aClanAPI §7● §6Informationen über deinen Clan!");
                        player.sendMessage("§7● §eTag §8»§e " + sqlClan.getTag().complete());
                        player.sendMessage("§7● §eName §8»§e " + sqlClan.getName().complete());
                        player.sendMessage("§7● §eRealname §8»§e " + sqlClan.getRealname().complete());
                        player.sendMessage("§7● §eId §8»§e " + sqlClan.getId());
                        player.sendMessage("§7● §eCoins §8»§e " + sqlClan.getCoins().complete());
                        List<SQLPlayerAllocation> sqlPlayerAllocations = sqlClan.getMembers().complete();
                        List<String> list = new ArrayList<>();
                        for (SQLPlayerAllocation sqlPlayerAllocation : sqlPlayerAllocations) {
                            SQLPlayer target = new SQLPlayer(sqlPlayerAllocation.getUUID().complete());
                            list.add(target.retrieveName().complete());
                        }
                        player.sendMessage("§7● §eMembers §8»§e " + Arrays.toString(new List[]{list}));
                    })
            );
            primePlayer.thePlayer().openInventory(guiBuilder.build(Example.getInstance()));
            primePlayer.thePlayer().updateInventory();
        });
        return true;
    }
}
