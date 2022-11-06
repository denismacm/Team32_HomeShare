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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InvitationsFragment extends Fragment {
    private RecyclerView recyclerView;
    private AdapterInvitations adapter;
    private ArrayList<Invitation> invitationsList;
    private Spinner spin;
    private String[] items = new String[]{"Name", "Gender", "Distance",
            "Number of Bedrooms","Number of Bathrooms", "Price", "Maximum Number of Roommates"};

    public InvitationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_invitations, container, false);
        InitializeCardView(view);

        // Take the instance of Spinner and
        // apply OnItemSelectedListener on it which
        // tells which item of spinner is clicked
        spin = (Spinner) view.findViewById(R.id.invitesFilter);
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

        // Create the instance of ArrayAdapter
        // having the list of courses
        ArrayAdapter ad
                = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, items);//use getActivity();

        // set simple layout resource file
        // for each item of spinner
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the ArrayAdapter (ad) data on the
        // Spinner which binds data to spinner
        spin.setAdapter(ad);

        Button btn = (Button) view.findViewById(R.id.submitBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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




//        Invitation inv1 = new Invitation("Invitation 1", "Denis Mac");
//        invitationsList.add(inv1);
//        Invitation inv2 = new Invitation("Invitation 2", "Aaron Wong");
//        invitationsList.add(inv2);
//        Invitation inv3 = new Invitation("Invitation 3", "Michael Kim");
//        invitationsList.add(inv3);
//        Invitation inv4 = new Invitation("Invitation 4", "Lana Nguyen");
//        invitationsList.add(inv4);
//        Invitation inv5 = new Invitation("Invitation 5", "Lilly Tran");
//        invitationsList.add(inv5);
//        Invitation inv6 = new Invitation("Invitation 6", "Jefferson Nguyen");
//        invitationsList.add(inv6);
//        Invitation inv7 = new Invitation("Invitation 7", "Teresa Tran");
//        invitationsList.add(inv7);

    }
}