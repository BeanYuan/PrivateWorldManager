package beanyuan.privateworldmanager.papi;

import beanyuan.privateworldmanager.PrivateWorldManager;
import beanyuan.privateworldmanager.managers.WorldManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PublicWorldNumberAPI extends PlaceholderExpansion {
    private final PrivateWorldManager plugin;

    public PublicWorldNumberAPI(PrivateWorldManager plugin) {
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
        return null; // Placeholder is unknown by the Expansion
    }

    @Override
    public String onPlaceholderRequest(Player player, String params) {
        if (player != null) {
            if (params.equalsIgnoreCase("_public_worlds")) {
                List<String> worldNames = PrivateWorldManager.worldData.getWorlds();
                int publicWorldNumber = 0;

                for (String worldName : worldNames) {
                    World world = Bukkit.getWorld(worldName);
                    if (WorldManager.getWorldStatus(world) == 1) {
                        publicWorldNumber ++;
                    }
                }

                return String.valueOf(publicWorldNumber);
            }
        }


        return null; // Placeholder is unknown by the Expansion
    }
}
