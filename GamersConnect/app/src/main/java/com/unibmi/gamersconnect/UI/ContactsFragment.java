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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.unibmi.gamersconnect.database.Contact;
import com.unibmi.gamersconnect.database.Message;
import com.unibmi.gamersconnect.database.User;

import java.util.Iterator;

public class ContactsFragment extends Fragment {
    Context cx;
    RecyclerView rv;
    boolean search;

    Button searchContact;


    private DatabaseReference mDatabase;
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private LinearLayoutManager linearLayoutManager;
    private FirebaseRecyclerAdapter adapter;
    public ContactsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        search = true;
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
        ((Button)view.findViewById(R.id.search_contact_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchOrFollow();
            }
        });

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
        ImageView mContactImage;
        TextView mContactText;

        public ViewHolder(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.list_item_layout_contacts);
            mContactImage = itemView.findViewById(R.id.contact_image_in_list);
            mContactText = itemView.findViewById(R.id.contact_text_in_list);


        }

        public void setImgContact(int imgIndex) {
            switch (imgIndex) {
                case 1:
                    mContactImage.setImageResource(R.drawable.pic1);
                    break;
                case 2:
                    mContactImage.setImageResource(R.drawable.pic2);
                    break;
                case 3:
                    mContactImage.setImageResource(R.drawable.pic3);
                    break;
                case 4:
                    mContactImage.setImageResource(R.drawable.pic4);
                    break;
                case 5:
                    mContactImage.setImageResource(R.drawable.pic5);
                    break;
                case 6:
                    mContactImage.setImageResource(R.drawable.pic6);
                    break;
                case 7:
                    mContactImage.setImageResource(R.drawable.pic7);
                    break;
                case 8:
                    mContactImage.setImageResource(R.drawable.pic8);
                    break;
                default:
                    mContactImage.setImageResource(R.drawable.pic9);
                    break;

            }
        }

        public void setTxtContact(String string) {
            mContactText.setText(string);
        }
    }

        /**
         * A Simple Adapter for the RecyclerView
         */
        @Override
        public void onStart() {
            super.onStart();
            fetch();
            adapter.startListening();

        }

        @Override
        public void onStop() {
            super.onStop();
            adapter.stopListening();
        }

        private void add(String follow){
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            final String uid = user.getUid();
            if(follow!="") {
                final Query query = FirebaseDatabase.getInstance().getReference().child("users").orderByChild("username").equalTo(follow);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            String CUid = dataSnapshot.getChildren().iterator().next().getKey();
                            Contact contact = new Contact(uid, CUid);
                            mDatabase.child("contacts").push().setValue(contact);
                        }
                        else{
                            Toast.makeText(cx, "Nincs ilyen felhasználó!", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(cx,"Error!",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
        public void searchOrFollow(){
            if(search){
                this.getView().findViewById(R.id.contactLayout).setVisibility(getView().VISIBLE);
                search = false;
                ((Button) this.getView().findViewById(R.id.search_contact_btn)).setText("Follow");
            }
            else{
                this.getView().findViewById(R.id.contactLayout).setVisibility(getView().GONE);
                search = true;
                String follow = ((EditText) this.getView().findViewById(R.id.contact_name)).getText().toString();
                ((Button) this.getView().findViewById(R.id.search_contact_btn)).setText("Search");
                add(follow);
            }
        }
        private void fetch() {
            Log.i("contact fetch: ", "right after start");
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            final String uid = user.getUid();
            final String[] username = {""};
            Query usernamequery = FirebaseDatabase.getInstance()
                    .getReference("users");
            usernamequery.equalTo(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChildren()){
                            username[0] = dataSnapshot.child("username").getValue().toString();
                        }
                    }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            Query query = mDatabase.child("contact").orderByChild("user").equalTo(username[0]);

            FirebaseRecyclerOptions<Contact> options =
                    new FirebaseRecyclerOptions.Builder<Contact>()
                            .setQuery(query, new SnapshotParser<Contact>() {
                                @NonNull
                                @Override
                                public Contact parseSnapshot(@NonNull DataSnapshot snapshot) {
                                    return new Contact(snapshot.child("uid").getValue().toString(),
                                            snapshot.child("fid").getValue().toString());
                                }
                                })
                            .build();

            adapter = new FirebaseRecyclerAdapter<Contact, ViewHolder>(options) {
                @Override
                public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.list_item_contact, parent, false);

                    return new ContactsFragment.ViewHolder(view);
                }


                @Override
                protected void onBindViewHolder(ViewHolder holder, final int position, Contact contact) {
                    holder.setTxtContact(contact.fid);
                    holder.setImgContact(1);

                }
            };
            rv.setAdapter(adapter);
        }
    }

