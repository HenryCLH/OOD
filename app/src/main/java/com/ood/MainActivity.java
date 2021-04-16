package com.ood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;


import android.content.ClipData;
import android.content.Context;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private DrawerLayout drawerLayout;
    private ListView listLeftDrawer;

    private String userName = "1001";
    private String nameString = "";

    private List<Map<String, Object>> listitem;
    private MyAdapter<Map<String, Object>> myAdapter1;

    private String[] risk;
    private String[] numOfLogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        nameString = intent.getStringExtra("nameString");
        numOfLogs = new String[] {"0","0","0","0","0","0"};

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        listLeftDrawer = (ListView) findViewById(R.id.list_left_drawer);

        Map<String, Object> item1 = new HashMap<>();
        item1.put("touxiang", R.mipmap.userinfo);
        item1.put("name", "Change Password");
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
        risk = new String[]{"0", "0", "0", "0", "0", "0"};
        listitem = new ArrayList<Map<String, Object>>();

        //创建一个simpleAdapter
        //SimpleAdapter myAdapter = new SimpleAdapter(getApplicationContext(), listitem, R.layout.list_item, new String[]{"touxiang", "name", "says"}, new int[]{R.id.imgtou, R.id.name, R.id.says});
        myAdapter1 = new MyAdapter<Map<String, Object>>((ArrayList) listitem, R.layout.list_item) {
            @Override
            public void bindView(ViewHolder holder, Map<String, Object> obj) {
                holder.setText(R.id.name, (CharSequence) obj.get("name"));
                holder.setText(R.id.says, (CharSequence) obj.get("says"));
                holder.setImageResource(R.id.imgtou, (int) obj.get("touxiang"));
            }
        };
        ListView listView = (ListView) findViewById(R.id.list_test);
        listView.setAdapter(myAdapter1);
        listView.setOnItemClickListener(this);
    }


    // 点击返回键直接返回到手机桌面
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent();// 创建Intent对象
        intent.setAction(Intent.ACTION_MAIN);// 设置Intent动作
        intent.addCategory(Intent.CATEGORY_HOME);// 设置Intent种类
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent == listLeftDrawer) {
            if (position == 1) {
                try {
                    Util.writeLoginFile("loginFile", "", this.getApplicationContext());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            } else if (position == 0) {
                Intent changePwdIntent = new Intent(MainActivity.this, ChangePassword.class);
                changePwdIntent.putExtra("ID", userName);
                changePwdIntent.putExtra("nameString", nameString);
                startActivity(changePwdIntent);
            }
            drawerLayout.closeDrawer(listLeftDrawer);
        } else {
            switch (position) {
                case 0:
                    Intent intent = new Intent(MainActivity.this,LikelihoodActivity.class);
                    int likelihood = getLikelihood();
                    intent.putExtra("risk", likelihood);
                    for(int i=0; i<numOfLogs.length; i++) {
                        intent.putExtra(String.valueOf(i), numOfLogs[i]);
                    }
                    System.out.println(likelihood);
                    //changePwdIntent.putExtra("ID", userName);
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

    @Override public void onStart()
    {
        super.onStart();
        File logDir = getDir(userName, Context.MODE_PRIVATE);
        String[] path = new String[]{"/symptomLogCount", "/medicineLogCount", "/doctorVisitLogCount", "/tripLogCount", "/friendsNewsLogCount", "/takeOutLogCount"};

        String[] says = new String[]{"Low", "", "0 log(s)", "0 log(s)", "0 log(s)", "0 log(s)", "0 log(s)", "0 log(s)"};

        for(int i = 0; i < 6; i++)
        {
            File file = new File(logDir.getPath() + path[i]);
            try {
                FileInputStream countIn = new FileInputStream(file);
                int length = countIn.available();
                byte [] buffer = new byte[length];
                countIn.read(buffer);
                countIn.close();
                String[] ss = (new String(buffer)).split(" ");
                says[i+2] = ss[0] + " log(s)";
                numOfLogs[i] = ss[0];
                risk[i] = ss[1];
            } catch (Exception e) {
                System.out.println("Read Count File Outside Failed!!! Num: " + i);
            }
        }

        int likelihood = getLikelihood();
        if(likelihood > 3) {
            says[0] = "High";
        }else {
            says[0] = "Low";
        }

        int[] imgIds = new int[]{R.mipmap.likelihood, R.mipmap.news, R.mipmap.symptom, R.mipmap.medicine, R.mipmap.doctor, R.mipmap.trip, R.mipmap.friends, R.mipmap.takeout};
        String[] names = {"Likelihood", "News", "Symptom Log", "Medicines Log", "Doctor Visit Log", "Trip Log", "Friends News Log", "Take Out Log"};

        listitem.clear();
        for (int i = 0; i < names.length; i++) {
            Map<String, Object> showitem = new HashMap<String, Object>();
            showitem.put("touxiang", imgIds[i]);
            showitem.put("name", names[i]);
            showitem.put("says", says[i]);
            listitem.add(showitem);
        }
        myAdapter1.notifyDataSetChanged();
    }

    public int getLikelihood() {
        int likelihood=0;
        for(int i=0; i<risk.length; i++) {
            likelihood += Integer.parseInt(risk[i]);
        }
        likelihood = likelihood / risk.length;
        return likelihood;
    }
}