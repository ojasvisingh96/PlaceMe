package com.example.ojasvisingh.placeme;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


/**
 * A simple {@link Fragment} subclass.
 */
public class Statistics extends Fragment {


    public Statistics() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity().getApplicationContext());
        String personId = acct.getId();
        View rootView = inflater.inflate(R.layout.fragment_statistics, container, false);
        DocumentReference studentDB= FirebaseFirestore.getInstance().document("students/" + personId);
        Log.d("Id ",personId);
        final TextView name =  rootView.findViewById(R.id.Name_prof);
        final TextView rollno =  rootView.findViewById(R.id.rollno_prof);
        final TextView branch =  rootView.findViewById(R.id.branch_prof);
        final TextView degree =  rootView.findViewById(R.id.degree_prof);
       final TextView cgpa =  rootView.findViewById(R.id.CGPA_prof);
        final TextView enrolled =  rootView.findViewById(R.id.Enrolled_prof);
        studentDB.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                name.setText("Name: "+documentSnapshot.getString("name"));
                rollno.setText("Roll No: "+documentSnapshot.getString("roll"));

                branch.setText("Brnach: "+documentSnapshot.getString("branch"));

                degree.setText("Degree: "+documentSnapshot.getString("degree"));

                cgpa.setText("CGPA: "+documentSnapshot.getString("cgpa"));
                enrolled.setText("Enrolled:  "+documentSnapshot.getString("enrolled"));



            }
        });






        return rootView;

    }


}
