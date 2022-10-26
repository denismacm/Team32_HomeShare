package com.hfad.team32_homeshare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class InvitationsFeedActivity extends AppCompatActivity{
    ArrayList<Invitation> invites;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitationsfeed);
//        // Lookup the recyclerview in activity layout
//        RecyclerView rvInvitations = (RecyclerView) findViewById(R.id.invite_1);
//
//        // Initialize contacts
//        Invitation inv = new Invitation();
//        inv.date = "10/20/2022";
//        inv.invitationID = "ksdjfaldsjflasldfkasdf";
//        invites.add(inv);
//        // Create adapter passing in the sample user data
//        AdapterInvitations adapter = new AdapterInvitations(invites);
//        // Attach the adapter to the recyclerview to populate items
//        rvInvitations.setAdapter(adapter);
//        // Set layout manager to position the items
//        rvInvitations.setLayoutManager(new LinearLayoutManager(this));
        // That's all!
    }

}
