package de.marvinleiers.skywars.menus;

import de.grindmc.mc.system.PaperSpigot.MiniGamesAPI.utils.GameState;
import de.marvinleiers.menuapi.Menu;
import de.marvinleiers.menuapi.MenuUserInformation;
import de.marvinleiers.skywars.SkyWars;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

public class SelectTeamMenu extends Menu
{
    public SelectTeamMenu(MenuUserInformation menuUserInformation)
    {
        super(menuUserInformation);
    }

    @Override
    public String getTitle()
    {
        return "Team auswählen";
    }

    @Override
    public int getSlots()
    {
        return 9;
    }

    @Override
    public void setItems()
    {
        inventory.addItem(makeItem(Material.BLUE_WOOL, "§9§lBlau"));
        inventory.addItem(makeItem(Material.RED_WOOL, "§c§lRot"));
        inventory.addItem(makeItem(Material.YELLOW_WOOL, "§e§lGelb"));
        inventory.addItem(makeItem(Material.GREEN_WOOL, "§2§lGrün"));
    }

    @Override
    public void handleClickActions(InventoryClickEvent inventoryClickEvent)
    {
        if (!SkyWars.getGame().getGameState().toString().contains("LOBBY"))
            return;

        String team = ChatColor.stripColor(inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName()).toLowerCase();

        if (team.equalsIgnoreCase("blau"))
            SkyWars.getPlugin().getTeamBlue().joinTeam(player);
        else if (team.equalsIgnoreCase("rot"))
            SkyWars.getPlugin().getTeamRed().joinTeam(player);
        else if (team.equalsIgnoreCase("gelb"))
            SkyWars.getPlugin().getTeamYellow().joinTeam(player);
        else if (team.equalsIgnoreCase("grün"))
            SkyWars.getPlugin().getTeamGreen().joinTeam(player);

        player.closeInventory();
    }
}
