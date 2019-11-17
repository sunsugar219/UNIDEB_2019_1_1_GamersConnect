package com.unibmi.gamersconnect.UI;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.unibmi.gamersconnect.R;


public class EditProfileFragment extends Fragment{
    private DatabaseReference mDatabase;
    public EditProfileFragment() {

        // Required empty public constructor

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        View v = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        // Inflate the layout for this fragment
        return v;
    }




}
