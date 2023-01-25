package beanyuan.privateworldmanager.sql;

import beanyuan.privateworldmanager.PrivateWorldManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlGetter {

    private PrivateWorldManager plugin;
    private String tableName;

    public SqlGetter(PrivateWorldManager plugin, String tableName) {
        this.plugin = plugin;
        this.tableName = tableName;
    }

    public void createTable() {
        PreparedStatement ps = null;
        try {
            if (tableName.equalsIgnoreCase("player_data")) {
                ps = plugin.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS player_data " +
                        "(PLAYER VARCHAR(100),CREATE_TIMES INT(100),GREEN INT(100))");
            } else if (tableName.equalsIgnoreCase("world_data")) {
                ps = plugin.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS world_data " +
                        "(WORLD VARCHAR(100),OWNER VARCHAR(100),VISIT_WHITE_LIST VARCHAR(100),BLACK_LIST VARCHAR(100)," +
                        "BUILD_WHITE_LIST VARCHAR(100),VISIBLE INT(100),SPAWN_LOCATION VARCHAR(100),WORLD_TYPE VARCHAR(100))");

            }
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createPlayer(Player player) {
        try {
            String playerName = player.getPlayerListName();
            if (!exists(player)) {
                PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("INSERT IGNORE INTO player_data" +
                        " (PLAYER) VALUES (?)");
                ps.setString(1, playerName);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createWorld(World world) {
        try {
            String worldName = world.getName();
            if (!exists(world)) {
                PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("INSERT IGNORE INTO world_data" +
                        " (WORLD) VALUES (?)");
                ps.setString(1, worldName);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean exists(Player player) {
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT * FROM player_data WHERE PLAYER=?");
            ps.setString(1, player.getName());

            ResultSet results = ps.executeQuery();
            if (results.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean exists(World world) {
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT * FROM world_data WHERE WORLD=?");
            ps.setString(1, world.getName());

            ResultSet results = ps.executeQuery();
            if (results.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void deleteWorld(World world) {
        try {
            if (exists(world)) {
                PreparedStatement st = plugin.SQL.getConnection().prepareStatement("DELETE FROM world_data WHERE WORLD = ?");
                st.setString(1, world.getName());
                st.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getWorlds() {
        List<String> worldNames = new ArrayList<>();

        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT WORLD FROM world_data");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                worldNames.add(rs.getString("WORLD"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return worldNames;
    }

    public void setOwner(World world, Player player) {
        try {
            if (!exists(world)) {
                createWorld(world);
            }
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE world_data SET OWNER=? WHERE WORLD=?");
            ps.setString(1, player.getName());
            ps.setString(2, world.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getOwner(World world) {
        try {
            if (!exists(world)) {
                createWorld(world);
            }
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT OWNER FROM world_data WHERE WORLD=?");
            ps.setString(1, world.getName());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("OWNER");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setVisible(World world, int visible) {
        try {
            if (!exists(world)) {
                createWorld(world);
            }
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE world_data SET VISIBLE=? WHERE WORLD=?");
            ps.setInt(1, visible);
            ps.setString(2, world.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getVisible(World world) {
        try {
            if (!exists(world)) {
                createWorld(world);
            }
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT VISIBLE FROM world_data WHERE WORLD=?");
            ps.setString(1, world.getName());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("VISIBLE");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void setBlackList(World world, String blackList) {
        try {
            if (!exists(world)) {
                createWorld(world);
            }
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE world_data SET BLACK_LIST=? WHERE WORLD=?");
            ps.setString(1, blackList);
            ps.setString(2, world.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getBlackList(World world) {
        try {
            if (!exists(world)) {
                createWorld(world);
            }
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT BLACK_LIST FROM world_data WHERE WORLD=?");
            ps.setString(1, world.getName());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("BLACK_LIST");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setVisitWhiteList(World world, String whiteList) {
        try {
            if (!exists(world)) {
                createWorld(world);
            }
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE world_data SET VISIT_WHITE_LIST=? WHERE WORLD=?");
            ps.setString(1, whiteList);
            ps.setString(2, world.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getVisitWhiteList(World world) {
        try {
            if (!exists(world)) {
                createWorld(world);
            }
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT VISIT_WHITE_LIST FROM world_data WHERE WORLD=?");
            ps.setString(1, world.getName());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("VISIT_WHITE_LIST");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setBuildWhiteList(World world, String whiteList) {
        try {
            if (!exists(world)) {
                createWorld(world);
            }
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE world_data SET BUILD_WHITE_LIST=? WHERE WORLD=?");
            ps.setString(1, whiteList);
            ps.setString(2, world.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getBuildWhiteList(World world) {
        try {
            if (!exists(world)) {
                createWorld(world);
            }
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT BUILD_WHITE_LIST FROM world_data WHERE WORLD=?");
            ps.setString(1, world.getName());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("BUILD_WHITE_LIST");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setSpawnLocation(World world, double x, double y, double z) {
        try {
            if (!exists(world)) {
                createWorld(world);
            }
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE world_data SET SPAWN_LOCATION=? WHERE WORLD=?");
            ps.setString(1, x + "," + y + "," + z);
            ps.setString(2, world.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getSpawnLocation(World world) {
        try {
            if (!exists(world)) {
                createWorld(world);
            }
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT SPAWN_LOCATION FROM world_data WHERE WORLD=?");
            ps.setString(1, world.getName());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("SPAWN_LOCATION");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setWorldType(World world, String worldType) {
        try {
            if (!exists(world)) {
                createWorld(world);
            }
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE world_data SET WORLD_TYPE=? WHERE WORLD=?");
            ps.setString(1, worldType);
            ps.setString(2, world.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getWorldType(String worldName) {
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT WORLD_TYPE FROM world_data WHERE WORLD=?");
            ps.setString(1, worldName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("WORLD_TYPE");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Player> getPlayers() {
        List<Player> playerList = new ArrayList<>();

        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT PLAYER FROM world_data");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Player player = Bukkit.getPlayer(rs.getString("PLAYER"));
                if (player != null) {
                    playerList.add(player);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return playerList;
    }

    public void setCreateTimes(Player player, int times) {
        try {
            if (!exists(player)) {
                createPlayer(player);
            }
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE player_data SET CREATE_TIMES=? WHERE PLAYER=?");
            ps.setInt(1, times);
            ps.setString(2, player.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getCreateTimes(Player player) {
        try {
            if (!exists(player)) {
                createPlayer(player);
            }
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT CREATE_TIMES FROM player_data WHERE PLAYER=?");
            ps.setString(1, player.getName());
            ResultSet rs = ps.executeQuery();
            int times = 0;
            if (rs.next()) {
                times = rs.getInt("CREATE_TIMES");
                return times;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void setGreen(Player player, int green) {
        try {
            if (!exists(player)) {
                createPlayer(player);
            }
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE player_data SET GREEN=? WHERE PLAYER=?");
            ps.setInt(1, green);
            ps.setString(2, player.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getGreen(Player player) {
        try {
            if (!exists(player)) {
                createPlayer(player);
            }
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT GREEN FROM player_data WHERE PLAYER=?");
            ps.setString(1, player.getName());
            ResultSet rs = ps.executeQuery();
            int green = 0;
            if (rs.next()) {
                green = rs.getInt("GREEN");
                return green;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
