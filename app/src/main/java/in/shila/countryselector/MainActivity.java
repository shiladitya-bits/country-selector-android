package in.shila.countryselector;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import in.shila.countryselector.adapter.CountriesListAdapter;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private CountriesListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupSpinner();
    }

    private void setupSpinner(){
        Spinner spinner = (Spinner) findViewById(R.id.countrySelector);
        spinner.setOnItemSelectedListener(this);

        String[] recourseList = this.getResources().getStringArray(R.array.CountryCodes);

        adapter = new CountriesListAdapter(this, recourseList);
        spinner.setAdapter(adapter);

        int selectedCountry = adapter.getPositionForDeviceCountry();

        if (selectedCountry != -1) {
            spinner.setSelection(selectedCountry);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
