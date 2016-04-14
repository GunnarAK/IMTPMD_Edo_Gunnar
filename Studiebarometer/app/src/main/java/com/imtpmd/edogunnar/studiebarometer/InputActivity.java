package com.imtpmd.edogunnar.studiebarometer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gunnar on 25-3-2016.
 */
public class InputActivity extends AppCompatActivity {
    com.imtpmd.edogunnar.studiebarometer.SharedPreferences mprefs;
//    com.imtpmd.edogunnar.studiebarometer.MainActivity mainActivity;

    private MainActivity mainActivity;
    private String[] spinnerArrayPeriode = {"1", "2", "3", "4"};
    private List<String> spinnerArrayVak = new ArrayList<String>();

    JSONArray jsonArray;

    Spinner spinnerPeriode;
    String itemSelectedPeriode;
    Spinner spinnerVak;
    String itemSelectedVak;

    JSONObject vak;
    String vakNaam;
    String vakPeriode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        spinnerPeriode = (Spinner) findViewById(R.id.periodeKeuze);
        spinnerPeriode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                itemSelectedPeriode = parent.getItemAtPosition(pos).toString();
                populateSpinnerVak();
                Toast.makeText(getBaseContext(), "andere periode geklikt", Toast.LENGTH_SHORT).show();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerVak = (Spinner) findViewById(R.id.vakKeuze);
        spinnerVak.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                itemSelectedVak = parent.getItemAtPosition(pos).toString();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        try {
            JSONObject obj = new JSONObject(mprefs.readStringFromSharedPreferences(getBaseContext(), "vakken"));
            jsonArray = obj.getJSONArray("vakken");

            for (int i = 0; i < jsonArray.length(); i++) {
                vak = jsonArray.getJSONObject(i);
                vakNaam = vak.getString("name");
                vakPeriode = vak.getString("period");

                if(itemSelectedPeriode == vakPeriode)
                {
                    spinnerArrayVak.add(vakNaam);
                    Log.d("vakPeriode", vakPeriode);
                }

//                Log.d("vakNaam[" + i + "]", vakPeriode + ", " + vakNaam);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        populateSpinnerPeriode();

        Button confirmButton = (Button) findViewById(R.id.buttonConfirm);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "Cijfer toegevoegd!", Toast.LENGTH_SHORT).show();
//                itemSelectedPeriode;
//                itemSelectedVak;
                EditText cijferInvoer = (EditText) findViewById(R.id.cijferInvoer);
                cijferInvoer.getText();
                startActivity(new Intent(InputActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    public void populateSpinnerPeriode()
    {

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArrayPeriode); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPeriode.setAdapter(spinnerArrayAdapter);
    }

    public void populateSpinnerVak()
    {
        spinnerArrayVak.clear();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                vak = jsonArray.getJSONObject(i);
                vakNaam = vak.getString("name");
                vakPeriode = vak.getString("period");

//                Log.d("periode vergelijking", vakPeriode+" ?= "+itemSelectedPeriode);
                if(vakPeriode.equals(itemSelectedPeriode))
                {
                    spinnerArrayVak.add(vakNaam);
//                    Log.d("vakPeriode", vakPeriode);
                }

//                Log.d("spinnerArrayVak", spinnerArrayVak.toString());
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArrayVak); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVak.setAdapter(spinnerArrayAdapter);
    }
}
