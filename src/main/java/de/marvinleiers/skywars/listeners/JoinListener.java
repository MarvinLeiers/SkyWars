package de.marvinleiers.skywars.listeners;

import de.grindmc.mc.system.PaperSpigot.MiniGamesAPI.Game;
import de.grindmc.mc.system.PaperSpigot.MiniGamesAPI.team.Team;
import de.grindmc.mc.system.PaperSpigot.Utils.Utils;
import de.marvinleiers.skywars.SkyWars;
import de.marvinleiers.skywars.utils.GameUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Random;

public class JoinListener implements Listener
{
    private static Random random = new Random();
    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();

        if (GameUtils.isInLobby(player))
        {
            if (SkyWars.getPlugin().getGameConfig().isSet("lobby"))
            {
                Location lobby = SkyWars.getPlugin().getGameConfig().getLocation("lobby");
                player.teleport(lobby);

                /*
                    int x = random.nextInt(SkyWars.getPlugin().getTeams().size());
                    Team team = SkyWars.getPlugin().getTeams().get(x);
                    team.joinTeam(player);
                 */

                ItemStack selector = new ItemStack(Material.COMPASS);
                ItemMeta meta = selector.getItemMeta();
                meta.setDisplayName("§f§lTeam wählen");
                selector.setItemMeta(meta);

                player.setGameMode(GameMode.SURVIVAL);
                player.setFoodLevel(20);
                player.setHealth(player.getHealthScale());
                player.getInventory().clear();
                player.getInventory().setItem(4, selector);

                SkyWars.getGame().setMinPlayersReached();

                if (SkyWars.getGame().getMinPlayersReached())
                    GameUtils.start();
            }
            else
            {
                //TODO: Change to api
                player.sendMessage("§cLobby has not been set yet!");
            }
        }
        else
        {
            //TODO: Change to api
            player.sendMessage("§cadd spectator...");
        }
    }
}
