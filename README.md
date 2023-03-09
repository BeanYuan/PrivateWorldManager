PrivateWorldManager

PrivateWorldManager is a Minecraft plugin that allows players to create their own private worlds. Players can set their world to be either public or private - private worlds require a whitelist and green authentication to access. Green authenticated players can enter any private world.

Players can upload and download their own private worlds, as well as manage them. Private worlds can also be edited, with tools such as a region selector, block placer, and block remover.

Features:

- Create private worlds
- Set worlds to public or private
- Whitelist for private worlds
- Green authentication for accessing private worlds
- Upload and download private worlds
- Edit private worlds with region selection, block placing, and block removing

Installation:

1. Copy PrivateWorldManager.jar into the plugins folder of your Minecraft server.
2. Restart the server.
3. Configure the plugin by editing config.yml.

Commands:

- /pwm create [worldname] - creates a new private world
- /pwm public [worldname] - sets the world to public
- /pwm private [worldname] - sets the world to private
- /pwm whitelist [worldname] [add/remove] [playername] - adds or removes a player from the whitelist
- /pwm green [add/remove] [playername] - adds or removes a player from the green authentication list
- /pwm upload [worldname] - uploads a private world
- /pwm download [worldname] - downloads a private world
- /pwm delete [worldname] - deletes a private world
- /pwm tp [worldname] - teleports to a private world
- /pwm wand - gives the player the selection wand

Permissions:

- pwm.create - allows the player to create a private world
- pwm.public - allows the player to set a world to public
- pwm.private - allows the player to set a world to private
- pwm.whitelist - allows the player to add or remove players from the whitelist
- pwm.green - allows the player to add or remove players from the green authentication list
- pwm.upload - allows the player to upload a private world
- pwm.download - allows the player to download a private world
- pwm.delete - allows the player to delete a private world
- pwm.tp - allows the player to teleport to a private world
- pwm.wand - allows the player to use the selection wand

Configuration:

The plugin can be configured by editing the config.yml file. The following options are available:

- allow_public: whether to allow players to create public worlds (true/false, default true)
- default_world_type: the default type for newly created worlds ("public" or "private", default "public")
- max_upload_size_mb: the maximum file size for uploaded worlds in megabytes (default 100)
- max_world_size: the maximum size of a private world in blocks (default 5000)
- wand_item: the item used for the selection wand (default stick)
- wand_item_name: the display name for the selection wand (default "PrivateWorldManager Wand")

Credits:

PrivateWorldManager was developed by [Your Name Here].

License:

This plugin is licensed under the MIT License.
