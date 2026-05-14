package com.example.vacationplannertool;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.vacationplannertool.database.repository;
import com.example.vacationplannertool.entity.Excursion;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddExcursionActivity extends AppCompatActivity {
    private repository repo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_excursion);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FloatingActionButton backBtn = findViewById(R.id.addExcBackBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        FloatingActionButton saveBtn = findViewById(R.id.addExcSaveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repo = new repository(getApplication());

                EditText excNameField = findViewById(R.id.addExcNameField);
                String newExcName = excNameField.getText().toString();
                int vacId = getIntent().getIntExtra("vacId", -1);
                if(vacId >= 0) {
                    Excursion newExc = new Excursion();
                    newExc.setExcName(newExcName);
                    newExc.setVacId(vacId);
                    newExc.setExcId(0);
                    repo.insert(newExc);
                    Toast.makeText(AddExcursionActivity.this, "Excursion created successfully", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });


    }
}