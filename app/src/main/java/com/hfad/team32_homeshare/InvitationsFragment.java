package com.hfad.team32_homeshare;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvitationsFragment extends Fragment {
    private RecyclerView recyclerView;
    private AdapterInvitations adapter;
    public ArrayList<Invitation> invitationsList;
    private ArrayList<String> declinedInvitationIDs;
    private Spinner spin;
    private Spinner spinTwo;
    private String[] items = new String[]{"First Name", "Gender", "Class Standing", "Distance from USC (miles, filter by maximum)",
            "Number of Bedrooms","Number of Bathrooms", "Price (filter by maximum)", "Number of Roommates", "Deadline Year YYYY (filter before)", "Year Posted YYYY (filter before)"};
    private String[] order = new String[]{"Ascending", "Descending"};

    public InvitationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        declinedInvitationIDs = new ArrayList<>();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_invitations, container, false);
        InitializeCardView(view);
//        updateDeclinedInvitations();
        EditText et = view.findViewById(R.id.searchInvite);
//        TextView textView = view.findViewById(R.id.randomText);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Take the instance of Spinner and
        // apply OnItemSelectedListener on it which
        // tells which item of spinner is clicked
        spin = (Spinner) view.findViewById(R.id.invitesFilter);
        spinTwo = (Spinner) view.findViewById(R.id.invitesOrder);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String text = adapterView.getItemAtPosition(position).toString();
//                Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinTwo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String text = adapterView.getItemAtPosition(position).toString();
//                Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
                String qu = spin.getSelectedItem().toString();
                // Ascending
                if (position == 0) {
                    if (qu.equals("Gender")) {
                        Collections.sort(invitationsList, Comparator.comparing(Invitation::getGender));
                        adapter.notifyDataSetChanged();
                    } else if (qu.equals("Class Standing")) {
                        // Used to be
                        // Collections.sort(invitationsList, Comparator.comparing(Invitation::getOwnerClassStandingNum));
                        sortInvitationsList_ClassStandingNum_Ascending(invitationsList);
                        adapter.notifyDataSetChanged();
                    } else if (qu.equals("Distance from USC (miles, filter by maximum)")) {
                        Collections.sort(invitationsList, Comparator.comparing(Invitation::getDistance));
                        adapter.notifyDataSetChanged();
                    } else if (qu.equals("Number of Bedrooms")) {
                        Collections.sort(invitationsList, Comparator.comparing(Invitation::getNumBed));
                        adapter.notifyDataSetChanged();
                    } else if (qu.equals("Number of Bathrooms")) {
                        Collections.sort(invitationsList, Comparator.comparing(Invitation::getNumBath));
                        adapter.notifyDataSetChanged();
                    } else if (qu.equals("Price (filter by maximum)")) {
                        Collections.sort(invitationsList, Comparator.comparing(Invitation::getMaxPrice));
                        adapter.notifyDataSetChanged();
                    } else if (qu.equals("Number of Roommates")) {
                        Collections.sort(invitationsList, Comparator.comparing(Invitation::getNumRoommates));
                        adapter.notifyDataSetChanged();
                    } else if (qu.equals("Deadline Year YYYY (filter before)")) {
                        Collections.sort(invitationsList, Comparator.comparing(Invitation::getDeadlineYear));
                        adapter.notifyDataSetChanged();
                    } else if (qu.equals("Year Posted YYYY (filter before)")) {
                        sortInvitationsList_Date_Ascending(invitationsList);
                        adapter.notifyDataSetChanged();
                    } else {
                        Collections.sort(invitationsList, Comparator.comparing(Invitation::getFullName));
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    if (qu.equals("Gender")) {
                        Collections.sort(invitationsList, Comparator.comparing(Invitation::getGender));
                        Collections.reverse(invitationsList);
                        adapter.notifyDataSetChanged();
                    } else if (qu.equals("Class Standing")) {
                        // Used to be
                        // Collections.sort(invitationsList, Comparator.comparing(Invitation::getOwnerClassStandingNum));
                        // Collections.reverse(invitationsList);
                        sortInvitationsList_ClassStandingNum_Descending(invitationsList);
                        adapter.notifyDataSetChanged();
                    } else if (qu.equals("Distance from USC (miles, filter by maximum)")) {
                        Collections.sort(invitationsList, Comparator.comparing(Invitation::getDistance));
                        Collections.reverse(invitationsList);
                        adapter.notifyDataSetChanged();
                    } else if (qu.equals("Number of Bedrooms")) {
                        Collections.sort(invitationsList, Comparator.comparing(Invitation::getNumBed));
                        Collections.reverse(invitationsList);
                        adapter.notifyDataSetChanged();
                    } else if (qu.equals("Number of Bathrooms")) {
                        Collections.sort(invitationsList, Comparator.comparing(Invitation::getNumBath));
                        Collections.reverse(invitationsList);
                        adapter.notifyDataSetChanged();
                    } else if (qu.equals("Price (filter by maximum)")) {
                        Collections.sort(invitationsList, Comparator.comparing(Invitation::getMaxPrice));
                        Collections.reverse(invitationsList);
                        adapter.notifyDataSetChanged();
                    } else if (qu.equals("Number of Roommates")) {
                        Collections.sort(invitationsList, Comparator.comparing(Invitation::getNumRoommates));
                        Collections.reverse(invitationsList);
                        adapter.notifyDataSetChanged();
                    } else if (qu.equals("Deadline Year YYYY (filter before)")) {
                        Collections.sort(invitationsList, Comparator.comparing(Invitation::getDeadlineYear));
                        Collections.reverse(invitationsList);
                        adapter.notifyDataSetChanged();
                    } else if (qu.equals("Year Posted YYYY (filter before)")) {
                        sortInvitationsList_Date_Descending(invitationsList);
                        adapter.notifyDataSetChanged();
                    } else {
                        Collections.sort(invitationsList, Comparator.comparing(Invitation::getFullName));
                        Collections.reverse(invitationsList);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Create the instance of ArrayAdapter
        // having the list of courses
        ArrayAdapter ad
                = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, items);//use getActivity();
        ArrayAdapter adTwo
                = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, order);

        // set simple layout resource file
        // for each item of spinner
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adTwo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the ArrayAdapter (ad) data on the
        // Spinner which binds data to spinner
        spin.setAdapter(ad);
        spinTwo.setAdapter(adTwo);

        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                invitationsList.clear();
                CreateDataForCards(view);
            }
        });


        Button btn = (Button) view.findViewById(R.id.searchBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = et.getText().toString().trim().toLowerCase();
                String query = spin.getSelectedItem().toString();
                updateDeclinedInvitations();
//                if (text.equals("")) {
//                    invitationsList.clear();
//                    CreateDataForCards(view);
//                } else if
                if (query.equals("Gender")){
//                    textView.setText(text+query);
                    invitationsList.clear();
                    db.collection("users").whereGreaterThanOrEqualTo("gender", text).whereLessThanOrEqualTo("gender", text+"\uF7FF").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot user : task.getResult()) {
                                    String ownerID = user.getId();
                                    String fullName = user.get("fullName").toString();
                                    String ownerGender = user.get("gender").toString();
                                    int ownerClassStandingNum = Integer.parseInt(user.get("classStandingNum").toString());
//                                    textView.setText(fullName);
                                    db.collection("invitations").whereEqualTo("ownerID",ownerID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    if (!declinedInvitationIDs.contains(document.getId()) && !document.get("active").toString().equals("false")) {
                                                        Invitation inv = new Invitation();
                                                        inv.fullName = fullName;
                                                        inv.ownerGender = ownerGender;
                                                        inv.ownerClassStandingNum = ownerClassStandingNum;
                                                        inv.invitationID = document.getId();
                                                        inv.ownerID = ownerID;
                                                        inv.date = document.get("date").toString();
                                                        inv.home = (Map<String, Object>) document.get("home");
                                                        inv.numRoommatesCapacity = Integer.parseInt(document.get("numRoommatesCapacity").toString());
                                                        inv.numSpotsLeft = Integer.parseInt(document.get("numSpotsLeft").toString());
                                                        inv.expectation = document.get("expectation").toString();
                                                        invitationsList.add(inv);
                                                        adapter.notifyDataSetChanged();
                                                    }
                                                }
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    });
                } else if (query.equals("First Name")) {
                    Toast.makeText(view.getContext(), "Name not supported", Toast.LENGTH_LONG);
                    invitationsList.clear();
                    db.collection("users").whereGreaterThanOrEqualTo("fullNameLower", text).whereLessThanOrEqualTo("fullNameLower", text+"\uF7FF").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot user : task.getResult()) {
                                    String ownerID = user.getId();
                                    String fullName = user.get("fullName").toString();
                                    String ownerGender = user.get("gender").toString();
                                    int ownerClassStandingNum = Integer.parseInt(user.get("classStandingNum").toString());
//                                    textView.setText(fullName);
                                    db.collection("invitations").whereEqualTo("ownerID",ownerID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    if (!declinedInvitationIDs.contains(document.getId()) && !document.get("active").toString().equals("false")) {
                                                        Invitation inv = new Invitation();
                                                        inv.fullName = fullName;
                                                        inv.ownerGender = ownerGender;
                                                        inv.ownerClassStandingNum = ownerClassStandingNum;
                                                        inv.invitationID = document.getId();
                                                        inv.ownerID = ownerID;
                                                        inv.date = document.get("date").toString();
                                                        inv.home = (Map<String, Object>) document.get("home");
                                                        inv.numRoommatesCapacity = Integer.parseInt(document.get("numRoommatesCapacity").toString());
                                                        inv.numSpotsLeft = Integer.parseInt(document.get("numSpotsLeft").toString());
                                                        inv.expectation = document.get("expectation").toString();
                                                        invitationsList.add(inv);
                                                        adapter.notifyDataSetChanged();
                                                    }
                                                }
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    });
                } else if (query.equals("Class Standing")) {
                    invitationsList.clear();
                    db.collection("users").whereGreaterThanOrEqualTo("classStanding", text).whereLessThanOrEqualTo("classStanding", text+"\uF7FF").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot user : task.getResult()) {
                                    String ownerID = user.getId();
                                    String fullName = user.get("fullName").toString();
                                    String ownerGender = user.get("gender").toString();
                                    int ownerClassStandingNum = Integer.parseInt(user.get("classStandingNum").toString());
//                                    textView.setText(fullName);
                                    db.collection("invitations").whereEqualTo("ownerID",ownerID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    if (!declinedInvitationIDs.contains(document.getId()) && !document.get("active").toString().equals("false")) {
                                                        Invitation inv = new Invitation();
                                                        inv.fullName = fullName;
                                                        inv.ownerGender = ownerGender;
                                                        inv.ownerClassStandingNum = ownerClassStandingNum;
                                                        inv.invitationID = document.getId();
                                                        inv.ownerID = ownerID;
                                                        inv.date = document.get("date").toString();
                                                        inv.home = (Map<String, Object>) document.get("home");
                                                        inv.numRoommatesCapacity = Integer.parseInt(document.get("numRoommatesCapacity").toString());
                                                        inv.numSpotsLeft = Integer.parseInt(document.get("numSpotsLeft").toString());
                                                        inv.expectation = document.get("expectation").toString();
                                                        invitationsList.add(inv);
                                                        adapter.notifyDataSetChanged();
                                                    }
                                                }
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    });
                } else if (query.equals("Number of Bedrooms")) {
                    try {
                        invitationsList.clear();
                        int numBed = Integer.parseInt(text);
                        db.collection("invitations").whereEqualTo("home.numBedrooms", numBed).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        if (!declinedInvitationIDs.contains(document.getId()) && !document.get("active").toString().equals("false")) {
                                            String ownerID = document.get("ownerID").toString();
                                            String invID = document.getId();
                                            db.collection("users").document(ownerID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        DocumentSnapshot doc = task.getResult();
                                                        String fullName = doc.get("fullName").toString();
                                                        String ownerGender = doc.get("gender").toString();
                                                        int ownerClassStandingNum = Integer.parseInt(doc.get("classStandingNum").toString());
                                                        Invitation inv = new Invitation();
                                                        inv.fullName = fullName;
                                                        inv.ownerGender = ownerGender;
                                                        inv.ownerClassStandingNum = ownerClassStandingNum;
                                                        inv.invitationID = invID;
                                                        inv.ownerID = ownerID;
                                                        inv.date = document.get("date").toString();
                                                        inv.home = (Map<String, Object>) document.get("home");
                                                        inv.numRoommatesCapacity = Integer.parseInt(document.get("numRoommatesCapacity").toString());
                                                        inv.numSpotsLeft = Integer.parseInt(document.get("numSpotsLeft").toString());
                                                        inv.expectation = document.get("expectation").toString();
                                                        invitationsList.add(inv);
                                                        adapter.notifyDataSetChanged();
                                                    }
                                                }
                                            });
                                        }
                                    }
                                }
                            }
                        });
                    } catch (Exception e) {
                    }
                } else if (query.equals("Number of Bathrooms")) {
                    try {
                        invitationsList.clear();
                        int numBath = Integer.parseInt(text);
                        db.collection("invitations").whereEqualTo("home.numBathrooms", numBath).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        if (!declinedInvitationIDs.contains(document.getId()) && !document.get("active").toString().equals("false")) {
                                            String ownerID = document.get("ownerID").toString();
                                            String invID = document.getId();
                                            db.collection("users").document(ownerID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        DocumentSnapshot doc = task.getResult();
                                                        String fullName = doc.get("fullName").toString();
                                                        String ownerGender = doc.get("gender").toString();
                                                        int ownerClassStandingNum = Integer.parseInt(doc.get("classStandingNum").toString());
                                                        Invitation inv = new Invitation();
                                                        inv.fullName = fullName;
                                                        inv.ownerGender = ownerGender;
                                                        inv.ownerClassStandingNum = ownerClassStandingNum;
                                                        inv.invitationID = invID;
                                                        inv.ownerID = ownerID;
                                                        inv.date = document.get("date").toString();
                                                        inv.home = (Map<String, Object>) document.get("home");
                                                        inv.numRoommatesCapacity = Integer.parseInt(document.get("numRoommatesCapacity").toString());
                                                        inv.numSpotsLeft = Integer.parseInt(document.get("numSpotsLeft").toString());
                                                        inv.expectation = document.get("expectation").toString();
                                                        invitationsList.add(inv);
                                                        adapter.notifyDataSetChanged();
                                                    }
                                                }
                                            });
                                        }
                                    }
                                }
                            }
                        });
                    } catch (Exception e) {
                    }
                } else if (query.equals("Number of Roommates")) {
                    try {
                        invitationsList.clear();
                        int numRoom = Integer.parseInt(text);
                        db.collection("invitations").whereEqualTo("numRoommatesCapacity", numRoom).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        if (!declinedInvitationIDs.contains(document.getId()) && !document.get("active").toString().equals("false")) {
                                            String ownerID = document.get("ownerID").toString();
                                            String invID = document.getId();
                                            db.collection("users").document(ownerID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        DocumentSnapshot doc = task.getResult();
                                                        String fullName = doc.get("fullName").toString();
                                                        String ownerGender = doc.get("gender").toString();
                                                        int ownerClassStandingNum = Integer.parseInt(doc.get("classStandingNum").toString());
                                                        Invitation inv = new Invitation();
                                                        inv.fullName = fullName;
                                                        inv.ownerGender = ownerGender;
                                                        inv.ownerClassStandingNum = ownerClassStandingNum;
                                                        inv.invitationID = invID;
                                                        inv.ownerID = ownerID;
                                                        inv.date = document.get("date").toString();
                                                        inv.home = (Map<String, Object>) document.get("home");
                                                        inv.numRoommatesCapacity = Integer.parseInt(document.get("numRoommatesCapacity").toString());
                                                        inv.numSpotsLeft = Integer.parseInt(document.get("numSpotsLeft").toString());
                                                        inv.expectation = document.get("expectation").toString();
                                                        invitationsList.add(inv);
                                                        adapter.notifyDataSetChanged();
                                                    }
                                                }
                                            });
                                        }
                                    }
                                }
                            }
                        });
                    } catch (Exception e) {
                    }
                } else if (query.equals("Deadline Year YYYY (filter before)")) {
                    invitationsList.clear();
                    db.collection("invitations").whereLessThanOrEqualTo("home.deadlineYear",text).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    if (!declinedInvitationIDs.contains(document.getId()) && !document.get("active").toString().equals("false")) {
                                        String ownerID = document.get("ownerID").toString();
                                        String invID = document.getId();
                                        db.collection("users").document(ownerID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    DocumentSnapshot doc = task.getResult();
                                                    String fullName = doc.get("fullName").toString();
                                                    String ownerGender = doc.get("gender").toString();
                                                    int ownerClassStandingNum = Integer.parseInt(doc.get("classStandingNum").toString());
                                                    Invitation inv = new Invitation();
                                                    inv.fullName = fullName;
                                                    inv.ownerGender = ownerGender;
                                                    inv.ownerClassStandingNum = ownerClassStandingNum;
                                                    inv.invitationID = invID;
                                                    inv.ownerID = ownerID;
                                                    inv.date = document.get("date").toString();
                                                    inv.home = (Map<String, Object>) document.get("home");
                                                    inv.numRoommatesCapacity = Integer.parseInt(document.get("numRoommatesCapacity").toString());
                                                    inv.numSpotsLeft = Integer.parseInt(document.get("numSpotsLeft").toString());
                                                    inv.expectation = document.get("expectation").toString();
                                                    invitationsList.add(inv);
                                                    adapter.notifyDataSetChanged();
                                                }
                                            }
                                        });
                                    }
                                }
                            }
                        }
                    });
                } else if (query.equals("Year Posted YYYY (filter before)")) {
                    invitationsList.clear();
                    db.collection("invitations").whereLessThanOrEqualTo("dateYear",text).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    if (!declinedInvitationIDs.contains(document.getId()) && !document.get("active").toString().equals("false")) {
                                        String ownerID = document.get("ownerID").toString();
                                        String invID = document.getId();
                                        db.collection("users").document(ownerID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    DocumentSnapshot doc = task.getResult();
                                                    String fullName = doc.get("fullName").toString();
                                                    String ownerGender = doc.get("gender").toString();
                                                    int ownerClassStandingNum = Integer.parseInt(doc.get("classStandingNum").toString());
                                                    Invitation inv = new Invitation();
                                                    inv.fullName = fullName;
                                                    inv.ownerGender = ownerGender;
                                                    inv.ownerClassStandingNum = ownerClassStandingNum;
                                                    inv.invitationID = invID;
                                                    inv.ownerID = ownerID;
                                                    inv.date = document.get("date").toString();
                                                    inv.home = (Map<String, Object>) document.get("home");
                                                    inv.numRoommatesCapacity = Integer.parseInt(document.get("numRoommatesCapacity").toString());
                                                    inv.numSpotsLeft = Integer.parseInt(document.get("numSpotsLeft").toString());
                                                    inv.expectation = document.get("expectation").toString();
                                                    invitationsList.add(inv);
                                                    adapter.notifyDataSetChanged();
                                                }
                                            }
                                        });
                                    }
                                }
                            }
                        }
                    });
                } else if (query.equals("Distance from USC (miles, filter by maximum)")) {
                    try {
                        invitationsList.clear();
                        float dist = Float.parseFloat(text);
                        db.collection("invitations").whereLessThanOrEqualTo("home.distanceFromCampus", dist).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        if (!declinedInvitationIDs.contains(document.getId()) && !document.get("active").toString().equals("false")) {
                                            String ownerID = document.get("ownerID").toString();
                                            String invID = document.getId();
                                            db.collection("users").document(ownerID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        DocumentSnapshot doc = task.getResult();
                                                        String fullName = doc.get("fullName").toString();
                                                        String ownerGender = doc.get("gender").toString();
                                                        int ownerClassStandingNum = Integer.parseInt(doc.get("classStandingNum").toString());
                                                        Invitation inv = new Invitation();
                                                        inv.fullName = fullName;
                                                        inv.ownerGender = ownerGender;
                                                        inv.ownerClassStandingNum = ownerClassStandingNum;
                                                        inv.invitationID = invID;
                                                        inv.ownerID = ownerID;
                                                        inv.date = document.get("date").toString();
                                                        inv.home = (Map<String, Object>) document.get("home");
                                                        inv.numRoommatesCapacity = Integer.parseInt(document.get("numRoommatesCapacity").toString());
                                                        inv.numSpotsLeft = Integer.parseInt(document.get("numSpotsLeft").toString());
                                                        inv.expectation = document.get("expectation").toString();
                                                        invitationsList.add(inv);
                                                        adapter.notifyDataSetChanged();
                                                    }
                                                }
                                            });
                                        }
                                    }
                                }
                            }
                        });
                    } catch (Exception e) {
                    }
                } else if (query.equals("Price (filter by maximum)")) {
                    try {
                        invitationsList.clear();
                        int pri = Integer.parseInt(text);
                        db.collection("invitations").whereLessThanOrEqualTo("home.maxPrice", pri).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        if (!declinedInvitationIDs.contains(document.getId()) && !document.get("active").toString().equals("false")) {
                                            String ownerID = document.get("ownerID").toString();
                                            String invID = document.getId();
                                            db.collection("users").document(ownerID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        DocumentSnapshot doc = task.getResult();
                                                        String fullName = doc.get("fullName").toString();
                                                        String ownerGender = doc.get("gender").toString();
                                                        int ownerClassStandingNum = Integer.parseInt(doc.get("classStandingNum").toString());
                                                        Invitation inv = new Invitation();
                                                        inv.fullName = fullName;
                                                        inv.ownerGender = ownerGender;
                                                        inv.ownerClassStandingNum = ownerClassStandingNum;
                                                        inv.invitationID = invID;
                                                        inv.ownerID = ownerID;
                                                        inv.date = document.get("date").toString();
                                                        inv.home = (Map<String, Object>) document.get("home");
                                                        inv.numRoommatesCapacity = Integer.parseInt(document.get("numRoommatesCapacity").toString());
                                                        inv.numSpotsLeft = Integer.parseInt(document.get("numSpotsLeft").toString());
                                                        inv.expectation = document.get("expectation").toString();
                                                        invitationsList.add(inv);
                                                        adapter.notifyDataSetChanged();
                                                    }
                                                }
                                            });
                                        }
                                    }
                                }
                            }
                        });
                    } catch (Exception e) {
                    }
                }
//                textView.setText(text+query);
//                Toast.makeText(view.getContext(), text, Toast.LENGTH_LONG);
            }
        });

        return view;
    }

    private void InitializeCardView(View view) {
        recyclerView = view.findViewById(R.id.invite_1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        invitationsList = new ArrayList<>();

        adapter = new AdapterInvitations(getActivity(), invitationsList);
        recyclerView.setAdapter(adapter);
        CreateDataForCards(view);
    }

    private void CreateDataForCards(View view) {
//        TextView textView = view.findViewById(R.id.randomText);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        updateDeclinedInvitations();
        db.collection("invitations").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (!declinedInvitationIDs.contains(document.getId()) && !document.get("active").toString().equals("false")) {
                            String ownerID = document.get("ownerID").toString();
                            String invID = document.getId();
                            db.collection("users").document(ownerID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot doc = task.getResult();
                                        String fullName = doc.get("fullName").toString();
                                        String ownerGender = doc.get("gender").toString();
                                        int ownerClassStandingNum = Integer.parseInt(doc.get("classStandingNum").toString());
                                        Invitation inv = new Invitation();
                                        inv.fullName = fullName;
                                        inv.ownerGender = ownerGender;
                                        inv.ownerClassStandingNum = ownerClassStandingNum;
                                        inv.invitationID = invID;
                                        inv.ownerID = ownerID;
                                        inv.date = document.get("date").toString();
                                        inv.home = (Map<String, Object>) document.get("home");
                                        inv.numRoommatesCapacity = Integer.parseInt(document.get("numRoommatesCapacity").toString());
                                        inv.numSpotsLeft = Integer.parseInt(document.get("numSpotsLeft").toString());
                                        inv.expectation = document.get("expectation").toString();
                                        invitationsList.add(inv);
                                        adapter.notifyDataSetChanged();
                                    }
                                }
                            });
                        }
                    }
                }
            }
        });
    }

    private void updateDeclinedInvitations() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        db.collection("users").document(firebaseAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot ds = task.getResult();
                    declinedInvitationIDs = (ArrayList<String>) ds.get("declinedInvitationsList");
                }
            }
        });
    }

    public void sortInvitationsList_ClassStandingNum_Ascending(ArrayList<Invitation> invList) {
        Collections.sort(invList, Comparator.comparing(Invitation::getOwnerClassStandingNum));
    }

    public void sortInvitationsList_ClassStandingNum_Descending(ArrayList<Invitation> invList) {
        Collections.sort(invList, Comparator.comparing(Invitation::getOwnerClassStandingNum));
        Collections.reverse(invList);
    }
    public void sortInvitationsList_Date_Ascending(ArrayList<Invitation> invList) {
        Collections.sort(invList, Comparator.comparing(Invitation::getYearPosted));
    }

    public void sortInvitationsList_Date_Descending(ArrayList<Invitation> invList) {
        Collections.sort(invList, Comparator.comparing(Invitation::getYearPosted));
        Collections.reverse(invList);
    }
}
