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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vacationplannertool.adapters.excursionAdapter;
import com.example.vacationplannertool.adapters.vacationAdapter;
import com.example.vacationplannertool.database.repository;
import com.example.vacationplannertool.entity.Excursion;
import com.example.vacationplannertool.entity.Vacation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DetailedVacation extends AppCompatActivity {
    private repository repo;
    private int vacId;

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
        vacId = intent.getIntExtra("id", -1);
        TextView tempTxt = findViewById(R.id.vacTempTextView);
        String tempStr = "Detailed View on " + vacName + " Coming Soon!";
        tempTxt.setText(tempStr);


        FloatingActionButton addVacBacButton = findViewById(R.id.vacDetailBackBtn);
        addVacBacButton.setOnClickListener(new View.OnClickListener() {
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




    }

    protected void onResume() {
        super.onResume();
        TextView tempTxt = findViewById(R.id.vacTempTextView);
        repo = new repository(getApplication());
        Vacation vacToDisp = new Vacation();
        for (Vacation vac : repo.getVacations()) {
            if (vac.getVacId() == vacId) {
                vacToDisp = vac;
            }
        }

        String tempStr = "Details of Vacation";
        tempTxt.setText(tempStr);

        String vacName = vacToDisp.getVacName();
        String hotName = vacToDisp.getVacHotel();
        String staDate = vacToDisp.getVacStartDate();
        String endDate = vacToDisp.getVacEndDate();

        TextView nam = findViewById(R.id.detVacNameField);
        TextView hot = findViewById(R.id.detVacHotelField);
        TextView sta = findViewById(R.id.detVacStartField);
        TextView end = findViewById(R.id.detVacEndField);

        nam.setText(vacName);
        hot.setText(hotName);
        sta.setText(staDate);
        end.setText(endDate);

        RecyclerView recyclerView = findViewById(R.id.excListRecView);
        List<Excursion> excList = repo.getAssocExcursions(vacId);
        final excursionAdapter excAdapter = new excursionAdapter(this);
        recyclerView.setAdapter(excAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        excAdapter.setExcs(excList);

        FloatingActionButton vacDelBtn = findViewById(R.id.vacDetailDelBtn);
        vacDelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repo = new repository(getApplication());
                Vacation vacToDelete = new Vacation();
                List<Excursion> assocExcs = new ArrayList<>();
                for (Vacation vac : repo.getVacations()) {
                    if (vac.getVacId() == vacId) {
                        assocExcs = repo.getAssocExcursions(vacId);
                        vacToDelete = vac;
                    }
                }
                if(assocExcs.isEmpty()) {
                    repo.delete(vacToDelete);

                    Toast.makeText(DetailedVacation.this, "Vacation deleted successfully", Toast.LENGTH_LONG).show();
                    finish();
                }
                else {
                    Toast.makeText(DetailedVacation.this, "Vacation could not be deleted", Toast.LENGTH_LONG).show();
                }
            }


        });
    }

}