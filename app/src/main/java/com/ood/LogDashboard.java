package com.ood;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogDashboard extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{

    private int logType;
    private SimpleAdapter logAdapter;
    private List<Map<String, String>> logList;
    private Button visibleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_dashboard);
        Intent intent = getIntent();
        logType = intent.getIntExtra("type", 0);

        logList = new ArrayList<Map<String, String>>();
        for (int i = 0; i < 20; i++) {
            Map<String, String> logItem = new HashMap<String, String>();
            logItem.put("logName", "Log" + i + " Type" + logType);
            logItem.put("logTime", "2021-04-13 12:34");
            logItem.put("log", "this is a log");
            logList.add(logItem);
        }

        logAdapter = new SimpleAdapter(getApplicationContext(), logList, R.layout.list_log, new String[]{"logName", "logTime"}, new int[]{R.id.logName, R.id.logTime})
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent)
            {
                View v = super.getView(position, convertView, parent);

                Button button = v.findViewById(R.id.deleteButon);
                button.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        logList.remove(position);
                        logAdapter.notifyDataSetChanged();
                        v.setVisibility(View.INVISIBLE);
                        visibleButton = null;
                    }
                });

                return v;
            }
        };
        ListView listView = (ListView) findViewById(R.id.logList);
        listView.setAdapter(logAdapter);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(LogDashboard.this);
        switch(logType)
        {
            case 2:
            {
                Button deleteButton = (Button) view.findViewById(R.id.deleteButon);
                if(visibleButton != null)
                {
                    visibleButton.setVisibility(View.INVISIBLE);
                    visibleButton = null;
                }
                else {
                    Map<String, String> logItem = logList.get(position);
                    View logView = LayoutInflater.from(LogDashboard.this).inflate(R.layout.symptom_log, null);
                    TextView logName = (TextView) logView.findViewById(R.id.symptomLogName);
                    String s = logItem.get("logName");
                    logName.setText(logItem.get("logName"));
                    TextView logTime = (TextView) logView.findViewById(R.id.symptomLogTime);
                    logTime.setText(logItem.get("logTime"));
                    TextView logDescription = (TextView) logView.findViewById(R.id.symptomLogDescription);
                    logDescription.setText(logItem.get("log"));

                    builder.setView(logView);
                    builder.show();
                }
                break;
            }
            case 3:
            {

                break;
            }
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        if(visibleButton != null)
        {
            visibleButton.setVisibility(View.INVISIBLE);
        }
        visibleButton = view.findViewById(R.id.deleteButon);
        visibleButton.setVisibility(View.VISIBLE);
        return true;
    }
}