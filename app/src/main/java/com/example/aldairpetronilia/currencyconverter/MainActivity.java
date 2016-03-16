package com.example.aldairpetronilia.currencyconverter;

import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    Map<String, Currency> allCurrency = new HashMap<String, Currency>();
    Spinner toCurrencySpinner;
    Spinner fromCurrencySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        createCurrency();
        loadSpinners();
        //getrates.execute();
    }

    private void loadSpinners() {
        toCurrencySpinner = (Spinner) findViewById(R.id.toCurrencySpinner);
        fromCurrencySpinner = (Spinner) findViewById(R.id.fromCurrencySpinner);

        List<String> spinnerList = new ArrayList<String>();
        for (Map.Entry<String, Currency> entry: allCurrency.entrySet()){

            spinnerList.add(entry.getValue().getSpinnerText());
        }

        java.util.Collections.sort(spinnerList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerList
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromCurrencySpinner.setAdapter(adapter);
        toCurrencySpinner.setAdapter(adapter);
    }

    private class getrates extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }
    }

    private void createCurrency()  {

        AssetManager assetManager = getAssets();
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        String nextCurrency;

        try {
            inputStream = assetManager.open("currencyCodes.txt");
            bufferedReader= new BufferedReader(new InputStreamReader(inputStream));


        } catch (Exception e) {

            e.printStackTrace();
            Log.i("Scanner Info", "Error Occurred");

        } finally {
            if (inputStream == null){
                System.out.println("Error Occurred");
            } else {
                System.out.println("Currency Code File Access Success");
            }
        }

        try {
            while ((nextCurrency = bufferedReader.readLine()) != null){
                allCurrency.put(nextCurrency.substring(0,3), new Currency(nextCurrency.substring(0,3),nextCurrency.substring(4)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
