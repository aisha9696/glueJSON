package com.test.superkassa;


import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
/**
 * Created by Aisha on 03.01.2019.
 */
public class App {

    public static String filepath = System.getProperty("user.dir")+ "\\input.json";

    //проверка является ли строка самодостаточным
    public static boolean checkFull(JSONArray array){
        boolean full = true;
        for(int j = 0; j < array.size(); j++){
            if(Objects.isNull(array.get(j))){
                full = false;
            }
        }
        return  full;
    }
    //функция для парсинга json обьектов
    public static JSONArray newJSON(JSONArray list){
        JSONArray newList = new JSONArray();

        for(int i = 0; i < list.size(); i++){
            JSONArray jsonArray = (JSONArray) list.get(i);

            if(checkFull(jsonArray)){
                newList.add(jsonArray);
            }else{
                for (int k = i+1; k < list.size(); k++){
                    JSONArray jsonArray2 = (JSONArray) list.get(k);
                    boolean same = true;

                    if (jsonArray.size() == jsonArray2.size()){
                        JSONArray newSubList = new JSONArray();
                        for(int j = 0; j < jsonArray.size(); j++){
                            if(!Objects.isNull(jsonArray.get(j)) && !Objects.isNull(jsonArray2.get(j))){
                                same = false;
                                newSubList.add(jsonArray.get(j));
                            }else if(Objects.isNull(jsonArray.get(j)) && Objects.isNull(jsonArray2.get(j))){
                                newSubList.add(null);
                            }else{
                                if(Objects.isNull(jsonArray.get(j)) && !Objects.isNull(jsonArray2.get(j))){
                                    newSubList.add(jsonArray2.get(j));
                                }else{
                                    newSubList.add(jsonArray.get(j));
                                }
                            }
                        }
                        if(same){
                            if(checkFull(newSubList)){
                                newList.add(newSubList);
                            }else{
                                list.add(newSubList);
                            }
                        }
                    }
                }
            }
        }

        return newList;

    }


    public static void main(String[] args) {


        if(filepath.endsWith(".json")){
            JSONParser parser = new JSONParser();
            try {
                FileReader reader = new FileReader(filepath);
                JSONArray obj = (JSONArray) parser.parse(reader);

                FileWriter file = new FileWriter( System.getProperty("user.dir")+"\\output.json");
                file.write(newJSON(obj).toJSONString());
                file.flush();


            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }else {
            System.err.print(" Enter json file!");
        }
    }
}
