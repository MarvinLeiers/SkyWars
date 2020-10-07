package de.marvinleiers.skywars;

import de.grindmc.mc.system.PaperSpigot.MiniGamesAPI.Game;
import de.grindmc.mc.system.PaperSpigot.MiniGamesAPI.spectator.Spectator;
import de.grindmc.mc.system.PaperSpigot.MiniGamesAPI.team.Team;
import de.marvinleiers.customconfig.CustomConfig;
import de.marvinleiers.menuapi.MenuAPI;
import de.marvinleiers.skywars.commands.AdminCommands;
import de.marvinleiers.skywars.listeners.GameListener;
import de.marvinleiers.skywars.listeners.JoinListener;
import de.marvinleiers.skywars.listeners.OpenTeamSelectorListener;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class SkyWars extends JavaPlugin
{
    /**
     * TODO:
     *  -/sw chest, um truhen hinzuzufügen
     */

    private static Game game;

    private ArrayList<Team> teams;
    private CustomConfig gameConfig;

    private Spectator spectator;
    private Team teamBlue;
    private Team teamRed;
    private Team teamYellow;
    private Team teamGreen;

    @Override
    public void onEnable()
    {
        MenuAPI.setUp(this);

        gameConfig = new CustomConfig(this.getDataFolder().getPath() + "/config.yml");
        spectator = new Spectator();
        teams = new ArrayList<>();
        game = new Game();

        game.setMiniGameName("SkyWars");
        //TODO change minplayers to 5
        game.setMinPlayers(2);
        //TODO: game.setMaxPlayers(16);
        game.setSpectatorOnDeath(true);
        game.setAnimals(false);
        game.setMobs(false);
        game.setVotingEnabled(false);

        spectator.setGameMode(GameMode.ADVENTURE);
        spectator.setAllowFlight(true);
        spectator.setCanInteract(false);
        spectator.setColor(ChatColor.GRAY);

        if (getGameConfig().isSet("spectator"))
            spectator.setSpawn(getGameConfig().getLocation("spectator"));

        teamBlue = new Team("Blau");
        teamRed = new Team("Rot");
        teamYellow = new Team("Gelb");
        teamGreen = new Team("Grün");

        setUpTeam(teamBlue, ChatColor.BLUE);
        setUpTeam(teamRed, ChatColor.RED);
        setUpTeam(teamYellow, ChatColor.YELLOW);
        setUpTeam(teamGreen, ChatColor.GREEN);

        this.getServer().getPluginManager().registerEvents(new JoinListener(), this);
        this.getServer().getPluginManager().registerEvents(new GameListener(), this);
        this.getServer().getPluginManager().registerEvents(new OpenTeamSelectorListener(), this);
        this.getCommand("skywars").setExecutor(new AdminCommands());
    }
    
    private void setUpTeam(Team team, ChatColor color)
    {
        team.setCanInteract(true);
        team.setCanBreak(true);
        team.setCanPlace(true);
        team.setCanRespawn(false);
        team.setFriendlyFire(false);
        team.setColor(color);
        team.setMinSize(1);
        team.setMaxSize(3);

        Location spawn = getGameConfig().getLocation("teams." + team.getTeamname().toLowerCase() + ".spawn");
        System.out.println("Spawn for team " + team.getTeamname().toLowerCase() + " " + spawn);

        if (spawn != null)
            team.setSpawn(spawn);

        teams.add(team);
    }

    public Spectator getSpectator()
    {
        return spectator;
    }

    public Team getTeamBlue()
    {
        return teamBlue;
    }

    public Team getTeamRed()
    {
        return teamRed;
    }

    public Team getTeamYellow()
    {
        return teamYellow;
    }

    public Team getTeamGreen()
    {
        return teamGreen;
    }

    public ArrayList<Team> getTeams()
    {
        return teams;
    }

    public static Game getGame()
    {
        return game;
    }

    public CustomConfig getGameConfig()
    {
        return gameConfig;
    }

    public static SkyWars getPlugin()
    {
        return getPlugin(SkyWars.class);
    }
}
