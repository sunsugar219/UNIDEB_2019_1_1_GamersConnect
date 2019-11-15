package com.unibmi.gamersconnect.UI;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.unibmi.gamersconnect.R;
import com.unibmi.gamersconnect.database.Message;
import com.unibmi.gamersconnect.database.User;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 *  interface
 * to handle interaction events.
 * Use the factory method to
 * create an instance of this fragment.
 */
public class WallFragment extends Fragment{
    private DatabaseReference mDatabase;
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

    public WallFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        isNew = true;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        return inflater.inflate(R.layout.fragment_wall, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        newSend = view.findViewById(R.id.new_send_btn);
        newMsgLayout = view.findViewById(R.id.new_msg_view);
        dateInput = view.findViewById(R.id.msg_date);
        venueInput = view.findViewById(R.id.msg_place);
        descriptionInput = view.findViewById(R.id.msg_description);
        cx = getActivity().getApplicationContext();
        rv = view.findViewById(R.id.recyclerview);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(new SimpleRVAdapter(cx));
        newSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("is it new? ", String.valueOf(isNew));

                newOrSend(view);
            }
        });

        // Attach a listener to read the data at our posts reference
        mDatabase.addValueEventListener(new ValueEventListener() {
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

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                //Message newMessage = dataSnapshot.getValue(Message.class);
                /*System.out.println("Message: " + newMessage.expectedDate);
                System.out.println("Venue: " + newMessage.venue);
                System.out.println("Description: " + newMessage.description);*/
                //System.out.println("Message: "+ newMessage);
                System.out.println("Previous Post ID: " + prevChildKey);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * A Simple Adapter for the RecyclerView
     */
    public class SimpleRVAdapter extends RecyclerView.Adapter<SimpleViewHolder> {
        private String[] dataSource;
        public SimpleRVAdapter(Context cx){

            dataSource = cx.getResources().getStringArray(R.array.testTexts);
        }

        @Override
        public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            View view = new TextView(parent.getContext());
            view.setBackgroundColor(getResources().getColor(android.R.color.white));
            view.setLayoutParams(lp);
            view.setPaddingRelative(5, 5, 5, 5);
            SimpleViewHolder viewHolder = new SimpleViewHolder(view);
            viewHolder.textView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(SimpleViewHolder holder, int position) {
            holder.textView.setText(dataSource[position]);
        }

        @Override
        public int getItemCount() {
            return dataSource.length;
        }

    }

    /**
     * A Simple ViewHolder for the RecyclerView
     */
    public static class SimpleViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public SimpleViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }
    }

    public void newOrSend(View view){
        if (isNew){
            newSend.setText(R.string.send_text);
            newMsgLayout.setVisibility(View.VISIBLE);
            isNew = false;
        }else{
            isNew = true;
            newSend.setText(R.string.new_post);
            newMsgLayout.setVisibility(View.GONE);
            date = dateInput.getText().toString();
            venue = venueInput.getText().toString();
            description = descriptionInput.getText().toString();
            writeNewMessage(date, venue, description);
                }
        }

    private void writeNewMessage(String date, String venue, String description) {
        Message message = new Message(date, venue, description);

        mDatabase.child("messages").push().setValue(message);
    }

    }

    /*RecyclerView mRecyclerView;
    int[] mTextList;

    private OnFragmentInteractionListener mListener;

    public WallFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WallFragment.
     */
    // TODO: Rename and change types and number of parameters
    /*public static WallFragment newInstance(String param1, String param2) {
        WallFragment fragment = new WallFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wall, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

   /* @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), 1);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        mTextList = new int[]{R.string.testText1,
                R.string.testText2,
                R.string.testText3,
                R.string.testText4,
                R.string.testText5,
                R.string.testText6,
                R.string.testText7,
                R.string.testText8,
                R.string.testText9,
                R.string.testText10,
                R.string.testText11,
                R.string.testText12,
                R.string.testText13,
                R.string.testText14,
                R.string.testText15,
                R.string.testText16};

        MyAdapter myAdapter = new MyAdapter(getActivity(), mTextList);
        mRecyclerView.setAdapter(myAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}*/

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    /*interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/

