package fr.realcraft.plugin.data.management.files;

import fr.realcraft.plugin.Main;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;

public class FileGestion {

    public static File getDataFolder() {
        return Main.getINSTANCE().getDataFolder();
    }

    public static void createDirectory(String directoryName) {
        if(!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        String path = getDataFolder().getAbsolutePath();
        File directory = new File(path + "/" + directoryName);
        if(!directory.exists()) {
            directory.mkdir();
        }
    }

    public static void createDirectory(String directoryName1, String directoryName2) {
        if(!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        String path = getDataFolder().getAbsolutePath();
        File directory = new File(path + "/" + directoryName1);
        if(!directory.exists()) {
            directory.mkdir();
        }
        File directory2 = new File(directory + "/" + directoryName2);
        if(!directory2.exists()) {
            directory2.mkdir();
        }
    }

    public static void createDirectory(String directoryName1, String directoryName2, String directoryName3) {
        if(!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        String path = getDataFolder().getAbsolutePath();
        File directory = new File(path + "/" + directoryName1);
        if(!directory.exists()) {
            directory.mkdir();
        }
        File directory2 = new File(directory + "/" + directoryName2);
        if(!directory2.exists()) {
            directory2.mkdir();
        }
        File directory3 = new File(directory2 + "/" + directoryName3);
        if(!directory3.exists()) {
            directory3.mkdir();
        }
    }

    public static void createFile(String fileName) {
        if(!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        File file = new File(getDataFolder(), fileName + ".yml");

        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void createFile(String fileName, String directoryName) {
        if(!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        String path = getDataFolder().getAbsolutePath();

        File directory = new File(path + "/" + directoryName);

        if(!directory.exists()) {
            directory.mkdir();
        }

        File file = new File(directory, fileName + ".yml");

        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void createFile(String fileName, String directoryName1, String directoryName2) {
        if(!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        String path = getDataFolder().getAbsolutePath();
        File directory = new File(path + "/" + directoryName1);
        if(!directory.exists()) {
            directory.mkdir();
        }
        File directory2 = new File(directory + "/" + directoryName2);
        if(!directory2.exists()) {
            directory2.mkdir();
        }
        File file = new File(directory2, fileName + ".yml");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void createFile(String fileName, String directoryName1, String directoryName2, String directoryName3) {
        if(!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        String path = getDataFolder().getAbsolutePath();
        File directory = new File(path + "/" + directoryName1);
        if(!directory.exists()) {
            directory.mkdir();
        }
        File directory2 = new File(directory + "/" + directoryName2);
        if(!directory2.exists()) {
            directory2.mkdir();
        }
        File directory3 = new File(directory2 + "/" + directoryName3);
        if(!directory3.exists()) {
            directory3.mkdir();
        }
        File file = new File(directory3, fileName + ".yml");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static File getFile(String fileName) {
        return new File(getDataFolder(), fileName + ".yml");
    }

    public static File getFile(String fileName, String directoryName1) {
        String path = getDataFolder().getAbsolutePath();
        File directory = new File(path + "/" + directoryName1);
        return new File(directory, fileName + ".yml");
    }

    public static File getFile(String fileName, String directoryName1, String directoryName2) {
        String path = getDataFolder().getAbsolutePath();
        File directory = new File(path + "/" + directoryName1);
        File directory2 = new File(directory + "/" + directoryName2);
        return new File(directory2, fileName + ".yml");
    }

    public static File getFile(String fileName, String directoryName1, String directoryName2, String directoryName3) {
        String path = getDataFolder().getAbsolutePath();
        File directory = new File(path + "/" + directoryName1);
        File directory2 = new File(directory + "/" + directoryName2);
        File directory3 = new File(directory2 + "/" + directoryName3);
        return new File(directory3, fileName + ".yml");
    }

    public static void save(FileConfiguration fileConfiguration, String fileName) {
        try {
            fileConfiguration.save(getFile(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save(FileConfiguration fileConfiguration, String fileName, String directoryName1) {
        try {
            fileConfiguration.save(getFile(fileName,directoryName1));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save(FileConfiguration fileConfiguration, String fileName, String directoryName1, String directoryName2) {
        try {
            fileConfiguration.save(getFile(fileName,directoryName1,directoryName2));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save(FileConfiguration fileConfiguration, String fileName, String directoryName1, String directoryName2, String directoryName3) {
        try {
            fileConfiguration.save(getFile(fileName,directoryName1,directoryName2,directoryName3));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
