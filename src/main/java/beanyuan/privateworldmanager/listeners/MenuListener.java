package beanyuan.privateworldmanager.listeners;

import beanyuan.privateworldmanager.PrivateWorldManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class MenuListener implements Listener {

    PrivateWorldManager plugin;

    public MenuListener(PrivateWorldManager plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMenuOpen(InventoryOpenEvent event) {
        // check if it's world setting menu
        if (event.getView().getTitle().equals(plugin.constant.worldSettingMenuTitle)) {

        }
    }
}
