package com.example.vacationplannertool;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.example.vacationplannertool.entity.Vacation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UpdateVacationActivity extends AppCompatActivity {
    final Calendar calStart = Calendar.getInstance();
    final Calendar calEnd = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener startVacDateDPD;
    private DatePickerDialog.OnDateSetListener endVacDateDPD;
    private repository repo;


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
        //String vacName = intent.getStringExtra("name");
        repo = new repository(getApplication());
        Vacation thisVac = new Vacation();
        for(Vacation vac : repo.getVacations()) {
            if(vac.getVacId() == vacId)
            {
                thisVac = vac;
            }
        }
        String vacName = thisVac.getVacName();
        String vacHotel = thisVac.getVacHotel();
        String vacStart = thisVac.getVacStartDate();
        String vacEnd = thisVac.getVacEndDate();


        EditText updateVacName = findViewById(R.id.updateVacName);
        updateVacName.setText(vacName);

        EditText updateVacHotel = findViewById(R.id.updateVacHotel);
        updateVacHotel.setText(vacHotel);

        EditText updateVacStart = findViewById(R.id.updateStartDate);
        updateVacStart.setText(vacStart);

        EditText updateVacEnd = findViewById(R.id.updateEndDate);
        updateVacEnd.setText(vacEnd);

        String format = "yyyy/MM/dd";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        try {
            Date startDate = sdf.parse(vacStart);
            Date endDate = sdf.parse(vacEnd);
            if(startDate != null) {
                calStart.setTime(startDate);
            }
            if(endDate != null) {
                calEnd.setTime(endDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


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
                EditText updateVacHotel = findViewById(R.id.updateVacHotel);
                EditText updateVacStart = findViewById(R.id.updateStartDate);
                EditText updateVacEnd = findViewById(R.id.updateEndDate);

                String newVacName = updateVacName.getText().toString();
                String newVacHotel = updateVacHotel.getText().toString();
                String newVacStart = updateVacStart.getText().toString();
                String newVacEnd = updateVacEnd.getText().toString();


                Vacation updatedVac = new Vacation();
                updatedVac.setVacId(vacId);
                updatedVac.setVacName(newVacName);
                updatedVac.setVacHotel(newVacHotel);
                updatedVac.setVacStartDate(newVacStart);
                updatedVac.setVacEndDate(newVacEnd);
                if(calEnd.before(calStart)) {
                    Toast.makeText(UpdateVacationActivity.this, "End Date must be after Start Date", Toast.LENGTH_LONG).show();
                }
                else {
                    repo.update(updatedVac);
                    Toast.makeText(UpdateVacationActivity.this, "Updated Vacation Successfully!", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

        });

        startVacDateDPD = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker dp, int yr, int mo, int dy) {
                calStart.set(Calendar.YEAR, yr);
                calStart.set(Calendar.MONTH, mo);
                calStart.set(Calendar.DAY_OF_MONTH, dy);

                String format = "yyyy/MM/dd";
                SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
                TextView addVacStarDate = findViewById(R.id.updateStartDate);
                addVacStarDate.setText(sdf.format(calStart.getTime()));

            }
        };

        endVacDateDPD = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker dp, int yr, int mo, int dy) {
                calEnd.set(Calendar.YEAR, yr);
                calEnd.set(Calendar.MONTH, mo);
                calEnd.set(Calendar.DAY_OF_MONTH, dy);

                String format = "yyyy/MM/dd";
                SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
                TextView addVacEndDate = findViewById(R.id.updateEndDate);
                addVacEndDate.setText(sdf.format(calEnd.getTime()));

            }
        };

        TextView addVacStarDate = findViewById(R.id.updateStartDate);
        addVacStarDate.setKeyListener(null);
        addVacStarDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date date;
                new DatePickerDialog(UpdateVacationActivity.this, startVacDateDPD,
                        calStart.get(Calendar.YEAR),
                        calStart.get(Calendar.MONTH),
                        calStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        TextView addVacEndDate = findViewById(R.id.updateEndDate);
        addVacEndDate.setKeyListener(null);
        addVacEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date date;
                new DatePickerDialog(UpdateVacationActivity.this, endVacDateDPD,
                        calEnd.get(Calendar.YEAR),
                        calEnd.get(Calendar.MONTH),
                        calEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
}