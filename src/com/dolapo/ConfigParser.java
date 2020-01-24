package com.dolapo;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigParser {
    private String fileName = "production";
    private Map<String, String> output;

    public ConfigParser(String fileName) {
        this.output = new HashMap<String, String>();
        if(fileName.equalsIgnoreCase("production")){
            this.fileName = "C:\\Users\\adeleye adedolapo\\Desktop\\JavaPrograms\\classTaskTwo\\src\\com\\dolapo\\config.txt";
        }else if(fileName.equalsIgnoreCase("development")){
            this.fileName = "C:\\Users\\adeleye adedolapo\\Desktop\\JavaPrograms\\classTaskTwo\\src\\com\\dolapo\\config.txt.dev";
        }else if(fileName.equalsIgnoreCase(("staging"))){
            this.fileName = "C:\\Users\\adeleye adedolapo\\Desktop\\JavaPrograms\\classTaskTwo\\src\\com\\dolapo\\config.txt.staging";
        }
    }

    public String getFileName() {
        return fileName;
    }

    private Map<String, String>getOutput(){
        return output;
    }

    public void ReadConfigWithNIO(){
        Path path = Paths.get(this.fileName);
        List<String> lines;
        Map output = new HashMap<String, String>();
        boolean status = true;
        int count = 0;
        try {
            lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            for(String line : lines){
                if(!line.isEmpty()) {
                    if (line.equals("[application]")) {
                        status = false;
                    }
                    if (status) {
                        String[] lineArr = line.split("=");
                        String key = lineArr[0];
                        String value = lineArr[1];
                        output.put(key, value);
                    }
                    if ((!status && count < 3) && !line.equals("[application]")) {
                        String[] lineArr = line.split("=");
                        String key = "application." + lineArr[0];
                        String value = lineArr[1];
                        output.put(key, value);
                        count++;
                    }
                    if (count == 3) {
                        status = true;
                    }
                }
            }
            System.out.println(output);
            System.out.println(output.get("mode"));
        }catch (Exception e){
            System.out.println("Exception: " + e.getMessage());
        }
    }
    public static void main(String[] args) {
        try{
            String name = null;
            if(args.length == 0){
                name = "production";
            }else if(args[0].equalsIgnoreCase("production") || args[0].equalsIgnoreCase("development") || args[0].equalsIgnoreCase("staging")){

                name = args[0];
            }else{
                System.out.println("invalid input");
            }

            ConfigParser test = new ConfigParser(name);
            test.ReadConfigWithNIO();
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());;
        }
    }
}
