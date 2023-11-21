package xyris.darkxx.DataDropper;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by a player.");
            return true;
        }

        Player player = (Player) sender;

        player.sendMessage("§a§lDataDropper");
        player.sendMessage(centerText("§a§lDownload Files"));
        player.sendMessage("§fDownload a file via command §7(4GB MAX FILE SIZE LIMIT)");
        player.sendMessage("§aPermission \"dd.filedownload\"");
        player.sendMessage("§f- /filedownload <url>");
        player.sendMessage("§7Example:§f /filedownload https://darkxx.xyz/downloadtestfile");
        player.sendMessage(centerText("§a§lAliases"));
        player.sendMessage("§f- fdw");
        player.sendMessage("§f- fd");
        player.sendMessage("§f- download file");

        player.sendMessage("§a§lUnarchive Files");
        player.sendMessage(centerText("§a§lUnarchive a file via command §7(ZIP / TAR.GZ)"));
        player.sendMessage("§aPermission \"dd.unarchive\"");
        player.sendMessage("§f- /unarchive <path>");
        player.sendMessage("§7Example:§f /unarchive /home/container/darkxx.zip");
        player.sendMessage(centerText("§a§lAliases"));
        player.sendMessage("§f- unarch");
        player.sendMessage("§f- unarc");

        return true;
    }

    // Function to center-align text for chat display
    private String centerText(String text) {
        int maxWidth = 40; // Adjust the width as needed
        int spaces = (maxWidth - text.length()) / 2;
        return " ".repeat(Math.max(0, spaces)) + text;
    }
}
