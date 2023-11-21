package xyris.darkxx.DataDropper;

import org.bukkit.plugin.java.JavaPlugin;

public class DataDropper extends JavaPlugin {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_BOLD = "\u001B[1m";

    @Override
    public void onEnable() {
        getCommand("filedownload").setExecutor(new FileDownloadCommand());
        getCommand("fdw").setExecutor(new FileDownloadCommand());
        getCommand("fd").setExecutor(new FileDownloadCommand());
        getCommand("downloadfile").setExecutor(new FileDownloadCommand());
        getCommand("unarchive").setExecutor(new UnarchivePlugin());
        getCommand("ddhelp").setExecutor(new HelpCommand());

        saveResource("ReadMe.md", false);
        saveResource("config.js", false);

        getLogger().info("\033[0;31m ______              _             ______                                                            ");
        getLogger().info("\033[0;31m|_   _ `.           / |_          |_   _ `.                                                          ");
        getLogger().info("\033[0;31m  | | `. \\  ,--.   `| |-'  ,--.     | | `. \\  _ .--.    .--.    _ .--.    _ .--.    .---.   _ .--.  ");
        getLogger().info("\033[0;31m  | |  | | `'_\\ :   | |   `'_\\ :    | |  | | [ `/'`\\] / .'`\\ \\ [ '/'`\\ \\ [ '/'`\\ \\ / /__\\\\ [ `/'`\\] ");
        getLogger().info("\033[0;31m _| |_.' / // | |,  | |,  // | |,  _| |_.' /  | |     | \\__. |  | \\__/ |  | \\__/ | | \\__.,  | |     ");
        getLogger().info("\033[0;31m|______.'  \\'-;__/  \\__/  \\'-;__/ |______.'  [___]     '.__.'   | ;.__/   | ;.__/   '.__.' [___]    ");
        getLogger().info("\033[0;31m                                                               [__|      [__|                       ");

        getLogger().info("\u001B[32m\u001B[1mDataDropper has been enabled\u001B[0m");
    }

    @Override
    public void onDisable() {
        getLogger().severe("\u001B[31m\u001B[1mDataDropper has been disabled, Good Bye!\u001B[0m");
    }
}