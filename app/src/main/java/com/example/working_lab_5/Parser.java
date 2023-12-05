package com.example.working_lab_5;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Parser {

    Parser() {

    }

    public Map<String, String> getFormattedRates(String jsonData) {
        Map<String, String> formattedRates = new HashMap<>();

        try {
            JSONObject jsonObject = new JSONObject(jsonData);

            Iterator<String> keys = jsonObject.keys();
            while (keys.hasNext()) {
                String currencyCode = keys.next();
                JSONObject currencyObject = jsonObject.getJSONObject(currencyCode);

                String currencyName = currencyObject.getString("name");
                double inverseRate = currencyObject.getDouble("inverseRate");

                System.out.println(currencyName + " " + inverseRate);

                formattedRates.put(currencyName, String.valueOf(inverseRate));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return formattedRates;
    }

}

