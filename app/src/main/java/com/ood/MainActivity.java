package com.ood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;


import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private DrawerLayout drawerLayout;
    private ListView listLeftDrawer;
    private ArrayList<ClipData.Item> menuLists;
    private MyAdapter<ClipData.Item> myAdapter = null;

    private String userName = "1001";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] names = {"Likelihood", "News", "Symptom Log", "Medicines Log", "Doctor Visit Log", "Trip Log", "Friends News Log", "Take Out Log"};
        String[] says = new String[]{"Low", "test", "test", "test", "test", "test","test", "test"};
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
        //SimpleAdapter myAdapter = new SimpleAdapter(getApplicationContext(), listitem, R.layout.list_item, new String[]{"touxiang", "name", "says"}, new int[]{R.id.imgtou, R.id.name, R.id.says});
        MyAdapter<Map<String, Object>> myAdapter = new MyAdapter<Map<String, Object>>((ArrayList) listitem, R.layout.list_item) {
            @Override
            public void bindView(ViewHolder holder, Map<String, Object> obj) {
                holder.setText(R.id.name, (CharSequence) obj.get("name"));
                holder.setText(R.id.says, (CharSequence) obj.get("says"));
                holder.setImageResource(R.id.imgtou, (int) obj.get("touxiang"));
            }
        };
        ListView listView = (ListView) findViewById(R.id.list_test);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                Intent intent = new Intent(MainActivity.this, LikelihoodActivity.class);
                startActivity(intent);
                break;
            case 1:
                Intent intent1 = new Intent(MainActivity.this, NewsActivity.class);
                startActivity(intent1);
                break;
            case 2:
                Intent symptomIntent = new Intent(MainActivity.this, LogDashboard.class);
                symptomIntent.putExtra("ID", userName);
                symptomIntent.putExtra("type", 2);
                startActivity(symptomIntent);
                break;
            case 3:
                Intent medicinesIntent = new Intent(MainActivity.this, LogDashboard.class);
                medicinesIntent.putExtra("ID", userName);
                medicinesIntent.putExtra("type", 3);
                startActivity(medicinesIntent);
                break;
            case 4:
                Intent doctorVisitIntent = new Intent(MainActivity.this, LogDashboard.class);
                doctorVisitIntent.putExtra("ID", userName);
                doctorVisitIntent.putExtra("type", 4);
                startActivity(doctorVisitIntent);
                break;
            case 5:
                Intent tripIntent = new Intent(MainActivity.this, LogDashboard.class);
                tripIntent.putExtra("ID", userName);
                tripIntent.putExtra("type", 5);
                startActivity(tripIntent);
                break;
            case 6:
                Intent friendsNewsIntent = new Intent(MainActivity.this, LogDashboard.class);
                friendsNewsIntent.putExtra("ID", userName);
                friendsNewsIntent.putExtra("type", 6);
                startActivity(friendsNewsIntent);
                break;
            case 7:
                Intent takeOutIntent = new Intent(MainActivity.this, LogDashboard.class);
                takeOutIntent.putExtra("ID", userName);
                takeOutIntent.putExtra("type", 7);
                startActivity(takeOutIntent);
                break;
        }
    }
}