/*
Admin end implementation , presently diplays list of registered students and view jobs button to view and create new jobs
 */
package com.example.ojasvisingh.placeme;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Admin extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //recycle view

        mRecyclerView = (RecyclerView) findViewById(R.id.my_other_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        final ArrayList<ArrayList<String>> myDataset = new ArrayList<>();

        mAdapter = new MyOtherAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);


        CollectionReference jobsDB= FirebaseFirestore.getInstance().collection("students");
        jobsDB.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                for(DocumentSnapshot jobRec: documentSnapshots.getDocuments())
                {
                    ArrayList<String> temp = new ArrayList<String>();
                    temp.add(jobRec.get("name").toString());
                    temp.add(jobRec.get("roll").toString());
                    temp.add(jobRec.get("degree").toString());
                    temp.add(jobRec.get("branch").toString());
                    temp.add(jobRec.get("cgpa").toString());

                    myDataset.add(temp);
                }

//                Log.w("recycle", myDataset.toString());
                Log.w("recycle", "" + mAdapter.getItemCount() );
                // specify an adapter (see also next example)
                mAdapter.notifyDataSetChanged();
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    public void onSignOutPress(View v){
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                        Toast.makeText(getApplicationContext(), "Successfully signed out", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    public void viewJobs(View v)
    {
        Intent viewJobs=new Intent(getApplicationContext(),AdminJob.class);
        startActivity(viewJobs);
    }
}
