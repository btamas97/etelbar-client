package com.example.dreamer.etelbarclient;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    int previousViewId = -1;
    private int tableViewNumber;
    private static int tableNumber;
    private DatabaseReference mReference;
    private FirebaseDatabase mDatabase;
    private List<Table> tables = new ArrayList<>();
    private ImageButton tableButton;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    public static int getTableViewNumber(){
        return tableNumber;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference();
        mReference.child("Table").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child:children) {
                    Table table = child.getValue(Table.class);
                    table.setId(child.getKey());
                    tables.add(table);
                    if(!table.isFree()){
                        ImageButton btn = (ImageButton)findViewById(table.getViewID());
                        btn.setImageResource(R.drawable.table_red);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null){
            Intent nouser = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(nouser);
            finish();
        }

    }

    public void jumpNext(View view) {
        view.startAnimation(buttonClick);
        if(previousViewId !=-1){
            tableViewNumber = previousViewId;
            String talbeKey ="";
            for (Table t: tables) {
                if(t.getViewID()== tableViewNumber){
                    talbeKey = t.getId();
                    tableNumber = t.getNumber();
                }
            }
            DatabaseReference mDR = mDatabase.getReference();
            mDR.child("Table").child(talbeKey).child("isFree").setValue(false);

            Intent jump = new Intent(MainActivity.this,EtlapActivity.class);
            startActivity(jump);
            finish();
        }
        else
            Toast.makeText(MainActivity.this,"Nem lett asztal kiválasztva!",Toast.LENGTH_SHORT).show();
    }

    public void table1_Click(final View view) {
        for (Table t:tables) {
            if(t.getViewID()==view.getId()){
                view.startAnimation(buttonClick);
                if(t.isFree()){
                    if(previousViewId != -1){
                        tableButton = (ImageButton)findViewById(previousViewId);
                        tableButton.setImageResource(R.drawable.table_grey);
                    }
                    ImageButton btn = (ImageButton)findViewById(view.getId());
                    btn.setImageResource(R.drawable.table_green);
                    previousViewId = view.getId();
                }
            }
        }
    }
}
