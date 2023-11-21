package xyris.darkxx.DataDropper;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileDownloadCommand implements CommandExecutor {

    private static final long MAX_FILE_SIZE = 4L * 1024L * 1024L * 1024L;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player) && !(sender instanceof ConsoleCommandSender)) {
            sender.sendMessage("§cYou don't have permission to use this command.");
            return false;
        }

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!player.isOp() && !player.hasPermission("dd.filedownload")) {
                player.sendMessage("§cYou don't have permission to use this command.");
                return false;
            }
        }

        if (label.equalsIgnoreCase("filedownload") || label.equalsIgnoreCase("fdw") || label.equalsIgnoreCase("fd") || label.equalsIgnoreCase("downloadfile")) {
            if (args.length == 1) {
                String fileUrl = args[0];
                Player player = (Player) sender;

                try {
                    URL url = new URL(fileUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    long fileSize = connection.getContentLengthLong();

                    if (fileSize > MAX_FILE_SIZE) {
                        player.sendMessage("§cFile size exceeds the limit of 4GB.");
                        return true;
                    }

                    player.sendMessage("§eDownloading file... Please wait.");

                    InputStream inputStream = connection.getInputStream();

                    String[] urlParts = fileUrl.split("/");
                    String fileName = urlParts[urlParts.length - 1];

                    File outputFile = new File(fileName);

                    FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    long transferredBytes = 0;
                    int lastProgress = 0;

                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, bytesRead);
                        transferredBytes += bytesRead;
                        int percentage = (int) ((transferredBytes * 100) / fileSize);
                        if (percentage >= lastProgress + 10) {
                            lastProgress = percentage - (percentage % 10);
                            sendProgressMessage(player, lastProgress);
                        }
                    }

                    fileOutputStream.close();
                    inputStream.close();

                    player.sendMessage("§aFile§a§l '" + fileName + "'§a downloaded and saved in the root directory.");
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                    player.sendMessage("§cFailed to download the file.");
                }
            } else {
                sender.sendMessage("§cUsage: /filedownload <url>");
                return false;
            }
        }

        return false;
    }

    private void sendProgressMessage(Player player, int percentage) {
        player.sendMessage("§aDownloading... " + percentage + "%");
    }
}
