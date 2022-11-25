package com.example.realtimedatabase_java;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;


public class MainActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etNim;
    private Button btnSave;
    private RecyclerView rvUser;
//  edit
    @SuppressLint("StaticFieldLeak")
    public static Context context;

    private  FirebaseDatabase rdb = RealtimeDatabase.getInstance();

    private UserAdapter userAdapter;

    private ArrayList<Modul> listUser=new ArrayList<>();
    private ArrayList<String> listKey = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        context = this;
        findViewId();

        btnSave.setOnClickListener(view -> {
            setDataToDatabse(new Modul(
                            UUID.randomUUID().toString(),
                            etName.getText().toString(),
                            etNim.getText().toString()
                    )
            );
        });


        getDataFromDatabase();
    }


    private void initRecycleView() {
        rvUser.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rvUser.setAdapter(userAdapter);
    }

    private void findViewId() {
        etName = findViewById(R.id.etName);
        etNim = findViewById(R.id.etNim);
        btnSave = findViewById(R.id.btSave);
        rvUser = findViewById(R.id.rvUser);

    }

    private void setDataToDatabse(Modul user){
        if(!user.getName().isEmpty() && !user.getNim().isEmpty()){
            btnSave.setEnabled(false);

            rdb.getReference("user").child(user.getId()).setValue(user).addOnSuccessListener(runnable -> {
                etName.setText("");
                etNim.setText("");
                btnSave.setEnabled(true);
                Toast.makeText(this,"Data berhasil disimpan",Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(runnable -> {
                Toast.makeText(this,"Data gagal disimpan",Toast.LENGTH_SHORT).show();
            });

        }
        InputMethodManager systemService =(InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        systemService.hideSoftInputFromWindow(etName.getWindowToken(),0);
        systemService.hideSoftInputFromWindow(etNim.getWindowToken(),0);
    }

    private void getDataFromDatabase(){
        ProgressBar progressBar = findViewById(R.id.pbCircular);
        progressBar.setVisibility(View.VISIBLE);

        rdb.getReference("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressBar.setVisibility(View.GONE);
                listUser.clear();
                listKey.clear();
                snapshot.getChildren().forEach(dataSnapshot -> {
                    HashMap userMap = (HashMap) dataSnapshot.getValue();
                    Modul user = new Modul(
                            (String) userMap.get("id"),
                            (String) userMap.get("name"),
                            (String) userMap.get("nim")
                    );


                    listUser.add(user);
                    listKey.add(dataSnapshot.getKey());
                });

                userAdapter = new UserAdapter(listUser);
                initRecycleView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Log.e("DB ERROR",error.getMessage());
            }
        });
    }




}