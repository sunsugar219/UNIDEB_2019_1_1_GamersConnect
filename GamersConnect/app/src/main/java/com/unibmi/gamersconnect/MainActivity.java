package com.unibmi.gamersconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    FragmentPagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*TextView tv = findViewById(R.id.offer_text);
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
        tv.setMovementMethod(LinkMovementMethod.getInstance());*/


        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);
        adapterViewPager = new ViewPagerAdapter(this, getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itemProfile:
                Toast.makeText(getApplicationContext(),"Item 1 Selected",Toast.LENGTH_LONG).show();
                return true;
            case R.id.itemWall:
                Toast.makeText(getApplicationContext(),"Item 2 Selected",Toast.LENGTH_LONG).show();
                return true;
            case R.id.itemContacts:
                Toast.makeText(getApplicationContext(),"Item 3 Selected",Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}