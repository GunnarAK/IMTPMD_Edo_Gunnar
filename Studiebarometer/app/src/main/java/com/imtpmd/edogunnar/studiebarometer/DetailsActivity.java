package com.imtpmd.edogunnar.studiebarometer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Gunnar on 25-3-2016.
 */
public class DetailsActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        TextView vakNaam = (TextView) findViewById(R.id.vakNaamEditGrade);
        TextView kernvakToggle = (TextView) findViewById(R.id.vakKernBoolean);
        TextView vakPeriode = (TextView) findViewById(R.id.vakPeriodeEditGrade);
        TextView vakStudiepunten = (TextView) findViewById(R.id.vakStudiepuntenEditGrade);

        EditText cijferInvoer = (EditText) findViewById(R.id.cijferInvoerEditGrade);
        Button buttonConfirmEditGrade = (Button) findViewById(R.id.buttonEditGrade);



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
