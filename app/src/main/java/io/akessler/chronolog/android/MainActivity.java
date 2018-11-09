package io.akessler.chronolog.android;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.akessler.chronolog.R;
import io.akessler.chronolog.model.TimeEntry;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private DatabaseReference mDatabase;

    private RecyclerView rvTimeEntries;

    private List<TimeEntry> timeEntries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        loadTimeEntries();
        setupTimeToggle();

        rvTimeEntries = findViewById(R.id.rvTimeEntries);
        rvTimeEntries.setAdapter(new TimeEntryAdapter(timeEntries));
        rvTimeEntries.setLayoutManager(new LinearLayoutManager(this));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(toggle) {
                    mDatabase.child("toggle").child("endTime").setValue(System.currentTimeMillis());
                }
                else {
                    TimeEntry entry = new TimeEntry("Chronolog Test", System.currentTimeMillis());
    //                    entry.key = mDatabase.child("times").push().getKey();
                    mDatabase.child("toggle").setValue(entry);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadTimeEntries() {
        if(timeEntries == null) {
            timeEntries = new ArrayList<>();
        }
        mDatabase.child("times").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(TAG, "Adding child, " + dataSnapshot.getKey());
                TimeEntry entry = dataSnapshot.getValue(TimeEntry.class);
                entry.key = dataSnapshot.getKey();
                timeEntries.add(0, entry);
                rvTimeEntries.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private boolean toggle;
    private void setupTimeToggle() {
        mDatabase.child("toggle").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                TimeEntry entry = dataSnapshot.getValue(TimeEntry.class);
                if(entry.endTime == -1) { // could just check for -1
                    // means this entry is still being timed or was just created (toggle on)
                    toggle = true;
                    Log.d("TOGGLE", "TOGGLE ON");
                }
                else {
                    // means this entry is no longer being timed (toggle off)
                    if(toggle) { // toggle will be false on first load
                        entry.key = mDatabase.child("times").push().getKey();
                        mDatabase.child("times").child(entry.key).setValue(entry);
                    }
                    toggle = false;
                    Log.d("TOGGLE", "TOGGLE OFF");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // TODO Handle failed listener
            }
        });
    }
}
