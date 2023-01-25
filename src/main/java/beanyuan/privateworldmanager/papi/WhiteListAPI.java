package beanyuan.privateworldmanager.papi;

import beanyuan.privateworldmanager.PrivateWorldManager;
import beanyuan.privateworldmanager.managers.WorldManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WhiteListAPI extends PlaceholderExpansion {
    private final PrivateWorldManager plugin;

    public WhiteListAPI(PrivateWorldManager plugin) {
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
    public String onRequest(OfflinePlayer player, String params) {
        return null; // Placeholder is unknown by the Expansion
    }

    @Override
    public String onPlaceholderRequest(Player player, String params) {
        if (player != null) {
            int worldIndex = 0;
            if (params.contains("_world_") && params.contains("_white_list")) {
                worldIndex = Integer.parseInt(params.replace("_world_", "").replace("_spawn_location", ""));
            } else {
                return null;
            }

            if (worldIndex >= 0) {
                String worldName = "pWorld_" + player.getName() + "_" + worldIndex;
                World world = Bukkit.getWorld(worldName);
                return WorldManager.formatPlayerList(WorldManager.getVisitWhiteList(world));
            }
        }


        return null; // Placeholder is unknown by the Expansion
    }
}
