package de.marvinleiers.skywars.commands.adminsubcommands;

import de.marvinleiers.skywars.SkyWars;
import de.marvinleiers.skywars.commands.Subcommand;
import org.bukkit.entity.Player;

public class SetSpectatorSpawn extends Subcommand
{
    @Override
    public String getName()
    {
        return "spectator";
    }

    @Override
    public String getDescription()
    {
        return "Setze den Spectator-Spawn";
    }

    @Override
    public String getSyntax()
    {
        return "/skywars spectator";
    }

    @Override
    public void execute(Player player, String[] args)
    {
        SkyWars.getPlugin().getGameConfig().setLocation("spectator", player.getLocation());
        SkyWars.getPlugin().getSpectator().setSpawn(player.getLocation());

        player.sendMessage("§aSpawn wurde gesetzt!");
    }
}
