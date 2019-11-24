package com.unibmi.gamersconnect.UI;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.unibmi.gamersconnect.MainActivity;
import com.unibmi.gamersconnect.R;
import com.unibmi.gamersconnect.database.Message;

import java.util.Iterator;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 *  interface
 * to handle interaction events.
 * Use the factory method to
 * create an instance of this fragment.
 */
public class WallFragment extends Fragment {
    private DatabaseReference mDatabase;
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private MainActivity MA;
    Context cx;
    RecyclerView rv;
    boolean isNew;
    Button newSend;
    View newMsgLayout;

    String date;
    String venue;
    String description;

    EditText dateInput;
    EditText venueInput;
    EditText descriptionInput;

    private LinearLayoutManager linearLayoutManager;
    private FirebaseRecyclerAdapter adapter;

    public WallFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        isNew = true;
        mDatabase = FirebaseDatabase.getInstance().getReference();

        return inflater.inflate(R.layout.fragment_wall, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            MA = (MainActivity) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement MyInterface");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        newSend = view.findViewById(R.id.new_send_btn);
        newMsgLayout = view.findViewById(R.id.new_msg_view);
        dateInput = view.findViewById(R.id.msg_date);
        venueInput = view.findViewById(R.id.msg_place);
        descriptionInput = view.findViewById(R.id.msg_description);
        cx = getActivity().getApplicationContext();
        linearLayoutManager = new LinearLayoutManager(cx);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        rv = view.findViewById(R.id.recyclerview);
        rv.setLayoutManager(linearLayoutManager);
        //rv.setHasFixedSize(true);

        fetch();
        newSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("is it new? ", String.valueOf(isNew));

                newOrSend(view);
            }
        });
        MA.disableDrawer();
        // Attach a listener to read the data at our posts reference
        mDatabase.child("user_messages" + user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Message message = dataSnapshot.getValue(Message.class);
                System.out.println(message);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        mDatabase.child("messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Message message = dataSnapshot.getValue(Message.class);
                System.out.println(message);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        /*mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                //Message newMessage = dataSnapshot.getValue(Message.class);
                /*System.out.println("Message: " + newMessage.expectedDate);
                System.out.println("Venue: " + newMessage.venue);
                System.out.println("Description: " + newMessage.description);*/
                //System.out.println("Message: "+ newMessage);
               /* System.out.println("Previous Post ID: " + prevChildKey);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });*/

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String uid = user.getUid();
        mDatabase.child("user-messages"+uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Message message = dataSnapshot.getValue(Message.class);
                if (dataSnapshot.exists()) {
                    Log.i("onresume: ", "datasnapshot exists");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public void newOrSend(View view) {
        if (isNew) {
            newSend.setText(R.string.send_text);
            newMsgLayout.setVisibility(View.VISIBLE);
            isNew = false;
        } else {
            date = dateInput.getText().toString().trim();
            venue = venueInput.getText().toString().trim();
            description = descriptionInput.getText().toString().trim();
            //submitMessage();
            writeNewMessage(date, venue, description);
            isNew = true;
            newSend.setText(R.string.new_post);
            newMsgLayout.setVisibility(View.GONE);
        }
    }

    private void writeNewMessage(final String date, final String venue, final String description) {
        final String uid = user.getUid();
        final String uemail = user.getEmail();
        Query query = FirebaseDatabase.getInstance()
                .getReference("users");
//                .child("users").child(uid);


                      query.orderByKey().equalTo(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                          @Override
                          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                              if (dataSnapshot.hasChildren()){
                                  Iterator<DataSnapshot> iter = dataSnapshot.getChildren().iterator();
                                  while (iter.hasNext()){
                                      DataSnapshot snap = iter.next();
                                      Long picture = (Long)(snap.child("profilePic").getValue());
                                      Integer picIndex = picture != null ? picture.intValue() : null;
                                      Log.i("Wall picindex in new:", String.valueOf(picIndex));
                                      if (picIndex<1 || picIndex >9){
                                          picIndex = 1;
                                          if (validateForm()) {
                                              Message message = new Message(uid, picIndex, uemail, date, venue, description);
                                              mDatabase.child("messages").push().setValue(message);
                                              mDatabase.child("user_messages" + uid).push().setValue(message);
                                          }
                                      }else {
                                          if (validateForm()) {
                                              Message message = new Message(uid, picIndex, uemail, date, venue, description);
                                              mDatabase.child("messages").push().setValue(message);
                                              mDatabase.child("user_messages" + uid).push().setValue(message);
                                          }
                                      }
                                  }
                              }
                          }

                          @Override
                          public void onCancelled(@NonNull DatabaseError databaseError) {

                          }
                      });
        /*int picIndex = 1;
        if (validateForm()) {
            Message message = new Message(uid, picIndex, uemail, date, venue, description);
            mDatabase.child("messages").push().setValue(message);
            mDatabase.child("user_messages" + uid).push().setValue(message);
        }*/

        mDatabase.child("user-messages"+uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Message message = dataSnapshot.getValue(Message.class);
                if (dataSnapshot.exists()) {
                    Log.i("onresume: ", "datasnapshot exists");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private boolean validateForm() {
        if (TextUtils.isEmpty(date)) {
            dateInput.setError(getString(R.string.required));
            return false;
        } else if (TextUtils.isEmpty(venue)) {
            venueInput.setError(getString(R.string.required));
            return false;
        } else if (TextUtils.isEmpty(description)) {
            description = "";
            return true;
        } else {
            dateInput.setError(null);
            venueInput.setError(null);
            return true;
        }
    }

    private void setEditingEnabled(boolean enabled) {
        dateInput.setEnabled(enabled);
        venueInput.setEnabled(enabled);
        descriptionInput.setEnabled(enabled);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout root;
        ImageView mAuthorImage;
        TextView mAuthorText, mMessageVenue, mMessageDate, mMessageDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.list_item_layout);
            mAuthorImage = itemView.findViewById(R.id.author_image_in_list);
            mAuthorText = itemView.findViewById(R.id.author_text_in_list);
            mMessageVenue = itemView.findViewById(R.id.message_venue_string);
            mMessageDate = itemView.findViewById(R.id.message_date);
            mMessageDescription = itemView.findViewById(R.id.message_description);

        }

        public void setImgAuthor(int imgIndex) {
            switch (imgIndex){
                case 1:
                    mAuthorImage.setImageResource(R.drawable.pic1);
                    break;
                case 2:
                    mAuthorImage.setImageResource(R.drawable.pic2);
                    break;
                case 3:
                    mAuthorImage.setImageResource(R.drawable.pic3);
                    break;
                case 4:
                    mAuthorImage.setImageResource(R.drawable.pic4);
                    break;
                case 5:
                    mAuthorImage.setImageResource(R.drawable.pic5);
                    break;
                case 6:
                    mAuthorImage.setImageResource(R.drawable.pic6);
                    break;
                case 7:
                    mAuthorImage.setImageResource(R.drawable.pic7);
                    break;
                case 8:
                    mAuthorImage.setImageResource(R.drawable.pic8);
                    break;
                case 9:
                    mAuthorImage.setImageResource(R.drawable.pic9);
                    break;
                default:
                        mAuthorImage.setImageResource(R.drawable.pic9);
                        break;

            }
        }

        public void setTxtAuthor(String string) {
            mAuthorText.setText(string);
        }

        public void setTxtVenue(String string) {
            mMessageVenue.setText(string);
        }

        public void setTxtDate(String string) {
            mMessageDate.setText(string);
        }

        public void setTxtDesc(String string) {
            mMessageDescription.setText(string);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private void fetch() {
        Log.i("fetch: ", "right after start");
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String uid = user.getUid();

        //egyelőre csak a saját üziket mutatja, mivel a kontaktok még nincsenek készen
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("user_messages" + uid);

        FirebaseRecyclerOptions<Message> options =
                new FirebaseRecyclerOptions.Builder<Message>()
                        .setQuery(query, new SnapshotParser<Message>() {
                            @NonNull
                            @Override
                            public Message parseSnapshot(@NonNull DataSnapshot snapshot) {
                                if (snapshot.child("picIndex").getValue() != null) {
                                    return new Message(uid,
                                            Integer.valueOf(snapshot.child("picIndex").getValue().toString()),
                                            snapshot.child("author").getValue().toString(),
                                            snapshot.child("date").getValue().toString(),
                                            snapshot.child("venue").getValue().toString(),
                                            snapshot.child("description").getValue().toString());
                                } else{
                                    return new Message(uid, 9,
                                            snapshot.child("author").getValue().toString(),
                                            snapshot.child("date").getValue().toString(),
                                            snapshot.child("venue").getValue().toString(),
                                            snapshot.child("description").getValue().toString());
                                }

                            }
                        })
                        .build();

        adapter = new FirebaseRecyclerAdapter<Message, ViewHolder>(options) {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_message, parent, false);

                return new ViewHolder(view);
            }


            @Override
            protected void onBindViewHolder(ViewHolder holder, final int position, Message message) {
                holder.setImgAuthor(message.getPicIndex());
                holder.setTxtAuthor(message.getAuthor());
                holder.setTxtDate(message.getDate());
                holder.setTxtVenue(message.getVenue());
                holder.setTxtDesc(message.getDescription());
            }
        };
        rv.setAdapter(adapter);
    }


}

