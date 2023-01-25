package beanyuan.privateworldmanager.managers;

import beanyuan.privateworldmanager.PrivateWorldManager;
import beanyuan.privateworldmanager.util.Constant;
import org.bukkit.*;
import org.bukkit.entity.Player;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WorldManager {
    public static void createWorld(Player player, String worldType) {
        String worldName = "pWorld_" + player.getDisplayName() + "_" + PrivateWorldManager.playerData.getCreateTimes(player);

        WorldCreator wc = new WorldCreator(worldName);
        wc.environment(World.Environment.NORMAL);
        wc.type(WorldType.valueOf(worldType));

        wc.createWorld();

        World world = Bukkit.getWorld(worldName);

        initialWorld(world, player, worldType);
    }

    public static void initialWorld(World world, Player player, String worldType) {
        PrivateWorldManager.playerData.setCreateTimes(player, PrivateWorldManager.playerData.getCreateTimes(player) + 1);
        PrivateWorldManager.worldData.setOwner(world, player);
        PrivateWorldManager.worldData.setVisitWhiteList(world, player.getName());
        PrivateWorldManager.worldData.setBuildWhiteList(world, player.getName());
        PrivateWorldManager.worldData.setVisible(world, 1);
        PrivateWorldManager.worldData.setSpawnLocation(world, 0, 4, 0);
        PrivateWorldManager.worldData.setWorldType(world, worldType);
    }

    public static void loadWorld(String worldName, String worldType) {
        WorldCreator wc = new WorldCreator(worldName);
        wc.environment(World.Environment.NORMAL);
        wc.type(WorldType.valueOf(worldType));

        wc.createWorld();
    }

    public static void unloadWorld(World world, boolean save) {
        // kick all player to main world
        Location location = new Location(Bukkit.getWorld("world"), 100, 100, 100);
        if (world.getPlayers().size() > 0) {
            for (Player player : world.getPlayers()) {
                player.teleport(location);
            }
        }

        Bukkit.unloadWorld(world, save);
    }

    public static boolean teleportWorld(Player player, String worldID) {
        // whether this player is in the whitelist
        World world = getWorldFromWorldID(worldID);
        List<Player> whiteList = getVisitWhiteList(world);
        List<Player> blackList = getBlackList(world);
        List<Player> greenList = getGreenList();
        // check world status
        if (getWorldStatus(world) == 0) {
            // private status
            if (whiteList.contains(player) && !blackList.contains(player)) {
                Location location = getSpawnLocationFromWorld(world);
                player.teleport(location);

                return true;
            }
        } else if (getWorldStatus(world) == 1) {
            // public status
            if (!blackList.contains(player)) {
                Location location = getSpawnLocationFromWorld(world);
                player.teleport(location);

                return true;
            }
        } else if (getWorldStatus(world) == 2) {
            // green status
            if (greenList.contains(player) && !blackList.contains(player)) {
                Location location = getSpawnLocationFromWorld(world);
                player.teleport(location);

                return true;
            }
        }

        return false;
    }

    public static void deleteWorld(World world, Player owner) {
        unloadWorld(world, false);

        try {
            FileUtils.deleteDirectory(world.getWorldFolder());
        } catch (IOException e) {
            e.printStackTrace();
        }

        PrivateWorldManager.worldData.deleteWorld(world);
        PrivateWorldManager.playerData.setCreateTimes(owner, PrivateWorldManager.playerData.getCreateTimes(owner) - 1);
    }

    public static Boolean setWorldVisible(World world, int number) {
        if (number == 0 || number == 1) {
            PrivateWorldManager.worldData.setVisible(world, number);
            return true;
        }
        return false;
    }

    public static Boolean setWorldTime(World world, long timeTick) {
        if (timeTick >= 0) {
            world.setTime(timeTick);
            return true;
        }
        return false;
    }

    public static Boolean setWorldWeather(World world, String weather) {
        if (weather.equalsIgnoreCase("rainy")) {
            world.setStorm(true);

            return true;
        } else if (weather.equalsIgnoreCase("sun")) {
            world.setStorm(false);

            return true;
        }
        return false;
    }

    public static void setPlayerGreen(Player player) {
        PrivateWorldManager.playerData.setGreen(player, 1);
    }

    public static void addPlayerBuildWhiteList(Player player, World world) {
        String oldWhiteList = PrivateWorldManager.worldData.getBuildWhiteList(world);
        if (oldWhiteList == null) {
            PrivateWorldManager.worldData.setBuildWhiteList(world, player.getName());
        } else {
            PrivateWorldManager.worldData.setBuildWhiteList(world, oldWhiteList + "," + player.getName());
        }
    }

    public static void addPlayerBlackList(Player player, World world) {
        String oldWhiteList = PrivateWorldManager.worldData.getBlackList(world);
        if (oldWhiteList == null) {
            PrivateWorldManager.worldData.setBlackList(world, player.getName());
        } else {
            PrivateWorldManager.worldData.setBlackList(world, oldWhiteList + "," + player.getName());
        }
    }

    public static void addPlayerVisitWhiteList(Player player, World world) {
        String oldWhiteList = PrivateWorldManager.worldData.getVisitWhiteList(world);
        if (oldWhiteList == null) {
            PrivateWorldManager.worldData.setVisitWhiteList(world, player.getName());
        } else {
            PrivateWorldManager.worldData.setVisitWhiteList(world, oldWhiteList + "," + player.getName());
        }
    }

    public static int getWorldStatus(World world) {
        return PrivateWorldManager.worldData.getVisible(world);
    }

    public static Player getWorldOwner(World world) {
        return Bukkit.getPlayer(PrivateWorldManager.worldData.getOwner(world));
    }

    public static Player getPlayerFromWorldID(String worldID) {
        return Bukkit.getPlayer(worldID.split("\\.")[0]);
    }

    public static World getWorldFromWorldID(String worldID) {
        return Bukkit.getWorld("pWorld_" + worldID.split("\\.")[0] + "_" + worldID.split("\\.")[1]);
    }

    public static Location getSpawnLocationFromWorld(World world) {
        String locationString = PrivateWorldManager.worldData.getSpawnLocation(world);
        return new Location(world, Double.parseDouble(locationString.split(",")[0]),
                Double.parseDouble(locationString.split(",")[1]),
                Double.parseDouble(locationString.split(",")[2]));
    }

    public static String formatPlayerList(List<Player> playerList) {
        String playerListString = "";
        for (Player player : playerList) {
            if (playerListString == "") {
                playerListString = playerListString + player.getName();
            } else {
                playerListString = playerListString + ", " + player.getName();
            }
        }
        return playerListString;
    }

    public static List<Player> getBuildWhiteList(World world) {
        String sqlString = PrivateWorldManager.worldData.getBuildWhiteList(world);
        List<Player> playerList = new ArrayList<>();
        if (sqlString != null) {
            for (String playerName : sqlString.split(",")) {
                Player player = Bukkit.getPlayer(playerName);

                // whether this player is offline
                if (player != null) {
                    playerList.add(player);
                }
            }
        }

        return playerList;
    }

    public static List<Player> getBlackList(World world) {
        String sqlString = PrivateWorldManager.worldData.getBlackList(world);
        List<Player> playerList = new ArrayList<>();
        if (sqlString != null) {
            for (String playerName : sqlString.split(",")) {
                Player player = Bukkit.getPlayer(playerName);

                // whether this player is offline
                if (player != null) {
                    playerList.add(player);
                }
            }
        }

        return playerList;
    }

    public static List<Player> getVisitWhiteList(World world) {
        String sqlString = PrivateWorldManager.worldData.getVisitWhiteList(world);
        List<Player> playerList = new ArrayList<>();
        if (sqlString != null) {
            for (String playerName : sqlString.split(",")) {
                Player player = Bukkit.getPlayer(playerName);

                // whether this player is offline
                if (player != null) {
                    playerList.add(player);
                }
            }
        }

        return playerList;
    }

    public static List<Player> getGreenList() {
        List<Player> playerList = PrivateWorldManager.playerData.getPlayers();

        for (Player player : playerList) {
            if (PrivateWorldManager.playerData.getGreen(player) == 1) {
                playerList.add(player);
            }
        }

        return playerList;
    }

    public static void backupWorld(PrivateWorldManager plugin, World world) {
        String worldName = world.getName();
        File directory = new File(plugin.getServer().getWorldContainer().getAbsolutePath());
        File targetDirectory = new File(plugin.getDataFolder(), Constant.backupWorldDirectory + "/" + worldName);

        for (File file : directory.listFiles()) {
            if (file.getName().equalsIgnoreCase(worldName)) {
                try {
                    unloadWorld(world, true);

                    FileUtils.copyDirectory(file, targetDirectory);

                    loadWorld(worldName, PrivateWorldManager.worldData.getWorldType(worldName));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
