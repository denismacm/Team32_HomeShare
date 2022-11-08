package com.hfad.team32_homeshare;

import static android.view.View.GONE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.text.Html;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class AdapterInvitations extends RecyclerView.Adapter<AdapterInvitations.MyHolder> {

    Context context;
    ArrayList<Invitation> invitationList;
    private Spinner spin;
    private Spinner bedSpin;
    private Spinner bathSpin;
    private Spinner daySpin;
    private Spinner monthSpin;
    private Spinner yearSpin;
    private Spinner roommateSpin;
    private Spinner spotSpin;
    private String[] homeTypeArray = new String[]{"Apartment", "Condo", "Studio", "Townhouse"};
    private String[] bedBathArray = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};
    private String[] daysArray = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    private String[] monthsArray= new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    private String[] yearsArray = new String[]{"2022", "2023", "2024", "2025", "2026"};
    private EditText editText;
//    private float distanceFromCampus;

    public AdapterInvitations(Context context, ArrayList<Invitation> invitationList) {
        this.context = context;
        this.invitationList = invitationList;
    }

    @NonNull
    @Override
    public AdapterInvitations.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
//        Context context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.activity_cards, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int i) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String currentUserID = firebaseAuth.getCurrentUser().getUid();

        Invitation inv = invitationList.get(i);
        String fullName = inv.fullName;
        String gender = inv.ownerGender;
        String datePosted = inv.date;
        String invitationID = inv.invitationID;
        if (inv.ownerID.equals(currentUserID)) {
            holder.messageButton.setVisibility(GONE);
            holder.deleteButton.setVisibility(GONE);
            holder.editInv.setVisibility(View.VISIBLE);
            holder.trashInv.setVisibility(View.VISIBLE);
        }
//        String hiddenID = UUID.randomUUID().toString();
        String expectation = inv.expectation;
        String invID = inv.invitationID;

        Map<String, Object> home = inv.home;
        String location = home.get("location").toString();
        Boolean userPriceRange = (Boolean) home.get("userPriceRange");
//        String price;
//        if (userPriceRange) {
//            price = "Min Price: " + home.get("minPrice");
//            price += "Max Price: " + home.get("maxPrice");
//        } else {
//            price = "Price: " + home.get("onePrice");
//        }
        String numRoommatesCapacity = "Looking for " + String.valueOf(inv.numRoommatesCapacity) + " roommates";
        String numSpotsLeft = String.valueOf(inv.numSpotsLeft) + " spots left";
        String date = "Date posted: " + datePosted;
        holder.nameTv.setText(fullName);
        holder.locationTv.setText(location);
        holder.datePostedTv.setText(date);

//        Invitation invite = invitationList.get(i);
//        holder.setDetails(invite);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  IMPLEMENT OVERLAY
//                Toast.makeText(context, ""+ userEmail, Toast.LENGTH_SHORT).show(); // to show that clicking works
                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.overlay_invitations, null);

                // create the popup window
                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.MATCH_PARENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                String title = "<b>Invitation Details</b>";
                ((TextView)popupWindow.getContentView().findViewById(R.id.detailTitle)).setText(Html.fromHtml(title));
                String name = "<b>Name: </b>" + fullName;
                ((TextView)popupWindow.getContentView().findViewById(R.id.nameOL)).setText(Html.fromHtml(name));
                String gen = "<b>Gender: </b>" + gender;
                ((TextView)popupWindow.getContentView().findViewById(R.id.genderOL)).setText(Html.fromHtml(gen));
                String address = "<b>Address: </b>" + location;
                ((TextView)popupWindow.getContentView().findViewById(R.id.locationOL)).setText(Html.fromHtml(address));
                Float distFloat = Float.parseFloat(home.get("distanceFromCampus").toString());
                String distance = "<b>Distance from Campus: </b><br>" + String.format("%.2f", distFloat) + " miles";
                ((TextView)popupWindow.getContentView().findViewById(R.id.distanceOL)).setText(Html.fromHtml(distance));
                String date = "<b>Date posted: </b>" + datePosted;
                ((TextView)popupWindow.getContentView().findViewById(R.id.datePostedOL)).setText(Html.fromHtml(date));
                String bbQuantity = "<b>Bed/Bath: </b>" + home.get("bbQuantity").toString();
                ((TextView)popupWindow.getContentView().findViewById(R.id.bbQuantityOL)).setText(Html.fromHtml(bbQuantity));
                String deadline = "<b>Deadline: </b>" + home.get("deadline").toString();
                ((TextView)popupWindow.getContentView().findViewById(R.id.deadlineOL)).setText(Html.fromHtml(deadline));
                String homeType = "<b>Home Type: </b>" + home.get("homeType").toString();
                ((TextView)popupWindow.getContentView().findViewById(R.id.homeTypeOL)).setText(Html.fromHtml(homeType));
                String description = "<b>Roommate Expectations: </b><br>" + expectation;
                ((TextView)popupWindow.getContentView().findViewById(R.id.expectationOL)).setText(Html.fromHtml(description));
                ImageView img = (ImageView) popupWindow.getContentView().findViewById(R.id.profilePicOL);
                StorageReference pathReference = FirebaseStorage.getInstance().getReference().child("images/" + inv.ownerID);
                long MAX_BYTES = 1024 * 1024 * 10;
                pathReference.getBytes(MAX_BYTES).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        img.setImageBitmap(bitmap);
                    }
                });
                String price = "";
                if (userPriceRange) {
                    price = "<b>Min Price: </b>" + home.get("minPrice");
                    price += "<br><b>Max Price: </b>" + home.get("maxPrice");
                } else {
                    price = "<b>Price: </b>" + home.get("onePrice");
                }
                ((TextView)popupWindow.getContentView().findViewById(R.id.priceOL)).setText(Html.fromHtml(price));
                ((TextView)popupWindow.getContentView().findViewById(R.id.numCapacityOL)).setText(numRoommatesCapacity);
                ((TextView)popupWindow.getContentView().findViewById(R.id.numSpotsOL)).setText(numSpotsLeft);
                popupView.findViewById(R.id.dismiss).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window tolken
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                // dismiss the popup window when touched
//                popupView.setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View v, MotionEvent event) {
//                        popupWindow.dismiss();
//                        return true;
//                    }
//                });
            }
        });

        holder.messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(inv.ownerID)) {
                    return;
                }
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.create_invite_layout, null);
                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                // lets taps outside the popup also dismiss it
                boolean focusable = true;
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                popupView.findViewById(R.id.submitInvite).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                        String senderID = firebaseAuth.getCurrentUser().getUid();
//
                        final EditText edit =  (EditText) popupView.findViewById(R.id.inviteEt);
                        String getRecipientID = inv.ownerID;
                        String getInvID = inv.invitationID;
                        String getMessage = edit.getText().toString();
                        String currentDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
                        String responseID = UUID.randomUUID().toString();

                        //create response
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("users").document(getRecipientID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot documentSnapshot = task.getResult();
                                    ArrayList<String> responsesIDArray = (ArrayList<String>) documentSnapshot.get("responsesList");
                                    responsesIDArray.add(responseID);
                                    db.collection("users").document(getRecipientID).update("responsesList", responsesIDArray);

                                    Map<String, Object> responseMap = new HashMap<>();
                                    responseMap.put("date", currentDate);
                                    responseMap.put("invitationID", getInvID);
                                    responseMap.put("message", getMessage);
                                    responseMap.put("recipientID", getRecipientID);
                                    responseMap.put("senderID", senderID);
                                    responseMap.put("accepted", false);

                                    db.collection("responses").document(responseID).set(responseMap);
                                    popupWindow.dismiss();
                                }
                            }
                        });
                    }
                });
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(inv.ownerID)) {
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(true);
                builder.setTitle("Are you sure you want to hide this invitation for " + location + "?");
                builder.setMessage("Pull down to see refreshed changes after confirming.");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                                String senderID = firebaseAuth.getCurrentUser().getUid();
                                FirebaseFirestore db = FirebaseFirestore.getInstance();

                                db.collection("users").document(senderID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot documentSnapshot = task.getResult();
                                            ArrayList<String> hiddenInvitesIDArray = (ArrayList<String>) documentSnapshot.get("declinedInvitationsList");
                                            hiddenInvitesIDArray.add(invID);
                                            db.collection("users").document(senderID).update("declinedInvitationsList", hiddenInvitesIDArray);
                                        }
                                    }
                                });
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        holder.editInv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!FirebaseAuth.getInstance().getCurrentUser().getUid().equals(inv.ownerID)) {
                    return;
                }
                LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                @SuppressLint("InflateParams") View popupView = li.inflate(R.layout.new_invitations, null);

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
                        = new ArrayAdapter(context, android.R.layout.simple_spinner_item, homeTypeArray);
                ArrayAdapter bedAd
                        = new ArrayAdapter(context, android.R.layout.simple_spinner_item, bedBathArray);
                ArrayAdapter bathAd
                        = new ArrayAdapter(context, android.R.layout.simple_spinner_item, bedBathArray);
                ArrayAdapter dayAd
                        = new ArrayAdapter(context, android.R.layout.simple_spinner_item, daysArray);
                ArrayAdapter monthAd
                        = new ArrayAdapter(context, android.R.layout.simple_spinner_item, monthsArray);
                ArrayAdapter yearAd
                        = new ArrayAdapter(context, android.R.layout.simple_spinner_item, yearsArray);
                ArrayAdapter roommateAd
                        = new ArrayAdapter(context, android.R.layout.simple_spinner_item, bedBathArray);
                ArrayAdapter spotAd
                        = new ArrayAdapter(context, android.R.layout.simple_spinner_item, bedBathArray);
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

                spin.setSelection(getIndex(spin, home.get("homeType").toString()));
                bedSpin.setSelection(getIndex(bedSpin, home.get("numBedrooms").toString()));
                bathSpin.setSelection(getIndex(bathSpin, home.get("numBathrooms").toString()));
                daySpin.setSelection(getIndex(daySpin, home.get("deadlineDay").toString()));
                monthSpin.setSelection(getIndex(monthSpin, home.get("deadlineMonth").toString()));
                yearSpin.setSelection(getIndex(yearSpin, home.get("deadlineYear").toString()));
                roommateSpin.setSelection(getIndex(roommateSpin, String.valueOf(inv.numRoommatesCapacity)));
                spotSpin.setSelection(getIndex(spotSpin, String.valueOf(inv.numSpotsLeft)));

                if ((Boolean) inv.home.get("userPriceRange")) {
                    EditText minPriceEt = (EditText) popupView.findViewById(R.id.minPriceEt);
                    minPriceEt.setText(inv.home.get("minPrice").toString());
                    EditText maxPriceEt = (EditText) popupView.findViewById(R.id.maxPriceEt);
                    maxPriceEt.setText(inv.home.get("maxPrice").toString());
                } else {
                    EditText priceEt = (EditText) popupView.findViewById(R.id.price);
                    priceEt.setText(inv.home.get("onePrice").toString());
                }

                EditText desc = (EditText) popupView.findViewById(R.id.requirementEt);
                desc.setText(inv.expectation);


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
                editText.setText(home.get("location").toString());
//                Places.initialize(context, "AIzaSyC6VpIx6wQUPFZiA_M40pa4sBJqCzSrzfI");
                editText.setFocusable(false);
                editText.setEnabled(false);
//                editText.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);
//                        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(context);
//                        ((Activity) context).startActivityForResult(intent, 100);
//                    }
//                });

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
//                        EditText desc = (EditText) popupView.findViewById(R.id.requirementEt);
                        String expectation = desc.getText().toString().trim();
                        String currentDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
                        String [] dateArray = currentDate.split("/");
                        String dateMonth = dateArray[0];
                        String dateDay = dateArray[1];
                        String dateYear = dateArray[2];
                        float distanceFromCampus = Float.parseFloat(home.get("distanceFromCampus").toString());
                        int roommateNum =  Integer.parseInt(roommateSpin.getSelectedItem().toString());
                        int spotNum = Integer.parseInt(spotSpin.getSelectedItem().toString());
                        String address = editText.getText().toString();
                        if (address.isEmpty()) {
                            editText.setError("Address cannot be empty.");
                            return;
                        }

                        if (cb.isChecked()) {
                            EditText minPriceEt = (EditText) popupView.findViewById(R.id.minPriceEt);
                            EditText maxPriceEt = (EditText) popupView.findViewById(R.id.maxPriceEt);
                            int minPrice, maxPrice;
                            if (minPriceEt.getText().toString().equals("") || maxPriceEt.getText().toString().equals("")) {
                                return;
                            }
                            minPrice = Integer.parseInt(minPriceEt.getText().toString());
                            maxPrice = Integer.parseInt(maxPriceEt.getText().toString());

                            FirebaseFirestore db = FirebaseFirestore.getInstance();
//                            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//                            String invID = UUID.randomUUID().toString();
//                            db.collection("invitations").document(invitationID).update("active", true);
//                            db.collection("invitations").document(invitationID).update("date", currentDate);
                            db.collection("invitations").document(invitationID).update("dateDay", dateDay);
                            db.collection("invitations").document(invitationID).update("dateMonth", dateMonth);
                            db.collection("invitations").document(invitationID).update("dateYear", dateYear);
                            db.collection("invitations").document(invitationID).update("numRoommatesCapacity", roommateNum);
                            db.collection("invitations").document(invitationID).update("numSpotsLeft", spotNum);
//                            db.collection("invitations").document(invitationID).update("ownerID", firebaseAuth.getCurrentUser().getUid());
                            db.collection("invitations").document(invitationID).update("expectation", expectation);

                            Map<String, Object> new_home = new HashMap<>();
                            new_home.put("bbQuantity", String.valueOf(bedNum) + "B" + String.valueOf(bathNum) + "B");
                            new_home.put("deadline", deadlineMonth+"/"+deadlineDay+"/"+deadlineYear);
                            new_home.put("deadlineDay", deadlineDay);
                            new_home.put("deadlineMonth", deadlineMonth);
                            new_home.put("deadlineYear", deadlineYear);
                            new_home.put("homeType", homeType);
                            new_home.put("location", address);
                            new_home.put("userPriceRange", true);
                            new_home.put("minPrice", minPrice);
                            new_home.put("maxPrice", maxPrice);
                            new_home.put("numBedrooms", bedNum);
                            new_home.put("numBathrooms", bathNum);
                            new_home.put("distanceFromCampus", distanceFromCampus);
                            db.collection("invitations").document(invitationID).update("home", new_home);
//                            db.collection("users").document(firebaseAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                                @Override
//                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                                    if (task.isSuccessful()) {
//                                        DocumentSnapshot ds = task.getResult();
//                                        ArrayList<String> invitationIDList = (ArrayList<String>) ds.get("invitationsList");
//                                        invitationIDList.add(invitationID);
//                                        db.collection("users").document(firebaseAuth.getCurrentUser().getUid()).update("invitationsList", invitationIDList);
//                                        popupWindow.dismiss();
//                                    }
//                                }
//                            });
                            popupWindow.dismiss();

                        } else {
                            EditText priceEt = (EditText) popupView.findViewById(R.id.price);
                            int price;
                            if (priceEt.getText().toString().equals("")) {
                                return;
                            }
                            price = Integer.parseInt(priceEt.getText().toString());

                            FirebaseFirestore db = FirebaseFirestore.getInstance();
//                            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//                            String invID = UUID.randomUUID().toString();
                            Map<String, Object> map = new HashMap<>();
                            map.put("active", true);
//                            db.collection("invitations").document(invitationID).update("active", true);
//                            db.collection("invitations").document(invitationID).update("date", currentDate);
                            db.collection("invitations").document(invitationID).update("dateDay", dateDay);
                            db.collection("invitations").document(invitationID).update("dateMonth", dateMonth);
                            db.collection("invitations").document(invitationID).update("dateYear", dateYear);
                            db.collection("invitations").document(invitationID).update("numRoommatesCapacity", roommateNum);
                            db.collection("invitations").document(invitationID).update("numSpotsLeft", spotNum);
//                            db.collection("invitations").document(invitationID).update("ownerID", firebaseAuth.getCurrentUser().getUid());
                            db.collection("invitations").document(invitationID).update("expectation", expectation);

                            Map<String, Object> new_home = new HashMap<>();
                            new_home.put("bbQuantity", String.valueOf(bedNum) + "B" + String.valueOf(bathNum) + "B");
                            new_home.put("deadline", deadlineMonth+"/"+deadlineDay+"/"+deadlineYear);
                            new_home.put("deadlineDay", deadlineDay);
                            new_home.put("deadlineMonth", deadlineMonth);
                            new_home.put("deadlineYear", deadlineYear);
                            new_home.put("homeType", homeType);
                            new_home.put("location", address);
                            new_home.put("userPriceRange", false);
                            new_home.put("onePrice", price);
                            new_home.put("maxPrice", price);
                            new_home.put("numBedrooms", bedNum);
                            new_home.put("numBathrooms", bathNum);
                            new_home.put("distanceFromCampus", distanceFromCampus);
                            db.collection("invitations").document(invitationID).update("home", new_home);
//                            db.collection("users").document(firebaseAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                                @Override
//                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                                    if (task.isSuccessful()) {
//                                        DocumentSnapshot ds = task.getResult();
//                                        ArrayList<String> invitationIDList = (ArrayList<String>) ds.get("invitationsList");
//                                        invitationIDList.add(invitationID);
//                                        db.collection("users").document(firebaseAuth.getCurrentUser().getUid()).update("invitationsList", invitationIDList);
//                                        popupWindow.dismiss();
//                                    }
//                                }
//                            });
                            popupWindow.dismiss();
                        }
                    }

                });
            }
        });

        holder.trashInv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!FirebaseAuth.getInstance().getCurrentUser().getUid().equals(inv.ownerID)) {
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(true);
                builder.setTitle("Are you sure you want to delete this invitation for " + location + "?");
                builder.setMessage("This will delete all responses linked to this invitation as well.");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                db.collection("invitations").document(invID).delete();
                                ArrayList<String> responsesToDelete = new ArrayList<>();
                                db.collection("responses").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (DocumentSnapshot ds : task.getResult()) {
                                                if (invID.equals(ds.get("invitationID").toString())) {
                                                    responsesToDelete.add(ds.getId());
                                                }
                                            }
                                            for (String resID : responsesToDelete) {
                                                db.collection("responses").document(resID).delete();
                                            }
                                            db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        for (DocumentSnapshot d : task.getResult()) {
                                                            ArrayList<String> resIDList = (ArrayList<String>) d.getData().get("responsesList");
                                                            if (!resIDList.isEmpty()) {
                                                                for (String resID : responsesToDelete) {
                                                                    resIDList.remove(resID);
                                                                }
                                                                db.collection("users").document(d.getId()).update("responsesList", resIDList);
                                                            }
                                                            if (d.getId().equals(currentUserID)) {
                                                                db.collection("users").document(currentUserID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                        if (task.isSuccessful()) {
                                                                            DocumentSnapshot _ds = task.getResult();
                                                                            ArrayList<String> invs = (ArrayList<String>) _ds.get("invitationsList");
                                                                            invs.remove(invID);
                                                                            db.collection("users").document(currentUserID).update("invitationsList", invs);
                                                                        }
                                                                    }
                                                                });
                                                            }
                                                        }
                                                    }
                                                }
                                            });

                                        }
                                    }
                                });
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return invitationList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
//        ImageView
        TextView nameTv, locationTv, datePostedTv;
        Button messageButton, deleteButton, sendButton;
        Button editInv, trashInv;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            nameTv = itemView.findViewById(R.id.name);
            locationTv = itemView.findViewById(R.id.location);
            datePostedTv = itemView.findViewById(R.id.datePosted);
            messageButton = itemView.findViewById(R.id.respondTo);
            deleteButton = itemView.findViewById(R.id.rejectInv);
            sendButton = itemView.findViewById(R.id.submitInvite);
            editInv = itemView.findViewById(R.id.editPost);
            trashInv = itemView.findViewById(R.id.trashInv);
        }

    }

    private int getIndex(Spinner spinner, String myString) {
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }
        return 0;
    }
}


