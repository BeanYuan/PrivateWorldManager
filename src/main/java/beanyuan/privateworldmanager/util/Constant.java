package beanyuan.privateworldmanager.util;

import beanyuan.privateworldmanager.PrivateWorldManager;

public class Constant {
    PrivateWorldManager plugin;

    public Constant(PrivateWorldManager plugin) {

        this.plugin = plugin;
        prefix = plugin.getConfig().getString("prefix");
        worldSettingMenuTitle = plugin.getConfig().getString("world_setting_menu.title");
        greenStatus = plugin.getConfig().getString("green_status");
        privateStatus = plugin.getConfig().getString("private_status");
        publicStatus = plugin.getConfig().getString("public_status");
    }

    public static String worldTypeFlat = "FLAT";

    public static String worldCreatePermission = "privateworldmanager.create";
    public static String worldCreateOtherPermission = "privateworldmanager.create.other";
    public static String worldVisitPermission = "privateworldmanager.visit";
    public static String worldVisitOtherPermission = "privateworldmanager.visit.other";
    public static String worldVisitListPermission = "privateworldmanager.visitlist";
    public static String worldVisitListOtherPermission = "privateworldmanager.visitlist.other";
    public static String worldBuildPermission = "privateworldmanager.build";
    public static String worldBuildOtherPermission = "privateworldmanager.build.other";
    public static String worldBlacklistPermission = "privateworldmanager.blacklist";
    public static String worldBlacklistOtherPermission = "privateworldmanager.blacklist.other";
    public static String worldBlacklistListPermission = "privateworldmanager.blacklist.list";
    public static String worldBlacklistListOtherPermission = "privateworldmanager.blacklist.list.other";
    public static String worldTpPermission = "privateworldmanager.tp";
    public static String worldTpOtherPermission = "privateworldmanager.tp.other";
    public static String worldWeatherPermission = "privateworldmanager.weather";
    public static String worldWeatherOtherPermission = "privateworldmanager.weather.other";
    public static String worldVisiblePermission = "privateworldmanager.visible";
    public static String worldVisibleOtherPermission = "privateworldmanager.visible.other";
    public static String worldInvisiblePermission = "privateworldmanager.invisible";
    public static String worldInvisibleOtherPermission = "privateworldmanager.invisible.other";
    public static String worldTimePermission = "privateworldmanager.time";
    public static String worldTimeOtherPermission = "privateworldmanager.time.other";
    public static String worldBackupPermission = "privateworldmanager.backup";
    public static String worldBackupOtherPermission = "privateworldmanager.backup.other";
    public static String worldBackupAllPermission = "privateworldmanager.backup.all";
    public static String worldRemovePermission = "privateworldmanager.remove";
    public static String worldRemoveOtherPermission = "privateworldmanager.remove.other";
    public static String worldGreenPermission = "privateworldmanager.green";
    public static String worldGreenOtherPermission = "privateworldmanager.green.other";
    public static String worldGreenListPermission = "privateworldmanager.green.list";

    public static String sqlPlayerDataTable = "player_data";
    public static String sqlWorldDataTable = "world_data";

    public static String nullVisitWhiteList = "无";

    public static String backupWorldDirectory = "backup_world";

    public String prefix;
    public String worldSettingMenuTitle;

    public String greenStatus;
    public String privateStatus;
    public String publicStatus;
}
