package com.example.aldairpetronilia.currencyconverter;

import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    static final String ratesApi = "http://finance.yahoo.com/d/quotes.csv?e=.csv&f=sl1d1t1&s=USD";
    Map<String, Currency> allCurrency = new HashMap<String, Currency>();
    Spinner toCurrencySpinner;
    Spinner fromCurrencySpinner;

    public void convertCurrency(View view){

        Log.i("info", "Button Pressed");
        Button convertButton = (Button) findViewById(R.id.convertButton);
        EditText currencyAmount = (EditText) findViewById(R.id.currencyEditText);
        TextView convertedAmountText = (TextView) findViewById(R.id.conversionTextView);
        String fromCurrencyText = fromCurrencySpinner.getSelectedItem().toString();
        String toCurrencyText = toCurrencySpinner.getSelectedItem().toString();
        Currency fromCurrency = allCurrency.get(fromCurrencyText.substring(1, 4));
        Currency toCurrency = allCurrency.get(toCurrencyText.substring(1, 4));

        double convertedAmount;

        Double currencyAmountDouble = Double.parseDouble(currencyAmount.getText().toString());

        if (currencyAmountDouble.isNaN()) {

            Toast.makeText(getApplicationContext(),"Please Enter a Number", Toast.LENGTH_LONG).show();

        } else if (fromCurrency.getRate() == 0 || toCurrency.getRate() == 0) {

            Toast.makeText(getApplicationContext(),"Rates Not Loaded", Toast.LENGTH_LONG).show();

        } else {
            System.out.println("Running");
            convertedAmount = currencyAmountDouble / fromCurrency.getRate() * toCurrency.getRate();
            DecimalFormat df = new DecimalFormat("#.###");
            convertedAmountText.setText(df.format(convertedAmount));

        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        createCurrency();
        loadSpinners();
        getRates getRates;
        new getRates().execute(ratesApi);
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

    private class getRates extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            for (Map.Entry<String, Currency> entry : allCurrency.entrySet()) {

                try {
                    URL url = null;

                    if (!(entry.getKey().equals("USD"))) {

                        url = new URL(params[0] + entry.getValue().getCurrencyCode() + "=X");

                    }

                    if (url != null) {

                        Scanner scanner = new Scanner(url.openStream());
                        String nextLine = scanner.nextLine();
                        nextLine = nextLine.substring(nextLine.indexOf(",") + 1);
                        System.out.println(Double.parseDouble(nextLine.substring(0, nextLine.indexOf(","))));
                        entry.getValue().setRate(Double.parseDouble(nextLine.substring(0, nextLine.indexOf(","))));
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

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
