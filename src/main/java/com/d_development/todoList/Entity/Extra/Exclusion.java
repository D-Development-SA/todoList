package com.d_development.todoList.Entity.Extra;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Exclusion {
    private final String path = "src/main/java/com/d_development/todoList/Entity/Extra";
    private final File file = new File(path, "exclusions.txt");
    private Set<String> ExcUserName = new HashSet<>();

    public Exclusion() {
        try {
            createFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<String> getExcUserName() throws IOException {
        String text;
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader br = new BufferedReader(fileReader);
            text = br.readLine();
            br.close();

            if (text != null && !text.isEmpty())
                ExcUserName = Arrays.stream(text.split("@")).filter(text1-> !text1.isEmpty()).collect(Collectors.toSet());

        } catch (IOException e) {
            System.out.println("File not found");
        }

        return ExcUserName;
    }

    public void createExclusion(String name) throws IOException {
        String s = getExcUserName().stream()
                .filter(text-> !text.equals(name))
                .reduce((a, b) -> a + "@" + b ).orElse("");

        FileWriter fileWriter = new FileWriter(file);

        fileWriter.write(s + "@" + name);
        fileWriter.flush();
        fileWriter.close();

        System.out.println("-----------Create the exclusion successfully-----------");
    }

    public void setExcUserName(Set<String> excUserName) {
        ExcUserName = excUserName;
    }

    private void createFile() throws IOException {
        if (!file.exists()) file.createNewFile();
    }
}
