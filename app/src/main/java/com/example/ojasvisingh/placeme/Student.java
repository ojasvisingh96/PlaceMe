/*
Student's home page where all the job openings are listed and at the bottom is a button to view status of job openings for which he/she has enrolled in
 */
package com.example.ojasvisingh.placeme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class Student extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    String name,branch,degree,roll;
    float cgpa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        String personId = acct.getId();
        DocumentReference studentDB= FirebaseFirestore.getInstance().document("students/" + personId);      //fetching my record
        studentDB.addSnapshotListener(this,new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                name=documentSnapshot.getString("name");
                branch=documentSnapshot.getString("branch");
                degree=documentSnapshot.getString("degree");
                roll=documentSnapshot.getString("roll");
                cgpa=Float.parseFloat(documentSnapshot.getString("cgpa"));
                Log.d("My record ",name+" "+branch+" "+degree+" "+roll+" "+cgpa);
                getJobOpenings();

            }
        });



    }
    public void getJobOpenings()
    {
        mRecyclerView = (RecyclerView) findViewById(R.id.jobsListStudent);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        final ArrayList<ArrayList<String>> myDataset = new ArrayList<>();

        mAdapter = new MyAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);
        CollectionReference jobsDB= FirebaseFirestore.getInstance().collection("jobs");
        jobsDB.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                for(DocumentSnapshot jobRec: documentSnapshots.getDocuments())
                {
                    ArrayList<String> temp = new ArrayList<String>();
                    temp.add(jobRec.get("name").toString());
                    temp.add(jobRec.get("profile").toString());
                    temp.add(jobRec.get("ctc").toString());
                    temp.add(jobRec.get("location").toString());
                    temp.add(jobRec.get("date").toString());

                    myDataset.add(temp);
                }

//                Log.w("recycle", myDataset.toString());
                Log.w("recycle", "" + mAdapter.getItemCount() );
                // specify an adapter (see also next example)
                mAdapter.notifyDataSetChanged();
            }
        });


    }
    public void signOut(View v)
    {
        Intent signOut=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(signOut);

    }
    public void enrolledJobs(View v)
    {

        Intent enrolledJobs=new Intent(this,EnrolledJobs.class);
        startActivity(enrolledJobs);

    }
}
