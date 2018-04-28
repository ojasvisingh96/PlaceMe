/*
Displays to student the job openings he/she applied for and the stage at which each placement process is in as well as whether the student is selected for same
 */
package com.example.ojasvisingh.placeme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class EnrolledJobs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrolled_jobs);
        RecyclerView enrolledLisr=findViewById(R.id.enrolledList);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        final String personId = acct.getId();
        DocumentReference studentDB= FirebaseFirestore.getInstance().document("students/" +personId);
        final ArrayList<String> names=new ArrayList<>();
        final ArrayList<String> dates=new ArrayList<>();
        final ArrayList<String> times=new ArrayList<>();
        final  ArrayList<String> stages=new ArrayList<>();
        final ArrayList<String> results=new ArrayList<>();
        enrolledLisr.setLayoutManager(new LinearLayoutManager(this));
        final EnrolledJobAdapter enrolledListad=new EnrolledJobAdapter(names,dates,times,stages,results);
        enrolledLisr.setAdapter(enrolledListad);
        studentDB.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String enrolled=documentSnapshot.getString("enrolled");
                if(enrolled!=null)
                {
                    String [] enrolls=enrolled.split(",");
                    for(int i=0;i<enrolls.length;i++)
                    {
                        DocumentReference processDB= FirebaseFirestore.getInstance().document("process/" +enrolls[i]);
                        processDB.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                names.add(documentSnapshot.getId());
                                if(documentSnapshot.getString("date")!=null)
                                {
                                    dates.add(documentSnapshot.getString("date"));
                                    times.add(documentSnapshot.getString("time"));
                                    stages.add(documentSnapshot.getString("stage"));
                                    if(documentSnapshot.getString(personId)!=null)
                                    {
                                        results.add("selected");

                                    }
                                    else
                                        results.add("not selected");
                                }
                                else
                                {
                                    dates.add("TBD");
                                    times.add("TBD");
                                    stages.add("TBD");
                                    results.add("TBD");
                                }
                                enrolledListad.notifyDataSetChanged();



                            }
                        });

                    }
                }

            }
        });

    }
}

class EnrolledJob extends RecyclerView.ViewHolder{  //view holder for recycler view
    TextView name;
    TextView date;
    TextView time;
    TextView stage;
    TextView result;
    String id;

    public EnrolledJob(View itemView) {
        super(itemView);
        name=itemView.findViewById(R.id.enrollComp);
        date=itemView.findViewById(R.id.enrollDate);
        time=itemView.findViewById(R.id.enrollTime);
        stage=itemView.findViewById(R.id.enrollStage);
        result=itemView.findViewById(R.id.enrollResult);




    }

}

class EnrolledJobAdapter extends RecyclerView.Adapter<EnrolledJob>     //Adapter for recycler view
{
    ArrayList<String> Names;
    ArrayList<String> date;
    ArrayList<String> time;
    ArrayList<String> stage;
    ArrayList<String> result;

    public EnrolledJobAdapter(ArrayList<String> names, ArrayList<String> date, ArrayList<String> time, ArrayList<String> stage, ArrayList<String> result) {
        Names = names;
        this.date = date;
        this.time = time;
        this.stage = stage;
        this.result = result;
    }

    @Override
    public EnrolledJob onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=(LayoutInflater.from(parent.getContext()));
        View Row=(View) inflater.inflate(R.layout.row_layout_student_enrolled,parent,false);
        return new EnrolledJob(Row);
    }

    @Override
    public void onBindViewHolder(EnrolledJob holder, int position) {
        holder.name.setText(Names.get(position));
        holder.result.setText(result.get(position));
        holder.date.setText(date.get(position));
        holder.time.setText(time.get(position));
        holder.stage.setText(stage.get(position));


    }

    @Override
    public int getItemCount() {
        return Names.size();
    }
}