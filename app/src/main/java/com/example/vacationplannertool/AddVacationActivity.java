package com.example.vacationplannertool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.vacationplannertool.database.repository;
import com.example.vacationplannertool.entity.Vacation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddVacationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_vacation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        FloatingActionButton addVacBack = findViewById(R.id.addVacBackBtn);
        addVacBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });


        FloatingActionButton addVacSave = findViewById(R.id.addVacSaveBtn);
        addVacSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText vacNameField = findViewById(R.id.addVacNameField);
                String newVacName = vacNameField.getText().toString();
                repository repo = new repository(getApplication());

                Vacation newVac = new Vacation();
                newVac.setVacName(newVacName);
                newVac.setVacId(0);

                repo.insert(newVac);
                Toast.makeText(AddVacationActivity.this, "Vacation created successfully", Toast.LENGTH_LONG).show();
                finish();

            }

        });
    }
}