package com.example.ojasvisingh.placeme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AdminJobDetails extends AppCompatActivity {

    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_job_details);
        name=getIntent().getStringExtra("comp");
    }
    public void editProcess(View v)
    {
        Intent editProcess=new Intent(v.getContext(),EditProcess.class);
        editProcess.putExtra("comp",name);
        startActivity(editProcess);
    }

    public void markAttendance(View v)
    {
        Intent editProcess=new Intent(v.getContext(),MarkAttendance.class);
        editProcess.putExtra("comp",name);
        startActivity(editProcess);
    }

    public void editJobDetails(View v)
    {
        Intent editProcess=new Intent(v.getContext(),CreateJob.class);
        editProcess.putExtra("comp",name);
        startActivity(editProcess);
    }
}
