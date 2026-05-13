package com.example.vacationplannertool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vacationplannertool.adapters.vacationAdapter;
import com.example.vacationplannertool.database.repository;
import com.example.vacationplannertool.entity.Vacation;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private repository repo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });




    }


    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.vacListRecView);
        repo = new repository(getApplication());
        List<Vacation> vacList = repo.getVacations();
        final vacationAdapter vacAdapter = new vacationAdapter(this);
        recyclerView.setAdapter(vacAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacAdapter.setVacs(vacList);

        Button addVacFragButton = findViewById(R.id.addvacationfragbutton);
        addVacFragButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddVacationActivity.class);
                startActivity(intent);
            }


        });
    }
}