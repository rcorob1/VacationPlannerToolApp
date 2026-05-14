package com.example.vacationplannertool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.vacationplannertool.database.repository;
import com.example.vacationplannertool.entity.Excursion;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailedExcursion extends AppCompatActivity {
    private repository repo;
    private int vacId;
    private int excId;
    private String excName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detailed_excursion);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }

    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        vacId = intent.getIntExtra("vacId", -1);
        excId = intent.getIntExtra("excId", -1);
        repo = new repository(getApplication());
        TextView excTextName = findViewById(R.id.excDetTextName);
        Excursion currentExc = new Excursion();
        for(Excursion exc : repo.getExcursions()){
            if(exc.getExcId() == excId) {
                currentExc = exc;
            }
        };

        String excName = currentExc.getExcName();
        excTextName.setText(excName);

        FloatingActionButton excDelBtn = findViewById(R.id.excDeleteBtn);
        excDelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Excursion currentExc = new Excursion();
                for(Excursion exc : repo.getExcursions()){
                    if(exc.getExcId() == excId) {
                        currentExc = exc;
                    }
                };
                try {
                    repo.delete(currentExc);
                    Toast.makeText(DetailedExcursion.this, "Excursion deleted successfully", Toast.LENGTH_LONG).show();
                    finish();
                } catch(Exception e) {
                    Toast.makeText(DetailedExcursion.this, "Excursion could not be deleted", Toast.LENGTH_LONG).show();
                }
            }


        });

        FloatingActionButton addExcBacBtn = findViewById(R.id.addExcBackBtn);
        addExcBacBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }

        });
    }


}