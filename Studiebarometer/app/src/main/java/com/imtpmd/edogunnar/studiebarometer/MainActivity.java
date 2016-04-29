package com.imtpmd.edogunnar.studiebarometer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<HashMap<String, String>> arrayListVakken;
    String vakNaam;
    String vakStudiePunten;
    String vakCijfer;
    String vakPeriode;

    JSONArray jsonArray;

    public String myPreferences;
    //    public String naamStudent;
    SharedPreferences mPrefs;
    SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy");
    String stringDatumBeginPeriode1;
    String stringDatumEindPeriode1;
    String stringDatumBeginPeriode2;
    String stringDatumEindPeriode2;
    String stringDatumBeginPeriode3;
    String stringDatumEindPeriode3;
    String stringDatumBeginPeriode4;
    String stringDatumEindPeriode4;

    TextView huidigePeriode;
    TextView advies;

    List<String> cijferLijst;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        naamVaststellen();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open nieuwe activity om cijfers toe te voegen
                startActivity(new Intent(MainActivity.this, InputActivity.class));
//                finish();
            }
        });

        Button alleVakkenButton = (Button) findViewById(R.id.buttonAlleVakken);
        assert alleVakkenButton != null;
        alleVakkenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open nieuwe activity om alle vakken te bekijken
                startActivity(new Intent(MainActivity.this, VakkenLijstActivity.class));
            }
        });

        final ListView lvCijferlijst = (ListView) findViewById(R.id.cijfersListView);
        assert lvCijferlijst != null;
        lvCijferlijst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                String message = "abc";
//                Log.d("lv - vaknaam", vakNaam);
//                Log.d("lv - cijferLijst", cijferLijst.get(position).toString());

                String arr[] = cijferLijst.get(position).split(":", 4);

//                Log.d("lv - vak", arr[0]);
                String vak[] = arr[0].toString().split(" ", 2);
                String vakNaamSelected = vak[0];
//                vakje.split(" ", 2);
                Log.d("vakNaamSelected", vakNaamSelected);


//                Log.d("lv - position", String.valueOf(position));
//                Log.d("lv - id", String.valueOf(id));
                intent.putExtra("vakNaam", vakNaamSelected);
                startActivity(intent);
            }
        });



        advies = (TextView) findViewById(R.id.advies);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("onResume", "CALLED");
        datumVaststellen();
        studiepuntenVaststellen();
        adviesGeven();
    }

    public void naamVaststellen() {
        try {

            TextView naamStudent = (TextView) findViewById(R.id.naamStudent);
            naamStudent.setText(mPrefs.readStringFromSharedPreferences(getBaseContext(), "naamStudent"));
        } catch (NullPointerException e) {
            Log.d("Naam mPrefs", "Kon naam niet vinden");
            startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            finish();
        }
    }


    public void datumVaststellen() {
        Date datumVandaagParsed = new Date();

        String datumVandaag = simpleDate.format(new Date());
        Log.d("datumVandaag", datumVandaag);

        huidigePeriode = (TextView) findViewById(R.id.huidigePeriode);


        Date datumBeginPeriode1 = new Date();
        Date datumEindPeriode1 = new Date();


        Date datumBeginPeriode2 = new Date();
        Date datumEindPeriode2 = new Date();


        Date datumBeginPeriode3 = new Date();
        Date datumEindPeriode3 = new Date();


        Date datumBeginPeriode4 = new Date();
        Date datumEindPeriode4 = new Date();

        try {

            datumVandaagParsed = simpleDate.parse(datumVandaag);

            stringDatumBeginPeriode1 = "01/09/2015";
            datumBeginPeriode1 = simpleDate.parse(stringDatumBeginPeriode1);
            stringDatumEindPeriode1 = "10/11/2015";
            datumEindPeriode1 = simpleDate.parse(stringDatumEindPeriode1);

            Log.d("Periode 1", stringDatumBeginPeriode1 + " - " + stringDatumEindPeriode1);

            stringDatumBeginPeriode2 = "17/11/2015";
            datumBeginPeriode2 = simpleDate.parse(stringDatumBeginPeriode2);
            stringDatumEindPeriode2 = "02/02/2016";
            datumEindPeriode2 = simpleDate.parse(stringDatumEindPeriode2);

            Log.d("Periode 2", stringDatumBeginPeriode2 + " - " + stringDatumEindPeriode2);

            stringDatumBeginPeriode3 = "09/02/2016";
            datumBeginPeriode3 = simpleDate.parse(stringDatumBeginPeriode3);
            stringDatumEindPeriode3 = "20/04/2016";
            datumEindPeriode3 = simpleDate.parse(stringDatumEindPeriode3);

            Log.d("Periode 3", stringDatumBeginPeriode3 + " - " + stringDatumEindPeriode3);

            stringDatumBeginPeriode4 = "27/04/2016";
            datumBeginPeriode4 = simpleDate.parse(stringDatumBeginPeriode4);
            stringDatumEindPeriode4 = "13/07/2016";
            datumEindPeriode4 = simpleDate.parse(stringDatumEindPeriode4);

            Log.d("Periode 4", stringDatumBeginPeriode4 + " - " + stringDatumEindPeriode4);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }


        try {
            // periode 1
            if (datumVandaagParsed.after(datumBeginPeriode1) && datumVandaagParsed.before(datumEindPeriode1)) {

                huidigePeriode.setText("Periode 1");
            }

            // periode tussen periode 1 en 2
            if (datumVandaagParsed.after(datumEindPeriode1) && datumVandaagParsed.before(datumBeginPeriode2)) {

                huidigePeriode.setText("Periode 2 begint vanaf " + datumBeginPeriode2);
            }

            // periode 2
            if (datumVandaagParsed.after(datumBeginPeriode2) && datumVandaagParsed.before(datumEindPeriode2)) {

                huidigePeriode.setText("Periode 2");
            }

            // periode tussen periode 2 en 3
            if (datumVandaagParsed.after(datumEindPeriode2) && datumVandaagParsed.before(datumBeginPeriode3)) {

                huidigePeriode.setText("Periode 3 begint vanaf " + datumBeginPeriode3);
            }

            // periode 3
            if (datumVandaagParsed.after(datumBeginPeriode3) && datumVandaagParsed.before(datumEindPeriode3)) {

                huidigePeriode.setText("Periode 3");
            }

            // periode tussen periode 3 en 4
            if (datumVandaagParsed.after(datumEindPeriode3) && datumVandaagParsed.before(datumBeginPeriode4)) {

                huidigePeriode.setText("Periode 4 begint vanaf " + datumBeginPeriode4);
            }

            // periode 4
            if (datumVandaagParsed.after(datumBeginPeriode4) && datumVandaagParsed.before(datumEindPeriode4)) {

                huidigePeriode.setText("Periode 4");
            }

            Log.d("huidigePeriode MA", huidigePeriode.getText().toString());

        } catch (NullPointerException e) {
            Log.d("periode uitlezen", "niet gelukt");
            e.printStackTrace();
        }


        try {
            myPreferences = mPrefs.readStringFromSharedPreferences(getBaseContext(), "vakken");
        } catch (NullPointerException e) {
            Log.d("sharedPreferences", "geen preferences gevonden");
            e.printStackTrace();
            JSONParser(loadJSONFromAsset());
        }


    }

    public void studiepuntenVaststellen() {
        try {
            TextView studiePunten = (TextView) findViewById(R.id.aantalStudiepunten);
            int aantalStudiePunten = 0;

            try {
                JSONObject obj = new JSONObject(mPrefs.readStringFromSharedPreferences(getBaseContext(), "vakken"));

                jsonArray = obj.getJSONArray("vakken");

                cijferLijst = new ArrayList<String>();
                ListView cijferLijstListView = (ListView) findViewById(R.id.cijfersListView);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject vak = jsonArray.getJSONObject(i);
                    vakNaam = vak.getString("name");
                    vakPeriode = vak.getString("period");
                    double vakCijfer = Double.parseDouble(vak.getString("grade"));
                    int vakStudiePunten = Integer.parseInt(vak.getString("ects"));

                    // tellen studiepunten
                    if (vakCijfer >= 5.5) {
                        aantalStudiePunten = aantalStudiePunten + vakStudiePunten;
                    }

                    if (vakCijfer >= 1) {
//                        cijferLijstListView.setBackgroundColor(Color.parseColor("#ffffff"));
//                        if (vakCijfer >= 5.5)
//                        {
//                            cijferLijstListView.setBackgroundColor(Color.parseColor("#00ff00"));
//                        }
                        cijferLijst.add(vakNaam + "      periode: " + vakPeriode + "      EC's: " + vakStudiePunten + "      cijfer: " + vakCijfer);

                    }

                    // ingevoerde cijfers noteren in listview
                    // populate listview
                    // per listitem: cijferVak & cijferCijfer

//                    ListView cijferLijstListView = (ListView) findViewById(R.id.cijfersListView);

//                    List<String> cijferLijst = new ArrayList<String>();

//                    cijferLijst.clear();
//                    try {
//                        for (int c = 0; c < jsonArray.length(); c++) {
//                            vak = jsonArray.getJSONObject(c);
////                            Log.d("vak", vak.toString());
//                            String vakNaamCijferLijst = vak.getString("name");
//                            String vakPeriodeCijferLijst = vak.getString("period");
//                            String vakCijferCijferLijst = vak.getString("grade");
//                            String vakStudiepuntenCijferLijst = vak.getString("ects");
//
//                            if (Double.parseDouble(vakCijferCijferLijst) >= 1) {
//                                cijferLijst.add(vakNaamCijferLijst + ", periode: " + vakPeriodeCijferLijst + ", EC's: " + vakStudiepuntenCijferLijst + ", cijfer: " + vakCijferCijferLijst);
//
//                            }
                }
                Log.d("cijferLijst", cijferLijst.toString());
                if (cijferLijst.isEmpty()) {
                    cijferLijst.add("Je hebt nog geen cijfers ingevoerd");
                }

//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }

                ArrayAdapter<String> cijferLijstArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cijferLijst); //selected item will look like a spinner set from XML
//                        cijferLijstArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                cijferLijstListView.setAdapter(cijferLijstArrayAdapter);


            } catch (JSONException e) {
                e.printStackTrace();
            }


            studiePunten.setText(String.valueOf(aantalStudiePunten));


//            Log.d("EC's mPrefs", mPrefs.readStringFromSharedPreferences(getBaseContext(), "aantalStudiepunten"));
        } catch (NullPointerException e) {
            Log.d("EC's", "Kon studiepunten-aantal niet berekenen");
            e.printStackTrace();
        }
    }

    public void adviesGeven() {

        int adviesTotaalEC = 0;
//        int puntenPeriode1 = 13;
        int puntenPeriode2 = 16;
        int puntenPeriode3 = 14;
        int puntenPeriode4 = 17;

        boolean nietIngevuldeCijfers = true;

        int mogelijkePunten = 0;

        if (huidigePeriode.getText().toString().contains("Periode 1")) {
            try {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject vak = jsonArray.getJSONObject(i);
//                    vakNaam = vak.getString("name");
//                    vakPeriode = vak.getString("period");
                    String vakCijfer = vak.getString("grade");
                    double vakCijferDouble = Double.parseDouble(vakCijfer);
                    int vakStudiePunten = Integer.parseInt(vak.getString("ects"));

                    if (vakCijferDouble >= 5.5) {
                        adviesTotaalEC = adviesTotaalEC + vakStudiePunten;
                    }
                    if (vakCijfer.equals("0") && (vakPeriode.equals("1") || vakPeriode.equals("2") || vakPeriode.equals("3") || vakPeriode.equals("4")))
                    {
                        mogelijkePunten = mogelijkePunten + vakStudiePunten;
                    }
                }

                Log.d("mogelijkePunten", String.valueOf(mogelijkePunten));
                Log.d("adviesTotaalEC", String.valueOf(adviesTotaalEC));
                adviesTotaalEC = adviesTotaalEC + mogelijkePunten;// + puntenPeriode2 + puntenPeriode3 + puntenPeriode4;
                Log.d("new adviesTotaalEC", String.valueOf(adviesTotaalEC));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (huidigePeriode.getText().toString().contains("Periode 2")) {
            try {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject vak = jsonArray.getJSONObject(i);
//                    vakNaam = vak.getString("name");
//                    vakPeriode = vak.getString("period");
                    String vakCijfer = vak.getString("grade");
                    double vakCijferDouble = Double.parseDouble(vakCijfer);
                    int vakStudiePunten = Integer.parseInt(vak.getString("ects"));

                    if (vakCijferDouble >= 5.5) {
                        adviesTotaalEC = adviesTotaalEC + vakStudiePunten;
                    }
                    if (vakCijfer.equals("0") && (vakPeriode.equals("2") || vakPeriode.equals("3") || vakPeriode.equals("4")))
                    {
                        mogelijkePunten = mogelijkePunten + vakStudiePunten;
                    }
                }

                Log.d("mogelijkePunten", String.valueOf(mogelijkePunten));
                Log.d("adviesTotaalEC", String.valueOf(adviesTotaalEC));
                adviesTotaalEC = adviesTotaalEC + mogelijkePunten;// + puntenPeriode3 + puntenPeriode4;
                Log.d("new adviesTotaalEC", String.valueOf(adviesTotaalEC));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (huidigePeriode.getText().toString().contains("Periode 3")) {
            try {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject vak = jsonArray.getJSONObject(i);
//                    vakNaam = vak.getString("name");
//                    vakPeriode = vak.getString("period");
                    String vakCijfer = vak.getString("grade");
                    double vakCijferDouble = Double.parseDouble(vakCijfer);
                    int vakStudiePunten = Integer.parseInt(vak.getString("ects"));

                    if (vakCijferDouble >= 5.5) {
                        adviesTotaalEC = adviesTotaalEC + vakStudiePunten;
                    }
                    if (vakCijfer.equals("0") && (vakPeriode.equals("3") || vakPeriode.equals("4")))
                    {
                        mogelijkePunten = mogelijkePunten + vakStudiePunten;
                    }
                }

                Log.d("mogelijkePunten", String.valueOf(mogelijkePunten));
                Log.d("adviesTotaalEC", String.valueOf(adviesTotaalEC));
                adviesTotaalEC = adviesTotaalEC + mogelijkePunten;// + puntenPeriode4;
                Log.d("new adviesTotaalEC", String.valueOf(adviesTotaalEC));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (huidigePeriode.getText().toString().contains("Periode 4")) {
            try {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject vak = jsonArray.getJSONObject(i);
                    vakNaam = vak.getString("name");
                    vakPeriode = vak.getString("period");
                    String vakCijfer = vak.getString("grade");
                    double vakCijferDouble = Double.parseDouble(vakCijfer);
                    int vakStudiePunten = Integer.parseInt(vak.getString("ects"));

                    if (vakCijferDouble >= 5.5) {
                        adviesTotaalEC = adviesTotaalEC + vakStudiePunten;
                    }
//                    Log.d("vakCijfer", String.valueOf(vakCijfer));
//                    Log.d("vakPeriode", vakPeriode);

                    if (vakCijfer.equals("0") && vakPeriode.equals("4"))
                    {
                        mogelijkePunten = mogelijkePunten + vakStudiePunten;
                        nietIngevuldeCijfers = true;
                    }
                }

                Log.d("mogelijkePunten", String.valueOf(mogelijkePunten));
                Log.d("adviesTotaalEC", String.valueOf(adviesTotaalEC));
                adviesTotaalEC = adviesTotaalEC + mogelijkePunten;
                Log.d("new adviesTotaalEC", String.valueOf(adviesTotaalEC));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (adviesTotaalEC == 60 || adviesTotaalEC >= 60) {
            if (adviesTotaalEC > 60) {
                adviesTotaalEC = 60;
            }

            advies.setText("Er kunnen nog 60 punten behaald worden! Ga zo door!");

        }
        if (adviesTotaalEC < 60) {
            String deel1 = "Je gaat je propedeuse dit jaar helaas niet meer halen. Gelukkig heb je in het tweede jaar weer kans!";
            String deel2 = "\nJe kunt dit jaar nog eindigen met " + adviesTotaalEC + " punten.";
            if (nietIngevuldeCijfers == false) {deel2 = "";};
            advies.setText( deel1 + deel2 + "\n\nMisgelopen punten: " + (60 - adviesTotaalEC));

        }
        if (adviesTotaalEC < 50) {
            String deel1 = "Je blijft helaas zitten omdat je voor het einde van het jaar niet minimaal 50 punten kunt halen.";
            String deel2 = "\nJe kunt dit jaar nog eindigen met " + adviesTotaalEC + " punten.";
            if (nietIngevuldeCijfers == false) {deel2 = "";};
            advies.setText( deel1 + deel2 + "\n\nMisgelopen punten: " + (60 - adviesTotaalEC));
        }
        if (adviesTotaalEC < 40) {
            String deel1 = "Het is helaas niet meer mogelijk om 40 punten te scoren. Je krijgt een negatief BSA.";
            String deel2 = "\nJe kunt dit jaar nog eindigen met " + adviesTotaalEC + " punten.";
            if (nietIngevuldeCijfers == false) {deel2 = "";};
            advies.setText( deel1 + deel2 + "\n\nMisgelopen punten: " + (60 - adviesTotaalEC));
        }
    }

    public String JSONParser(String jsonString) {
        Log.d("JSONParser()", "Started!");

        Log.d("myPreferences", "GEEN PREFS GEVONDEN : " + myPreferences);
        try {
            JSONObject obj = new JSONObject(jsonString);

            jsonArray = obj.getJSONArray("vakken");
            arrayListVakken = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> hashMap;

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject vak = jsonArray.getJSONObject(i);
                vakNaam = vak.getString("name");
                vakStudiePunten = vak.getString("ects");
                vakCijfer = vak.getString("grade");
                vakPeriode = vak.getString("period");

                // waarden in arraylist zetten
                hashMap = new HashMap<String, String>();
                hashMap.put("name", vakNaam);
                hashMap.put("ects", vakStudiePunten);
                hashMap.put("grade", vakCijfer);
                hashMap.put("period", vakPeriode);

                arrayListVakken.add(hashMap);
            }
            Log.d("jsonstring.json", "succesvol ingelezen");


        } catch (JSONException e) {
            Log.d("JSONParser()", "catch");
            e.printStackTrace();
        }
        Log.d("arrayListVakken", arrayListVakken.toString());
        return jsonString;
    }

    public ArrayList<HashMap<String, String>> getArrayList() {
        return arrayListVakken;
    }

    public String loadJSONFromAsset() {
        // JSON string ophalen
        Log.d("loadJSONFromAsset()", "Started!");
        Toast.makeText(getBaseContext(), "jsonstring.json inlezen!; ", Toast.LENGTH_SHORT).show();
        String json; // = null;
        try {
            InputStream is = getAssets().open("jsonstring.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            mPrefs.saveStringToSharedPreferences(getBaseContext(), "vakken", json); // opslaan in preferences

        } catch (IOException ex) {
            Log.d("loadJSONFromAsset()", "catch");
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
