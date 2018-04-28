/*
Creates new job opening after admin fills in required details
 */
package com.example.ojasvisingh.placeme;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class CreateJob extends AppCompatActivity {

    private EditText jobDateAdmin;

    private DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            jobDateAdmin.setText("" + dayOfMonth + "/" + monthOfYear + "/" + year);
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_job);

        jobDateAdmin = (EditText) findViewById(R.id.jobDateAdmin);
    }
    public void createJobConfirm(View v)
    {
        String jobCompany=((TextView)findViewById(R.id.jobCompanyAdmin)).getText().toString();
        String jobLocation=((TextView)findViewById(R.id.jobLocationAdmin)).getText().toString();
        String jobProfile=((TextView)findViewById(R.id.jobProfileAdmin)).getText().toString();
        String jobCtc=((TextView)findViewById(R.id.jobCtcAdmin)).getText().toString();
        String jobDate=((TextView)findViewById(R.id.jobDateAdmin)).getText().toString();
        if(jobCompany.equals("")==false && jobLocation.equals("")==false&&jobProfile.equals("")==false&&jobCtc.equals("")==false&&jobDate.equals("")==false)
        {
            DocumentReference jobsDB= FirebaseFirestore.getInstance().document("jobs/" + jobCompany);
            HashMap<String,String> jobRec=new HashMap<>();
            OnSuccessListener insertRec=new OnSuccessListener() {
                @Override
                public void onSuccess(Object o) {
                    Toast.makeText(getApplicationContext(),"Successfully Created Job !!!",Toast.LENGTH_LONG).show();
                    Intent jobList =new Intent(getApplicationContext(),AdminJob.class);
                    startActivity(jobList);
                }
            };
            jobRec.put("name",jobCompany);
            jobRec.put("location",jobLocation);
            jobRec.put("profile",jobProfile);
            jobRec.put("ctc",jobCtc);
            jobRec.put("date",jobDate);
            jobsDB.set(jobRec).addOnSuccessListener(insertRec);
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Please complete all the details",Toast.LENGTH_LONG).show();
        }

    }

    public void onDateClick(View v){
        new DatePickerDialog(this, dateListener, 2018, 04,
                28).show();
    }
}
