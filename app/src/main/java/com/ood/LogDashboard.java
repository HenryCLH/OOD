package com.ood;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogDashboard extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{

    private String userId;
    private int logType;
    private SimpleAdapter logAdapter;
    private List<Map<String, String>> logList;
    private Button visibleButton;

    private String log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_dashboard);
        Intent intent = getIntent();
        userId = intent.getStringExtra("ID");
        logType = intent.getIntExtra("type", 0);

        //TODO load log
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
    public boolean onCreateOptionsMenu(Menu menu)
    {
        menu.add(1, 1, 1, "Create Log");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(LogDashboard.this);
        View logView = LayoutInflater.from(LogDashboard.this).inflate(R.layout.create_symptom_log, null);

        EditText logNameText = (EditText) logView.findViewById(R.id.createSymptomLogName);
        EditText logDescriptionText = (EditText) logView.findViewById(R.id.createSymptomLogDescription);

        TextView showDate = (TextView) logView.findViewById(R.id.showDate);
        final int[] date = new int[3];
        showDate.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                logNameText.clearFocus();
                logDescriptionText.clearFocus();
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                AlertDialog.Builder choseDate = new AlertDialog.Builder(LogDashboard.this);
                View view = LayoutInflater.from(LogDashboard.this).inflate(R.layout.chose_date, null);

                DatePicker datePicker = view.findViewById(R.id.datePicker);
                date[0] = datePicker.getYear();
                date[1] = datePicker.getMonth() + 1;
                date[2] = datePicker.getDayOfMonth();

                choseDate.setPositiveButton("Set", new DialogInterface.OnClickListener()
                {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        date[0] = datePicker.getYear();
                        date[1] = datePicker.getMonth() + 1;
                        date[2] = datePicker.getDayOfMonth();
                        showDate.setText(date[0] + "-" + date[1] + "-" + date[2]);
                    }
                });

                choseDate.setView(view);
                choseDate.show();
            }
        });

        TextView showTime = (TextView) logView.findViewById(R.id.showTime);
        final int[] time = new int[2];
        showTime.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                logNameText.clearFocus();
                logDescriptionText.clearFocus();
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                AlertDialog.Builder choseTime = new AlertDialog.Builder(LogDashboard.this);
                View view = LayoutInflater.from(LogDashboard.this).inflate(R.layout.chose_time, null);

                TimePicker timePicker = view.findViewById(R.id.timePicker);
                time[0] = timePicker.getHour();
                time[1] = timePicker.getMinute();

                choseTime.setPositiveButton("Set", new DialogInterface.OnClickListener()
                {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        time[0] = timePicker.getHour();
                        time[1] = timePicker.getMinute();
                        showTime.setText(time[0] + ":" + time[1]);
                    }
                });

                choseTime.setView(view);
                choseTime.show();
            }
        });

        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                log = logNameText.getText().toString() + ";" + showDate.getText().toString() + ";" + showTime.getText().toString() + ";" + logDescriptionText.getText().toString();
                //TODO create
            }
        });

        builder.setView(logView);
        builder.show();

        return true;
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