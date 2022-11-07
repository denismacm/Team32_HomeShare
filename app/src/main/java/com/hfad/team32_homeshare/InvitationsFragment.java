package com.hfad.team32_homeshare;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InvitationsFragment extends Fragment {
    private RecyclerView recyclerView;
    private AdapterInvitations adapter;
    private ArrayList<Invitation> invitationsList;
    private Spinner spin;
    private Spinner spinTwo;
    private String[] items = new String[]{"Reset to default", "Name", "Gender", "Class Standing", "Distance in miles",
            "Number of Bedrooms","Number of Bathrooms", "Price", "Maximum Number of Roommates"};
    private String[] order = new String[]{"Ascending", "Descending"};

    public InvitationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_invitations, container, false);
        InitializeCardView(view);
        EditText et = view.findViewById(R.id.searchInvite);
        TextView textView = view.findViewById(R.id.randomText);
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

        Button btn = (Button) view.findViewById(R.id.searchBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = et.getText().toString().toLowerCase();
                String query = spin.getSelectedItem().toString();
//                if (text.equals("")) {
//                    invitationsList.clear();
//                    CreateDataForCards(view);
//                } else if
                if (query.equals("Gender")){
//                    textView.setText(text+query);
                    invitationsList.clear();
                    db.collection("users").whereEqualTo("gender", text).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot user : task.getResult()) {
                                    String ownerID = user.getId();
                                    String fullName = user.get("fullName").toString();
                                    textView.setText(fullName);
                                    db.collection("invitations").whereEqualTo("ownerID",ownerID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    Invitation inv = new Invitation();
                                                    inv.fullName = fullName;
                                                    inv.date = document.get("date").toString();
                                                    inv.home = (Map<String, Object>) document.get("home");
                                                    inv.numRoommatesCapacity = Integer.parseInt(document.get("numRoommatesCapacity").toString());
                                                    inv.numSpotsLeft = Integer.parseInt(document.get("numSpotsLeft").toString());
                                                    invitationsList.add(inv);
                                                    adapter.notifyDataSetChanged();
                                                }
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    });
                } else if (query.equals("Name")) {
                    Toast.makeText(view.getContext(), "Name not supported", Toast.LENGTH_LONG);
//                    invitationsList.clear();
//                    db.collection("users").whereGreaterThanOrEqualTo("fullNameLower", text).whereLessThanOrEqualTo("fullNameLower", text+"\uF7FF").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                            if (task.isSuccessful()) {
//                                for (QueryDocumentSnapshot user : task.getResult()) {
//                                    String ownerID = user.getId();
//                                    String fullName = user.get("fullName").toString();
//                                    textView.setText(fullName);
//                                    db.collection("invitations").whereEqualTo("ownerID",ownerID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                            if (task.isSuccessful()) {
//                                                for (QueryDocumentSnapshot document : task.getResult()) {
//                                                    Invitation inv = new Invitation();
//                                                    inv.fullName = fullName;
//                                                    inv.date = document.get("date").toString();
//                                                    inv.home = (Map<String, Object>) document.get("home");
//                                                    inv.numRoommatesCapacity = Integer.parseInt(document.get("numRoommatesCapacity").toString());
//                                                    inv.numSpotsLeft = Integer.parseInt(document.get("numSpotsLeft").toString());
//                                                    invitationsList.add(inv);
//                                                    adapter.notifyDataSetChanged();
//                                                }
//                                            }
//                                        }
//                                    });
//                                }
//                            }
//                        }
//                    });
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
        db.collection("invitations").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        Map<String, Object> map = document.getData();
//                        textView.setText(document.get("date").toString());
//                        DocumentReference ref = document.getDocumentReference("owner");
//                        DocumentSnapshot doc = ref.get().getResult();
                        String ownerID = document.get("ownerID").toString();
                        db.collection("users").document(ownerID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot doc = task.getResult();
                                    String fullName = doc.get("fullName").toString();
                                    Invitation inv = new Invitation();
                                    inv.fullName = fullName;
                                    inv.date = document.get("date").toString();
                                    inv.home = (Map<String, Object>) document.get("home");
                                    inv.numRoommatesCapacity = Integer.parseInt(document.get("numRoommatesCapacity").toString());
                                    inv.numSpotsLeft = Integer.parseInt(document.get("numSpotsLeft").toString());
                                    invitationsList.add(inv);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        });
                    }
                }
            }
        });
    }
}