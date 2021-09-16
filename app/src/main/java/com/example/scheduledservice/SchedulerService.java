package com.example.scheduledservice;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

public class SchedulerService extends JobService {
    private static final String TAG = "ExampleJobService";
    private Boolean jobCancelled = false;
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(TAG, "onStartJob: ");
        doBackgroundWork(jobParameters);
        return false;
    }

    public void doBackgroundWork(final JobParameters params ){
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<10;i++){
                    Log.d(TAG, "run: "+i);
                    if(jobCancelled){
                        return;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Log.d(TAG, "Job Finished");
            }
        }).start();
        jobFinished(params, false);
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d(TAG, "onStopJob: Job is Cancelled before Completion");
        jobCancelled= true;
        return true;
    }
}
