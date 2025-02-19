// src/main/java/me/stavgordeev/plugin/commands/MinigameCommand.java
package me.stavgordeev.plugin.commands;

import me.stavgordeev.plugin.Constants.MinigameConstants;
import me.stavgordeev.plugin.Minigame;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.bukkit.command.TabExecutor;

import java.util.List;

public class MinigameCommand implements CommandExecutor, TabExecutor {
    private final Minigame minigame;

    public MinigameCommand(Minigame minigame) {
        this.minigame = minigame;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("This command can only be run by a player.");
            return false;
        }

        if (args.length == 0) {
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "start":
                try {
                    minigame.start(player);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "stop":
                minigame.pauseGame(player);
                break;
            case "resume":
                minigame.resumeGame(player);
                break;
            case "end":
                minigame.endGame(player);
                break;
            case "nuke_area":
                minigame.nukeArea(MinigameConstants.GAME_START_LOCATION,50);
                break;
            default:
                player.sendMessage("Unknown command.");
                break;
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
    if (args.length == 1) {
        return List.of("start", "stop","resume", "end", "nuke_area");
    }
    return List.of();
    }
}