package com.unibmi.gamersconnect.UI;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.unibmi.gamersconnect.R;
import com.unibmi.gamersconnect.database.Message;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class EditProfileFragment extends Fragment{
    private DatabaseReference mDatabase;
    public ImageView pic1, pic2, pic3, pic4, pic5, pic6, pic7, pic8, pic9, big;
    private int[] imageArray = new int[]{R.drawable.pic1, R.drawable.pic2, R.drawable.pic3,
            R.drawable.pic4, R.drawable.pic5, R.drawable.pic6, R.drawable.pic7, R.drawable.pic8,
            R.drawable.pic9};
    private Button avatarChange, nameChange, emailChange, pwdChange;
    Map<String, Object> pic = new HashMap<>();
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    public EditProfileFragment() {

        // Required empty public constructor

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        View v = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        pic1 = v.findViewById(R.id.toPickPic1);
        pic2 = v.findViewById(R.id.toPickPic2);
        pic3 = v.findViewById(R.id.toPickPic3);
        pic4 = v.findViewById(R.id.toPickPic4);
        pic5 = v.findViewById(R.id.toPickPic5);
        pic6 = v.findViewById(R.id.toPickPic6);
        pic7 = v.findViewById(R.id.toPickPic7);
        pic8 = v.findViewById(R.id.toPickPic8);
        pic9 = v.findViewById(R.id.toPickPic9);
        big = v.findViewById(R.id.imageView_edit_profile);
        avatarChange = v.findViewById(R.id.button_avatar_change);
        nameChange = v.findViewById(R.id.button_change_name);
        emailChange = v.findViewById(R.id.button_change_email);
        pwdChange = v.findViewById(R.id.button_change_pwd);

        Query query = FirebaseDatabase.getInstance()
                .getReference("users");
//                .child("users").child(uid);

        final String uid = user.getUid();
        query.orderByKey().equalTo(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()){
                    Iterator<DataSnapshot> iter = dataSnapshot.getChildren().iterator();
                    while (iter.hasNext()){
                        DataSnapshot snap = iter.next();
                        Long picture = (Long)(snap.child("profilePic").getValue());
                        Integer picIndex = picture != null ? picture.intValue() : null;
                        Log.i("SetBigResource:", String.valueOf(picIndex));
                        if (picIndex<1 || picIndex >9){
                            picIndex = 1;
                            big.setImageResource(imageArray[picIndex-1]);

                        }else {
                            big.setImageResource(imageArray[picIndex-1]);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ImageView[] avatars = {pic1, pic2, pic3, pic4, pic5, pic6, pic7, pic8, pic9};
        for (int j=0;j<avatars.length;j++) {
            avatars[j].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("imageResource: ", String.valueOf(view.getId()));
                    //int id = view.getId();
                    for (int i = 0; i < avatars.length; i++) {
                        if (view == avatars[i]) {
                            imageInput(i);
                            Log.i("imageIndex: ", String.valueOf(i));
                        }
                    }
                }
            });
        }

        avatarChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference actUser = mDatabase.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                Log.i("avatarButton: ", String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getUid()));
                actUser.child("profilePic").setValue(pic.get("profilePic"));
            }
        });
        nameChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText et = view.findViewById(R.id.editText_profile_personName);
                if(et.getText().toString() !=""){
                DatabaseReference actUser = mDatabase.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                Log.i("avatarButton: ", String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getUid()));
                actUser.child("username").setValue(et.getText().toString());
                }
            }
        });
        emailChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText et = view.findViewById(R.id.editText_email_edit_profile);
                if (et.getText().toString() != "") {
                    DatabaseReference actUser = mDatabase.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    Log.i("avatarButton: ", String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getUid()));
                    actUser.child("email").setValue(et.getText().toString());
                }
            }
        });
        pwdChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText et = view.findViewById(R.id.editText_pwd_change);
                if (et.getText().toString() != "") {
                    DatabaseReference actUser = mDatabase.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    Log.i("avatarButton: ", String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getUid()));
                    actUser.child("password").setValue(et.getText().toString());
                }
            }
        });

    }

    private void imageInput (int index){
        big.setImageResource(imageArray[index]);
        pic.put("profilePic", index+1);
    }
}
