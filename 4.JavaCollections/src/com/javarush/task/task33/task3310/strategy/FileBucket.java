package com.javarush.task.task33.task3310.strategy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileBucket {

    private Path path;

    public FileBucket () {

        try{
            this.path = Files.createTempFile(null,null);
            Files.deleteIfExists(path);
            Files.createFile(path);
        }catch (IOException ignored) {}

        assert path != null;
        path.toFile().deleteOnExit();
    }

    public long getFileSize () {
        try {
            return Files.size(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void putEntry(Entry entry) {
        OutputStream os;
        try {
            os = Files.newOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(os);

            oos.writeObject(entry);

            oos.close();
            os.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Entry getEntry()  {
        try {
            if (getFileSize() > 0) {
                InputStream is = Files.newInputStream(path);
                ObjectInputStream ois = new ObjectInputStream(is);

                Entry entry = (Entry) ois.readObject();
                ois.close();
                is.close();
                return entry;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void remove() {
        try {
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
