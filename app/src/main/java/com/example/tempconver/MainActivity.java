package com.example.tempconver;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private EditText inputEditText;
    private TextView resultTextView;
    private Spinner inputUnitSpinner;
    private Spinner outputUnitSpinner;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        inputEditText = findViewById(R.id.titleinput2);
        resultTextView = findViewById(R.id.titleinput);
        inputUnitSpinner = findViewById(R.id.detectingSpinner);
        outputUnitSpinner = findViewById(R.id.detectingSpinner1);

        fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swapSpinnerSelections();
            }
        });
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.temperature_options,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputUnitSpinner.setAdapter(adapter);
        outputUnitSpinner.setAdapter(adapter);
        inputUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                convertTemperature();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        outputUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                convertTemperature();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                convertTemperature();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do nothing
            }
        });
    }
    public void onDeleteButtonClick(View view) {
        // Clear input and result fields
        inputEditText.setText("");
        resultTextView.setText("");

        // Change spinner selection to Celsius
        inputUnitSpinner.setSelection(getIndex(inputUnitSpinner, "Celsius"));
        outputUnitSpinner.setSelection(getIndex(outputUnitSpinner, "Celsius"));
    }
    private void swapSpinnerSelections() {
        int inputSelection = inputUnitSpinner.getSelectedItemPosition();
        int outputSelection = outputUnitSpinner.getSelectedItemPosition();

        inputUnitSpinner.setSelection(outputSelection);
        outputUnitSpinner.setSelection(inputSelection);
    }
    // Helper method to find the index of an item in a Spinner
    private int getIndex(Spinner spinner, String item) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(item)) {
                return i;
            }
        }
        return 0; // Default to the first item
    }
    private void convertTemperature() {
        String inputUnit = inputUnitSpinner.getSelectedItem().toString();
        String outputUnit = outputUnitSpinner.getSelectedItem().toString();
        String inputValueString = inputEditText.getText().toString();

        if (inputValueString.isEmpty()) {
            resultTextView.setText("");
            return;
        }

        double inputValue;
        try {
            inputValue = Double.parseDouble(inputValueString);
        } catch (NumberFormatException e) {
            resultTextView.setText("Invalid input");
            return;
        }

        double convertedValue;

        if (inputUnit.equals(outputUnit)) {
            resultTextView.setText(inputValueString + " " + outputUnit);
            return;
        }

        // Perform conversions
        switch (inputUnit) {
            case "Celsius":
                convertedValue = convertCelsiusTo(outputUnit, inputValue);
                break;
            case "Fahrenheit":
                convertedValue = convertFahrenheitTo(outputUnit, inputValue);
                break;
            case "Kelvin":
                convertedValue = convertKelvinTo(outputUnit, inputValue);
                break;
            case "Rankine":
                convertedValue = convertRankineTo(outputUnit, inputValue);
                break;
            case "Delisle":
                convertedValue = convertDelisleTo(outputUnit, inputValue);
                break;
            case "Newton":
                convertedValue = convertNewtonTo(outputUnit, inputValue);
                break;
            case "Réaumur":
                convertedValue = convertReaumurTo(outputUnit, inputValue);
                break;
            case "Rømer":
                convertedValue = convertRomerTo(outputUnit, inputValue);
                break;
            // Add more cases for other temperature units
            default:
                resultTextView.setText("Conversion not supported");
                return;
        }

        String result = String.format("%.2f %s", convertedValue, outputUnit);
        resultTextView.setText(result);
    }

    private double convertCelsiusTo(String outputUnit, double value) {
        // Perform conversions from Celsius to other units
        switch (outputUnit) {
            case "Fahrenheit":
                return value * 9 / 5 + 32;
            case "Kelvin":
                return value + 273.15;
            case "Rankine":
                return value * 9 / 5 + 491.67;
            case "Delisle":
                return (100 - value) * 3 / 2;
            case "Newton":
                return value * 33 / 100;
            case "Réaumur":
                return value * 4 / 5;
            case "Rømer":
                return value * 21 / 40 + 7.5;
            default:
                return value;
        }
    }

    private double convertFahrenheitTo(String outputUnit, double value) {
        // Perform conversions from Fahrenheit to other units
        switch (outputUnit) {
            case "Celsius":
                return (value - 32) * 5 / 9;
            case "Kelvin":
                return (value - 32) * 5 / 9 + 273.15;
            case "Rankine":
                return value + 459.67;
            case "Delisle":
                return (212 - value) * 5 / 6;
            case "Newton":
                return (value - 32) * 11 / 60;
            case "Réaumur":
                return (value - 32) * 4 / 9;
            case "Rømer":
                return (value - 32) * 7 / 24 + 7.5;
            default:
                return value;
        }
    }

    private double convertKelvinTo(String outputUnit, double value) {
        // Perform conversions from Kelvin to other units
        switch (outputUnit) {
            case "Celsius":
                return value - 273.15;
            case "Fahrenheit":
                return (value - 273.15) * 9 / 5 + 32;
            case "Rankine":
                return value * 9 / 5;
            case "Delisle":
                return (373.15 - value) * 3 / 2;
            case "Newton":
                return (value - 273.15) * 33 / 100;
            case "Réaumur":
                return (value - 273.15) * 4 / 5;
            case "Rømer":
                return (value - 273.15) * 21 / 40 + 7.5;
            default:
                return value;
        }
    }

    private double convertRankineTo(String outputUnit, double value) {
        // Perform conversions from Rankine to other units
        switch (outputUnit) {
            case "Celsius":
                return (value - 491.67) * 5 / 9;
            case "Fahrenheit":
                return value - 459.67;
            case "Kelvin":
                return value * 5 / 9;
            case "Delisle":
                return (671.67 - value) * 5 / 6;
            case "Newton":
                return (value - 491.67) * 11 / 60;
            case "Réaumur":
                return (value - 491.67) * 4 / 9;
            case "Rømer":
                return (value - 491.67) * 7 / 24 + 7.5;
            default:
                return value;
        }
    }

    private double convertDelisleTo(String outputUnit, double value) {
        // Perform conversions from Delisle to other units
        switch (outputUnit) {
            case "Celsius":
                return 100 - value * 2 / 3;
            case "Fahrenheit":
                return 212 - value * 6 / 5;
            case "Kelvin":
                return 373.15 - value * 2 / 3;
            case "Rankine":
                return 671.67 - value * 6 / 5;
            case "Newton":
                return 33 - value * 11 / 50;
            case "Réaumur":
                return 80 - value * 8 / 15;
            case "Rømer":
                return 60 - value * 7 / 20;
            default:
                return value;
        }
    }

    private double convertNewtonTo(String outputUnit, double value) {
        // Perform conversions from Newton to other units
        switch (outputUnit) {
            case "Celsius":
                return value * 100 / 33;
            case "Fahrenheit":
                return value * 60 / 11 + 32;
            case "Kelvin":
                return value * 100 / 33 + 273.15;
            case "Rankine":
                return value * 60 / 11 + 491.67;
            case "Delisle":
                return (33 - value) * 50 / 11;
            case "Réaumur":
                return value * 80 / 33;
            case "Rømer":
                return value * 35 / 22 + 7.5;
            default:
                return value;
        }
    }

    private double convertReaumurTo(String outputUnit, double value) {
        // Perform conversions from Réaumur to other units
        switch (outputUnit) {
            case "Celsius":
                return value * 5 / 4;
            case "Fahrenheit":
                return value * 9 / 4 + 32;
            case "Kelvin":
                return value * 5 / 4 + 273.15;
            case "Rankine":
                return value * 9 / 4 + 491.67;
            case "Delisle":
                return (80 - value) * 15 / 8;
            case "Newton":
                return value * 33 / 80;
            case "Rømer":
                return value * 21 / 32 + 7.5;
            default:
                return value;
        }
    }

    private double convertRomerTo(String outputUnit, double value) {
        // Perform conversions from Rømer to other units
        switch (outputUnit) {
            case "Celsius":
                return (value - 7.5) * 40 / 21;
            case "Fahrenheit":
                return (value - 7.5) * 24 / 7 + 32;
            case "Kelvin":
                return (value - 7.5) * 40 / 21 + 273.15;
            case "Rankine":
                return (value - 7.5) * 24 / 7 + 491.67;
            case "Delisle":
                return (60 - value) * 20 / 7;
            case "Newton":
                return (value - 7.5) * 22 / 35;
            case "Réaumur":
                return (value - 7.5) * 32 / 21;
            default:
                return value;
        }
    }
}
