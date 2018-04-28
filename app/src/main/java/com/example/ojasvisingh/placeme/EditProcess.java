/*
Edits ongoing job placement process, for example set the stage to ppt, test etc. , mark attendance , select candidates for next round etc.
 */
package com.example.ojasvisingh.placeme;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class EditProcess extends AppCompatActivity {
    static ArrayList<String> shortlisted=new ArrayList<>();
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_process);
        shortlisted=new ArrayList<>();
        name=getIntent().getStringExtra("comp");
        final RecyclerView students=findViewById(R.id.studentListProcess);
        DocumentReference processDB= FirebaseFirestore.getInstance().document("process/" +name);
        final ArrayList<String> Names=new ArrayList<>();
        final ArrayList<String> ids=new ArrayList<>();
        final ArrayList<String> marks=new ArrayList<>();
        final StudentMarkAdapter studentsad=new StudentMarkAdapter(Names,marks,ids);
        students.setLayoutManager(new LinearLayoutManager(this));
        students.setAdapter(studentsad);
        final HashMap<String,String> idTomk=new HashMap<>();
        processDB.get().addOnSuccessListener(this,new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                final HashMap<String,String> enrolled=(HashMap)documentSnapshot.getData();

                for(String key:enrolled.keySet())
                {
                    if(key.equals("date")==false&&key.equals("time")==false&&key.equals("stage")==false)
                    {
                        idTomk.put(key,"false");
                        DocumentReference studentDB= FirebaseFirestore.getInstance().document("students/" +key);
                        studentDB.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Names.add(documentSnapshot.getString("name"));
                                marks.add(idTomk.get(documentSnapshot.getId()));
                                ids.add(documentSnapshot.getId());
                                studentsad.notifyDataSetChanged();



                            }
                        });


                    }

                }

            }
        });



    }
    public void confirmProcess(View v)
    {
        TextView date=((TextView)findViewById(R.id.dateProcess));
        TextView time=((TextView)findViewById(R.id.timeProcess));
        TextView stage=((TextView)findViewById(R.id.stagesProcess));
        final DocumentReference processDB= FirebaseFirestore.getInstance().document("process/" +name);
        processDB.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                HashMap<String,String> oldRecs=(HashMap)documentSnapshot.getData();
                for(String id:oldRecs.keySet())
                {
                    if(oldRecs.get(id).equals("false"))
                    {
                        DocumentReference studentDB= FirebaseFirestore.getInstance().document("students/" +id);
                        studentDB.update("defaulter","yes");

                    }
                }

            }
        });
        if(shortlisted.size()>0&&date!=null&&time!=null&&stage!=null)
        {
            HashMap<String,String> stageDetails=new HashMap<>();
            stageDetails.put("date",date.getText().toString());
            stageDetails.put("time",time.getText().toString());
            stageDetails.put("stage",stage.getText().toString());
            for(String id:shortlisted)
            {
                stageDetails.put(id,"false");
            }
            processDB.set(stageDetails).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getApplicationContext(),"Successfully Updated To Next Stage",Toast.LENGTH_LONG).show();


                }
            });

        }
        else
        {
            Toast.makeText(getApplicationContext(),"Please fill all the fields and select at least one for next stage",Toast.LENGTH_LONG).show();

        }


    }
    public void placeStudents(View v)
    {
        final DocumentReference processDB= FirebaseFirestore.getInstance().document("process/" +name);
        for(String id:shortlisted)
         {
             final DocumentReference studentDB= FirebaseFirestore.getInstance().document("students/" +id);
             studentDB.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                 @Override
                 public void onSuccess(DocumentSnapshot documentSnapshot) {
                     if(documentSnapshot.getString("placed")==null)
                     {
                         studentDB.update("placed",name);
                     }
                     else
                     {
                         String placed=documentSnapshot.getString("placed");
                         placed=placed+","+name;
                         studentDB.update("placed",placed);
                     }
                     Toast.makeText(getApplicationContext(),"Successfully Placed Students",Toast.LENGTH_LONG).show();

                 }
             });

         }

    }

}

class StudentMark extends RecyclerView.ViewHolder{  //view holder for recycler view
    TextView name;
    CheckBox mark;
    String id;

    public StudentMark(final View itemView) {
        super(itemView);
        name=itemView.findViewById(R.id.studentMark);
        mark=itemView.findViewById(R.id.checkMark);
        mark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(id!=null&&itemView.getContext().getClass()==EditProcess.class) {
                    if (b == true)
                        EditProcess.shortlisted.add(id);
                    else
                        EditProcess.shortlisted.remove(id);
                }
                else if(id!=null)
                {
                    Log.d("Context","mark "+id);

                    if (b == true)
                    {
                        MarkAttendance.shortlisted.add(id);
                        MarkAttendance.unlisted.remove(id);

                    }
                    else {
                        MarkAttendance.shortlisted.remove(id);
                        MarkAttendance.unlisted.add(id);
                    }

                }


            }
        });



    }

}

class StudentMarkAdapter extends RecyclerView.Adapter<StudentMark>     //Adapter for recycler view
{
    ArrayList<String> Names;
    ArrayList<String> marks;
    ArrayList<String> ids;

    public StudentMarkAdapter(ArrayList<String> Names,ArrayList<String> marks,ArrayList<String> ids) {
        this.Names = Names;
        this.marks=marks;
        this.ids=ids;
    }

    @Override
    public StudentMark onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=(LayoutInflater.from(parent.getContext()));
        View Row=(View) inflater.inflate(R.layout.row_layout_student_attendance,parent,false);
        return new StudentMark(Row);
    }

    @Override
    public void onBindViewHolder(StudentMark holder, int position) {
        holder.name.setText(Names.get(position));
        if(marks.get(position).equals("true"))
        {
            holder.mark.setChecked(true);
        }
        else
            holder.mark.setChecked(false);
        holder.id=ids.get(position);


    }

    @Override
    public int getItemCount() {
        return Names.size();
    }
}

