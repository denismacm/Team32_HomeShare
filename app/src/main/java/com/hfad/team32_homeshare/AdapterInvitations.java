package com.hfad.team32_homeshare;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

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
        Invitation inv = invitationList.get(i);
        String fullName = inv.fullName;
        String datePosted = inv.date;
        String hiddenID = UUID.randomUUID().toString();

        Map<String, Object> home = inv.home;
        String location = home.get("location").toString();
        String bbQuantity = home.get("bbQuantity").toString();
        String deadline = "Deadline: " + home.get("deadline").toString();
        String homeType = "Home Type: " + home.get("homeType").toString();
        Boolean userPriceRange = (Boolean) home.get("userPriceRange");
//        String price;
//        if (userPriceRange) {
//            price = "Min Price: " + home.get("minPrice");
//            price += "Max Price: " + home.get("maxPrice");
//        } else {
//            price = "Price: " + home.get("onePrice");
//        }
        String numRoommatesCapacity = "Looking for " + String.valueOf(inv.numRoommatesCapacity) + " roommates";
        String numSpotsLeft = String.valueOf(inv.numSpotsLeft) + " spots left";

        holder.nameTv.setText(fullName);
        holder.locationTv.setText(location);
        holder.datePostedTv.setText("Date posted: " + datePosted);

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
                ((TextView)popupWindow.getContentView().findViewById(R.id.nameOL)).setText(fullName);
                ((TextView)popupWindow.getContentView().findViewById(R.id.locationOL)).setText(location);
                ((TextView)popupWindow.getContentView().findViewById(R.id.datePostedOL)).setText(datePosted);
                ((TextView)popupWindow.getContentView().findViewById(R.id.bbQuantityOL)).setText(bbQuantity);
                ((TextView)popupWindow.getContentView().findViewById(R.id.deadlineOL)).setText(deadline);
                ((TextView)popupWindow.getContentView().findViewById(R.id.homeTypeOL)).setText(homeType);
                String price = "";
                if (userPriceRange) {
                    price = "Min Price: " + home.get("minPrice");
                    price += "Max Price: " + home.get("maxPrice");
                } else {
                    price = "Price: " + home.get("onePrice");
                }
                ((TextView)popupWindow.getContentView().findViewById(R.id.priceOL)).setText(price);
                ((TextView)popupWindow.getContentView().findViewById(R.id.numCapacityOL)).setText(numRoommatesCapacity);
                ((TextView)popupWindow.getContentView().findViewById(R.id.numSpotsOL)).setText(numSpotsLeft);

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

        holder.messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.create_invite_layout, null);
                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                // lets taps outside the popup also dismiss it
                boolean focusable = true;
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                popupView.findViewById(R.id.submitInvite).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                        String senderID = firebaseAuth.getCurrentUser().getUid();
//
                        final EditText edit =  (EditText) popupView.findViewById(R.id.inviteEt);
                        String getRecipientID = inv.ownerID;
                        String getInvID = inv.invitationID;
                        String getMessage = edit.getText().toString();
                        String currentDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
                        String responseID = UUID.randomUUID().toString();

                        //create response
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("users").document(getRecipientID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot documentSnapshot = task.getResult();
                                    ArrayList<String> responsesIDArray = (ArrayList<String>) documentSnapshot.get("responsesList");
                                    responsesIDArray.add(responseID);
                                    db.collection("users").document(getRecipientID).update("responsesList", responsesIDArray);

                                    Map<String, String> responseMap = new HashMap<>();
                                    responseMap.put("date", currentDate);
                                    responseMap.put("invitationID", getInvID);
                                    responseMap.put("message", getMessage);
                                    responseMap.put("recipientID", getRecipientID);
                                    responseMap.put("senderID", senderID);

                                    db.collection("responses").document(responseID).set(responseMap);
                                    popupWindow.dismiss();
                                }
                            }
                        });
                    }
                });
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                String senderID = firebaseAuth.getCurrentUser().getUid();
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                db.collection("users").document(senderID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            ArrayList<String> hiddenInvitesIDArray = (ArrayList<String>) documentSnapshot.get("declinedInvitationsList");
                            hiddenInvitesIDArray.add(hiddenID);
                            db.collection("users").document(senderID).update("declinedInvitationsList", hiddenInvitesIDArray);
                            //remove invitation

                        }
                    }
                });


            }
        });

    }

    @Override
    public int getItemCount() {
        return invitationList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
//        ImageView
        TextView nameTv, locationTv, datePostedTv;
        Button messageButton, deleteButton, sendButton;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            nameTv = itemView.findViewById(R.id.name);
            locationTv = itemView.findViewById(R.id.location);
            datePostedTv = itemView.findViewById(R.id.datePosted);
            messageButton = itemView.findViewById(R.id.respondTo);
            deleteButton = itemView.findViewById(R.id.rejectInv);
            sendButton = itemView.findViewById(R.id.submitInvite);
        }

//        void setDetails(Invitation inv) {
//            nameTv.setText(inv.getInvitationID());
//            emailTv.setText(inv.getDate());
//        }
    }
}
