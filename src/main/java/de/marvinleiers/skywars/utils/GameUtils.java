package de.marvinleiers.skywars.utils;

import de.grindmc.mc.system.PaperSpigot.MiniGamesAPI.countdown.Countdown;
import de.grindmc.mc.system.PaperSpigot.MiniGamesAPI.team.Team;
import de.grindmc.mc.system.PaperSpigot.MiniGamesAPI.utils.GameState;
import de.marvinleiers.skywars.SkyWars;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.Random;

public class GameUtils
{
    private static Random random = new Random();

    public static void fillChest(Location loc)
    {
        Block block = loc.getBlock();

        if (block.getType() != Material.CHEST)
            return;


        Material[] weaponsArmor = new Material[] {
                Material.IRON_SWORD,
                Material.STONE_AXE,
                Material.LEATHER_BOOTS,
                Material.CHAINMAIL_CHESTPLATE,
                Material.IRON_LEGGINGS,
        };

        Material[] items = new Material[] {
                Material.BREAD,
                Material.COOKED_BEEF,
                Material.COOKED_PORKCHOP,
                Material.COBWEB
        };

        Chest chest = (Chest) block.getState();
        chest.getBlockInventory().clear();

        int[] limits = {4, 4, 4, 5, 5, 6};
        int limit = limits[random.nextInt(limits.length)];

        for (int i = 0; i < limit; i++)
        {
            int coinFlip = random.nextInt(2);

            if (coinFlip == 1)
                chest.getBlockInventory().setItem(random.nextInt(chest.getBlockInventory().getSize()), new ItemStack(weaponsArmor[random.nextInt(weaponsArmor.length)]));
            else
            {
                ItemStack item = new ItemStack(items[random.nextInt(items.length)]);
                item.setAmount(random.nextInt(3) + 1);

                chest.getBlockInventory().setItem(random.nextInt(chest.getBlockInventory().getSize()), item);
            }
        }

        System.out.println("filled chest at " + chest.getLocation());
    }
    public static boolean isInLobby(Player player)
    {
        if (SkyWars.getGame().getGameState() == GameState.LOBBY || SkyWars.getGame().getGameState() == GameState.LOBBY_NOE_PLAYERS)
            return true;

        return false;
    }

    public static void start()
    {
        SkyWars.getGame().setGameState(GameState.INGAME);

        try
        {
            for (Map.Entry<String, Object> entry : SkyWars.getPlugin().getGameConfig().getSection("chests").getValues(false).entrySet())
            {
                Location location = SkyWars.getPlugin().getGameConfig().getLocation("chests." + entry.getKey());
                GameUtils.fillChest(location);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        outerLoop:
        {
            for (Player all : Bukkit.getOnlinePlayers())
            {
                if (SkyWars.getPlugin().getSpectator().containsPlayer(all))
                {
                    System.out.println(all.getName() + " ist spectator");
                    continue;
                }

                if (!hasTeam(all))
                {
                    for (Team team : SkyWars.getPlugin().getTeams())
                    {
                        if (team.getCurrentSize() < team.getMaxSize())
                        {
                            team.joinTeam(all);
                            break;
                        }
                    }
                }
            }
        }

        Countdown countdown = new Countdown(new int[] { 1, 2, 3, 4, 5, 10, 20, 30 }, 30);
        countdown.setUseTitle(true);
        countdown.setUseXp(true);
        countdown.startForAll();
    }

    public static boolean hasTeam(Player player)
    {
        for (Team team : SkyWars.getPlugin().getTeams())
        {
            if (team.getPlayers().contains(player))
                return true;
        }

        return false;
    }
}
