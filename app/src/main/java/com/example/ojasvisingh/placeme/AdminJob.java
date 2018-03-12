package com.example.ojasvisingh.placeme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AdminJob extends AppCompatActivity {
    ArrayList<String> jobRecs=new ArrayList<>();
    ListView jobList;
    ArrayAdapter jobListAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_job);
        jobList=findViewById(R.id.jobListAdmin);
        jobListAd=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,jobRecs);
        jobList.setAdapter(jobListAd);
    }

    @Override
    protected void onStart() {
        super.onStart();
        CollectionReference jobsDB= FirebaseFirestore.getInstance().collection("jobs");
        jobsDB.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                for(DocumentSnapshot jobRec: documentSnapshots.getDocuments())
                {
                    String jobRecstr=jobRec.getData().toString();
                    if(jobRecs.contains(jobRecstr)==false) {
                        jobRecs.add(jobRecstr);
                        jobListAd.notifyDataSetChanged();
                    }

                }

            }
        });
    }

    public void createJob(View v)
    {
        Intent setupJob =new Intent(getApplicationContext(),CreateJob.class);
        startActivity(setupJob);

    }
}
