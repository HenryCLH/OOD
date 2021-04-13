package com.ood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;


import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.MenuItem;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private DrawerLayout drawerLayout;
    private ListView listLeftDrawer;
    //private MyAdapter<ClipData.Item> myAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        listLeftDrawer = (ListView) findViewById(R.id.list_left_drawer);

        Map<String, Object> item1 = new HashMap<>();
        item1.put("touxiang", R.mipmap.userinfo);
        item1.put("name", "Account Info");
        item1.put("says", "");
        Map<String, Object> item2 = new HashMap<>();
        item2.put("touxiang", R.mipmap.logout);
        item2.put("name", "Log out");
        item2.put("says", "");
        List<Map<String, Object>> leftList = new ArrayList<Map<String, Object>>();
        leftList.add(item1);
        leftList.add(item2);
        MyAdapter<Map<String, Object>> myAdapter = new MyAdapter<Map<String, Object>>((ArrayList) leftList, R.layout.list_item) {
            @Override
            public void bindView(ViewHolder holder, Map<String, Object> obj) {
                holder.setText(R.id.name, (CharSequence) obj.get("name"));
                holder.setText(R.id.says, (CharSequence) obj.get("says"));
                holder.setImageResource(R.id.imgtou, (int) obj.get("touxiang"));
            }
        };
        listLeftDrawer.setAdapter(myAdapter);
        listLeftDrawer.setOnItemClickListener(this);


        // main page list view set up
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
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 1) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        drawerLayout.closeDrawer(listLeftDrawer);
    }
}

// This is a test comment by CLH
// This is a test comment by Jin
// This is a test comment by limin