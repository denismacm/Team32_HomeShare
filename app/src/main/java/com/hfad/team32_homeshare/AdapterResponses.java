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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

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
        View view = LayoutInflater.from(context).inflate(R.layout.responses_cards, viewGroup, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int i) {
        Response res = responseList.get(i);
        String userName = res.senderName;
        String currentUser = res.senderID;
        String recipient = res.recipientID;
        String responseID = UUID.randomUUID().toString();
        String address = res.address;
        String datePosted = res.date;
        String message = res.message;
        String invID = res.invID;
        holder.nameTv.setText(userName);
        holder.dateTv.setText(datePosted);

//        Response invite = responseList.get(i);
//        holder.setDetails(invite);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  IMPLEMENT OVERLAY
//                Toast.makeText(context, ""+ userEmail, Toast.LENGTH_SHORT).show(); // to show that clicking works
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.overlay_invitations, null);

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                ((TextView)popupWindow.getContentView().findViewById(R.id.nameOL)).setText(userName);
                ((TextView)popupWindow.getContentView().findViewById(R.id.datePostedOL)).setText(datePosted);
                ((TextView)popupWindow.getContentView().findViewById(R.id.locationOL)).setText(address);
                ((TextView)popupWindow.getContentView().findViewById(R.id.bbQuantityOL)).setText(message);
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

        holder.yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.respond_to_layout, null);
                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                // lets taps outside the popup also dismiss it
                boolean focusable = true;
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                popupView.findViewById(R.id.submitMessage).setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                         final EditText edit =  (EditText) popupView.findViewById(R.id.inviteEt);
                         String getMessage = edit.getText().toString();
                         String currentDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());

                         FirebaseFirestore db = FirebaseFirestore.getInstance();
                         db.collection("users").document(recipient).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                             @Override
                             public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                 if (task.isSuccessful()) {
                                     DocumentSnapshot documentSnapshot = task.getResult();
                                     ArrayList<String> invitesIDArray = (ArrayList<String>) documentSnapshot.get("responsesList");
                                     invitesIDArray.add(responseID);
                                     db.collection("users").document(recipient).update("responsesList", invitesIDArray);

                                     Map<String, String> responseMap = new HashMap<>();
                                     responseMap.put("date", currentDate);
                                     responseMap.put("invitationID", invID);
                                     responseMap.put("message", getMessage);
                                     responseMap.put("recipientID", recipient);
                                     responseMap.put("senderID", currentUser);

                                     db.collection("responses").document(responseID).set(responseMap);
                                     popupWindow.dismiss();
                                 }
                             }
                         });
                     }
                });
            }
        });

        holder.noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                db.collection("users").document(currentUser).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            ArrayList<String> responsesIDArray = (ArrayList<String>) documentSnapshot.get("responsesList");

                            responsesIDArray.remove(responseID);

                            db.collection("users").document(currentUser).update("responsesList", responsesIDArray);
                        }
                    }
                });
            }
        });
    }


    @Override
    public int getItemCount() {
        return responseList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        //        ImageView
        TextView nameTv, dateTv;
        Button yesButton, noButton, sendButton;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            nameTv = itemView.findViewById(R.id.name);
            dateTv = itemView.findViewById(R.id.date);
            yesButton = itemView.findViewById(R.id.yesResponse);
            noButton = itemView.findViewById(R.id.noResponse);
            sendButton = itemView.findViewById(R.id.submitMessage);
        }

//        void setDetails(Response inv) {
//            nameTv.setText(inv.getResponseID());
//            emailTv.setText(inv.getDate());
//        }
    }
}
