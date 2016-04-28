package com.imtpmd.edogunnar.studiebarometer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gunnar on 28-4-2016.
 */
public class VakkenLijstActivity extends AppCompatActivity {

    SharedPreferences mPrefs;
    List<String> vakkenlijst;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vakkenlijst);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }



        final ListView lvVakkenlijst = (ListView) findViewById(R.id.vakkenlijst);
        assert lvVakkenlijst != null;
        lvVakkenlijst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(VakkenLijstActivity.this, DetailsActivity.class);
                String message = "abc";
//                Log.d("lv - vaknaam", vakNaam);
//                Log.d("lv - cijferLijst", cijferLijst.get(position).toString());

                String arr[] = vakkenlijst.get(position).split(":", 4);

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        vakkenLijstVullen();
    }

    public void vakkenLijstVullen()
    {
        try {
            vakkenlijst = new ArrayList<String>();
            ListView vakkenLijstListView = (ListView) findViewById(R.id.vakkenlijst);

            try {
                JSONObject obj = new JSONObject(mPrefs.readStringFromSharedPreferences(getBaseContext(), "vakken"));

                JSONArray jsonArray = obj.getJSONArray("vakken");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject vak = jsonArray.getJSONObject(i);
                    String vakNaam = vak.getString("name");
                    String vakPeriode = vak.getString("period");
                    double vakCijfer = Double.parseDouble(vak.getString("grade"));
                    int vakStudiePunten = Integer.parseInt(vak.getString("ects"));

                    vakkenlijst.add(vakNaam + "      periode: " + vakPeriode + "      EC's: " + vakStudiePunten + "      cijfer: " + vakCijfer);

                }
                Log.d("cijferLijst", vakkenlijst.toString());
                if (vakkenlijst.isEmpty()) {
                    vakkenlijst.add("Je hebt nog geen cijfers ingevoerd");
                }
                ArrayAdapter<String> cijferLijstArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, vakkenlijst); //selected item will look like a spinner set from XML
                vakkenLijstListView.setAdapter(cijferLijstArrayAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
                vakkenlijst.add("Niet gelukt om JSON in te laden");
            }
        }
        catch (NullPointerException e)
        {
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
