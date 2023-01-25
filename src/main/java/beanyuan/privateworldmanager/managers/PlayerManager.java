package beanyuan.privateworldmanager.managers;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class PlayerManager {
    public static OfflinePlayer getOfflinePlayer(String name) {
        for(OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            if(player.getName().equals(name)) return player;
        }
        return null;
    }
}
