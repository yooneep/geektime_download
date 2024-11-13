package com.geekdoc.download.util;

import com.geekdoc.download.constant.CommonConstant;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

import java.io.File;
import java.io.IOException;

public class MP3FileNameChanger {
    public static void main(String[] args) {
        String folderPath = CommonConstant.filePath+"mp3/";
        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files!= null) {
                for (File file : files) {
                    if(file.isDirectory()){
                        for(File file1:file.listFiles()){
                            changeFileName(file1);
                        }
                    }else if (file.isFile() && file.getName().toLowerCase().endsWith(".mp3")) {
                        changeFileName(file);
                    }
                }
            }
        }
    }

    public static void changeFileName(File file){
        try {
            AudioFile audioFile = AudioFileIO.read(file);
            Tag tag = audioFile.getTag();
            // 获取文件名（不包含路径）
            String fileNameWithoutPath = file.getName();
            // 去除文件名后缀，获取主文件名部分（可选操作，如果希望写入完整文件名可省略这步）
            String baseFileName = fileNameWithoutPath.substring(0, fileNameWithoutPath.lastIndexOf('.'));
            // 将文件名（这里使用主文件名部分）写入标题属性
            tag.setField(FieldKey.TITLE, baseFileName);
            audioFile.commit();
            System.out.println("已将 " + file.getName() + " 的文件名写入属性标题中");
        } catch (IOException | CannotReadException | TagException | InvalidAudioFrameException | CannotWriteException e) {
            e.printStackTrace();
        } catch (ReadOnlyFileException e) {
            throw new RuntimeException(e);
        }
    }
}