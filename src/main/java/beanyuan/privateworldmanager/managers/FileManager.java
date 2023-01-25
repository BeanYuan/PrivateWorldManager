package beanyuan.privateworldmanager.managers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    public static List<File> getWorldFiles(String path) {
        List<File> files = new ArrayList<>();
        File directory = new File(path);

        if (directory.listFiles().length > 0) {
            for (File file : directory.listFiles()) {
                if (file.getName().contains("pWorld")) {
                    files.add(file);
                }
            }
        }

        return files;
    }

    private static void copyDirectory(File sourceDirectory, File destinationDirectory) throws IOException {
        if (!destinationDirectory.exists()) {
            destinationDirectory.mkdir();
        }
        for (String f : sourceDirectory.list()) {
            copyDirectoryCompatibityMode(new File(sourceDirectory, f), new File(destinationDirectory, f));
        }
    }

    public static void copyDirectoryCompatibityMode(File source, File destination) throws IOException {
        if (source.isDirectory()) {
            copyDirectory(source, destination);
        } else {
            copyFile(source, destination);
        }
    }

    private static void copyFile(File sourceFile, File destinationFile)
            throws IOException {
        try (InputStream in = new FileInputStream(sourceFile);
             OutputStream out = new FileOutputStream(destinationFile)) {
            byte[] buf = new byte[1024];
            int length;
            while ((length = in.read(buf)) > 0) {
                out.write(buf, 0, length);
            }
        }
    }
}
