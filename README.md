# PrivateWorldManager

PrivateWorldManager is a Bukkit/Spigot plugin for Minecraft that allows players to create their own private worlds and set them as either public or private. Private worlds require whitelisting to access, but are open to anyone with green certification. Players can upload or download their own private worlds, and interact with others' private worlds.

## Features

- Create private worlds that can be set as public or private
- Whitelist system for private worlds
- Green certification for unrestricted access to private worlds
- Upload and download private worlds
- Interact with other players' private worlds

## Commands

- `/pwm create <worldName>`: Create a new private world with the specified name
- `/pwm setpublic <worldName>`: Set the specified private world as public
- `/pwm setprivate <worldName>`: Set the specified public world as private
- `/pwm whitelist add <worldName> <playerName>`: Add the specified player to the whitelist for the specified private world
- `/pwm whitelist remove <worldName> <playerName>`: Remove the specified player from the whitelist for the specified private world
- `/pwm whitelist list <worldName>`: List all players on the whitelist for the specified private world
- `/pwm upload <worldName>`: Upload your current world to your private worlds list
- `/pwm download <worldName>`: Download the specified private world and load it as your current world
- `/pwm list`: List all private worlds you have access to
- `/pwm help`: Display a help message for all commands

## Permissions

- `privateworldmanager.create`: Allows players to create new private worlds
- `privateworldmanager.setpublic`: Allows players to set their private worlds as public
- `privateworldmanager.setprivate`: Allows players to set their public worlds as private
- `privateworldmanager.whitelist.add`: Allows players to add other players to the whitelist for their private worlds
- `privateworldmanager.whitelist.remove`: Allows players to remove other players from the whitelist for their private worlds
- `privateworldmanager.whitelist.list`: Allows players to list the players on the whitelist for their private worlds
- `privateworldmanager.upload`: Allows players to upload their current world to their private worlds list
- `privateworldmanager.download`: Allows players to download private worlds from their private worlds list
- `privateworldmanager.list`: Allows players to list all private worlds they have access to
- `privateworldmanager.help`: Allows players to view the help message for all commands

## Configurations

- `privateworldmanager.public-worlds`: A list of public worlds that players can access without certification

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.
