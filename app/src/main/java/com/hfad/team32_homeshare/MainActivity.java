package com.hfad.team32_homeshare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.hfad.team32_homeshare.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private Spinner spin;
    private String[] homeType = new String[]{"Apartment", "Condo", "Studio", "Townhouse"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new InvitationsFragment());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View popupView = getLayoutInflater().inflate(R.layout.new_invitations, null);

                //
                spin = (Spinner) popupView.findViewById(R.id.homeTypeSpinner);
                spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
//                String text = adapterView.getItemAtPosition(position).toString();
//                Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
                    }
                    //
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                ArrayAdapter ad
                        = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_item, homeType);
                ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin.setAdapter(ad);

                CheckBox cb = (CheckBox) popupView.findViewById(R.id.checkboxPrice);
                cb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (cb.isChecked()) {
                            TextInputLayout price =  popupView.findViewById(R.id.priceTil);
                            TextInputLayout minPrice =  popupView.findViewById(R.id.minPriceTil);
                            TextInputLayout maxPrice =  popupView.findViewById(R.id.maxPriceTil);
                            price.setVisibility(View.INVISIBLE);
                            minPrice.setVisibility(View.VISIBLE);
                            maxPrice.setVisibility(View.VISIBLE);

                            // Price vis = 0
                            // minPrice vis = 1 maxprice vis =1
                        } else {
                            TextInputLayout price =  popupView.findViewById(R.id.priceTil);
                            TextInputLayout minPrice =  popupView.findViewById(R.id.minPriceTil);
                            TextInputLayout maxPrice =  popupView.findViewById(R.id.maxPriceTil);
                            price.setVisibility(View.VISIBLE);
                            minPrice.setVisibility(View.INVISIBLE);
                            maxPrice.setVisibility(View.INVISIBLE);
                            // Price vis = 1
                            // min Price maxPrice vis = 0
                        }
                    }
                });

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window tolken
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
            }
        });

        binding.navigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_invitation:
                    replaceFragment(new InvitationsFragment());
                    break;
                case R.id.nav_responses:
                    replaceFragment(new ResponsesFragment());
                    break;
                case R.id.nav_settings:
                    replaceFragment(new SettingsFragment());
                    break;
            }

            return true;
        });

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}