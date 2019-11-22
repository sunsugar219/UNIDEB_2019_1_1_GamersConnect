package com.unibmi.gamersconnect.UI;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.unibmi.gamersconnect.R;
import com.unibmi.gamersconnect.database.Message;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MessageViewHolder> {

private Context mContext;
private ArrayList<Message> mMessageList;
    private int mNumberItems;


public MyAdapter(Context mContext, ArrayList<Message> mMessageList) {
        this.mContext = mContext;
        this.mMessageList = mMessageList;
        this.mContext = mContext;
}


    // Override our 3 functions
    // onCreateViewHolder()
    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Log.v("onCreateViewHolder", "onCreateViewHolder is called !");
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.list_item_message;

        // Inflate our new item view using a LayoutInflater. It takes the ID of layout in xml.
        // Then --> inflates or converts this collection of view groups and views.
        LayoutInflater inflater = LayoutInflater.from(context);


        // Set to false, so that the inflated layout will not be
        // immediately attached to its parent viewgroup.
        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        MessageViewHolder viewHolder = new MessageViewHolder(view);

        return viewHolder;

    }


    //onBindViewHolder()
    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {

        // Get the data model based on position

        Message message = mMessageList.get(position);
        // Set item views based on your views and data model
        TextView textView = holder.mMessageVenue;
        textView.setText(message.getVenue());

    }

    //getItemCount() : returns the mNumberItems var
    @Override
    public int getItemCount() {
        mNumberItems = mMessageList.size();
        return mNumberItems;
    }

}


class MessageViewHolder extends RecyclerView.ViewHolder {

    ImageView mAuthorImage;
    TextView mAuthorText, mMessageVenue, mMessageDate, mMessageDescription;

    public MessageViewHolder(View itemView) {
        super(itemView);

        mAuthorImage = itemView.findViewById(R.id.author_image_in_list);
        mAuthorText = itemView.findViewById(R.id.author_text_in_list);
        mMessageVenue = itemView.findViewById(R.id.message_venue_string);
        mMessageDate = itemView.findViewById(R.id.message_date);
        mMessageDescription = itemView.findViewById(R.id.message_description);
    }
}

