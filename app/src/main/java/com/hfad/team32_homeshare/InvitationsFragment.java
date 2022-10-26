package com.hfad.team32_homeshare;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class InvitationsFragment extends Fragment {

   RecyclerView recyclerView;
   AdapterInvitations adapterInvitations;
   List<Invitation> invitationList;

    public InvitationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_invitations, container, false);
        recyclerView = view.findViewById(R.id.invite_1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        invitationList = new ArrayList<>();
        Invitation inv = new Invitation();
        inv.date = "10/20/2022";
        inv.invitationID = "ksdjfaldsjflasldfkasdf";
        invitationList.add(inv);
//        getInvitations();
        return view;
    }

//    private void getInvitations() {
////        Invitation<>
//    }
}