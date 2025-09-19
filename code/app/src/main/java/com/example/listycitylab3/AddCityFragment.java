package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


public class AddCityFragment extends DialogFragment{

    //use to transfer data across classes
    interface AddCityDialogListener {
        void addCity(City city);

        void editCity(City city, String newCity, String newProvince);
    }

    //listener lets us reuse the fragment
    private AddCityDialogListener listener;

    //default constructor
    /*
    public AddCityFragment(){
        super();
    }
    //constructor that takes a City argument
    public AddCityFragment(City newCity){

    }
     */
    static AddCityFragment newInstance(City city){
        Bundle args = new Bundle();
        args.putSerializable("city", city);

        AddCityFragment fragment = new AddCityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        //let default function run (do regular stuff)
        super.onAttach(context);

        //set the listener to context if it's instance of our interface
        //can we treat MainActivity like it's our interface?
        if (context instanceof AddCityDialogListener){
            listener = (AddCityDialogListener) context;
        }
        else{
            throw new RuntimeException(context + " must implement AddCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
        //function returns dialog fragment we can render on screen
        //create a view
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);

        //get editor boxes for city and province
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        //use a bunch of builder functions to construct our fragment
        //chain builder functions that reference itself
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Add/edit a city")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Submit", (dialog, which) -> {
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();
                    //see if we saved a city in this fragment (used newInstance)
                    City city = (City)savedInstanceState.getSerializable("city");
                    if(city != null){
                        //if yes, set city and province name
                        listener.editCity(city, "edit city", provinceName);
                    }
                    else{
                        listener.addCity(new City("add city", provinceName));
                    }
                })
                .create();
    }


}