package beanyuan.privateworldmanager.commands;

import beanyuan.privateworldmanager.PrivateWorldManager;
import beanyuan.privateworldmanager.managers.FileManager;
import beanyuan.privateworldmanager.managers.PlayerManager;
import beanyuan.privateworldmanager.managers.WorldManager;
import beanyuan.privateworldmanager.util.Constant;
import beanyuan.privateworldmanager.util.Message;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;

public class Commands implements CommandExecutor {

    public PrivateWorldManager plugin;

    public Commands(PrivateWorldManager plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("create")) {
                if (sender instanceof Player) {
                    // if sender is player
                    Player playerSender = (Player) sender;

                    if (args.length < 2 && playerSender.hasPermission(Constant.worldCreatePermission)) {
                        WorldManager.createWorld(playerSender, Constant.worldTypeFlat);

                        return true;
                    } else if (args.length < 3 && playerSender.hasPermission(Constant.worldCreateOtherPermission)) {
                        Player targetPlayer = Bukkit.getPlayer(args[1]);

                        // whether this player is offline
                        if (targetPlayer != null) {
                            WorldManager.createWorld(targetPlayer, Constant.worldTypeFlat);

                            return true;
                        } else {
                            sender.sendMessage(Message.playerIsOfflineOrNotExist);
                        }
                    } else if (args.length < 4 && playerSender.hasPermission(Constant.worldCreateOtherPermission)) {
                        Player targetPlayer = Bukkit.getPlayer(args[1]);
                        String worldType = args[2];

                        // whether this player is offline
                        if (targetPlayer != null) {
                            WorldManager.createWorld(targetPlayer, worldType);

                            return true;
                        } else {
                            sender.sendMessage(Message.playerIsOfflineOrNotExist);
                        }
                    } else {
                        sender.sendMessage(Message.invalidPermission);
                    }

                    return false;
                } else {
                    // if sender is console

                    if (args.length == 2) {
                        Player targetPlayer = Bukkit.getPlayer(args[1]);

                        // whether this player is offline
                        if (targetPlayer != null) {
                            WorldManager.createWorld(targetPlayer, Constant.worldTypeFlat);

                            return true;
                        }
                    } else if (args.length == 3) {
                        Player targetPlayer = Bukkit.getPlayer(args[1]);
                        String worldType = args[2];

                        // whether this player is offline
                        if (targetPlayer != null) {
                            WorldManager.createWorld(targetPlayer, worldType);

                            return true;
                        }
                    }

                    return false;
                }
            }
            else if (args[0].equalsIgnoreCase("tp")) {
                if (args.length == 3) {
                    String worldID = args[0];
                    Player playerSender = (Player) sender;
                    Player targetPlayer = Bukkit.getPlayer(args[1]);

                    // whether this player is offline
                    if (targetPlayer != null) {
                        if (targetPlayer.equals(playerSender)) {
                            if (playerSender.hasPermission(Constant.worldTpPermission)) {
                                return WorldManager.teleportWorld(targetPlayer, worldID);
                            } else {
                                sender.sendMessage(Message.invalidPermission);
                            }
                        } else {
                            if (playerSender.hasPermission(Constant.worldTpOtherPermission)) {
                                return WorldManager.teleportWorld(targetPlayer, worldID);
                            } else {
                                sender.sendMessage(Message.invalidPermission);
                            }
                        }
                    } else {
                        sender.sendMessage(Message.playerIsOfflineOrNotExist);
                    }
                } else {
                    sender.sendMessage(Message.tpInvalidArgument);
                }
            }
            else if (args[0].equalsIgnoreCase("visit")) {
                if (args[1].equalsIgnoreCase("list") && args.length == 3) {
                    if (sender instanceof Player) {
                        Player playerSender = (Player) sender;
                        String worldID = args[2];

                        Player targetWorldOwner = WorldManager.getPlayerFromWorldID(worldID);
                        World targetWorld = WorldManager.getWorldFromWorldID(worldID);

                        // whether the target world is valid
                        if (targetWorldOwner != null && targetWorld != null) {
                            // whether is sender itself
                            if (targetWorldOwner.getUniqueId().equals(playerSender.getUniqueId())) {
                                // whether sender have permission
                                if (playerSender.hasPermission(Constant.worldVisitListPermission)) {
                                    List<Player> whiteList = WorldManager.getVisitWhiteList(targetWorld);
                                    String whiteListString = WorldManager.formatPlayerList(whiteList);
                                    playerSender.sendMessage(targetWorld.getName() + " " + Message.visitWhiteList);
                                    playerSender.sendMessage(whiteListString);
                                } else {
                                    sender.sendMessage(Message.invalidPermission);
                                }
                            } else {
                                // whether sender have permission
                                if (playerSender.hasPermission(Constant.worldVisitListOtherPermission)) {
                                    List<Player> whiteList = WorldManager.getVisitWhiteList(targetWorld);
                                    String whiteListString = WorldManager.formatPlayerList(whiteList);
                                    playerSender.sendMessage(targetWorld.getName() + " " + Message.visitWhiteList);
                                    playerSender.sendMessage(whiteListString);
                                } else {
                                    sender.sendMessage(Message.invalidPermission);
                                }
                            }
                        } else {
                            playerSender.sendMessage(Message.visitListInvalidArgument);
                        }
                    } else {
                        String worldID = args[2];

                        Player targetWorldOwner = WorldManager.getPlayerFromWorldID(worldID);
                        World targetWorld = WorldManager.getWorldFromWorldID(worldID);

                        if (targetWorldOwner != null && targetWorld != null) {
                            List<Player> whiteList = WorldManager.getVisitWhiteList(targetWorld);
                            String whiteListString = WorldManager.formatPlayerList(whiteList);
                            sender.sendMessage(targetWorld.getName() + " " + Message.visitWhiteList);
                            sender.sendMessage(whiteListString);
                        } else {
                            sender.sendMessage(Message.visitListInvalidArgument);
                        }
                    }
                }
                else if (args.length == 3) {
                    if (sender instanceof Player) {
                        // player sender
                        Player playerSender = (Player) sender;
                        String worldID = args[1];
                        Player targetPlayer = Bukkit.getPlayer(args[2]);

                        Player targetWorldOwner = WorldManager.getPlayerFromWorldID(worldID);
                        World targetWorld = WorldManager.getWorldFromWorldID(worldID);

                        // whether world is valid
                        if (targetWorldOwner != null && targetWorld != null) {
                            // whether is sender itself
                            if (!targetWorldOwner.getUniqueId().equals(playerSender.getUniqueId())) {
                                // whether sender has permission
                                if (playerSender.hasPermission(Constant.worldVisitOtherPermission)) {
                                    // whether this player is offline
                                    if (targetPlayer != null) {
                                        WorldManager.addPlayerVisitWhiteList(targetPlayer, targetWorld);

                                        return true;
                                    } else {
                                        sender.sendMessage(Message.playerIsOfflineOrNotExist);
                                    }
                                }
                            } else {
                                // whether sender has permission
                                if (playerSender.hasPermission(Constant.worldVisitPermission)) {
                                    // whether this player is offline
                                    if (targetPlayer != null) {
                                        WorldManager.addPlayerVisitWhiteList(targetPlayer, targetWorld);
                                        return true;
                                    } else {
                                        sender.sendMessage(Message.playerIsOfflineOrNotExist);
                                    }
                                }
                            }
                        } else {
                            sender.sendMessage(Message.visitInvalidWorld);
                        }

                    } else {
                        // console sender
                        String worldID = args[0];
                        Player targetPlayer = Bukkit.getPlayer(args[1]);

                        Player targetWorldOwner = WorldManager.getPlayerFromWorldID(worldID);
                        World targetWorld = WorldManager.getWorldFromWorldID(worldID);

                        // whether world is valid
                        if (targetWorldOwner != null && targetWorld != null) {
                            if (targetPlayer != null) {
                                WorldManager.addPlayerVisitWhiteList(targetPlayer, targetWorld);

                                return true;
                            } else {
                                sender.sendMessage(Message.playerIsOfflineOrNotExist);
                            }
                        } else {
                            sender.sendMessage(Message.visitInvalidWorld);
                        }
                    }
                } else {
                    sender.sendMessage(Message.visitInvalidArgument);
                }
            }
            else if (args[0].equalsIgnoreCase("blacklist")) {
                if (args[1].equalsIgnoreCase("list") && args.length == 3) {
                    if (sender instanceof Player) {
                        Player playerSender = (Player) sender;
                        String worldID = args[2];

                        Player targetWorldOwner = WorldManager.getPlayerFromWorldID(worldID);
                        World targetWorld = WorldManager.getWorldFromWorldID(worldID);

                        // whether the target world is valid
                        if (targetWorldOwner != null && targetWorld != null) {
                            // whether is sender itself
                            if (targetWorldOwner.getUniqueId().equals(playerSender.getUniqueId())) {
                                // whether sender have permission
                                if (playerSender.hasPermission(Constant.worldVisitListPermission)) {
                                    List<Player> whiteList = WorldManager.getVisitWhiteList(targetWorld);
                                    String whiteListString = WorldManager.formatPlayerList(whiteList);
                                    playerSender.sendMessage(targetWorld.getName() + " " + Message.visitWhiteList);
                                    playerSender.sendMessage(whiteListString);
                                } else {
                                    sender.sendMessage(Message.invalidPermission);
                                }
                            } else {
                                // whether sender have permission
                                if (playerSender.hasPermission(Constant.worldVisitListOtherPermission)) {
                                    List<Player> whiteList = WorldManager.getVisitWhiteList(targetWorld);
                                    String whiteListString = WorldManager.formatPlayerList(whiteList);
                                    playerSender.sendMessage(targetWorld.getName() + " " + Message.visitWhiteList);
                                    playerSender.sendMessage(whiteListString);
                                } else {
                                    sender.sendMessage(Message.invalidPermission);
                                }
                            }
                        } else {
                            playerSender.sendMessage(Message.visitListInvalidArgument);
                        }
                    } else {
                        String worldID = args[2];

                        Player targetWorldOwner = WorldManager.getPlayerFromWorldID(worldID);
                        World targetWorld = WorldManager.getWorldFromWorldID(worldID);

                        if (targetWorldOwner != null && targetWorld != null) {
                            List<Player> whiteList = WorldManager.getVisitWhiteList(targetWorld);
                            String whiteListString = WorldManager.formatPlayerList(whiteList);
                            sender.sendMessage(targetWorld.getName() + " " + Message.visitWhiteList);
                            sender.sendMessage(whiteListString);
                        } else {
                            sender.sendMessage(Message.visitListInvalidArgument);
                        }
                    }
                }
                else if (args.length == 3) {
                    if (sender instanceof Player) {
                        // player sender
                        Player playerSender = (Player) sender;
                        String worldID = args[1];
                        Player targetPlayer = Bukkit.getPlayer(args[2]);

                        Player targetWorldOwner = WorldManager.getPlayerFromWorldID(worldID);
                        World targetWorld = WorldManager.getWorldFromWorldID(worldID);

                        // whether world is valid
                        if (targetWorldOwner != null && targetWorld != null) {
                            // whether is sender itself
                            if (!targetWorldOwner.getUniqueId().equals(playerSender.getUniqueId())) {
                                // whether sender has permission
                                if (playerSender.hasPermission(Constant.worldBlacklistOtherPermission)) {
                                    // whether this player is offline
                                    if (targetPlayer != null) {
                                        WorldManager.addPlayerBlackList(targetPlayer, targetWorld);

                                        return true;
                                    } else {
                                        sender.sendMessage(Message.playerIsOfflineOrNotExist);
                                    }
                                }
                            } else {
                                // whether sender has permission
                                if (playerSender.hasPermission(Constant.worldBlacklistPermission)) {
                                    // whether this player is offline
                                    if (targetPlayer == null) {
                                        WorldManager.addPlayerBlackList(targetPlayer, targetWorld);

                                        return true;
                                    } else {
                                        sender.sendMessage(Message.playerIsOfflineOrNotExist);
                                    }
                                }
                            }
                        } else {
                            sender.sendMessage(Message.visitInvalidWorld);
                        }

                    } else {
                        // console sender
                        String worldID = args[0];
                        Player targetPlayer = Bukkit.getPlayer(args[1]);

                        Player targetWorldOwner = WorldManager.getPlayerFromWorldID(worldID);
                        World targetWorld = WorldManager.getWorldFromWorldID(worldID);

                        // whether world is valid
                        if (targetWorldOwner != null && targetWorld != null) {
                            if (targetPlayer != null) {
                                WorldManager.addPlayerBlackList(targetPlayer, targetWorld);

                                return true;
                            } else {
                                sender.sendMessage(Message.playerIsOfflineOrNotExist);
                            }
                        } else {
                            sender.sendMessage(Message.visitInvalidWorld);
                        }
                    }
                } else {
                    sender.sendMessage(Message.visitInvalidArgument);
                }
            }
            else if (args[0].equalsIgnoreCase("green")) {
                if (args[1].equalsIgnoreCase("list")) {
                    if (sender instanceof Player) {
                        Player playerSender = (Player) sender;

                        if (playerSender.hasPermission(Constant.worldGreenListPermission)) {
                            List<Player> playerList = WorldManager.getGreenList();
                            String greenListString = WorldManager.formatPlayerList(playerList);
                            sender.sendMessage(Message.greenList);
                            sender.sendMessage(greenListString);

                            return true;
                        } else {
                            playerSender.sendMessage(Message.invalidPermission);
                        }
                    } else {
                        List<Player> playerList = WorldManager.getGreenList();
                        String greenListString = WorldManager.formatPlayerList(playerList);
                        sender.sendMessage(Message.greenList);
                        sender.sendMessage(greenListString);

                        return true;
                    }
                }
                else if (args.length == 2) {
                    if (sender instanceof Player) {
                        // player sender
                        Player playerSender = (Player) sender;
                        Player targetPlayer = Bukkit.getPlayer(args[1]);

                        if (targetPlayer != null && targetPlayer.getName().equals(playerSender.getName())) {
                            if (playerSender.hasPermission(Constant.worldGreenOtherPermission)) {
                                WorldManager.setPlayerGreen(targetPlayer);

                                return true;
                            } else {
                                sender.sendMessage(Message.invalidPermission);
                            }
                        } else if (targetPlayer != null && !targetPlayer.getName().equals(playerSender.getName())) {
                            if (playerSender.hasPermission(Constant.worldGreenOtherPermission)) {
                                WorldManager.setPlayerGreen(targetPlayer);

                                return true;
                            } else {
                                sender.sendMessage(Message.invalidPermission);
                            }
                        }
                    } else {
                        // console sender
                        Player targetPlayer = Bukkit.getPlayer(args[1]);

                        if (targetPlayer != null) {
                            WorldManager.setPlayerGreen(targetPlayer);

                            return true;
                        } else {
                            sender.sendMessage(Message.playerIsOfflineOrNotExist);
                        }
                    }
                } else {
                    sender.sendMessage(Message.visitInvalidArgument);
                }
            }
            else if (args[0].equalsIgnoreCase("build")) {
                if (args.length == 3) {
                    if (sender instanceof Player) {
                        // player sender
                        Player playerSender = (Player) sender;
                        String worldID = args[1];
                        Player targetPlayer = Bukkit.getPlayer(args[2]);

                        Player targetWorldOwner = WorldManager.getPlayerFromWorldID(worldID);
                        World targetWorld = WorldManager.getWorldFromWorldID(worldID);

                        // whether world is valid
                        if (targetWorldOwner != null && targetWorld != null) {
                            // whether is sender itself
                            if (!targetWorldOwner.getUniqueId().equals(playerSender.getUniqueId())) {
                                // whether sender has permission
                                if (playerSender.hasPermission(Constant.worldBuildOtherPermission)) {
                                    // whether this player is offline
                                    if (targetPlayer != null) {
                                        WorldManager.addPlayerBuildWhiteList(targetPlayer, targetWorld);

                                        return true;
                                    } else {
                                        sender.sendMessage(Message.playerIsOfflineOrNotExist);
                                    }
                                } else {
                                    sender.sendMessage(Message.invalidPermission);
                                }
                            } else {
                                // whether sender has permission
                                if (playerSender.hasPermission(Constant.worldBuildPermission)) {
                                    // whether this player is offline
                                    if (targetPlayer != null) {
                                        WorldManager.addPlayerBuildWhiteList(targetPlayer, targetWorld);

                                        return true;
                                    } else {
                                        sender.sendMessage(Message.playerIsOfflineOrNotExist);
                                    }
                                } else {
                                    sender.sendMessage(Message.invalidPermission);
                                }
                            }
                        } else {
                            sender.sendMessage(Message.visitInvalidWorld);
                        }

                    } else {
                        // console sender
                        String worldID = args[1];
                        Player targetPlayer = Bukkit.getPlayer(args[2]);

                        Player targetWorldOwner = WorldManager.getPlayerFromWorldID(worldID);
                        World targetWorld = WorldManager.getWorldFromWorldID(worldID);

                        // whether world is valid
                        if (targetWorldOwner != null && targetWorld != null) {
                            if (targetPlayer != null) {
                                WorldManager.addPlayerVisitWhiteList(targetPlayer, targetWorld);

                                return true;
                            } else {
                                sender.sendMessage(Message.playerIsOfflineOrNotExist);
                            }
                        } else {
                            sender.sendMessage(Message.visitInvalidWorld);
                        }
                    }
                } else {
                    sender.sendMessage(Message.visitInvalidArgument);
                }
            }
            else if (args[0].equalsIgnoreCase("visible")) {
                if (args.length == 2) {
                    if (sender instanceof Player) {
                        // player sender
                        Player playerSender = (Player) sender;
                        String worldID = args[1];

                        Player targetWorldOwner = WorldManager.getPlayerFromWorldID(worldID);
                        World targetWorld = WorldManager.getWorldFromWorldID(worldID);

                        // whether world is valid
                        if (targetWorldOwner != null && targetWorld != null) {
                            // whether is sender itself
                            if (!targetWorldOwner.getUniqueId().equals(playerSender.getUniqueId())) {
                                // whether sender has permission
                                if (playerSender.hasPermission(Constant.worldVisibleOtherPermission)) {
                                    return WorldManager.setWorldVisible(targetWorld, 1);
                                } else {
                                    sender.sendMessage(Message.invalidPermission);
                                }
                            } else {
                                // whether sender has permission
                                if (playerSender.hasPermission(Constant.worldVisiblePermission)) {
                                    return WorldManager.setWorldVisible(targetWorld, 1);
                                } else {
                                    sender.sendMessage(Message.invalidPermission);
                                }
                            }
                        } else {
                            sender.sendMessage(Message.visitInvalidWorld);
                        }

                    } else {
                        // console sender
                        String worldID = args[1];

                        Player targetWorldOwner = WorldManager.getPlayerFromWorldID(worldID);
                        World targetWorld = WorldManager.getWorldFromWorldID(worldID);

                        // whether world is valid
                        if (targetWorldOwner != null && targetWorld != null) {
                            return WorldManager.setWorldVisible(targetWorld, 1);
                        } else {
                            sender.sendMessage(Message.visitInvalidWorld);
                        }
                    }
                } else {
                    sender.sendMessage(Message.visitInvalidArgument);
                }
            }
            else if (args[0].equalsIgnoreCase("invisible")) {
                if (args.length == 2) {
                    if (sender instanceof Player) {
                        // player sender
                        Player playerSender = (Player) sender;
                        String worldID = args[1];

                        Player targetWorldOwner = WorldManager.getPlayerFromWorldID(worldID);
                        World targetWorld = WorldManager.getWorldFromWorldID(worldID);

                        // whether world is valid
                        if (targetWorldOwner != null && targetWorld != null) {
                            // whether is sender itself
                            if (!targetWorldOwner.getUniqueId().equals(playerSender.getUniqueId())) {
                                // whether sender has permission
                                if (playerSender.hasPermission(Constant.worldInvisibleOtherPermission)) {
                                    return WorldManager.setWorldVisible(targetWorld, 0);
                                } else {
                                    sender.sendMessage(Message.invalidPermission);
                                }
                            } else {
                                // whether sender has permission
                                if (playerSender.hasPermission(Constant.worldInvisiblePermission)) {
                                    return WorldManager.setWorldVisible(targetWorld, 0);
                                } else {
                                    sender.sendMessage(Message.invalidPermission);
                                }
                            }
                        } else {
                            sender.sendMessage(Message.visitInvalidWorld);
                        }

                    } else {
                        // console sender
                        String worldID = args[1];

                        Player targetWorldOwner = WorldManager.getPlayerFromWorldID(worldID);
                        World targetWorld = WorldManager.getWorldFromWorldID(worldID);

                        // whether world is valid
                        if (targetWorldOwner != null && targetWorld != null) {
                            return WorldManager.setWorldVisible(targetWorld, 0);
                        } else {
                            sender.sendMessage(Message.visitInvalidWorld);
                        }
                    }
                } else {
                    sender.sendMessage(Message.visitInvalidArgument);
                }
            }
            else if (args[0].equalsIgnoreCase("time")) {
                if (args.length == 2) {
                    if (sender instanceof Player) {
                        // when sender not specific world
                        Player playerSender = (Player) sender;
                        long timeTick = Long.parseLong(args[1]);

                        World targetWorld = playerSender.getWorld();
                        Player targetWorldOwner = WorldManager.getWorldOwner(targetWorld);

                        // whether world is valid
                        if (targetWorldOwner != null) {
                            // whether is other's world
                            if (playerSender.getUniqueId().equals(targetWorldOwner.getUniqueId())) {
                                // whether have permission
                                if (playerSender.hasPermission(Constant.worldTimePermission)) {
                                    return WorldManager.setWorldTime(targetWorld, timeTick);
                                } else {
                                    sender.sendMessage(Message.invalidPermission);
                                }
                            } else {
                                // whether have permission
                                if (playerSender.hasPermission(Constant.worldTimeOtherPermission)) {
                                    return WorldManager.setWorldTime(targetWorld, timeTick);
                                } else {
                                    sender.sendMessage(Message.invalidPermission);
                                }
                            }
                        } else {
                            sender.sendMessage(Message.visitInvalidWorld);
                        }
                    } else {
                        // console send
                        sender.sendMessage(Message.visitInvalidWorld);
                    }
                } else if (args.length == 3) {
                    if (sender instanceof Player) {
                        // when sender not specific world
                        Player playerSender = (Player) sender;
                        long timeTick = Long.parseLong(args[1]);

                        World targetWorld = Bukkit.getWorld(args[2]);
                        Player targetWorldOwner = WorldManager.getWorldOwner(targetWorld);

                        // whether world is valid
                        if (targetWorld != null && targetWorldOwner != null) {
                            // whether is other's world
                            if (playerSender.getUniqueId().equals(targetWorldOwner.getUniqueId())) {
                                // whether have permission
                                if (playerSender.hasPermission(Constant.worldTimePermission)) {
                                    return WorldManager.setWorldTime(targetWorld, timeTick);
                                } else {
                                    sender.sendMessage(Message.invalidPermission);
                                }
                            } else {
                                // whether have permission
                                if (playerSender.hasPermission(Constant.worldTimeOtherPermission)) {
                                    return WorldManager.setWorldTime(targetWorld, timeTick);
                                } else {
                                    sender.sendMessage(Message.invalidPermission);
                                }
                            }
                        }
                    } else {
                        // console send
                        long timeTick = Long.parseLong(args[1]);

                        World targetWorld = Bukkit.getWorld(args[2]);
                        Player targetWorldOwner = WorldManager.getWorldOwner(targetWorld);

                        // whether world is valid
                        if (targetWorld != null && targetWorldOwner != null) {
                            // whether is other's world
                            return WorldManager.setWorldTime(targetWorld, timeTick);
                        }
                    }
                }
            }
            else if (args[0].equalsIgnoreCase("weather")) {
                if (args.length == 2) {
                    if (sender instanceof Player) {
                        // when sender not specific world
                        Player playerSender = (Player) sender;
                        String weather = args[1];

                        World targetWorld = playerSender.getWorld();
                        Player targetWorldOwner = WorldManager.getWorldOwner(targetWorld);

                        // whether world is valid
                        if (targetWorldOwner != null) {
                            // whether is other's world
                            if (playerSender.getUniqueId().equals(targetWorldOwner.getUniqueId())) {
                                // whether have permission
                                if (playerSender.hasPermission(Constant.worldWeatherPermission)) {
                                    return WorldManager.setWorldWeather(targetWorld, weather);
                                } else {
                                    sender.sendMessage(Message.invalidPermission);
                                }
                            } else {
                                // whether have permission
                                if (playerSender.hasPermission(Constant.worldWeatherOtherPermission)) {
                                    return WorldManager.setWorldWeather(targetWorld, weather);
                                } else {
                                    sender.sendMessage(Message.invalidPermission);
                                }
                            }
                        } else {
                            sender.sendMessage(Message.visitInvalidWorld);
                        }
                    } else {
                        // console send
                        sender.sendMessage(Message.visitInvalidWorld);
                    }
                } else if (args.length == 3) {
                    if (sender instanceof Player) {
                        // when sender not specific world
                        Player playerSender = (Player) sender;
                        String weather = args[1];

                        World targetWorld = Bukkit.getWorld(args[2]);
                        Player targetWorldOwner = WorldManager.getWorldOwner(targetWorld);

                        // whether world is valid
                        if (targetWorld != null && targetWorldOwner != null) {
                            // whether is other's world
                            if (playerSender.getUniqueId().equals(targetWorldOwner.getUniqueId())) {
                                // whether have permission
                                if (playerSender.hasPermission(Constant.worldWeatherPermission)) {
                                    return WorldManager.setWorldWeather(targetWorld, weather);
                                } else {
                                    sender.sendMessage(Message.invalidPermission);
                                }
                            } else {
                                // whether have permission
                                if (playerSender.hasPermission(Constant.worldWeatherOtherPermission)) {
                                    return WorldManager.setWorldWeather(targetWorld, weather);
                                } else {
                                    sender.sendMessage(Message.invalidPermission);
                                }
                            }
                        }
                    } else {
                        // console send
                        String weather = args[1];

                        World targetWorld = Bukkit.getWorld(args[2]);
                        Player targetWorldOwner = WorldManager.getWorldOwner(targetWorld);

                        // whether world is valid
                        if (targetWorld != null && targetWorldOwner != null) {
                            // whether is other's world
                            return WorldManager.setWorldWeather(targetWorld, weather);
                        } else {
                            sender.sendMessage(Message.visitInvalidWorld);
                        }
                    }
                }
            }
            else if (args[0].equalsIgnoreCase("backup")) {
                if (args.length == 2) {
                    if (sender instanceof Player) {
                        Player playerSender = (Player) sender;
                        if (args[1].equalsIgnoreCase("all")) {
                            List<File> files = FileManager.getWorldFiles(plugin.getServer().getWorldContainer().getAbsolutePath());

                            for (File file : files) {
                                World world = Bukkit.getWorld(file.getName());

                                if (world != null) {
                                    WorldManager.backupWorld(plugin, world);
                                }
                            }

                            return true;
                        } else {
                            String worldID = args[1];
                            World world = WorldManager.getWorldFromWorldID(worldID);

                            if (world != null) {
                                if (PrivateWorldManager.worldData.getOwner(world).equalsIgnoreCase(playerSender.getName())) {
                                    // if world is sender self
                                    if (playerSender.hasPermission(Constant.worldBackupPermission)) {
                                        WorldManager.backupWorld(plugin, world);
                                    } else {
                                        sender.sendMessage(Message.invalidPermission);
                                    }
                                } else {
                                    // if world is not self
                                    if (playerSender.hasPermission(Constant.worldBackupOtherPermission)) {
                                        WorldManager.backupWorld(plugin, world);
                                    } else {
                                        sender.sendMessage(Message.invalidPermission);
                                    }
                                }

                                return true;
                            }

                            return false;
                        }
                    }

                }
            }
            else if (args[0].equalsIgnoreCase("remove")) {
                if (args.length == 2) {
                    if (sender instanceof Player) {
                        Player playerSender = (Player) sender;
                        String worldID = args[1];
                        World targetWorld = WorldManager.getWorldFromWorldID(worldID);
                        Player owner = WorldManager.getPlayerFromWorldID(worldID);

                        if (targetWorld != null && owner != null) {
                            if (playerSender.getName().equals(owner.getName())) {
                                if (playerSender.hasPermission(Constant.worldRemovePermission)) {
                                    WorldManager.deleteWorld(targetWorld, owner);

                                    return true;
                                } else {
                                    sender.sendMessage(Message.invalidPermission);
                                }
                            } else {
                                if (playerSender.hasPermission(Constant.worldRemoveOtherPermission)) {
                                    WorldManager.deleteWorld(targetWorld, owner);

                                    return true;
                                } else {
                                    sender.sendMessage(Message.invalidPermission);
                                }
                            }
                        }
                    } else {
                        String worldID = args[1];
                        World targetWorld = WorldManager.getWorldFromWorldID(worldID);
                        Player owner = WorldManager.getPlayerFromWorldID(worldID);

                        if (targetWorld != null && owner != null) {
                            WorldManager.deleteWorld(targetWorld, owner);

                            return true;
                        } else {
                            sender.sendMessage(Message.visitInvalidWorld);
                        }
                    }

                    return true;
                }
            }
        }

        return false;
    }
}
