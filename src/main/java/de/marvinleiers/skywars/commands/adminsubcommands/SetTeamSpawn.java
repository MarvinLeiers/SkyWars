package de.marvinleiers.skywars.commands.adminsubcommands;

import de.marvinleiers.menuapi.MenuAPI;
import de.marvinleiers.skywars.commands.Subcommand;
import de.marvinleiers.skywars.menus.SetTeamSpawnsMenu;
import org.bukkit.entity.Player;

public class SetTeamSpawn extends Subcommand
{
    @Override
    public String getName()
    {
        return "teams";
    }

    @Override
    public String getDescription()
    {
        return "Setze die Team-Spawns";
    }

    @Override
    public String getSyntax()
    {
        return "/skywars teams";
    }

    @Override
    public void execute(Player player, String[] args)
    {
        new SetTeamSpawnsMenu(MenuAPI.getMenuUserInformation(player)).open();
    }
}
