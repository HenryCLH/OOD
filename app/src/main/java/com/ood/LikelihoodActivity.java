package com.ood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
        setTitle("Likelihood");
        symptom = (TextView) findViewById(R.id.num_symptom);
        medicine = (TextView) findViewById(R.id.num_medicines);
        doctorVisit = (TextView) findViewById(R.id.num_doctor_visit);
        trip = (TextView) findViewById(R.id.num_trip);
        friendsNews = (TextView) findViewById(R.id.num_friends_news);
        takeout = (TextView) findViewById(R.id.num_take_out);
        result = (TextView) findViewById(R.id.result);

        Intent intent = getIntent();
        String risk = "Low";
        if(intent.getIntExtra("risk", 0) <= 3) {
            risk = "Low";
        } else if(intent.getIntExtra("risk", 0) >3 && intent.getIntExtra("risk", 0) < 7) {
            risk = "Medium";
        } else {
            risk = "High";
        }
        System.out.println("=======");
        System.out.println(intent.getIntExtra("risk", 0));
        symptom.setText("You have " + intent.getStringExtra("0") + " symptom log(s).");
        medicine.setText("You have " + intent.getStringExtra("1") + " medicine log(s).");
        doctorVisit.setText("You have " + intent.getStringExtra("2") + " doctor visit log(s).");
        trip.setText("You have " + intent.getStringExtra("3") + " trip log(s).");
        friendsNews.setText("You have " + intent.getStringExtra("4") + " friends news log(s).");
        takeout.setText("You have " + intent.getStringExtra("5") + " takeout log(s).");
        result.setText("According to our algorithm, we speculate that your probability of infecting COVID-19 is "+risk+".");

    }

}