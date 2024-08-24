package com.geekdoc.download.util;

import java.io.File;

public class DeleteFilesStartingWithStart {
    public static void main(String[] args) {
        String directoryPath = "/Users/pangning/geektime/md/";
        deleteFilesStartingWith000(directoryPath);
    }

    public static void deleteFilesStartingWith000(String directoryPath) {
        File directory = new File(directoryPath);
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files!= null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().startsWith("000")) {
                        if (!file.delete()) {
                            System.out.println("Failed to delete file: " + file.getAbsolutePath());
                        } else {
                            System.out.println("Deleted file: " + file.getAbsolutePath());
                        }
                    } else if (file.isDirectory()) {
                        // 递归处理子目录
                        deleteFilesStartingWith000(file.getAbsolutePath());
                    }
                }
            }
        } else {
            System.out.println("The specified path is not a valid directory.");
        }
    }
}
