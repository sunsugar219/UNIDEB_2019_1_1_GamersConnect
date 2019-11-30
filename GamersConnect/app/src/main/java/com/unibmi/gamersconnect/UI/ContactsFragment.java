package com.unibmi.gamersconnect.UI;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.unibmi.gamersconnect.R;
import com.unibmi.gamersconnect.database.User;

public class ContactsFragment extends Fragment {
    Context cx;
    RecyclerView rv;
    boolean isNew;

    Button searchContact;

    View newMSGLayout;

    private DatabaseReference mDatabase;
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private LinearLayoutManager linearLayoutManager;
    private FirebaseRecyclerAdapter adapter;

    public ContactsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        isNew = true;
        mDatabase = FirebaseDatabase.getInstance().getReference();

        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        searchContact = view.findViewById(R.id.search_contact_btn);
        //newMsgLayout = view.findViewById(R.id.new_msg_view);
        super.onViewCreated(view, savedInstanceState);


        cx = getActivity().getApplicationContext();
        linearLayoutManager = new LinearLayoutManager(cx);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        rv = view.findViewById(R.id.recyclerviewContacts);
        rv.setLayoutManager(linearLayoutManager);


        fetch();

        mDatabase.child("users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String uid = user.getUid();
        mDatabase.child("users" + uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout root;
        ImageView mAuthorImage;
        TextView mAuthorText, mUserVenue, mUserDate, mUserDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.list_item_layout_contacts);
            mAuthorImage = itemView.findViewById(R.id.author_image_in_list);
            mAuthorText = itemView.findViewById(R.id.author_text_in_list);
           /* mUserVenue = itemView.findViewById(R.id.author_text_in_list);
            muserDate = itemView.findViewById(R.id.user_date);
            muserDescription = itemView.findViewById(R.id.user_description);*/

        }

        public void setImgContact(int imgIndex) {
            switch (imgIndex) {
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

        public void setTxtContact(String string) {
            mAuthorText.setText(string);
        }
    }

        /**
         * A Simple Adapter for the RecyclerView
         */
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
                    .child("users").child(uid);

            FirebaseRecyclerOptions<User> options =
                    new FirebaseRecyclerOptions.Builder<User>()
                            .setQuery(query, new SnapshotParser<User>() {
                                @NonNull
                                @Override
                                public User parseSnapshot(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.child("picIndex").getValue() != null) {
                                        return new User(snapshot.child("username").getValue().toString(),
                                                Integer.valueOf(snapshot.child("picIndex").getValue().toString()));
                                    } else {
                                        return new User(snapshot.child("username").getValue().toString(), 9);

                                    }

                                }
                            })
                            .build();

            adapter = new FirebaseRecyclerAdapter<User, ContactsFragment.ViewHolder>(options) {
                @Override
                public ContactsFragment.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.list_item_contact, parent, false);

                    return new ContactsFragment.ViewHolder(view);
                }


                @Override
                protected void onBindViewHolder(ContactsFragment.ViewHolder holder, final int position, User user) {
                    holder.setImgContact(user.getProfilePic());
                    holder.setTxtContact(user.getUsername());

                }
            };
            rv.setAdapter(adapter);
        }
    }

