package com.imtpmd.edogunnar.studiebarometer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Gunnar on 25-3-2016.
 */
public class DetailsActivity extends AppCompatActivity {

    SharedPreferences mPrefs;

    JSONObject obj;
    JSONArray jsonArray;
    JSONObject vak;
    int vakIndex;
    String vakNaamJSON;
    String vakPeriodeJSON;
    String vakEctsJSON;
    String vakCijferJSON;

    TextView vakNaam;
    TextView kernvakToggle;
    TextView vakPeriode;
    TextView vakStudiepunten;
    EditText cijferInvoer;

    String vakNaamDetail;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();

        vakNaamDetail = intent.getExtras().getString("vakNaam");
        Log.d("vak - detailsactivity", vakNaamDetail);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        vakNaam = (TextView) findViewById(R.id.vakNaamEditGrade);
        kernvakToggle = (TextView) findViewById(R.id.vakKernBoolean);
        vakPeriode = (TextView) findViewById(R.id.vakPeriodeEditGrade);
        vakStudiepunten = (TextView) findViewById(R.id.vakStudiepuntenEditGrade);

        cijferInvoer = (EditText) findViewById(R.id.cijferInvoerEditGrade);
        Button buttonConfirmEditGrade = (Button) findViewById(R.id.buttonEditGrade);
        ImageButton deleteCijferButton = (ImageButton) findViewById(R.id.deleteCijfer);


        vakNaam.setText(vakNaamDetail);

        if ( vakNaamDetail.contains("IOPR1") || vakNaamDetail.contains("IOPR2") || vakNaamDetail.contains("IRDB") || vakNaamDetail.contains("INET"))
        {
            kernvakToggle.setText("Kernvak: ja");
        }
        else
        {
            kernvakToggle.setText("Kernvak: nee");
        }

        detailsVullen();

        buttonConfirmEditGrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cijferInvoer.getText().toString().equals(""))
                {
                    Log.d("input", "FOUTTTTTTT");
                    cijferInvoer.setError("Geef een cijfer op tussen 1 t/m 10\nBijvoorbeeld '5.6'");
                }
                else if (Double.parseDouble(cijferInvoer.getText().toString()) <= 10 && Double.parseDouble(cijferInvoer.getText().toString()) >= 1) {
                    try {
//                    if (vak.getString("name").equals(vakNaamDetail)) { // compare for the key-value
                        ((JSONObject) jsonArray.get(vakIndex)).put("grade", cijferInvoer.getText().toString()); // put the new value for the key
                        Log.d("vak naam", vak.getString("name"));
                        Log.d("cijferInvoer", cijferInvoer.getText().toString());
//                    }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    String json = "{\"vakken\":" + jsonArray.toString() + "}";
                    mPrefs.saveStringToSharedPreferences(getBaseContext(), "vakken", json);
                    Log.d("jsonArray", jsonArray.toString());

                    finish();
                }
                else {

                    Log.d("input", "FOUTTTTTTT");
                    cijferInvoer.setError("Geef een cijfer op tussen 1 t/m 10\nBijvoorbeeld '5.6'");
                    cijferInvoer.setText("");

                }
            }
        });

        deleteCijferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
//                    if (vak.getString("name").equals(vakNaamDetail)) { // compare for the key-value
                    ((JSONObject) jsonArray.get(vakIndex)).put("grade", "0"); // cijfer verwijderen uit cijferlijst, betekent cijfer 0 maken.
                    Log.d("vak naam", vak.getString("name"));
                    Log.d("cijferInvoer", cijferInvoer.getText().toString());
//                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }


                String json = "{\"vakken\":" + jsonArray.toString() + "}";
                mPrefs.saveStringToSharedPreferences(getBaseContext(), "vakken", json);
                Log.d("jsonArray", jsonArray.toString());

                finish();
            }
        });
    }

    public void detailsVullen()
    {
        try {
            obj = new JSONObject(mPrefs.readStringFromSharedPreferences(getBaseContext(), "vakken"));
            jsonArray = obj.getJSONArray("vakken");

            for (int i = 0; i < jsonArray.length(); i++) {
                vak = jsonArray.getJSONObject(i);
                vakNaamJSON = vak.getString("name");
                vakPeriodeJSON = vak.getString("period");
                vakEctsJSON = vak.getString("ects");
                vakCijferJSON = vak.getString("grade");
//                double vakCijfer = Double.parseDouble(vak.getString("grade"));

//                Log.d("periode vergelijking", vakPeriode+" ?= "+itemSelectedPeriode);
                if (vakNaamJSON.equals(vakNaamDetail) ) {
                    vakIndex = i;
                    vakPeriode.setText("Periode " + vakPeriodeJSON);
                    vakStudiepunten.setText("EC's: " + vakEctsJSON);
                    cijferInvoer.setHint(String.valueOf(Double.parseDouble(vakCijferJSON))); // toont cijfer als 5.0 waar het om een geheel getal gaat als 5
//                    Log.d("spinnerArrayVak popu-spinner", spinnerArrayVak.toString());
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
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
