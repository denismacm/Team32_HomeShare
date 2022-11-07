package com.hfad.team32_homeshare;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class AdapterInvitations extends RecyclerView.Adapter<AdapterInvitations.MyHolder> {

    Context context;
    ArrayList<Invitation> invitationList;

    public AdapterInvitations(Context context, ArrayList<Invitation> invitationList) {
        this.context = context;
        this.invitationList = invitationList;
    }

    @NonNull
    @Override
    public AdapterInvitations.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
//        Context context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.activity_cards, viewGroup, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int i) {
        String userName = invitationList.get(i).getInvitationID();
        String userEmail = invitationList.get(i).getDate();
        holder.nameTv.setText(userName);
        holder.emailTv.setText(userEmail);

//        Invitation invite = invitationList.get(i);
//        holder.setDetails(invite);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  IMPLEMENT OVERLAY
//                Toast.makeText(context, ""+ userEmail, Toast.LENGTH_SHORT).show(); // to show that clicking works
                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.overlay_invitations, null);

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                ((TextView)popupWindow.getContentView().findViewById(R.id.name1)).setText(userName);
                ((TextView)popupWindow.getContentView().findViewById(R.id.email1)).setText(userEmail);
                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window tolken
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                // dismiss the popup window when touched
//                popupView.setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View v, MotionEvent event) {
//                        popupWindow.dismiss();
//                        return true;
//                    }
//                });
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
//        FloatingActionButton fab;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            nameTv = itemView.findViewById(R.id.name);
            emailTv = itemView.findViewById(R.id.email);
//            fab = itemView.findViewById((R.id.fab));
        }

//        void setDetails(Invitation inv) {
//            nameTv.setText(inv.getInvitationID());
//            emailTv.setText(inv.getDate());
//        }
    }
}
