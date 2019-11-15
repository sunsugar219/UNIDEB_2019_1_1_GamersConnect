package com.unibmi.gamersconnect.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.unibmi.gamersconnect.R;

public class DetailFragment extends Fragment {

    TextView mText;


    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mText = (TextView)getView().findViewById(R.id.detailTextView);
        Bundle mBundle = getActivity().getIntent().getExtras();
        if(mBundle != null){
            mText.setText(mBundle.getInt("Text"));
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

}
