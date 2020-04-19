package entities;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

//implements Serializable. writeObject(), readObject()
public class Storage {
    final String FILE_NAME = "src/main/resources/hashmap.txt";
    private Map<String, Object> data = new HashMap<String, Object>();

    public Storage() {}

    public void putStorageData(String str, Object data) throws IOException {
        this.data.put(str, data);
        saveHashMapToFile();
    }

    public Object getStorageData(String str) {
        Object currentget = data.get(str);
        if (currentget!=null) {
            return currentget;
        }
        return new Object();
    }

    public void removeStorageData(String str) {
        data.remove(str);
    }

    public void saveHashMapToFile() throws IOException {
        Files.write(Paths.get(FILE_NAME),
                data.entrySet().stream().map(k->k.getKey()+"\r\n"+k.getValue()).collect(Collectors.toList()),
                StandardCharsets.UTF_8,
                Files.exists(Paths.get(FILE_NAME)) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE  );
    }

//    Files.lines(Paths.get(FILE_NAME), StandardCharsets.UTF_8).forEach((k, v) -> {map.put((String) k, (Object) v);});
//    Через лямбды не получилось, пришлось писать то что ниже
    public void readHashMapFromFile() throws IOException {
        File file = new File(FILE_NAME);
        Properties properties = new Properties();
        properties.load(new FileReader(file));

        for(Map.Entry<Object, Object> entry: properties.entrySet()) {
            data.put((String)entry.getKey(), entry.getValue());
        }
    }
}