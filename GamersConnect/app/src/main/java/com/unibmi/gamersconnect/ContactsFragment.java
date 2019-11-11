package com.unibmi.gamersconnect;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactsFragment extends Fragment {
    Context cx;
    RecyclerView rv;
    boolean isNew;

    Button searchContact;

    View newMSGLayout;

    public ContactsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        isNew = true;

        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        searchContact = view.findViewById(R.id.search_contact_btn);
        //newMsgLayout = view.findViewById(R.id.new_msg_view);
        cx = getActivity().getApplicationContext();
        rv = view.findViewById(R.id.recyclerview);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(new SimpleRVAdapter(cx));
        searchContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newOrSend();
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * A Simple Adapter for the RecyclerView
     */
    public class SimpleRVAdapter extends RecyclerView.Adapter<SimpleViewHolder> {
        private String[] dataSource;

        public SimpleRVAdapter(Context cx) {

            dataSource = cx.getResources().getStringArray(R.array.testTexts);
        }

        @Override
        public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            View view = new ImageView(parent.getContext());
            view.setBackgroundColor(getResources().getColor(android.R.color.white));
            view.setLayoutParams(lp);
            view.setPaddingRelative(5, 5, 5, 5);
            SimpleViewHolder viewHolder = new SimpleViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull SimpleViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }


    }

    /**
     * A Simple ViewHolder for the RecyclerView
     */
    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView;
        }
    }

    public void newOrSend() {
        if (isNew) {
            searchContact.setText(R.string.send_text);
            //newMsgLayout.setVisibility(View.VISIBLE);
        }

    }
}