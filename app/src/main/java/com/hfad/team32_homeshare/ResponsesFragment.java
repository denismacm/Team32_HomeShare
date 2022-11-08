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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map;

public class ResponsesFragment extends Fragment {
    private RecyclerView recyclerView;
    private AdapterResponses adapter;
    private ArrayList<Response> responsesList;
    private Spinner spin;
    private Spinner spinTwo;
    private String[] items = new String[]{"Name", "Gender"};
    private String[] order = new String[]{"Ascending", "Descending"};

    public ResponsesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_responses, container, false);
        InitializeCardView(view);

        // Take the instance of Spinner and
        // apply OnItemSelectedListener on it which
        // tells which item of spinner is clicked
        spin = (Spinner) view.findViewById(R.id.responsesFilter);
        spinTwo = (Spinner) view.findViewById(R.id.responsesOrder);

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
                if (position == 0) {
                    if (qu.equals("Gender")) {
                        Collections.sort(responsesList, Comparator.comparing(Response::getSenderGender));
                        adapter.notifyDataSetChanged();
                    } else {
                        Collections.sort(responsesList, Comparator.comparing(Response::getSenderName));
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    if (qu.equals("Gender")) {
                        Collections.sort(responsesList, Comparator.comparing(Response::getSenderGender));
                        Collections.reverse(responsesList);
                        adapter.notifyDataSetChanged();
                    } else {
                        Collections.sort(responsesList, Comparator.comparing(Response::getSenderName));
                        Collections.reverse(responsesList);
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
                = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, order);//use getActivity();

        // set simple layout resource file
        // for each item of spinner
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adTwo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the ArrayAdapter (ad) data on the
        // Spinner which binds data to spinner
        spin.setAdapter(ad);
        spinTwo.setAdapter(adTwo);

        Button queryBtn = (Button) view.findViewById(R.id.queryBtn);
        EditText searchText = (EditText) view.findViewById(R.id.searchResponse);
        queryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = searchText.getText().toString().trim().toLowerCase();
                String query = spin.getSelectedItem().toString();
                if (query.equals("Name")) {
                    ArrayList<Response> toRemove = new ArrayList<>();
                    for (Response _res : responsesList) {
                        if (!_res.senderName.toLowerCase().contains(text)) {
                            toRemove.add(_res);
                        }
                    }
                    for (Response _res : toRemove) {
                        responsesList.remove(_res);
                    }
                    adapter.notifyDataSetChanged();
                }
                else {
                    ArrayList<Response> toRemove = new ArrayList<>();
                    for (Response _res : responsesList) {
                        if (!_res.senderGender.contains(text)) {
                            toRemove.add(_res);
                        }
                    }
                    for (Response _res : toRemove) {
                        responsesList.remove(_res);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });

        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayoutResponses);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                responsesList.clear();
                CreateDataForCards(view);
            }
        });

        return view;
    }

    private void InitializeCardView(View view) {
        recyclerView = view.findViewById(R.id.response_1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        responsesList = new ArrayList<>();

        adapter = new AdapterResponses(getActivity(), responsesList);
        recyclerView.setAdapter(adapter);

        CreateDataForCards(view);
    }

    private void CreateDataForCards(View view) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String uid = firebaseAuth.getCurrentUser().getUid();
//        TextView tv = view.findViewById(R.id.rando);
        db.collection("users").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
             @Override
             public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                 if (task.isSuccessful()) {
                     DocumentSnapshot documentSnapshot = task.getResult();
                     ArrayList<String> respon = (ArrayList<String>) documentSnapshot.get("responsesList");

                     for (String res : respon) {
                         db.collection("responses").document(res).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                             @Override
                             public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                 if (task.isSuccessful()) {
                                     DocumentSnapshot doc = task.getResult();
                                     String senderID = doc.get("senderID").toString();
                                     String invitationID = doc.get("invitationID").toString();
                                     String message = doc.get("message").toString();
                                     String datePosted = doc.get("date").toString();
                                     String responseID = doc.getId();
                                     String recipientID = doc.get("recipientID").toString();
                                     Boolean accepted = (Boolean) doc.get("accepted");
                                     db.collection("invitations").document(invitationID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                         @Override
                                         public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                             if (task.isSuccessful()) {
                                                 DocumentSnapshot d = task.getResult();
                                                 String location = d.get("home.location").toString();
//                                                 tv.setText(location);
                                                 db.collection("users").document(senderID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                     @Override
                                                     public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                         if (task.isSuccessful()) {
                                                             DocumentSnapshot m = task.getResult();
                                                             String senderName = m.get("fullName").toString();
                                                             String senderGender = m.get("gender").toString();
                                                             Response r = new Response();
                                                             r.date = datePosted;
                                                             r.message = message;
                                                             r.senderName = senderName;
//                                                             tv.setText(senderName);
                                                             r.address = location;
                                                             r.senderGender = senderGender;
                                                             r.senderID = senderID;
                                                             r.recipientID = recipientID;
                                                             r.responseID = responseID;
                                                             r.invID = invitationID;
                                                             r.accepted = accepted;
                                                             responsesList.add(r);
                                                             adapter.notifyDataSetChanged();
                                                         }
                                                     }
                                                 });
                                             }
                                         }
                                     });
                                 }
                             }
                         });
                     }
                 }
             }
        });


//        Response inv1 = new Response("Response 1", "Denis Mac");
//        responsesList.add(inv1);
//        Response inv2 = new Response("Response 2", "Aaron Wong");
//        responsesList.add(inv2);
//        Response inv3 = new Response("Response 3", "Michael Kim");
//        responsesList.add(inv3);
//        Response inv4 = new Response("Response 4", "Lana Nguyen");
//        responsesList.add(inv4);
//        Response inv5 = new Response("Response 5", "Lilly Tran");
//        responsesList.add(inv5);
//        Response inv6 = new Response("Response 6", "Jefferson Nguyen");
//        responsesList.add(inv6);
//        Response inv7 = new Response("Response 7", "Teresa Tran");
//        responsesList.add(inv7);
//
//        adapter.notifyDataSetChanged();
    }

}