package beanyuan.privateworldmanager.papi;

import beanyuan.privateworldmanager.PrivateWorldManager;
import beanyuan.privateworldmanager.managers.WorldManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WorldStatusAPI extends PlaceholderExpansion {

    private final PrivateWorldManager plugin;

    public WorldStatusAPI(PrivateWorldManager plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "pwm";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Bean_Yuan";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true; // This is required or else PlaceholderAPI will unregister the Expansion on reload
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        int worldIndex = 0;
        if (params.contains("_world_") && params.contains("_status")) {
            worldIndex = Integer.parseInt(params.replace("_world_", "").replace("_status", ""));
        }

        if (worldIndex >= 0) {
            String worldName = "pWorld_" + player.getName() + "_" + worldIndex;
            World world = Bukkit.getWorld(worldName);
            int worldStatus = WorldManager.getWorldStatus(world);

            if (worldStatus == 0) {
                return plugin.constant.privateStatus;
            } else if (worldStatus == 1) {
                return plugin.constant.publicStatus;
            } else if (worldStatus == 2) {
                return plugin.constant.greenStatus;
            } else {
                return null;
            }
        }

        return null; // Placeholder is unknown by the Expansion
    }

    @Override
    public String onPlaceholderRequest(Player player, String params) {
        int worldIndex = 0;
        if (params.contains("_world_") && params.contains("_status")) {
            worldIndex = Integer.parseInt(params.replace("_world_", "").replace("_status", ""));
        } else {
            return null;
        }

        if (worldIndex >= 0) {
            String worldName = "pWorld_" + player.getName() + "_" + worldIndex;
            World world = Bukkit.getWorld(worldName);
            int worldStatus = WorldManager.getWorldStatus(world);

            if (worldStatus == 0) {
                return plugin.constant.privateStatus;
            } else if (worldStatus == 1) {
                return plugin.constant.publicStatus;
            } else if (worldStatus == 2) {
                return plugin.constant.greenStatus;
            } else {
                return null;
            }
        }

        return null; // Placeholder is unknown by the Expansion
    }
}
