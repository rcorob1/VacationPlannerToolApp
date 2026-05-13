package com.example.vacationplannertool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class DetailedVacation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detailed_vacation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        String vacName = intent.getStringExtra("name");
        int vacId = intent.getIntExtra("id", -1);
        TextView tempTxt = findViewById(R.id.vacTempTextView);
        String tempStr = "Detailed View on " + vacName + " Coming Soon!";
        tempTxt.setText(tempStr);
        FloatingActionButton addVacFragButton = findViewById(R.id.vacDetailBackBtn);
        addVacFragButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }


        });

        Button vacDetailUpdateBtn = findViewById(R.id.vacDetailUpdateBtn);
        vacDetailUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailedVacation.this, UpdateVacationActivity.class);
                intent.putExtra("name", vacName);
                intent.putExtra("id", vacId);
                startActivity(intent);
            }


        });

        FloatingActionButton vacDelBtn = findViewById(R.id.vacDetailDelBtn);
        vacDelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repository repo = new repository(getApplication());
                Vacation vacToDelete = new Vacation();
                for (Vacation vac : repo.getVacations()) {
                    if (vac.getVacId() == vacId) {
                        vacToDelete = vac;
                    }
                }

                repo.delete(vacToDelete);

                Toast.makeText(DetailedVacation.this, "Vacation deleted successfully", Toast.LENGTH_LONG).show();
                finish();
            }


        });
    }

}