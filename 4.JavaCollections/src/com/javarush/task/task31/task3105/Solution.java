package com.javarush.task.task31.task3105;

import javax.lang.model.util.Elements;
import java.awt.image.ImageProducer;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/* 
Добавление файла в архив
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream(args[1]);
        ZipInputStream zis = new ZipInputStream(fis);

        Map<String,ByteArrayOutputStream> map = new HashMap<>();
        ZipEntry entry;
        while ((entry = zis.getNextEntry()) != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int count = zis.read(buffer);
            baos.write(buffer,0,count);

            map.put(entry.getName(),baos);
            zis.closeEntry();
        }
        zis.close();

        Path file = Paths.get(args[0]);

        FileOutputStream fos = new FileOutputStream(args[1]);
        ZipOutputStream zos = new ZipOutputStream(fos);

        boolean exist = false;
        for (Map.Entry<String,ByteArrayOutputStream> pair : map.entrySet()){

            if (pair.getKey().equals("new/" + file.getFileName().toString())){
                zos.putNextEntry(new ZipEntry("new/" + file.getFileName().toString()));
                Files.copy(file,zos);
                exist = true;
                zos.closeEntry();

            }else if (pair.getKey().equals(file.getFileName().toString())){
                zos.putNextEntry(new ZipEntry(file.getFileName().toString()));
                Files.copy(file,zos);
                zos.closeEntry();
            }else{
                zos.putNextEntry(new ZipEntry(pair.getKey()));
                zos.write(pair.getValue().toByteArray());
                zos.closeEntry();
            }

        }

        if (!exist){
            zos.putNextEntry(new ZipEntry("new/" + file.getFileName().toString()));
            Files.copy(file,zos);
            zos.closeEntry();
        }
        zos.close();
    }

}
