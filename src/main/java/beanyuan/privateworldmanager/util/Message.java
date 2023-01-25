package beanyuan.privateworldmanager.util;

import org.bukkit.ChatColor;

public class Message {
    public static String playerIsNotExist = "该玩家不存在！";
    public static String playerIsOfflineOrNotExist = "该玩家不在线或不存在！";
    public static String tpInvalidArgument = "需要填写目标世界和玩家名称！";
    public static String visitWhiteList = "访问白名单:";
    public static String greenList = "绿色认证：";

    public static String visitInvalidArgument = "需要填写目标世界和玩家名称！";
    public static String visitInvalidWorld = "该世界不存在！";
    public static String visitListInvalidArgument = "该世界不存在！";

    public static String breakerInvalidPermission = "没有权限破坏方块在该世界！";
    public static String placerInvalidPermission = "没有权限放置方块在该世界！";
    public static String commanderInvalidPermission = "没有权限使用we等指令在该世界！";

    public static String invalidPermission = "没有权限！";

    static public final String WITH_DELIMITER = "((?<=%1$s)|(?=%1$s))";

    /**
     * @param text The string of text to apply color/effects to
     * @return Returns a string of text with color/effects applied
     */
    public static String translateColorCodes(String text){

        String[] texts = text.split(String.format(WITH_DELIMITER, "&"));

        StringBuilder finalText = new StringBuilder();

        for (int i = 0; i < texts.length; i++){
            if (texts[i].equalsIgnoreCase("&")){
                //get the next string
                i++;
                if (texts[i].charAt(0) == '#'){
                    finalText.append(net.md_5.bungee.api.ChatColor.of(texts[i].substring(0, 7)) + texts[i].substring(7));
                }else{
                    finalText.append(ChatColor.translateAlternateColorCodes('&', "&" + texts[i]));
                }
            }else{
                finalText.append(texts[i]);
            }
        }

        return finalText.toString();
    }
}
