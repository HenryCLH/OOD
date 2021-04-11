package com.ood;
import android.content.res.Resources;

import java.util.*;
import java.io.*;

public class fileReader {
    /**
     * 功能：Java读取txt文件的内容 步骤：1：先获得文件句柄 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
     * 3：读取到输入流后，需要读取生成字节流 4：一行一行的输出。readline()。 备注：需要考虑的是异常情况
     *            文件路径[到达文件:如： D:\aa.txt]
     * @return 将这个文件按照每一行切割成数组存放到list中。
     */
    public static Map<String, String> read()
    {
        Map<String, String> userData = new HashMap<>();
        try
        {


            File file = new File("user_data.txt");
            if (file.exists())
            { // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file));// 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;

                while ((lineTxt = bufferedReader.readLine()) != null)
                {
                    String[] temp = lineTxt.split(" ");
                    userData.put(temp[0], temp[1]);
                }
                bufferedReader.close();
                read.close();
            }
            else
            {
                file.createNewFile();
                System.out.println("找不到指定的文件");
            }
        }
        catch (Exception e)
        {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }

        return userData;
    }

    public static Map<String, String> getUserData(InputStream inputStream) {
        InputStreamReader inputStreamReader = null;
        Map<String, String> map = new HashMap<>();
        try {
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer sb = new StringBuffer("");
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                String[] temp = line.split(" ");
                map.put(temp[0], temp[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }


}
