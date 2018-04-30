package com.example.ojasvisingh.placeme;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class Job_opening extends Fragment {
//    private RecyclerView mRecyclerView;
//    final ArrayList<ArrayList<String>> myDataset=null;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    RecyclerView mRecyclerView;
    String name,branch,degree,roll;
    float cgpa;
    private GoogleSignInClient mGoogleSignInClient;
    public Job_opening() {
        // Required empty public constructor
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        RecyclerView mRecycler = (RecyclerView) getView().findViewById(R.id.jobsListStudent);
//        Log.d("yolo", "onCreate: "+mRecycler);
//        mRecycler.setAdapter(adapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
        String personId = acct.getId();

        View rootView = inflater.inflate(R.layout.fragment_job_opening, container, false);
        // 1. get a reference to recyclerView
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.jobsListStudent);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        final ArrayList<ArrayList<String>> myDataset = new ArrayList<>();
//        ArrayList<String> temp = new ArrayList<String>();
//
//
//            temp.add("name");
//            temp.add("profile");
//            temp.add("ctc");
//            temp.add("location");
//            temp.add("date");
//            temp.add("minCGPA");
//
//            myDataset.add(temp);
//


        DocumentReference studentDB= FirebaseFirestore.getInstance().document("students/" + personId);      //fetching my record
        studentDB.addSnapshotListener(getActivity(),new EventListener<DocumentSnapshot>() {
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

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mAdapter = new MyAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        // Inflate the layout for this fragment
getActivity().getIntent();


        return rootView;
    }

    public void getJobOpenings()
    {
//        mRecyclerView = (RecyclerView) findViewById(R.id.jobsListStudent);
//        mRecyclerView.setHasFixedSize(true);
//        mLayoutManager = new LinearLayoutManager(getActivity());
//        mRecyclerView.setLayoutManager(mLayoutManager);
        final ArrayList<ArrayList<String>> myDataset = new ArrayList<>();
//
//        mAdapter = new MyAdapter(myDataset);
//        mRecyclerView.setAdapter(mAdapter);
        CollectionReference jobsDB= FirebaseFirestore.getInstance().collection("jobs");
        jobsDB.addSnapshotListener(getActivity(),new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                ArrayList<ArrayList<String>> mD=new ArrayList<>();

                for(DocumentSnapshot jobRec: documentSnapshots.getDocuments())
                {
                    ArrayList<String> temp = new ArrayList<String>();

                    // check if student's CGPA >= minCGPA
                    if (jobRec.get("minCGPA")!=null&&(Float.compare(Float.parseFloat(jobRec.get("minCGPA").toString()), cgpa) <= 0 )){
                        temp.add(jobRec.get("name").toString());
                        temp.add(jobRec.get("profile").toString());
                        temp.add(jobRec.get("ctc").toString());
                        temp.add(jobRec.get("location").toString());
                        temp.add(jobRec.get("date").toString());
                        temp.add(jobRec.get("minCGPA").toString());

                        mD.add(temp);
                        Log.d("Jobs ",temp.toString());
                    }

                }

//                Log.w("recycle", myDataset.toString());
                Log.w("recycle", "" + mAdapter.getItemCount() );
                // specify an adapter (see also next example)
                mAdapter=new MyAdapter(mD);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }
        });


    }




}
