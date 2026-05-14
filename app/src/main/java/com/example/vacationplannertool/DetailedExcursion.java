package com.example.vacationplannertool;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.vacationplannertool.database.repository;
import com.example.vacationplannertool.entity.Excursion;
import com.example.vacationplannertool.entity.Vacation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DetailedExcursion extends AppCompatActivity {
    private repository repo;
    private int vacId;
    private int excId;
    private String excName;
    private Excursion currentExc;
    final Calendar calExc = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener excDateDPD;

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
        EditText excUpdTextName = findViewById(R.id.updExcNameField);
        EditText excUpdDate = findViewById(R.id.updExcDateField);
        currentExc = new Excursion();
        for(Excursion exc : repo.getExcursions()){
            if(exc.getExcId() == excId) {
                currentExc = exc;
            }
        };

        String excName = currentExc.getExcName();
        String excDateStr = currentExc.getExcDate();
        excTextName.setText(excName);
        excUpdTextName.setText(excName);
        excUpdDate.setText(excDateStr);

        String format = "yyyy/MM/dd";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        try {
            Date excDate = sdf.parse(excDateStr);
            if(excDate != null) {
                calExc.setTime(excDate);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        FloatingActionButton excDelBtn = findViewById(R.id.excDeleteBtn);
        excDelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentExc = new Excursion();
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

        FloatingActionButton addExcBacBtn = findViewById(R.id.excBackBtn);
        addExcBacBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }

        });

        FloatingActionButton saveBtn = findViewById(R.id.excSaveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repo = new repository(getApplication());

                Vacation assocVac = new Vacation();
                for(Vacation vac : repo.getVacations()) {
                    if(vacId == vac.getVacId()) {
                        assocVac = vac;
                    }
                }

                EditText excUpdTextName = findViewById(R.id.updExcNameField);
                EditText excUpdDate = findViewById(R.id.updExcDateField);
                CheckBox excNotiBox = findViewById(R.id.updExcCheckBox);

                String excNewName = excUpdTextName.getText().toString();
                String excNewDate = excUpdDate.getText().toString();

                String format = "yyyy/MM/dd";
                SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
                Date newDate = new Date();
                Date vacStart = new Date();
                Date vacEnd = new Date();
                try {
                    newDate = sdf.parse(excNewDate);
                    vacStart = sdf.parse(assocVac.getVacStartDate());
                    vacEnd = sdf.parse(assocVac.getVacEndDate());
                } catch (Exception e) { e.printStackTrace(); }

                if(newDate.before(vacStart) || newDate.after(vacEnd)) {
                    Toast.makeText(DetailedExcursion.this, "Excursion could not be updated", Toast.LENGTH_LONG).show();
                }
                else {
                    currentExc.setExcName(excNewName);
                    currentExc.setExcDate(excNewDate);
                    repo.update(currentExc);
                    Toast.makeText(DetailedExcursion.this, "Excursion updated successfully!", Toast.LENGTH_LONG).show();
                    finish();
                }



            }

        });


        excDateDPD = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker dp, int yr, int mo, int dy) {
                calExc.set(Calendar.YEAR, yr);
                calExc.set(Calendar.MONTH, mo);
                calExc.set(Calendar.DAY_OF_MONTH, dy);

                String format = "yyyy/MM/dd";
                SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
                TextView addExcDate = findViewById(R.id.updExcDateField);
                addExcDate.setText(sdf.format(calExc.getTime()));

            }
        };

        TextView addExcDate = findViewById(R.id.updExcDateField);
        addExcDate.setKeyListener(null);
        addExcDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date date;
                new DatePickerDialog(DetailedExcursion.this, excDateDPD,
                        calExc.get(Calendar.YEAR),
                        calExc.get(Calendar.MONTH),
                        calExc.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }


}