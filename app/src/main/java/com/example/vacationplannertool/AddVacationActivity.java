package com.example.vacationplannertool;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.example.vacationplannertool.entity.Vacation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddVacationActivity extends AppCompatActivity {

    final Calendar calStart = Calendar.getInstance();
    final Calendar calEnd = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener startVacDateDPD;
    DatePickerDialog.OnDateSetListener endVacDateDPD;


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

                EditText vacHotField = findViewById(R.id.addVacHotelField);
                String vacHotelName = vacHotField.getText().toString();

                String format = "yyyy/MM/dd";
                SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);

                String vacStartDate = sdf.format(calStart.getTime());
                String vacEndDate = sdf.format(calEnd.getTime());

                repository repo = new repository(getApplication());

                Vacation newVac = new Vacation();
                newVac.setVacName(newVacName);
                newVac.setVacHotel(vacHotelName);
                newVac.setVacStartDate(vacStartDate);
                newVac.setVacEndDate(vacEndDate);
                newVac.setVacId(0);

                if(calEnd.before(calStart)) {
                    Toast.makeText(AddVacationActivity.this, "End Date must be after Start Date", Toast.LENGTH_LONG).show();
                }
                else {
                    repo.insert(newVac);

                    CheckBox notiBox = findViewById(R.id.notiCheckbox);
                    if(notiBox.isChecked()) {
                        Date snDate = null;
                        Date enDate = null;
                        try {
                            snDate = sdf.parse(vacStartDate);
                            enDate = sdf.parse(vacEndDate);
                        } catch(Exception e) {
                            e.printStackTrace();
                        }
                        String snMsg = newVacName + " vacation begins today!";
                        Intent snIntent = new Intent(AddVacationActivity.this, MyNotificationReceiver.class);
                        snIntent.putExtra("msg", snMsg);

                        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                AddVacationActivity.this,
                                (int) System.currentTimeMillis(),
                                snIntent,
                                PendingIntent.FLAG_IMMUTABLE
                        );

                        String enMsg = newVacName + " vacation ends today!";
                        Intent enIntent = new Intent(AddVacationActivity.this, MyNotificationReceiver.class);
                        enIntent.putExtra("msg", enMsg);

                        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(
                                AddVacationActivity.this,
                                (int) System.currentTimeMillis(),
                                enIntent,
                                PendingIntent.FLAG_IMMUTABLE
                        );

                        AlarmManager alarMan = (AlarmManager) getSystemService(ALARM_SERVICE);
                        if (alarMan != null && snDate != null && enDate != null) {

                            alarMan.set(AlarmManager.RTC_WAKEUP, snDate.getTime(), pendingIntent);
                            alarMan.set(AlarmManager.RTC_WAKEUP, enDate.getTime(), pendingIntent2);
                        }
                        else {
                            Toast.makeText(AddVacationActivity.this, "Could not generate push notifications", Toast.LENGTH_LONG).show();
                        }
                    }
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
                TextView addVacStarDate = findViewById(R.id.addVacStartDate);
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
                TextView addVacEndDate = findViewById(R.id.addVacEndDate);
                addVacEndDate.setText(sdf.format(calEnd.getTime()));

            }
        };

        TextView addVacStarDate = findViewById(R.id.addVacStartDate);
        addVacStarDate.setKeyListener(null);
        addVacStarDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date date;
                new DatePickerDialog(AddVacationActivity.this, startVacDateDPD,
                        calStart.get(Calendar.YEAR),
                        calStart.get(Calendar.MONTH),
                        calStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        TextView addVacEndDate = findViewById(R.id.addVacEndDate);
        addVacEndDate.setKeyListener(null);
        addVacEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date date;
                new DatePickerDialog(AddVacationActivity.this, endVacDateDPD,
                        calEnd.get(Calendar.YEAR),
                        calEnd.get(Calendar.MONTH),
                        calEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
}