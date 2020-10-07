package de.marvinleiers.skywars.commands.adminsubcommands;

import de.marvinleiers.skywars.SkyWars;
import de.marvinleiers.skywars.commands.Subcommand;
import de.marvinleiers.skywars.utils.GameUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;

public class AddChest extends Subcommand
{
    @Override
    public String getName()
    {
        return "addchest";
    }

    @Override
    public String getDescription()
    {
        return "Kiste hinzufügen";
    }

    @Override
    public String getSyntax()
    {
        return "/skywars addchest";
    }

    @Override
    public void execute(Player player, String[] args)
    {
        Set<Material> nullMap = null;
        Block chest = player.getTargetBlock(nullMap, 6);

        if (chest.getType() != Material.CHEST)
        {
            //TODO: api
            player.sendMessage("§cDas ist keine Truhe!");
            return;
        }

        SkyWars.getPlugin().getGameConfig().setLocation("chests." + UUID.randomUUID(), chest.getLocation());
        GameUtils.fillChest(chest.getLocation());

        //TODO: api
        player.sendMessage("§aTruhe wurde hinzugefügt!");
    }
}
