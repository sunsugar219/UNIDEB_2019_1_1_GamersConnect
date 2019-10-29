package com.unibmi.gamersconnect;
import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * adapter for viewpager in Main Activity
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;

    public ViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new LogInFragment();
        } else if (position == 1){
            return new RegisterFragment();
        } else {
            return new WallFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0){
            return mContext.getString(R.string.title_log_in);
        } else if (position == 1){
            return mContext.getString(R.string.title_register);
        }else {
            return mContext.getString(R.string.title_wall);
        }
    }
}