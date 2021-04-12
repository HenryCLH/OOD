package com.ood;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.Enumeration;

public class LikelihoodActivity extends AppCompatActivity {
    private String[] files;
    private int[] numOfDiffLogs = {0, 0, 0, 0, 0, 0};
    private TextView symptom;
    private TextView medicine;
    private TextView doctorVisit;
    private TextView trip;
    private TextView friendsNews;
    private TextView takeout;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_likelihood);
        symptom = (TextView) findViewById(R.id.num_symptom);
        medicine = (TextView) findViewById(R.id.num_medicines);
        doctorVisit = (TextView) findViewById(R.id.num_doctor_visit);
        trip = (TextView) findViewById(R.id.num_trip);
        friendsNews = (TextView) findViewById(R.id.num_friends_news);
        takeout = (TextView) findViewById(R.id.num_take_out);
        result = (TextView) findViewById(R.id.result);

        symptom.setText("You have " + Integer.toString(numOfDiffLogs[0]) + " symptom log(s).");
        medicine.setText("You have " + Integer.toString(numOfDiffLogs[1]) + " medicine log(s).");
        doctorVisit.setText("You have " + Integer.toString(numOfDiffLogs[2]) + " doctor visit log(s).");
        trip.setText("You have " + Integer.toString(numOfDiffLogs[3]) + " trip log(s).");
        friendsNews.setText("You have " + Integer.toString(numOfDiffLogs[4]) + " friends news log(s).");
        takeout.setText("You have " + Integer.toString(numOfDiffLogs[5]) + " takeout log(s).");
        result.setText("According to our algorithm, we speculate that your probability of infecting COVID-19 is Low.");

    }

}