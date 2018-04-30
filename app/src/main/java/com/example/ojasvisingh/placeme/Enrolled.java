package com.example.ojasvisingh.placeme;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */

public class Enrolled extends Fragment {
    RecyclerView enrolledLisr;


    public Enrolled() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
        final String personId = acct.getId();

        View rootView = inflater.inflate(R.layout.fragment_enrolled, container, false);
        enrolledLisr = (RecyclerView) rootView.findViewById(R.id.enrolledList);

        DocumentReference studentDB= FirebaseFirestore.getInstance().document("students/" +personId);
        final ArrayList<String> names=new ArrayList<>();
        final ArrayList<String> dates=new ArrayList<>();
        final ArrayList<String> times=new ArrayList<>();
        final  ArrayList<String> stages=new ArrayList<>();
        final ArrayList<String> results=new ArrayList<>();
        enrolledLisr.setLayoutManager(new LinearLayoutManager(getActivity()));
        final EnrolledJobAdapter enrolledListad=new EnrolledJobAdapter(names,dates,times,stages,results);
        enrolledLisr.setAdapter(enrolledListad);
        studentDB.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                String enrolled = documentSnapshot.getString("enrolled");
                final ArrayList<String> names=new ArrayList<>();
                final ArrayList<String> dates=new ArrayList<>();
                final ArrayList<String> times=new ArrayList<>();
                final  ArrayList<String> stages=new ArrayList<>();
                final ArrayList<String> results=new ArrayList<>();
                if (enrolled != null) {
                    String[] enrolls = enrolled.split(",");
                    for (int i = 0; i < enrolls.length; i++) {
                        DocumentReference processDB = FirebaseFirestore.getInstance().document("process/" + enrolls[i]);
                        processDB.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {

                            @Override
                            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                                if(names.contains(documentSnapshot.getId()))
                                {
                                    int in=names.indexOf(documentSnapshot.getId());
                                    names.remove(in);
                                    dates.remove(in);
                                    times.remove(in);
                                    stages.remove(in);
                                    results.remove(in);
                                }

                                names.add(documentSnapshot.getId());
                                if (documentSnapshot.getString("date") != null) {
                                    dates.add(documentSnapshot.getString("date"));
                                    times.add(documentSnapshot.getString("time"));
                                    stages.add(documentSnapshot.getString("stage"));
                                    if (documentSnapshot.getString(personId) != null) {
                                        results.add("selected");

                                    } else
                                        results.add("not selected");
                                } else {
                                    dates.add("TBD");
                                    times.add("TBD");
                                    stages.add("TBD");
                                    results.add("TBD");
                                }
                                Log.d("Enrolled jobs",names.toString());
                                EnrolledJobAdapter enrolledListad=new EnrolledJobAdapter(names,dates,times,stages,results);
                                enrolledLisr.setAdapter(enrolledListad);
                            }

                        });

                    }
                }
            }
                        });


                        return rootView;
                    }

                }
