package com.geekdoc.download.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.regex.Pattern;

/**
 * @author: zhumlu@yonyou.com
 * @date: 2022/2/28 14:57
 * @description:
 */
public class FileUtil {
    public static Logger log = LoggerFactory.getLogger(FileUtil.class);
    private static Pattern FilePattern = Pattern.compile("[\\\\/:*?\"<> |]");

    public static void write(String content, String path, String fileName) {
        fileName = replace(fileName);
        String pathName = path + File.separator + fileName;
        try {
            File file = new File(pathName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            // 创建文件
            file.createNewFile();
            // creates a FileWriter Object
            FileWriter writer = new FileWriter(file);
            // 向文件写入内容
            writer.write(content);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            log.error("写入文件异常:", e);
        }

    }

    public static int readFile(String path, String fileName) {
        int i = 0;
        String pathName = path + File.separator + fileName;
        try {
            File file = new File(pathName);
            if (!file.exists()) {
                return i;
            }
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains("http")) {
                    i++;
                }
            }
        } catch (Exception e) {
            log.error("写入文件异常:", e);
        }
        return i;
    }

    public static boolean isExist(String path, String fileName) {
        String pathName = path + File.separator + fileName;
        try {
            File file = new File(pathName);
            if (file.exists()) {
                return true;
            }
        } catch (Exception e) {
            log.error("读取文件异常:", e);
        }
        return false;
    }

    public static String filter(String str) {
        return str == null ? null : FilePattern.matcher(str).replaceAll("");
    }


    public static String replace(String str) {
        return str.replaceAll("\\|", "")
                .replaceAll("\\｜", "")
                .replaceAll("\\s", "")
                .replaceAll("\\/", "");
    }

    public static void renameFilesInDirectory(File directory) {
        File[] files = directory.listFiles();

        if (files == null) {
            System.out.println("The directory is empty or an error occurred.");
            return;
        }

        for (File file : files) {
            if (file.isFile()) {
                String originalName = file.getName();
                String newName = originalName.replaceAll("[第讲]", "");

                if (!originalName.equals(newName)) {
                    File newFile = new File(directory, newName);
                    if (file.renameTo(newFile)) {
                        System.out.println("Renamed: " + originalName + " -> " + newName);
                    } else {
                        System.out.println("Failed to rename: " + originalName);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {

        String replace = replace("");
        System.out.println(replace);

//        renameFilesInDirectory(new File("/Users/pangning/geektime/md/100007201_从0开始学游戏开发_蔡能-[42]"));
    }
}
