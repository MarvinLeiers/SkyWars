package de.marvinleiers.skywars.listeners;

import de.marvinleiers.menuapi.MenuAPI;
import de.marvinleiers.skywars.SkyWars;
import de.marvinleiers.skywars.menus.SelectTeamMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class OpenTeamSelectorListener implements Listener
{
    @EventHandler
    public void onTeamSelectorOpen(PlayerInteractEvent event)
    {
        if (!event.hasItem() || !event.getItem().hasItemMeta() || !SkyWars.getGame().getGameState().toString().contains("LOBBY"))
            return;

        ItemStack compass = event.getItem();

        if (compass.getType() != Material.COMPASS)
            return;

        if (compass.getItemMeta().getDisplayName().contains("Team wählen"))
        {
            Player player = event.getPlayer();

            new SelectTeamMenu(MenuAPI.getMenuUserInformation(player)).open();
        }
    }
}
