package com.hfad.team32_homeshare;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class InvitationsFragment extends Fragment {
    private RecyclerView recyclerView;
    private AdapterInvitations adapter;
    private ArrayList<Invitation> invitationsList;
    private Spinner spin;
    private Spinner spinTwo;
    private String[] items = new String[]{"Name", "Gender", "Distance",
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

        return view;
    }

    private void InitializeCardView(View view) {
        recyclerView = view.findViewById(R.id.invite_1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        invitationsList = new ArrayList<>();

        adapter = new AdapterInvitations(getActivity(), invitationsList);
        recyclerView.setAdapter(adapter);

        CreateDataForCards();
    }

    private void CreateDataForCards() {
        Invitation inv1 = new Invitation("Invitation 1", "Denis Mac");
        invitationsList.add(inv1);
        Invitation inv2 = new Invitation("Invitation 2", "Aaron Wong");
        invitationsList.add(inv2);
        Invitation inv3 = new Invitation("Invitation 3", "Michael Kim");
        invitationsList.add(inv3);
        Invitation inv4 = new Invitation("Invitation 4", "Lana Nguyen");
        invitationsList.add(inv4);
        Invitation inv5 = new Invitation("Invitation 5", "Lilly Tran");
        invitationsList.add(inv5);
        Invitation inv6 = new Invitation("Invitation 6", "Jefferson Nguyen");
        invitationsList.add(inv6);
        Invitation inv7 = new Invitation("Invitation 7", "Teresa Tran");
        invitationsList.add(inv7);

        adapter.notifyDataSetChanged();
    }
}