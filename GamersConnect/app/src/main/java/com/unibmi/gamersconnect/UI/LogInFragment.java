package com.unibmi.gamersconnect.UI;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.unibmi.gamersconnect.R;
import com.unibmi.gamersconnect.database.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class LogInFragment extends Fragment {

    EditText passwordEdit, eMailEdit;
    private FirebaseAuth mAuth;

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
                //Navigation.findNavController(v).navigate(R.id.action_logInFragment_to_wallFragment);
                signIn();
            }
        });
        eMailEdit = view.findViewById(R.id.email_edit);
        passwordEdit = view.findViewById(R.id.password_edit);
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
        mAuth = FirebaseAuth.getInstance();

        // Inflate the layout for this fragment
        return view ;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mAuth.getCurrentUser() != null) {
            onAuthSuccess(mAuth.getCurrentUser());
        }
    }

    private void onAuthSuccess(FirebaseUser firebaseUser) {
        String email = firebaseUser.getEmail();
        String username = email;
        if (email != null && email.contains("@")) {
            username = email.split("@")[0];
        }
        String password = passwordEdit.getText().toString().trim();

        User user = new User(username, email, password);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(firebaseUser.getUid()).setValue(user);
        Navigation.findNavController(getView()).navigate(R.id.wallFragment);

    }

    private void signIn() {
        String email = eMailEdit.getText().toString().trim();
        String password = passwordEdit.getText().toString().trim();

        if (validateForm(email, password)) {
           // showProgressDialog();
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                   // hideProgressDialog();
                    if (task.isSuccessful()) {
                        onAuthSuccess(task.getResult().getUser());
                    } else {
                        Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }



    private boolean validateForm(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            eMailEdit.setError(getString(R.string.required));
            return false;
        } else if (TextUtils.isEmpty(password)) {
            eMailEdit.setError(getString(R.string.required));
            return false;
        } else {
            eMailEdit.setError(null);
            eMailEdit.setError(null);
            return true;
        }
    }

}




    /*@Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_sign_in:
                signIn();
                break;
            case R.id.button_sign_up:
                signUp();
                break;
        }
    }*/
