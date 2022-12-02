package com.hfad.team32_homeshare;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.checkerframework.checker.units.qual.A;

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
        String senderID = res.senderID;
        String gender = res.senderGender;
//        String currentUser = res.senderID;
//        String recipient = res.recipientID;
        String responseID = res.responseID;
//        String randomID = UUID.randomUUID().toString();
        String address = res.address;
        String datePosted = res.date;
        String message = res.message;
        String invID = res.invID;
        Boolean accepted = res.accepted;
        if (accepted) {
            holder.messageButton.setVisibility(View.VISIBLE);
            holder.yesButton.setVisibility(View.GONE);
        }

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
                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.MATCH_PARENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                String title = "<b>Response Details</b>";
                ((TextView)popupWindow.getContentView().findViewById(R.id.detailTitle)).setText(Html.fromHtml(title));
                String name = "<b>Name: </b>" + userName;
                ((TextView)popupWindow.getContentView().findViewById(R.id.nameOL)).setText(Html.fromHtml(name));
                String gen = "<b>Gender: </b>" + gender;
                ((TextView)popupWindow.getContentView().findViewById(R.id.genderOL)).setText(Html.fromHtml(gen));
                String location = "<b>Address: </b>" + address;
                ((TextView)popupWindow.getContentView().findViewById(R.id.locationOL)).setText(Html.fromHtml(location));
                String date = "<b>Date posted: </b>" + datePosted;
                ((TextView)popupWindow.getContentView().findViewById(R.id.datePostedOL)).setText(Html.fromHtml(date));
                String description = "<b>Message: </b>" + message;
                ((TextView)popupWindow.getContentView().findViewById(R.id.distanceOL)).setText(Html.fromHtml(description));
                ImageView img = (ImageView) popupWindow.getContentView().findViewById(R.id.profilePicOL);
                StorageReference pathReference = FirebaseStorage.getInstance().getReference().child("images/" + res.senderID);
                long MAX_BYTES = 1024 * 1024 * 10;
                pathReference.getBytes(MAX_BYTES).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        img.setImageBitmap(bitmap);
                    }
                });

                popupView.findViewById(R.id.dismiss).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
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
                         final EditText edit =  (EditText) popupView.findViewById(R.id.respondEt);
                         String getMessage = edit.getText().toString();
                         String currentDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());

                         FirebaseFirestore db = FirebaseFirestore.getInstance();
                         db.collection("users").document(senderID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                             @Override
                             public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                 if (task.isSuccessful()) {
                                     String randResID = UUID.randomUUID().toString();
                                     DocumentSnapshot documentSnapshot = task.getResult();

                                     db.collection("users").document(firebaseAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                         @Override
                                         public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                             if (task.isSuccessful()) {
                                                 DocumentSnapshot ds = task.getResult();
                                                 ArrayList<String> reIDArray = (ArrayList<String>) ds.get("responsesList");
                                                 reIDArray.remove(responseID);
                                                 db.collection("users").document(firebaseAuth.getCurrentUser().getUid()).update("responsesList", reIDArray);

                                                 db.collection("responses").document(responseID).delete();

                                                 db.collection("invitations").document(invID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                     @Override
                                                     public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                         if (task.isSuccessful()) {
                                                             DocumentSnapshot ds = task.getResult();
                                                             int numSp = Integer.parseInt(ds.get("numSpotsLeft").toString());
                                                             numSp--;
                                                             if (numSp == 0) {
                                                                 // Clear all responses related to this invitation and make invitation EXPIRED
                                                                 db.collection("responses").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                     @Override
                                                                     public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                         if (task.isSuccessful()) {
                                                                             ArrayList<String> toDel = new ArrayList<>();
                                                                             for (DocumentSnapshot ds : task.getResult()) {
                                                                                 if (ds.get("invitationID").toString().equals(invID) && ds.get("accepted").toString().equals("false")) {
                                                                                     toDel.add(ds.getId());
                                                                                 }
                                                                             }
                                                                             for (String rID : toDel) {
                                                                                 db.collection("responses").document(rID).delete();
                                                                                 reIDArray.remove(rID);
                                                                                 db.collection("users").document(firebaseAuth.getCurrentUser().getUid()).update("responsesList", reIDArray);
                                                                             }
                                                                             ArrayList<String> resIDArray = (ArrayList<String>) documentSnapshot.get("responsesList");
                                                                             resIDArray.add(randResID);
                                                                             db.collection("users").document(senderID).update("responsesList", resIDArray);
                                                                             Map<String, Object> responseMap = new HashMap<>();
                                                                             responseMap.put("date", currentDate);
                                                                             responseMap.put("invitationID", invID);
                                                                             responseMap.put("message", getMessage);
                                                                             responseMap.put("recipientID", senderID);
                                                                             responseMap.put("accepted", true);
                                                                             responseMap.put("senderID", firebaseAuth.getCurrentUser().getUid());

                                                                             db.collection("responses").document(randResID).set(responseMap);
                                                                         }
                                                                     }
                                                                 });
                                                                 db.collection("invitations").document(invID).update("active", false);
                                                             } else {
                                                                 ArrayList<String> resIDArray = (ArrayList<String>) documentSnapshot.get("responsesList");
                                                                 resIDArray.add(randResID);
                                                                 db.collection("users").document(senderID).update("responsesList", resIDArray);
                                                                 Map<String, Object> responseMap = new HashMap<>();
                                                                 responseMap.put("date", currentDate);
                                                                 responseMap.put("invitationID", invID);
                                                                 responseMap.put("message", getMessage);
                                                                 responseMap.put("recipientID", senderID);
                                                                 responseMap.put("accepted", true);
                                                                 responseMap.put("senderID", firebaseAuth.getCurrentUser().getUid());

                                                                 db.collection("responses").document(randResID).set(responseMap);
                                                             }
                                                             db.collection("invitations").document(invID).update("numSpotsLeft", numSp);

                                                             if (documentSnapshot.getData().get("token") != null && documentSnapshot.getData().get("token").toString() != "") {
                                                                 FCMSend.pushNotification(context,
                                                                         documentSnapshot.getData().get("token").toString(),
                                                                         "Response to invitation is accepted!",
                                                                         getMessage);
                                                             }
                                                             popupWindow.dismiss();
                                                         }
                                                     }
                                                 });

                                             }
                                         }
                                     });



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
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(true);
                builder.setTitle("Are you sure you want to permanently delete this response from " + userName + "?");
                builder.setMessage("Pull down to see refreshed changes after confirming.");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                                FirebaseFirestore db = FirebaseFirestore.getInstance();

                                db.collection("users").document(firebaseAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot documentSnapshot = task.getResult();
                                            ArrayList<String> responsesIDArray = (ArrayList<String>) documentSnapshot.get("responsesList");

                                            responsesIDArray.remove(responseID);
                                            db.collection("users").document(firebaseAuth.getCurrentUser().getUid()).update("responsesList", responsesIDArray);
                                            db.collection("responses").document(responseID).delete();
                                        }
                                    }
                                });
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        holder.messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.message_to_layout, null);
                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                // lets taps outside the popup also dismiss it
                boolean focusable = true;
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                popupView.findViewById(R.id.sendMessage).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                        final EditText edit =  (EditText) popupView.findViewById(R.id.respondEt);
                        String getMessage = edit.getText().toString();
                        String currentDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());

                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("users").document(senderID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    String randResID = UUID.randomUUID().toString();
                                    DocumentSnapshot documentSnapshot = task.getResult();
                                    ArrayList<String> resIDArray = (ArrayList<String>) documentSnapshot.get("responsesList");
                                    resIDArray.add(randResID);
                                    db.collection("users").document(senderID).update("responsesList", resIDArray);

                                    db.collection("users").document(firebaseAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                DocumentSnapshot ds = task.getResult();
                                                ArrayList<String> reIDArray = (ArrayList<String>) ds.get("responsesList");
                                                reIDArray.remove(responseID);
                                                db.collection("users").document(firebaseAuth.getCurrentUser().getUid()).update("responsesList", reIDArray);
                                                db.collection("responses").document(responseID).delete();

                                                Map<String, Object> responseMap = new HashMap<>();
                                                responseMap.put("date", currentDate);
                                                responseMap.put("invitationID", invID);
                                                responseMap.put("message", getMessage);
                                                responseMap.put("recipientID", senderID);
                                                responseMap.put("accepted", true);
                                                responseMap.put("senderID", firebaseAuth.getCurrentUser().getUid());

                                                db.collection("responses").document(randResID).set(responseMap);

                                                if (documentSnapshot.getData().get("token") != null && documentSnapshot.getData().get("token").toString() != "") {
                                                    FCMSend.pushNotification(context,
                                                            documentSnapshot.getData().get("token").toString(),
                                                            "New message!",
                                                            getMessage);
                                                }
                                                popupWindow.dismiss();
                                            }
                                        }
                                    });
                                }
                            }
                        });
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
        Button yesButton, noButton, sendButton, messageButton;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            nameTv = itemView.findViewById(R.id.name);
            dateTv = itemView.findViewById(R.id.date);
            yesButton = itemView.findViewById(R.id.yesResponse);
            noButton = itemView.findViewById(R.id.noResponse);
            sendButton = itemView.findViewById(R.id.submitMessage);
            messageButton = itemView.findViewById(R.id.messageSendBtn);
        }

//        void setDetails(Response inv) {
//            nameTv.setText(inv.getResponseID());
//            emailTv.setText(inv.getDate());
//        }
    }
}
