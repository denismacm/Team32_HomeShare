package com.hfad.team32_homeshare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdapterResponses extends RecyclerView.Adapter<AdapterResponses.MyHolder> {

    Context context;
    ArrayList<Response> responseList;

    public AdapterResponses(Context context, ArrayList<Response> responseList) {
        this.context = context;
        this.responseList = responseList;
    }

    @NonNull
    @Override
    public AdapterResponses.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
//        Context context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.activity_cards, viewGroup, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int i) {
        String userName = responseList.get(i).getResponsesID();
        String userEmail = responseList.get(i).getUserName();
        holder.nameTv.setText(userName);
        holder.emailTv.setText(userEmail);

//        Response invite = responseList.get(i);
//        holder.setDetails(invite);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  IMPLEMENT OVERLAY
                Toast.makeText(context, ""+ userEmail, Toast.LENGTH_SHORT).show(); // to show that clicking works
            }
        });

    }

    @Override
    public int getItemCount() {
        return responseList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        //        ImageView
        TextView nameTv, emailTv;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            nameTv = itemView.findViewById(R.id.name);
            emailTv = itemView.findViewById(R.id.email);
        }

//        void setDetails(Response inv) {
//            nameTv.setText(inv.getResponseID());
//            emailTv.setText(inv.getDate());
//        }
    }
}
