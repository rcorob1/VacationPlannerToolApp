package com.example.vacationplannertool;

import android.app.DatePickerDialog;
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

public class AddExcursionActivity extends AppCompatActivity {
    private repository repo;
    private Excursion newExc;
    final Calendar calExc = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener excDateDPD;

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


                int vacId = getIntent().getIntExtra("vacId", -1);

                repo = new repository(getApplication());

                Vacation assocVac = new Vacation();
                for(Vacation vac : repo.getVacations()) {
                    if(vacId == vac.getVacId()) {
                        assocVac = vac;
                    }
                }

                EditText excUpdTextName = findViewById(R.id.addExcNameField);
                EditText excUpdDate = findViewById(R.id.addExcDateField);
                CheckBox excNotiBox = findViewById(R.id.addExcCheckBox);

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
                    Toast.makeText(AddExcursionActivity.this, "Excursion could not be created", Toast.LENGTH_LONG).show();
                }
                else {
                    newExc = new Excursion();
                    newExc.setExcName(excNewName);
                    newExc.setExcDate(excNewDate);
                    newExc.setVacId(vacId);
                    newExc.setExcId(0);
                    repo.insert(newExc);
                    Toast.makeText(AddExcursionActivity.this, "Excursion created successfully", Toast.LENGTH_LONG).show();
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
                TextView addExcDate = findViewById(R.id.addExcDateField);
                addExcDate.setText(sdf.format(calExc.getTime()));

            }
        };

        TextView addExcDate = findViewById(R.id.addExcDateField);
        addExcDate.setKeyListener(null);
        addExcDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date date;
                new DatePickerDialog(AddExcursionActivity.this, excDateDPD,
                        calExc.get(Calendar.YEAR),
                        calExc.get(Calendar.MONTH),
                        calExc.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


    }
}