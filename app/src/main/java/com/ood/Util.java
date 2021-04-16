package com.ood;

import android.content.Context;

import org.apache.http.util.EncodingUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Util {
    public static void writeFile(String fileName,String writestr, Context context) throws IOException {
        try{
            FileOutputStream fout = context.openFileOutput(fileName, context.MODE_APPEND);
            byte [] bytes = writestr.getBytes();
            fout.write(bytes);
            fout.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void writeLoginFile(String fileName,String writestr, Context context) throws IOException {
        try{
            FileOutputStream fout = context.openFileOutput(fileName, context.MODE_PRIVATE);
            byte [] bytes = writestr.getBytes();
            fout.write(bytes);
            fout.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void writedMap2File(String fileName, Map<String, String> userData, Context context) throws IOException {
        FileOutputStream fout = context.openFileOutput(fileName, context.MODE_PRIVATE);
        String input = "";
        for (String key : userData.keySet()) {
            input += key + " " + userData.get(key) + "\n";
        }
        fout.write(input.getBytes());
        fout.close();
    }

    public static Map<String, String> readFile(String fileName, Context context) throws IOException {
        String res="";
        try{
            FileInputStream fin = context.openFileInput(fileName);
            int length = fin.available();
            byte [] buffer = new byte[length];
            fin.read(buffer);
            //设置编码格式
            res = EncodingUtils.getString(buffer, "UTF-8");
            fin.close();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        String[] rawData = res.split("\n");
        Map<String, String> map = new HashMap<>();
        for (String data : rawData) {
            String[] temp = data.split(" ");
            map.put(temp[0], temp[1]);
        }
        return map;
    }

    //读数据
    public static String readFileString(String fileName, Context context) throws IOException{
        String res="";
        try{
            FileInputStream fin = context.openFileInput(fileName);
            int length = fin.available();
            byte [] buffer = new byte[length];
            fin.read(buffer);
            res = EncodingUtils.getString(buffer, "UTF-8");
            fin.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }


}
