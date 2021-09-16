package com.example.scheduledservice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnStartJob = (Button) findViewById(R.id.btnStartJob);
        Button btnStopJob = (Button) findViewById(R.id.btnStopJob);

        btnStartJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ComponentName componentName = new ComponentName(MainActivity.this, SchedulerService.class);
                JobInfo info = new JobInfo.Builder(123,componentName)
//                        .setRequiresCharging(true)
                        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                        .setPersisted(true)
                        .setPeriodic(15*60*1000)
                        .build();
                JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
                int resultCode = scheduler.schedule(info);
                if (resultCode == JobScheduler.RESULT_SUCCESS){
                    Log.d(TAG, "onClick: Job Scheduled");
                }
                else{
                    Log.d(TAG, "onClick: Job Scheduling failed");
                }
            }
        });

        btnStopJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
                scheduler.cancel(123);
                Log.d(TAG, "onClick: Cancelled Job");
            }
        });
    }
}