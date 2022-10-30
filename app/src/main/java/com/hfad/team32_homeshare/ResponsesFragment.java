package com.hfad.team32_homeshare;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ResponsesFragment extends Fragment {
    private RecyclerView recyclerView;
    private AdapterResponses adapter;
    private ArrayList<Response> responsesList;

    public ResponsesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_responses, container, false);
        InitializeCardView(view);
        return view;
    }

    private void InitializeCardView(View view) {
        recyclerView = view.findViewById(R.id.invite_1);
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