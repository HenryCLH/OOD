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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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

    private File logDir;
    private File logFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_dashboard);
        Intent intent = getIntent();
        userId = intent.getStringExtra("ID");
        logType = intent.getIntExtra("type", 0);

        logDir = getDir(userId, Context.MODE_PRIVATE);
        String path = "/tmp";
        switch(logType)
        {
            case 2:
            {
                path = "/symptomLog";
                break;
            }
            case 3:
            {
                path = "/medicineLog";
                break;
            }
            case 4:
            {
                path = "/doctorVisitLog";
                break;
            }
            case 5:
            {
                path = "/tripLog";
                break;
            }
            case 6:
            {
                path = "/friendsNewsLog";
                break;
            }
            case 7:
            {
                path = "/takeOutLog";
                break;
            }
        }
        logFile = new File(logDir.getPath() + path);
        if(!logFile.exists())
        {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                System.out.println("Create Log File Failed!!!");
            }
        }

        logList = new ArrayList<Map<String, String>>();
        loadLogFile();

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

                        try {
                            FileOutputStream out = openFileOutput(logFile.getName().toString(), MODE_PRIVATE);
                            String tmps = "";
                            for(int i = 0; i < logList.size(); i++)
                            {
                                Map<String, String> tmpItem = logList.get(i);
                                tmps += tmpItem.get("logName") + " ;" + tmpItem.get("logTime") + ";" + tmpItem.get("log") + ";;\n";
                            }
                            out.write(tmps.getBytes());
                            out.close();
                            loadLogFile();
                            logAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            System.out.println("Write Log File Failed!!!");
                        }
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

        int logViewId = 0;

        switch(logType)
        {
            case 2:
            {
                logViewId = R.layout.create_symptom_log;
                break;
            }
            case 3:
            {
                logViewId = R.layout.create_medicine_log;
                break;
            }
            case 4:
            {
                logViewId = R.layout.create_doctor_visit_log;
                break;
            }
            case 5:
            {
                logViewId = R.layout.create_trip_log;
                break;
            }
            case 6:
            {
                logViewId = R.layout.create_friends_news_log;
                break;
            }
            case 7:
            {
                logViewId = R.layout.create_take_out_log;
                break;
            }
        }
        View logView = LayoutInflater.from(LogDashboard.this).inflate(logViewId, null);
        EditText logNameText = (EditText) logView.findViewById(R.id.createLogName);
        EditText logDescriptionText = (EditText) logView.findViewById(R.id.createLogDescription);

        Calendar calendar = Calendar.getInstance();

        TextView showDate = (TextView) logView.findViewById(R.id.showDate);
        String defaultDate = "";
        int defaultYear = calendar.get(Calendar.YEAR), defaultMonth = calendar.get(Calendar.MONTH) + 1, defaultDay = calendar.get(Calendar.DAY_OF_MONTH);
        defaultDate += defaultYear + "-";
        if(defaultMonth < 10)
            defaultDate += "0";
        defaultDate += defaultMonth + "-";
        if(defaultDay < 10)
            defaultDate += "0";
        defaultDate += defaultDay;
        showDate.setText(defaultDate);
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
                        String t1 = "", t2 = "";
                        if(date[1] < 10)
                            t1 = "0";
                        if(date[2] < 10)
                            t2 = "0";
                        showDate.setText(date[0] + "-" + t1 + date[1] + "-" + t2 + date[2]);
                    }
                });

                choseDate.setView(view);
                choseDate.show();
            }
        });

        TextView showTime = (TextView) logView.findViewById(R.id.showTime);
        String defaultTime = "";
        int defaultHour = calendar.get(Calendar.HOUR_OF_DAY), defaultMinute = calendar.get(Calendar.MINUTE);
        String tmpam_pm = "";
        if(defaultHour < 12) {
            if(defaultHour == 0)
                defaultHour += 12;
            tmpam_pm = " AM";
        }
        else {
            defaultHour -= 12;
            tmpam_pm = " PM";
        }
        if(defaultHour < 10)
            defaultTime += "0";
        defaultTime += defaultHour + ":";
        if(defaultMinute < 10)
            defaultTime += "0";
        defaultTime += defaultMinute + tmpam_pm;
        showTime.setText(defaultTime);
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
                time[0] = timePicker.getCurrentHour();
                time[1] = timePicker.getMinute();

                choseTime.setPositiveButton("Set", new DialogInterface.OnClickListener()
                {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        time[0] = timePicker.getCurrentHour();
                        time[1] = timePicker.getMinute();
                        String am_pm = "";
                        if(time[0] >= 12)
                        {
                            if(time[0] > 12)
                                time[0] -= 12;
                            am_pm = " PM";
                        }
                        else {
                            if(time[0] == 0)
                                time[0] += 12;
                            am_pm = " AM";
                        }
                        String t1 = "", t2 = "";
                        if(time[0] < 10)
                            t1 = "0";
                        if(time[1] < 10)
                            t2 = "0";
                        showTime.setText(t1 + time[0] + ":" + t2 + time[1] + am_pm);
                    }
                });

                choseTime.setView(view);
                choseTime.show();
            }
        });

        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                log = logNameText.getText().toString() + " ;" + showDate.getText().toString() + " " + showTime.getText().toString() + ";" + logDescriptionText.getText().toString() + " ;;\n";
                try {
                    FileOutputStream out = openFileOutput(logFile.getName().toString(), MODE_APPEND);
                    System.out.println(log);
                    out.write(log.getBytes());
                    out.close();
                    loadLogFile();
                    logAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    System.out.println("Write Log File Failed!!!");
                }
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
        Button deleteButton = (Button) view.findViewById(R.id.deleteButon);
        if(visibleButton != null)
        {
            visibleButton.setVisibility(View.INVISIBLE);
            visibleButton = null;
        }
        else {
            Map<String, String> logItem = logList.get(position);
            int tmpId = 0;
            switch(logType)
            {
                case 2: {
                    tmpId = R.layout.symptom_log;
                    break;
                }
                case 3: {
                    tmpId = R.layout.medicine_log;
                    break;
                }
                case 4: {
                    tmpId = R.layout.doctor_visit_log;
                    break;
                }
                case 5:
                {
                    tmpId = R.layout.trip_log;
                    break;
                }
                case 6:
                {
                    tmpId = R.layout.friends_news_log;
                    break;
                }
                case 7:
                {
                    tmpId = R.layout.take_out_log;
                    break;
                }
            }

            View logView = LayoutInflater.from(LogDashboard.this).inflate(tmpId, null);;
            ((TextView) logView.findViewById(R.id.logName)).setText(logItem.get("logName"));
            ((TextView) logView.findViewById(R.id.logTime)).setText(logItem.get("logTime"));
            ((TextView) logView.findViewById(R.id.logDescription)).setText(logItem.get("log"));
            builder.setView(logView);
            builder.show();
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

    private void loadLogFile()
    {
        logList.clear();
        try {
            FileInputStream in = openFileInput(logFile.getName().toString());
            int length = in.available();
            byte [] buffer = new byte[length];
            in.read(buffer);
            in.close();
            String[] logs = (new String(buffer)).split(";;\n");

            for(int i = 0; i < logs.length; i++)
            {
                Map<String, String> logItem = new HashMap<String, String>();
                String[] s = logs[i].split(";");
                logItem.put("logName", s[0]);
                logItem.put("logTime", s[1]);
                logItem.put("log", s[2]);
                logList.add(logItem);
            }
        } catch (Exception e) {
            System.out.println("Read Log File Failed!!!");
        }

        Collections.sort(logList, new Comparator<Map<String, String>>()
        {
            @Override
            public int compare(Map<String, String> o1, Map<String, String> o2) {
                String[] t1 = o1.get("logTime").split(" ");
                String[] t2 = o2.get("logTime").split(" ");
                if(t1[0].compareTo(t2[0]) == 0)
                {
                    if(t1[2].compareTo(t2[2]) == 0)
                        return -t1[1].compareTo(t2[1]);
                    else
                        return -t1[2].compareTo(t2[2]);
                }
                else
                    return -t1[0].compareTo(t2[0]);
            }
        });
    }
}