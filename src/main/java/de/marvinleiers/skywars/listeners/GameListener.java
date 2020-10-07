package de.marvinleiers.skywars.listeners;

import de.grindmc.mc.system.PaperSpigot.MiniGamesAPI.countdown.CountDownFinishEvent;
import de.grindmc.mc.system.PaperSpigot.MiniGamesAPI.countdown.Countdown;
import de.grindmc.mc.system.PaperSpigot.MiniGamesAPI.ending.GameEndingEvent;
import de.grindmc.mc.system.PaperSpigot.MiniGamesAPI.team.Team;
import de.grindmc.mc.system.PaperSpigot.MiniGamesAPI.utils.GameState;
import de.marvinleiers.skywars.SkyWars;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class GameListener implements Listener
{
    @EventHandler
    public void onDamage(EntityDamageEvent event)
    {
        if (SkyWars.getGame().getGameState().toString().contains("LOBBY"))
            event.setCancelled(true);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event)
    {
        if (SkyWars.getGame().getGameState().toString().contains("LOBBY") || SkyWars.getGame().getGameState() == GameState.ENDING)
            event.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event)
    {
        if (SkyWars.getGame().getGameState().toString().contains("LOBBY") || SkyWars.getGame().getGameState() == GameState.ENDING)
            event.setCancelled(true);
    }

    @EventHandler
    public void onStartCountdownFinish(CountDownFinishEvent event)
    {
        if (SkyWars.getGame().getGameState() != GameState.INGAME)
            return;

        for (Team team : SkyWars.getPlugin().getTeams())
        {
            team.teleportToSpawn();

            for (Player player : team.getPlayers())
            {
                player.getInventory().clear();
                player.setHealth(player.getHealthScale());
                player.setFoodLevel(20);
            }
        }
    }

    private boolean ending = false;

    @EventHandler
    public void onGameEnding(GameEndingEvent event)
    {
        SkyWars.getGame().setGameState(GameState.ENDING);

        Countdown countdown = new Countdown(new int[]{1, 2, 3, 4, 5, 10, 15}, 15);
        countdown.setUseXp(true);
        countdown.setUseTitle(false);
        countdown.startForAll();

        for (Player all : Bukkit.getOnlinePlayers())
            all.setGameMode(GameMode.ADVENTURE);

        ending = true;
    }

    @EventHandler
    public void onEndingCountdownFinish(CountDownFinishEvent event)
    {
        if (SkyWars.getGame().getGameState() != GameState.ENDING)
            return;

        if (!ending)
            return;

        ending = false;

        for (Player all : Bukkit.getOnlinePlayers())
            all.kickPlayer("§cSpiel beendet!");

        Bukkit.getScheduler().runTaskLater(SkyWars.getPlugin(), () -> Bukkit.shutdown(), 20);
    }
}
