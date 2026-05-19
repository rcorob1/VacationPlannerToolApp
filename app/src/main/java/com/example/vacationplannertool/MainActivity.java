package com.example.vacationplannertool;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vacationplannertool.adapters.vacationAdapter;
import com.example.vacationplannertool.database.repository;
import com.example.vacationplannertool.entity.Excursion;
import com.example.vacationplannertool.entity.Vacation;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Vacation> filteredVacs;
    private repository repo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) !=
                    PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }



    }


    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.vacListRecView);
        repo = new repository(getApplication());
        List<Vacation> vacList = repo.getVacations();
        final vacationAdapter vacAdapter = new vacationAdapter(this);
        recyclerView.setAdapter(vacAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacAdapter.setVacs(vacList);

        SearchView searchView = findViewById(R.id.vacSearchView);
        searchView.setIconifiedByDefault(false);
        searchView.setQuery("", false);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            filteredVacs = new ArrayList<Vacation>();
            for(Vacation vac : vacList) {
                if(vac.getVacName().toLowerCase().contains(newText.toLowerCase())) {
                    filteredVacs.add(vac);
                }
            }
            vacAdapter.setVacs(filteredVacs);
            return true;
        }
        });


        Button addVacFragButton = findViewById(R.id.addvacationfragbutton);
        addVacFragButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddVacationActivity.class);
                startActivity(intent);
            }


        });

        Button genRepBtn = findViewById(R.id.genReportButton);
        genRepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, VacReportActivity.class);
                startActivity(intent);
            }


        });
    }
}