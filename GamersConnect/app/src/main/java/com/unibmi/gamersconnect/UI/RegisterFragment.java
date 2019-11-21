package com.unibmi.gamersconnect.UI;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
public class RegisterFragment extends Fragment {
    private DatabaseReference mDatabase;
    private EditText eMailEdit, passwordEdit;
    private FirebaseAuth mAuth;


    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        View v = inflater.inflate(R.layout.fragment_register, container, false);
        eMailEdit = v.findViewById(R.id.emai_edit);
        passwordEdit = v.findViewById(R.id.passwrd_edit);
        Button button = v.findViewById(R.id.register_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
                //Navigation.findNavController(v).navigate(R.id.wallFragment);
            }
        });
        // Inflate the layout for this fragment
        return v;
    }

   /* private void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email);

        mDatabase.child("users").child(userId).setValue(user);
    }*/

    private void signUp() {
        String email = eMailEdit.getText().toString().trim();
        String password = passwordEdit.getText().toString().trim();

        if (validateForm(email, password)) {
            //showProgressDialog();
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                   // hideProgressDialog();
                    if (task.isSuccessful()) {
                        onAuthSuccess(task.getResult().getUser());
                    } else {
                        Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
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
        Navigation.findNavController(getView()).navigate(R.id.editProfileFragment);

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
