package de.marvinleiers.skywars.commands.adminsubcommands;

import de.marvinleiers.skywars.SkyWars;
import de.marvinleiers.skywars.commands.Subcommand;
import org.bukkit.entity.Player;

public class SetLobby extends Subcommand
{
    @Override
    public String getName()
    {
        return "lobby";
    }

    @Override
    public String getDescription()
    {
        return "Setze die Lobby";
    }

    @Override
    public String getSyntax()
    {
        return "/skywars lobby";
    }

    @Override
    public void execute(Player player, String[] args)
    {
        SkyWars.getPlugin().getGameConfig().setLocation("lobby", player.getLocation());
        player.sendMessage("§aLobby wurde gesetzt!");
    }
}
