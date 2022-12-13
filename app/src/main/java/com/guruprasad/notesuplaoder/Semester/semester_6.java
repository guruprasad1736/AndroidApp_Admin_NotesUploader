package com.guruprasad.notesuplaoder.Semester;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.guruprasad.notesuplaoder.R;
import com.guruprasad.notesuplaoder.adapter.lab_manual_adapter;
import com.guruprasad.notesuplaoder.file_model;
import com.guruprasad.notesuplaoder.navigation;

import java.util.Objects;

public class semester_6 extends AppCompatActivity {
    RecyclerView recyclerView;
    lab_manual_adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sem_6);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Semester 3 Lab Manual");


        recyclerView = findViewById(R.id.sem_6_rec);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<file_model> options = new FirebaseRecyclerOptions.Builder<file_model>().setQuery(FirebaseDatabase.getInstance()
                .getReference("admin_lab_manual").child("semester 6"),file_model.class).build();

        adapter = new lab_manual_adapter(options);
        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search,menu);

        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type here to search");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                processsearch(s);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                processsearch(s);

                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    private void processsearch(String s) {

        FirebaseRecyclerOptions<file_model>options = new FirebaseRecyclerOptions.Builder<file_model>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("admin_lab_manual").child("semester 6").
                        orderByChild("file_title").startAt(s).endAt(s+"\uf8ff"),file_model.class).build();

        adapter = new lab_manual_adapter(options);
        adapter.startListening();;
        recyclerView.setAdapter(adapter);


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), navigation.class));
        finish();
    }
}