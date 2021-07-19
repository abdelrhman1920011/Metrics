package com.example.metrics;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    Spinner types, units;
    TextView textView, textView2, output;
    RadioButton Mass, Time, Length;
    EditText input;
    String unit_identifier;
    ArrayAdapter<CharSequence> adapter1, adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //connecting the View elements to the Java Code
        types = findViewById(R.id.Types);
        textView = findViewById(R.id.Unit1);
        textView2 = findViewById(R.id.Unit2);
        input = findViewById(R.id.input_unit1);
        output = findViewById(R.id.output_unit2);
        units = findViewById(R.id.Units_spinner);
        Mass = findViewById(R.id.Mass_button);
        Length = findViewById(R.id.Length_button);
        Time = findViewById(R.id.Time_button);



        /*Creating an ArrayAdapter for the spinner,
        and fill it from the "Types" array in the strings file
        then assigning it to the spinner called types.
        */
        adapter1 = ArrayAdapter.createFromResource(this,
                R.array.types, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        types.setAdapter(adapter1);

        Length.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                types.setSelection(0);
            }
        });

        Mass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                types.setSelection(1);
            }
        });

        Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                types.setSelection(2);
            }
        });
        /*
        Adding a listener on the spinner to detect the value of the selcted
        item and filling the other spinner called units depending on the types spinner
        */
        types.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String type = types.getItemAtPosition(i).toString();
                switch (type) {
                    case "Length":
                        radios(true, false, false);
                        adapter2 = ArrayAdapter.createFromResource(MainActivity.this, R.array.length,
                                android.R.layout.simple_spinner_item);
                        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        units.setAdapter(adapter2);
                        break;
                    case "Mass":
                        radios(false, true, false);
                        adapter2 = ArrayAdapter.createFromResource(MainActivity.this,
                                R.array.mass, android.R.layout.simple_spinner_item);
                        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        units.setAdapter(adapter2);
                        break;
                    case "Time":
                        radios(false, false, true);
                        adapter2 = ArrayAdapter.createFromResource(MainActivity.this, R.array.time
                                , android.R.layout.simple_spinner_item);
                        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        units.setAdapter(adapter2);
                        break;
                    default:
                        throw new IllegalArgumentException("Can't resolve " + type);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        /*
         * adding a listener on the units spinner to detect the selected item's value
         * and convert the unit depending on that value*/
        units.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                unit_identifier = units.getItemAtPosition(i).toString();
                switch (unit_identifier) {
                    // in case of Length
                    case "Km/meter":
                        set("Kilometers:", "Meters:");
                        break;
                    case "meter/cm":
                        set("Meters: ", "CentiMeters: ");
                        break;
                    case "meter/mile":
                        set("Meters:", "Miles");
                        break;
                    case "meter/yard":
                        set("Meters: ", "Yard:");
                        break;
                    case "meter/foot":
                        set("Meters: ", "Foot: ");
                        break;
                    case "inch/cm":
                        set("Inches: ", "Centimeters: ");
                        break;
                    case "cm/inch":
                        set("Centimeters: ", "Inches:");
                        break;
                    case "cm/mm":
                        set("Centimeters: ", "Millimeters:");
                        break;
                    case "mm/nano m":
                        set("Millimeters: ", "Nanometers");
                        break;
                    case "micro m/ nano":
                        set("Micrometers: ", "Nanometers");
                        break;

                    //in case of Mass
                    case "Ton/Kg":
                        set("Tons: ", "Kilograms: ");
                        break;
                    case "Kg/gm":
                        set("Kilograms: ", "Grams: ");
                        break;
                    case "gm/Milligm":
                        set("Grams: ", "Milligrams:");
                        break;

                    //in case of Time
                    case "Days/Hours":
                        set("Days:", "Hours:");
                        break;
                    case "Hours/Minutes":
                        set("Hour: ", "Minutes: ");
                        break;
                    case "Minutes/Seconds":
                        set("Minutes:", "Seconds:");
                        break;
                    case "Seconds/Minutes":
                        set("Seconds:", "Minutes");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    //showing the selected units in text view and clear anything when any change happens
    public void set(String v1, String v2) {
        textView.setText(v1);
        textView2.setText(v2);
        output.setText(null);
        input.getText().clear();
    }

    public void convert(View view) {
        if (input.getText().toString().isEmpty()) {
            Toast.makeText(this, "please enter a number ", Toast.LENGTH_LONG).show();
        } else {
            //case Length
            calculate("Km/meter", 1000);
            calculate("meter/cm", 100);
            calculate("meter/mile", 0.00062137119224);
            calculate("meter/yard", 1.09361);
            divide("mile/meter", 0.00062137119224);
            calculate("meter/foot", 3.281);
            calculate("inch/cm", 2.45);
            divide("cm/inch", 2.45);
            calculate("cm/mm", 10);
            calculate("mm/nano m", Math.pow(10, 6));
            calculate("micro m/ nano", 1000);

            //case Mass
            calculate("Ton/Kg", 1000);
            calculate("Kg/gm", 1000);
            calculate("gm/Milligm", 1000);

            //case Time
            calculate("Days/Hours", 24);
            divide("Seconds/Minutes", 60);
            calculate("Hours/Minutes", 60);
            calculate("Minutes/Seconds", 60);
        }
    }

    /*to convert from unit to another you must multiply by a distinct constant
     * this function multiply the input of the user by the suitable constant
     * and the second function divides by the suitable constant. */
    public void calculate(String unit, double i) {
        if (unit_identifier.equals(unit)) {
            output.setText(String.valueOf(Integer.parseInt(input.getText().toString()) * i));
        }
    }

    public void divide(String unit, double i) {
        if (unit_identifier.equals(unit)) {
            output.setText(String.valueOf(Integer.parseInt(input.getText().toString()) * i));
        }
    }

    public void radios(boolean length, boolean mass, boolean time) {
        Length.setChecked(length);
        Mass.setChecked(mass);
        Time.setChecked(time);
    }
}