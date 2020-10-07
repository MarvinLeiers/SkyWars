package de.marvinleiers.skywars.menus;

import de.marvinleiers.menuapi.Menu;
import de.marvinleiers.menuapi.MenuUserInformation;
import de.marvinleiers.skywars.SkyWars;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class SetTeamSpawnsMenu extends Menu
{
    public SetTeamSpawnsMenu(MenuUserInformation menuUserInformation)
    {
        super(menuUserInformation);
    }

    @Override
    public String getTitle()
    {
        return "Set team zone";
    }

    @Override
    public int getSlots()
    {
        return 9;
    }

    @Override
    public void setItems()
    {
        ItemStack blue = makeItem(Material.BLUE_WOOL, "§9§lBlau");
        ItemStack red = makeItem(Material.RED_WOOL, "§c§lRot");
        ItemStack yellow = makeItem(Material.YELLOW_WOOL, "§e§lGelb");
        ItemStack green = makeItem(Material.GREEN_WOOL, "§2§lGrün");

        inventory.addItem(blue);
        inventory.addItem(red);
        inventory.addItem(yellow);
        inventory.addItem(green);
    }

    @Override
    public void handleClickActions(InventoryClickEvent inventoryClickEvent)
    {
        String rawName = inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName();
        String team = ChatColor.stripColor(rawName).toLowerCase();

        SkyWars.getPlugin().getGameConfig().setLocation("teams." + team + ".spawn", player.getLocation());

        player.sendMessage("§7Spawn für Team " + rawName + " §7wurde gesetzt.");
        player.closeInventory();

        if (team.contains("blau"))
            SkyWars.getPlugin().getTeamBlue().setSpawn(player.getLocation());
        else if (team.contains("rot"))
            SkyWars.getPlugin().getTeamRed().setSpawn(player.getLocation());
        else if (team.contains("gelb"))
            SkyWars.getPlugin().getTeamYellow().setSpawn(player.getLocation());
        else if (team.contains("grün"))
            SkyWars.getPlugin().getTeamGreen().setSpawn(player.getLocation());
    }
}