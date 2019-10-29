package com.unibmi.gamersconnect;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<TextViewHolder> {

private Context mContext;
private int[] mTextList;

public MyAdapter(Context mContext, int[] mPlaceList) {
        this.mContext = mContext;
        this.mTextList = mTextList;
        }

@Override
public TextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_custom_layout,
        parent, false);
        return new TextViewHolder(view);
        }

@Override
public void onBindViewHolder(final TextViewHolder holder, int position) {
        holder.mText.setText(mTextList[position]);
        holder.mText.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
        Intent mIntent = new Intent(mContext, DetailFragment.class);
        mIntent.putExtra("Text", mTextList[holder.getAdapterPosition()]);
        mContext.startActivity(mIntent);
        }
        });
        }

@Override
public int getItemCount() {
        return mTextList.length;
        }
        }


class TextViewHolder extends RecyclerView.ViewHolder {

    TextView mText;

    public TextViewHolder(View itemView) {
        super(itemView);

        mText = itemView.findViewById(R.id.tvText);
    }
}



