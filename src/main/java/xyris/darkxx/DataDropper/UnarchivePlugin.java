package xyris.darkxx.DataDropper;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnarchivePlugin implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender || sender instanceof Player && (sender.isOp() || sender.hasPermission("dd.unarchive"))) {
            if (args.length != 1) {
                sender.sendMessage("§cUsage /unarchive <filepath>");
                return false;
            }

            String filePath = args[0];

            File file = new File(filePath);
            if (!file.exists()) {
                sender.sendMessage("§cFile not found!");
                return false;
            }

            try {
                if (filePath.toLowerCase().endsWith(".zip")) {
                    unarchiveZipFile(sender, file);
                } else if (filePath.toLowerCase().endsWith(".tar.gz")) {
                    unarchiveTarGzFile(sender, file);
                } else {
                    sender.sendMessage("§cUnsupported file format!");
                    return false;
                }
                sender.sendMessage("§aFile successfully unarchived!");
                return true;
            } catch (IOException e) {
                sender.sendMessage("§cError while unarchiving the file: " + e.getMessage());
                return false;
            }
        } else {
            sender.sendMessage("§cYou don't have permission to use this command.");
            return false;
        }
    }

    private void unarchiveZipFile(CommandSender sender, File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(fis);
             ZipInputStream zis = new ZipInputStream(bis)) {

            byte[] buffer = new byte[1024];
            ZipEntry zipEntry;
            while ((zipEntry = zis.getNextEntry()) != null) {
                File newFile = new File(file.getParentFile(), zipEntry.getName());
                if (zipEntry.isDirectory()) {
                    if (!newFile.isDirectory() && !newFile.mkdirs()) {
                        throw new IOException("§cFailed to create directory: " + newFile);
                    }
                } else {
                    try (FileOutputStream fos = new FileOutputStream(newFile);
                         BufferedOutputStream bos = new BufferedOutputStream(fos, buffer.length)) {
                        int read;
                        while ((read = zis.read(buffer, 0, buffer.length)) != -1) {
                            bos.write(buffer, 0, read);
                        }
                    }
                }
            }
        }
    }

    private void unarchiveTarGzFile(CommandSender sender, File file) throws IOException {
        sender.sendMessage("§cTAR.GZ handling is not supported, This will be implemented in V2.");
    }
}
