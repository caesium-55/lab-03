package com.example.listycitylab3;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements
        AddCityFragment.AddCityDialogListener{
    //now we can treat MainActivity like an AddCityDialogListener
    private ArrayList<City> dataList;
    private ListView cityList;
    private CityArrayAdapter cityAdapter;

    @Override
    public void addCity(City city) {
        cityAdapter.add(city);
        //tell city we added something (function from ArrayAdapter?)
        cityAdapter.notifyDataSetChanged();
    }

    @Override
    public void editCity(City city, String newCity, String newProvince){
        city.setName(newCity);
        city.setProvince(newProvince);
        cityAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] cities = {"Edmonton", "Vancouver", "Toronto"};
        String[] provinces = {"AB", "BC", "ON"};

        dataList = new ArrayList<City>();
        for (int i = 0; i < cities.length; i++) {
            dataList.add(new City(cities[i], provinces[i]));
        }
        //dataList.addAll(Arrays.asList(cities));

        cityList = findViewById(R.id.city_list);
        //cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityAdapter = new CityArrayAdapter(this, dataList);

        cityList.setAdapter(cityAdapter);

        //DEFINE BUTTON ACTIVITY
        FloatingActionButton fab = findViewById(R.id.button_add_city);
        fab.setOnClickListener(v -> {
            new AddCityFragment().show(getSupportFragmentManager(), "Add City");
            //this calls the constructor of the fragment class
            //need new constructor that takes city?
        });

        //Define clickable list activity
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
                //gives us the index of the list item -- use to get city
                City newCity = cityAdapter.getItem(i);
                //then we want to make a new fragment that uses this city
                //could feed it a city to modify
                new AddCityFragment().newInstance(newCity)
                        .show(getSupportFragmentManager(), "Edit City");
            }
        });
    }
}