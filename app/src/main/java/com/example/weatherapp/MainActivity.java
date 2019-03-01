package com.example.weatherapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static  final String WEATHER_SOURCE = "http://api.openweathermap.org/data/2.5/weather?APPID=82445b6c96b99bc3ffb78a4c0e17fca5&mode=json&id=1735161";
    private TextView textV1, tvLocation, tvTemp, tvHumidity, tvWindSpeed, tvCloudiness;
    private Button btnReferesh;
    private ImageView ivIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         /*tvLocation = (TextView) findViewById(R.id.);
        tvTemp = (TextView) findViewById(R.id.);
        tvHumidity = (TextView) findViewById(R.id.);
        tvWindSpeed = (TextView) findViewById(R.id.);
        tvCloudiness = (TextView) findViewById(R.id.);
        btnReferesh = (Button) findViewById(R.id.);
        btnReferesh = (ImageView) findViewById(R.id.);*/

         textV1 = findViewById(R.id.textV1);
    }

    public void onClick(View v) {
        new WeatherDataRetrival().execute();
    }

    private class  WeatherDataRetrival extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            NetworkInfo networkInfo = ((ConnectivityManager)
                    MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

            if(networkInfo != null && networkInfo.isConnected()) {

                URL url = null;
                try {
                        url = new URL(WEATHER_SOURCE);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("GET");
                        conn.setConnectTimeout(15000);
                        conn.setReadTimeout(15000);
                        conn.connect();

                        int responseCode = conn.getResponseCode();

                        if(responseCode == HttpURLConnection.HTTP_OK){
                            BufferedReader bufferReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                            if(bufferReader !=null){
                                String readLine;
                                StringBuffer stringBuffer = new StringBuffer();
                                while ((readLine=bufferReader.readLine()) != null) {
                                    stringBuffer.append(readLine);
                                }
                                return stringBuffer.toString();
                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            } else {

            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if(result !=null){
                textV1.setText(result);

               /* final JSONObject weatherJSON;
                try {
                    weatherJSON = new JSONObject(result);
                    tvLocation.setText(weatherJSON.getString("name")+","+ weatherJSON.getJSONObject("sys").getString("country"));
                    tvWindSpeed.setText(String.valueOf(weatherJSON.getJSONObject("wind").getDouble("speed")) +"mps");
                    tvCloudiness.setText(String.valueOf(weatherJSON.getJSONObject("clouds").getInt("all")) + "%");

                    final JSONObject mainJSON = weatherJSON.getJSONObject("main");
                    tvTemp.setText(String.valueOf(mainJSON.getDouble("temp")));
                    tvHumidity.setText(String.valueOf(mainJSON.getInt("humidity")) + "%");

                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
            }
        }
    }

}




