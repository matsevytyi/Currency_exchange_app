package com.example.working_lab_5;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ListView ratesListView;
    private Map<String, String> originalFormattedRates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ratesListView = findViewById(R.id.ratesListView);

        DataLoader dataLoader = new DataLoader(this);
        dataLoader.execute("https://www.floatrates.com/widget/00002292/e7898aa49c6096a19a9418361b2a9cdd/usd.json");

        ImageButton searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(v -> {
            AutoCompleteTextView currencyEditText = findViewById(R.id.currencyEditText);
            String currencyName = currencyEditText.getText().toString();

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item_layout, R.id.currencyNameTextView, findCurrency(currencyName));
            ratesListView.setAdapter(adapter);
        });

    }

    public void updateListView(Map<String, String> formattedRates) {
        originalFormattedRates = formattedRates;  // Save the original data

        List<String> itemList = new ArrayList<>();
        for (Map.Entry<String, String> entry : formattedRates.entrySet()) {
            itemList.add(entry.getKey() + ": " + entry.getValue());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item_layout, R.id.currencyNameTextView, itemList);
        ratesListView.setAdapter(adapter);
    }

    public List<String> findCurrency(String currency) {
        if (originalFormattedRates == null) {
            return new ArrayList<>();
        }

        List<String> result = new ArrayList<>();
        for (Map.Entry<String, String> entry : originalFormattedRates.entrySet()) {
            if (entry.getKey().contains(currency)) {
                result.add(entry.getKey() + ": " + entry.getValue());
            }
        }
        return result;
    }
}