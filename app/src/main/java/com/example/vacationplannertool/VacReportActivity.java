package com.example.vacationplannertool;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.vacationplannertool.database.repository;
import com.example.vacationplannertool.entity.Vacation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VacReportActivity extends AppCompatActivity {
    final Calendar calStart = Calendar.getInstance();
    final Calendar calEnd = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener startRepDateDPD;
    DatePickerDialog.OnDateSetListener endRepDateDPD;
    repository repo;
    List<Vacation> vacList;
    TableLayout table;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vac_report);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    protected void onResume() {
        super.onResume();
        FloatingActionButton repBackBtn = findViewById(R.id.vacRepBackBtn);
        repBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }


        });

        TextView repStartField = findViewById(R.id.repStartDateField);
        TextView repEndField = findViewById(R.id.repEndDateField);

        startRepDateDPD = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker dp, int yr, int mo, int dy) {
                calStart.set(Calendar.YEAR, yr);
                calStart.set(Calendar.MONTH, mo);
                calStart.set(Calendar.DAY_OF_MONTH, dy);

                String format = "yyyy/MM/dd";
                SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);

                repStartField.setText(sdf.format(calStart.getTime()));
                if(calStart.after(calEnd)) {
                    calEnd.setTime(calStart.getTime());
                    repEndField.setText(sdf.format(calEnd.getTime()));
                }
                resetTable();
                updateTable();
            }
        };


        repStartField.setKeyListener(null);
        repStartField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date date;
                new DatePickerDialog(VacReportActivity.this, startRepDateDPD,
                        calStart.get(Calendar.YEAR),
                        calStart.get(Calendar.MONTH),
                        calStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endRepDateDPD = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker dp, int yr, int mo, int dy) {
                calEnd.set(Calendar.YEAR, yr);
                calEnd.set(Calendar.MONTH, mo);
                calEnd.set(Calendar.DAY_OF_MONTH, dy);

                String format = "yyyy/MM/dd";
                SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);

                repEndField.setText(sdf.format(calEnd.getTime()));
                if(calEnd.before(calStart)) {
                    calStart.setTime(calEnd.getTime());
                    repStartField.setText(sdf.format(calStart.getTime()));
                }
                resetTable();
                updateTable();

            }
        };

        repEndField.setKeyListener(null);
        repEndField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date date;
                new DatePickerDialog(VacReportActivity.this, endRepDateDPD,
                        calEnd.get(Calendar.YEAR),
                        calEnd.get(Calendar.MONTH),
                        calEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        calStart.setTime(new java.util.Date());
        calEnd.setTime(new java.util.Date());
        calStart.set(Calendar.HOUR_OF_DAY, 0);
        calStart.set(Calendar.MINUTE, 0);
        calStart.set(Calendar.SECOND, 0);
        calStart.set(Calendar.MILLISECOND, 0);
        calEnd.set(Calendar.HOUR_OF_DAY, 0);
        calEnd.set(Calendar.MINUTE, 0);
        calEnd.set(Calendar.SECOND, 0);
        calEnd.set(Calendar.MILLISECOND, 0);
        String format = "yyyy/MM/dd";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        repStartField.setText(sdf.format(calStart.getTime()));
        repEndField.setText(sdf.format(calEnd.getTime()));
        repo = new repository(getApplication());
        vacList = repo.getVacations();
        table = findViewById(R.id.vacRepTableLayout);

        resetTable();
        updateTable();


    }

    void resetTable() {
        table.removeAllViews();
        TableRow hrow = new TableRow(this);
        TextView header1 = new TextView(this);
        TextView header2 = new TextView(this);
        TextView header3 = new TextView(this);
        TextView header4 = new TextView(this);
        header1.setText("Name");
        header1.setPadding(32,16,64,16);
        //header1.setBackgroundColor(Color.LTGRAY);
        hrow.addView(header1);

        header2.setText("Hotel");
        header2.setPadding(32,16,64,16);
        hrow.addView(header2);

        header3.setText("Start Date");
        header3.setPadding(32,16,64,16);
        //header3.setBackgroundColor(Color.LTGRAY);
        hrow.addView(header3);

        header4.setText("End Date");
        header4.setPadding(32,16,64,16);
        hrow.addView(header4);

        table.addView(hrow);
    }
    void updateTable() {
        String format = "yyyy/MM/dd";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        Date sd = new Date();
        Date ed = new Date();

        Date calSD = calStart.getTime();
        Date calED = calEnd.getTime();
        for(Vacation v : vacList) {
            try {
                sd = sdf.parse(v.getVacStartDate());
                ed = sdf.parse(v.getVacEndDate());
                if(((!calSD.before(sd)) && (!calSD.after(ed))) || ((!calED.before(sd)) && (!calED.after(ed)))) {
                    TableRow row = new TableRow(this);

                    TextView r1 = new TextView(this);
                    r1.setText(v.getVacName());
                    r1.setPadding(32,16,64,16);
                    row.addView(r1);

                    TextView r2 = new TextView(this);
                    r2.setText(v.getVacHotel());
                    r2.setPadding(32,16,64,16);
                    row.addView(r2);

                    TextView r3 = new TextView(this);
                    r3.setText(v.getVacStartDate());
                    r3.setPadding(32,16,64,16);
                    row.addView(r3);

                    TextView r4 = new TextView(this);
                    r4.setText(v.getVacEndDate());
                    r4.setPadding(32,16,64,16);
                    row.addView(r4);

                    table.addView(row);
                }

            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}