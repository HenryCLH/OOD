package com.ood;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] names = {"Likelihood", "News", "Symptom Log", "Medicines Log", "Doctor Visit Log", "Trip Log", "Friends News Log", "Take Out Log"};
        String[] says = new String[]{"test", "test", "test", "test", "test", "test","test", "test"};
        int[] imgIds = new int[]{R.mipmap.likelihood, R.mipmap.news, R.mipmap.symptom, R.mipmap.medicine, R.mipmap.doctor, R.mipmap.trip, R.mipmap.friends, R.mipmap.takeout};

        List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < names.length; i++) {
            Map<String, Object> showitem = new HashMap<String, Object>();
            showitem.put("touxiang", imgIds[i]);
            showitem.put("name", names[i]);
            showitem.put("says", says[i]);
            listitem.add(showitem);
        }

        //创建一个simpleAdapter
        SimpleAdapter myAdapter = new SimpleAdapter(getApplicationContext(), listitem, R.layout.list_item, new String[]{"touxiang", "name", "says"}, new int[]{R.id.imgtou, R.id.name, R.id.says});
        ListView listView = (ListView) findViewById(R.id.list_test);
        listView.setAdapter(myAdapter);

    }
}

// This is a test comment by CLH
// This is a test comment by Jin
// This is a test comment by limin