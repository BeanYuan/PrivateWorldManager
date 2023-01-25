package beanyuan.privateworldmanager.listeners;

import beanyuan.privateworldmanager.managers.WorldManager;
import beanyuan.privateworldmanager.util.Message;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

public class WorldListener implements Listener {
    @EventHandler
    public void onPlayerBreakBlock(BlockBreakEvent event) {
        Player breaker = event.getPlayer();
        World currentWorld = event.getBlock().getWorld();
        List<Player> whiteList = WorldManager.getBuildWhiteList(currentWorld);

        // whether this player have permission to break
        if (!whiteList.contains(breaker)) {
            event.setCancelled(true);
            breaker.sendMessage(Message.breakerInvalidPermission);
        }
    }

    @EventHandler
    public void onPlayerPlaceBlock(BlockPlaceEvent event) {
        Player breaker = event.getPlayer();
        World currentWorld = event.getBlock().getWorld();
        List<Player> whiteList = WorldManager.getBuildWhiteList(currentWorld);

        // whether this player have permission to place
        if (!whiteList.contains(breaker)) {
            event.setCancelled(true);
            breaker.sendMessage(Message.placerInvalidPermission);
        }
    }

    @EventHandler
    public void onPlayerSendWeCommand(PlayerCommandPreprocessEvent event) {
        Player breaker = event.getPlayer();
        World currentWorld = breaker.getWorld();
        List<Player> whiteList = WorldManager.getBuildWhiteList(currentWorld);

        // whether this player have permission to send commands
        if (!whiteList.contains(breaker)) {
            if (event.getMessage().contains("//")) {
                event.setCancelled(true);
                breaker.sendMessage(Message.commanderInvalidPermission);
            }
        }
    }
}
