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

        return view;
    }

    private void InitializeCardView(View view) {
        recyclerView = view.findViewById(R.id.response_1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        responsesList = new ArrayList<>();

        adapter = new AdapterResponses(getActivity(), responsesList);
        recyclerView.setAdapter(adapter);

        CreateDataForCards();
    }

    private void CreateDataForCards() {
        Response inv1 = new Response("Response 1", "Denis Mac");
        responsesList.add(inv1);
        Response inv2 = new Response("Response 2", "Aaron Wong");
        responsesList.add(inv2);
        Response inv3 = new Response("Response 3", "Michael Kim");
        responsesList.add(inv3);
        Response inv4 = new Response("Response 4", "Lana Nguyen");
        responsesList.add(inv4);
        Response inv5 = new Response("Response 5", "Lilly Tran");
        responsesList.add(inv5);
        Response inv6 = new Response("Response 6", "Jefferson Nguyen");
        responsesList.add(inv6);
        Response inv7 = new Response("Response 7", "Teresa Tran");
        responsesList.add(inv7);

        adapter.notifyDataSetChanged();
    }

}