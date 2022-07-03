package com.example.prototype10;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class historyActivity extends AppCompatActivity {
    //history fucks you
    RecyclerView recyclerView;
    HistoryRecyclerAdapter historyRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("history");

        setContentView(R.layout.activity_history);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FirebaseRecyclerOptions<HistoryModel> options = new FirebaseRecyclerOptions.Builder<com.example.prototype10.HistoryModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("history"), com.example.prototype10.HistoryModel.class)
                .build();

        historyRecyclerAdapter = new HistoryRecyclerAdapter(options);
        recyclerView = findViewById(R.id.historyRecyclerView);

        recyclerView.setAdapter(historyRecyclerAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);


    }

    @Override
    public void onStart()
    {
        super.onStart();
        historyRecyclerAdapter.startListening();
    }
    @Override
    public void onStop(){
        super.onStop();
        historyRecyclerAdapter.stopListening();
    }



}