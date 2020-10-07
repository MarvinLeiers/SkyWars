package de.marvinleiers.skywars.commands;

import de.marvinleiers.skywars.commands.adminsubcommands.SetLobby;
import de.marvinleiers.skywars.commands.adminsubcommands.SetSpectatorSpawn;
import de.marvinleiers.skywars.commands.adminsubcommands.SetTeamSpawn;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class AdminCommands implements CommandExecutor
{
    private ArrayList<Subcommand> subcommands = new ArrayList<>();

    public AdminCommands()
    {
        subcommands.add(new SetTeamSpawn());
        subcommands.add(new SetLobby());
        subcommands.add(new SetSpectatorSpawn());
        subcommands.add(new AddChest());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!(sender instanceof Player))
        {
            sender.sendMessage("§cOnly for players!");
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 1)
        {
            for (Subcommand subcommand : subcommands)
                player.sendMessage("§c" + subcommand.getSyntax() + "§e- §7" + subcommand.getDescription());

            return true;
        }

        for (Subcommand subcommand : subcommands)
        {
            if (subcommand.getName().equalsIgnoreCase(args[0]))
            {
                subcommand.execute(player, args);
                return true;
            }
        }

        for (Subcommand subcommand : subcommands)
            player.sendMessage("§7" + subcommand.getSyntax() + "§e- §c" + subcommand.getDescription());

        return true;
    }
}
