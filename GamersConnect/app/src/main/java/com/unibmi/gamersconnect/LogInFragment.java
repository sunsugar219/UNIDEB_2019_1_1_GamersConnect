package com.unibmi.gamersconnect;


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


/**
 * A simple {@link Fragment} subclass.
 */
public class LogInFragment extends Fragment {


    public LogInFragment() {

        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_log_in, container, false);
        Button button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_logInFragment_to_wallFragment);
            }
        });
        TextView tv = view.findViewById(R.id.offer_text);
        String str = (String )tv.getText();
        SpannableString sstr = new SpannableString(str);
        sstr.setSpan(str,str.length()-10,str.length()-1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ClickableSpan cstr = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Navigation.findNavController(widget).navigate(R.id.action_logInFragment_to_registerFragment);
            }
        };
        sstr.setSpan(cstr,str.length()-10,str.length()-1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(sstr);
        tv.setMovementMethod(LinkMovementMethod.getInstance());

        // Inflate the layout for this fragment
        return view ;
    }

}
