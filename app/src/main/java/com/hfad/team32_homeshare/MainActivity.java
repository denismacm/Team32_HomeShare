package com.hfad.team32_homeshare;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.hfad.team32_homeshare.databinding.ActivityMainBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private Spinner spin;
    private Spinner bedSpin;
    private Spinner bathSpin;
    private Spinner daySpin;
    private Spinner monthSpin;
    private Spinner yearSpin;
    private Spinner roommateSpin;
    private Spinner spotSpin;
    private String[] homeType = new String[]{"Apartment", "Condo", "Studio", "Townhouse"};
    private String[] bedBath = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};
    private String[] days = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    private String[] months= new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    private String[] years = new String[]{"2022", "2023", "2024", "2025", "2026"};
    private EditText editText;
    private float distanceFromCampus;
    private FirebaseAuth firebaseAuth;


    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();

        replaceFragment(new InvitationsFragment());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                @SuppressLint("InflateParams") View popupView = getLayoutInflater().inflate(R.layout.new_invitations, null);

                //
                spin = popupView.findViewById(R.id.homeTypeSpinner);
                bedSpin = popupView.findViewById(R.id.bedSpinner);
                bathSpin = popupView.findViewById(R.id.bathSpinner);
                daySpin = popupView.findViewById(R.id.daySpinner);
                monthSpin = popupView.findViewById(R.id.monthSpinner);
                yearSpin = popupView.findViewById(R.id.yearSpinner);
                roommateSpin = popupView.findViewById(R.id.roommateSpinner);
                spotSpin = popupView.findViewById(R.id.spotSpinner);
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
                bedSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                bathSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                daySpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                monthSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                yearSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                roommateSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                spotSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                ArrayAdapter bedAd
                        = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_item, bedBath);
                ArrayAdapter bathAd
                        = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_item, bedBath);
                ArrayAdapter dayAd
                        = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_item, days);
                ArrayAdapter monthAd
                        = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_item, months);
                ArrayAdapter yearAd
                        = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_item, years);
                ArrayAdapter roommateAd
                        = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_item, bedBath);
                ArrayAdapter spotAd
                        = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_item, bedBath);
                ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                bedAd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                bathAd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dayAd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                monthAd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                yearAd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                roommateAd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spotAd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin.setAdapter(ad);
                bedSpin.setAdapter(bedAd);
                bathSpin.setAdapter(bathAd);
                daySpin.setAdapter(dayAd);
                monthSpin.setAdapter(monthAd);
                yearSpin.setAdapter(yearAd);
                roommateSpin.setAdapter(roommateAd);
                spotSpin.setAdapter(spotAd);

                CheckBox cb = popupView.findViewById(R.id.checkboxPrice);
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
                editText = popupView.findViewById(R.id.address);
                Places.initialize(MainActivity.this, "AIzaSyC6VpIx6wQUPFZiA_M40pa4sBJqCzSrzfI");
                editText.setFocusable(false);
                editText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);
                        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(MainActivity.this);
                        startActivityForResult(intent, 100);
                    }
                });

                // create the popup window
                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.MATCH_PARENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window tolken
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                popupView.findViewById(R.id.button_id).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String homeType = spin.getSelectedItem().toString();
                        int bedNum =  Integer.parseInt(bedSpin.getSelectedItem().toString());
                        int bathNum = Integer.parseInt(bathSpin.getSelectedItem().toString());
                        String deadlineMonth = monthSpin.getSelectedItem().toString();
                        String deadlineDay = daySpin.getSelectedItem().toString();
                        String deadlineYear  = yearSpin.getSelectedItem().toString();
                        EditText desc = (EditText) popupView.findViewById(R.id.requirementEt);
                        String expectation = desc.getText().toString().trim();
                        String currentDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
                        String [] dateArray = currentDate.split("/");
                        String dateMonth = dateArray[0];
                        String dateDay = dateArray[1];
                        String dateYear = dateArray[2];
                        int roommateNum =  Integer.parseInt(roommateSpin.getSelectedItem().toString());
                        int spotNum = Integer.parseInt(spotSpin.getSelectedItem().toString());
                        String address = editText.getText().toString();
                        if (address.isEmpty()) {
                            editText.setError("Address cannot be empty.");
                            return;
                        }

                        if (cb.isChecked()) {
                            EditText minPriceEt = popupView.findViewById(R.id.minPriceEt);
                            EditText maxPriceEt = popupView.findViewById(R.id.maxPriceEt);
                            int minPrice, maxPrice;
                            if (minPriceEt.getText().toString().equals("") || maxPriceEt.getText().toString().equals("")) {
                                return;
//                                minPriceEt.setError("Cannot be empty.");
                            }
//                            } else if (maxPriceEt.getText().toString().isEmpty()) {
//                                maxPriceEt.setError("Cannot be empty.");
//                            }
                            minPrice = Integer.parseInt(minPriceEt.getText().toString());
                            maxPrice = Integer.parseInt(maxPriceEt.getText().toString());

                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                            String invID = UUID.randomUUID().toString();
                            Map<String, Object> map = new HashMap<>();
                            map.put("active", true);
                            db.collection("invitations").document(invID).set(map);
                            db.collection("invitations").document(invID).update("date", currentDate);
                            db.collection("invitations").document(invID).update("dateDay", dateDay);
                            db.collection("invitations").document(invID).update("dateMonth", dateMonth);
                            db.collection("invitations").document(invID).update("dateYear", dateYear);
                            db.collection("invitations").document(invID).update("numRoommatesCapacity", roommateNum);
                            db.collection("invitations").document(invID).update("numSpotsLeft", spotNum);
                            db.collection("invitations").document(invID).update("ownerID", firebaseAuth.getCurrentUser().getUid());
                            db.collection("invitations").document(invID).update("expectation", expectation);

                            Map<String, Object> home = new HashMap<>();
                            home.put("bbQuantity", String.valueOf(bedNum) + "B" + String.valueOf(bathNum) + "B");
                            home.put("deadline", deadlineMonth+"/"+deadlineDay+"/"+deadlineYear);
                            home.put("deadlineDay", deadlineDay);
                            home.put("deadlineMonth", deadlineMonth);
                            home.put("deadlineYear", deadlineYear);
                            home.put("homeType", homeType);
                            home.put("location", address);
                            home.put("userPriceRange", true);
                            home.put("minPrice", minPrice);
                            home.put("maxPrice", maxPrice);
                            home.put("numBedrooms", bedNum);
                            home.put("numBathrooms", bathNum);
                            home.put("distanceFromCampus", distanceFromCampus);
                            db.collection("invitations").document(invID).update("home", home);
                            db.collection("users").document(firebaseAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot ds = task.getResult();
                                        ArrayList<String> invitationIDList = (ArrayList<String>) ds.get("invitationsList");
                                        invitationIDList.add(invID);
                                        db.collection("users").document(firebaseAuth.getCurrentUser().getUid()).update("invitationsList", invitationIDList);
                                        popupWindow.dismiss();
                                    }
                                }
                            });
                        } else {
                            EditText priceEt = (EditText) popupView.findViewById(R.id.price);
                            if (priceEt.getText().toString().isEmpty()) {
//                                priceEt.setError("Cannot be empty.");
                                return;
                            }
                            int price = Integer.parseInt(priceEt.getText().toString());

                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                            String invID = UUID.randomUUID().toString();
                            Map<String, Object> map = new HashMap<>();
                            map.put("active", true);
                            db.collection("invitations").document(invID).set(map);
                            db.collection("invitations").document(invID).update("date", currentDate);
                            db.collection("invitations").document(invID).update("dateDay", dateDay);
                            db.collection("invitations").document(invID).update("dateMonth", dateMonth);
                            db.collection("invitations").document(invID).update("dateYear", dateYear);
                            db.collection("invitations").document(invID).update("numRoommatesCapacity", roommateNum);
                            db.collection("invitations").document(invID).update("numSpotsLeft", spotNum);
                            db.collection("invitations").document(invID).update("ownerID", firebaseAuth.getCurrentUser().getUid());
                            db.collection("invitations").document(invID).update("expectation", expectation);

                            Map<String, Object> home = new HashMap<>();
                            home.put("bbQuantity", String.valueOf(bedNum) + "B" + String.valueOf(bathNum) + "B");
                            home.put("deadline", deadlineMonth+"/"+deadlineDay+"/"+deadlineYear);
                            home.put("deadlineDay", deadlineDay);
                            home.put("deadlineMonth", deadlineMonth);
                            home.put("deadlineYear", deadlineYear);
                            home.put("homeType", homeType);
                            home.put("location", address);
                            home.put("userPriceRange", false);
                            home.put("onePrice", price);
                            home.put("maxPrice", price);
                            home.put("numBedrooms", bedNum);
                            home.put("numBathrooms", bathNum);
                            home.put("distanceFromCampus", distanceFromCampus);
                            db.collection("invitations").document(invID).update("home", home);
                            db.collection("users").document(firebaseAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot ds = task.getResult();
                                        ArrayList<String> invitationIDList = (ArrayList<String>) ds.get("invitationsList");
                                        invitationIDList.add(invID);
                                        db.collection("users").document(firebaseAuth.getCurrentUser().getUid()).update("invitationsList", invitationIDList);
                                        popupWindow.dismiss();
                                    }
                                }
                            });
                        }
                    }
                });
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            assert data != null;
            Place place = Autocomplete.getPlaceFromIntent(data);
            editText.setText(place.getName());
//            textView1.setText(String.format("Locality Name: %s", place.getName()));
//            textView2.setText(String.valueOf(place.getLatLng()));
            float[] res = new float[3];
            Location.distanceBetween(Objects.requireNonNull(place.getLatLng()).latitude, place.getLatLng().longitude, 34.022415, -118.285530, res);
            distanceFromCampus = res[0]/1690;
//            textView2.setText(String.valueOf((float) res[0]/1690) + " miles from campus");
        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            assert data != null;
            Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(MainActivity.this, status.getStatusMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void checkUser() {
        // check if user is already logged in, if so then open profile activity
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser == null) {
            // user is already logged in
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        } else {

            // User is logged in, get token and update user field
            FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                @Override
                public void onComplete(@NonNull Task<String> task) {
                    if (!task.isSuccessful()) {
                        return;
                    }
                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    String token = task.getResult();
                    System.out.println("TOKEN: " + token);

                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    DocumentReference ref = db.collection("users").document(firebaseUser.getUid());
                    System.out.println(firebaseUser.getUid());
                    ref.update("token", token);

                    System.out.println("TOKEN was added to User's Firestore");
                }
            });
        }
    }
}