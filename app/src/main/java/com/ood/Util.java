package com.ood;

import android.content.Context;

import org.apache.http.util.EncodingUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;

public class Util {
    //写数据
    public void writeFile(String fileName,String writestr, Context ctx) throws IOException {
        try{
            FileOutputStream fout = ctx.openFileOutput(fileName, MODE_PRIVATE);
            byte [] bytes = writestr.getBytes();
            fout.write(bytes);
            fout.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    //读数据
    public String readFile(String fileName, Context ctx) throws IOException{
        String res="";
        try{
            FileInputStream fin = ctx.openFileInput(fileName);
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
