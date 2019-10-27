package com.unibmi.gamersconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_log_in);
        TextView tv = findViewById(R.id.offer_text);
        String text = "Not singed up yet? Register.";
        SpannableString sstr = new SpannableString(text);
        ClickableSpan cstr = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {

                Toast.makeText(MainActivity.this, "Váltás", Toast.LENGTH_SHORT).show();

            }
        };
        sstr.setSpan(cstr,19, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(sstr);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
