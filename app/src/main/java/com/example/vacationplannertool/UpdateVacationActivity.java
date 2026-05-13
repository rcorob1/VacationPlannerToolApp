package com.example.vacationplannertool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class UpdateVacationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_vacation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        int vacId = intent.getIntExtra("id", -1);
        String vacName = intent.getStringExtra("name");
        EditText updateVacName = findViewById(R.id.updateVacName);
        updateVacName.setText(vacName);
        TextView tempTxt = findViewById(R.id.vacUpdateTempTxt);
        String tempStr = "Update features for vacation " + vacName + " coming soon!";
        tempTxt.setText(tempStr);




        FloatingActionButton updateVacBack = findViewById(R.id.updateVacBackBtn);
        updateVacBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });

        FloatingActionButton updateVacSave = findViewById(R.id.updateVacSaveBtn);
        updateVacSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repository repo = new repository(getApplication());
                EditText updateVacName = findViewById(R.id.updateVacName);
                String newVacName = updateVacName.getText().toString();
                Vacation updatedVac = new Vacation();
                updatedVac.setVacId(vacId);
                updatedVac.setVacName(newVacName);
                repo.update(updatedVac);
                Toast.makeText(UpdateVacationActivity.this, "Updated Vacation Successfully!", Toast.LENGTH_LONG).show();
                finish();
            }

        });
    }
}