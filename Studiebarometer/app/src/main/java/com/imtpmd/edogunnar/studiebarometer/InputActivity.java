package com.imtpmd.edogunnar.studiebarometer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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
    EditText cijferInvoer;

    JSONObject vak;
    String vakNaam;
    String vakPeriode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        setContentView(R.layout.activity_input);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        spinnerPeriode = (Spinner) findViewById(R.id.periodeKeuze);
        spinnerPeriode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                itemSelectedPeriode = parent.getItemAtPosition(pos).toString();
                populateSpinnerVak();
//                Toast.makeText(getBaseContext(), "andere periode geklikt", Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//        TextView huidigePeriode = (TextView) findViewById(R.id.huidigePeriode);
//        Log.d("huidigePeriode", huidigePeriode.getText().toString());

//        if (huidigePeriode.getText().toString().contains("1")) {
//            spinnerPeriode.setSelection(0);
//        }
//        if (huidigePeriode.getText().toString().contains("2")) {
//            spinnerPeriode.setSelection(1);
//        }
//        if (huidigePeriode.getText().toString().contains("3")) {
//            spinnerPeriode.setSelection(2);
//        }
//        if (huidigePeriode.getText().toString().contains("4")) {
//            spinnerPeriode.setSelection(3);
//        }

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


                if (itemSelectedPeriode == vakPeriode) {
                    spinnerArrayVak.add(vakNaam);

                    Log.d("vakPeriode", vakPeriode);
                }

//                Log.d("vakNaam[" + i + "]", vakPeriode + ", " + vakNaam);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        populateSpinnerPeriode();

        Button confirmButton = (Button) findViewById(R.id.buttonConfirm);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getBaseContext(), "Cijfer toegevoegd!", Toast.LENGTH_SHORT).show();
//                itemSelectedPeriode;
//                itemSelectedVak;
                cijferInvoer = (EditText) findViewById(R.id.cijferInvoer);

//                cijferInvoer.addTextChangedListener(cijferWatcher);

                Log.d("jsonArray", jsonArray.toString());

                Log.d("Input cijferinvoer", cijferInvoer.getText().toString());
                Log.d("spinnerArrayVak", spinnerArrayVak.toString());

                if (!spinnerArrayVak.equals("[Kies een andere periode]")) {
                    if (!cijferInvoer.getText().toString().equals("")) {


                        if (Double.parseDouble(cijferInvoer.getText().toString()) <= 10 && Double.parseDouble(cijferInvoer.getText().toString()) >= 1) {
                            try {

//                JSONArray arr = new JSONArray(str);
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObj = (JSONObject) jsonArray.get(i); // get the josn object
                                    if (jsonObj.getString("name").equals(itemSelectedVak) && jsonObj.getString("period").equals(itemSelectedPeriode)) { // compare for the key-value
                                        ((JSONObject) jsonArray.get(i)).put("grade", cijferInvoer.getText().toString()); // put the new value for the key
                                        Log.d("cijferInvoer", cijferInvoer.getText().toString());
                                        Log.d("itemSelectedVak", itemSelectedVak);
                                        Log.d("itemSelectedPeriode", itemSelectedPeriode);

                                    }
                                }

                                String json = "{\"vakken\":" + jsonArray.toString() + "}";
                                mprefs.saveStringToSharedPreferences(getBaseContext(), "vakken", json);
                                Log.d("jsonArray", jsonArray.toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
//                        mainActivity.studiepuntenVaststellen();
                            finish();
                        } else {

                            Log.d("input", "FOUTTTTTTT");
                            cijferInvoer.setError("Geef een cijfer op tussen 1 t/m 10./n Bijvoorbeeld '5.6'");
                            cijferInvoer.setText("");
                        }
                    } else {

                        Log.d("input", "FOUTTTTTTT");
                        cijferInvoer.setError("Geef een cijfer op tussen 1 t/m 10./n Bijvoorbeeld '5.6'");
                        cijferInvoer.setText("");
                    }
                } else {
                    Log.d("vakken", "er zijn geen vakken meer, selecteer andere periode");
                    TextView spinnerArrayPeriodeTextCast = (TextView) spinnerVak.getSelectedView();
                    spinnerArrayPeriodeTextCast.setError("U moet even een andere periode kiezen.");
                }


//                startActivity(new Intent(InputActivity.this, MainActivity.class));

            }
        });
    }

//    private final TextWatcher cijferWatcher = new TextWatcher() {
//
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//        }
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//        }
//
//        public void afterTextChanged(Editable s) {
//
//        }
//    };

    public void populateSpinnerPeriode() {

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArrayPeriode); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPeriode.setAdapter(spinnerArrayAdapter);
    }

    public void populateSpinnerVak() {
        spinnerArrayVak.clear();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                vak = jsonArray.getJSONObject(i);
                vakNaam = vak.getString("name");
                vakPeriode = vak.getString("period");
                String vakCijferStr = vak.getString("grade");
                double vakCijfer = Double.parseDouble(vak.getString("grade"));

//                Log.d("periode vergelijking", vakPeriode+" ?= "+itemSelectedPeriode);
                if (vakPeriode.equals(itemSelectedPeriode) && (vakCijfer == 0 || vakCijferStr == "")) {
                    spinnerArrayVak.add(vakNaam);
//                    Log.d("vakPeriode", vakPeriode);
                    Log.d("spinnerArrayVak popu-spinner", spinnerArrayVak.toString());
                }


//                Log.d("spinnerArrayVak", spinnerArrayVak.toString());
            }
            if (spinnerArrayVak.isEmpty())
            {
                spinnerArrayVak.add("Kies een andere periode");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArrayVak); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVak.setAdapter(spinnerArrayAdapter);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}
