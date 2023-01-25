package beanyuan.privateworldmanager;

import beanyuan.privateworldmanager.commands.Commands;
import beanyuan.privateworldmanager.managers.WorldManager;
import beanyuan.privateworldmanager.papi.*;
import beanyuan.privateworldmanager.sql.MySql;
import beanyuan.privateworldmanager.sql.SqlGetter;
import beanyuan.privateworldmanager.util.Constant;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public final class PrivateWorldManager extends JavaPlugin {

    public static Logger log;
    public MySql SQL;
    public static SqlGetter playerData;
    public static SqlGetter worldData;
    public Constant constant;

    @Override
    public void onEnable() {
        // Plugin startup logic
        log = this.getLogger();

        // 连接数据库
        this.SQL = new MySql(this);
        playerData = new SqlGetter(this, Constant.sqlPlayerDataTable);
        worldData = new SqlGetter(this, Constant.sqlWorldDataTable);

        try {
            SQL.connect();
        } catch (ClassNotFoundException | SQLException e) {
            log.info("数据库连接失败！");
        }

        if (SQL.isConnected()) {
            log.info("数据库连接成功！");
            playerData.createTable();
            worldData.createTable();
        }

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        initialCommands();
        initialValue(this);
        initialPAPI(this);
        createBackupWorldDirectory();
        loadAllWorlds();

        // Small check to make sure that PlaceholderAPI is installed

    }

    public void loadAllWorlds() {
        List<String> worldNames = worldData.getWorlds();

        if (worldNames.size() > 0) {
            for (String worldName : worldNames) {
                String worldType = worldData.getWorldType(worldName);
                WorldManager.loadWorld(worldName, worldType);
            }
        }
    }

    public void initialPAPI(PrivateWorldManager plugin) {
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new WorldStatusAPI(plugin).register();
            new WorldNumberAPI(plugin).register();
            new WorldSpawnLocationAPI(plugin).register();
            new BlackListAPI(plugin).register();
            new WhiteListAPI(plugin).register();
            new PublicWorldNumberAPI(plugin).register();
            new PlayerCreateTimeAPI(plugin).register();
        }
    }

    public void initialValue(PrivateWorldManager plugin) {
        constant = new Constant(plugin);
    }

    public void initialCommands() {
        getCommand("pwm").setExecutor(new Commands(this));
    }

    public void createBackupWorldDirectory() {
        File directory = new File(getDataFolder(), Constant.backupWorldDirectory);

        if (!directory.exists()) { directory.mkdir(); }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
