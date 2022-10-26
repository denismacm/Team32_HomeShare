package com.hfad.team32_homeshare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterInvitations extends RecyclerView.Adapter<AdapterInvitations.MyHolder> {

    Context context;
    List<Invitation> invitationList;

    public AdapterInvitations(Context context, List<Invitation> invitationList) {
        this.context = context;
        this.invitationList = invitationList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_cards, viewGroup);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int i) {
        String userName = invitationList.get(i).invitationID;
        String userEmail = invitationList.get(i).date;

        holder.nameTv.setText(userName);
        holder.emailTv.setText(userEmail);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, ""+userEmail, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return invitationList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
//        ImageView
        TextView nameTv, emailTv;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            nameTv = itemView.findViewById(R.id.name);
            emailTv = itemView.findViewById(R.id.email);
        }
    }
}
