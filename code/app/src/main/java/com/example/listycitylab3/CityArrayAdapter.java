package com.example.listycitylab3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class CityArrayAdapter extends ArrayAdapter<City>{

    public CityArrayAdapter(Context context, ArrayList<City> cities) {
        super(context, 0, cities);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //returns view that represents given element when we want to use it
        //position is 0-indexed
        //convertView is meant to be possibly reused
        //parent is ListView (container holding all sub-view elements)
        View view;

        //if convertView holds nothing, inflate content.xml and assign to view
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.content,
                    parent, false);
        }
        //otherwise use convertView for our view
        else {
            view = convertView;
        }

        //get city from the list (getItem is ArrayAdapter method)
        City city = getItem(position);
        //attach city name and province
        TextView cityName = view.findViewById(R.id.city_text);
        TextView provinceName = view.findViewById(R.id.province_text);

        //set the city name and province name
        cityName.setText(city.getName());
        provinceName.setText(city.getProvince());

        return view;
    }

}