package com.example.working_lab_5;

import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class DataLoader extends AsyncTask<String, Void, String> {

    private MainActivity activity;

    public DataLoader(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... urls) {
        try {
            URL url = new URL(urls[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            } finally {
                urlConnection.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        // This method is called when the AsyncTask completes

        // Handle the result here
        if (result != null) {
            // Process the result, e.g., parse JSON or update UI
            // Call your method for processing the JSON result here
            Parser parser = new Parser();
            Map<String, String> formattedRates = parser.getFormattedRates(result);
            activity.updateListView(formattedRates);
        } else {
            System.out.println("Check your internet connection");
        }
    }
}