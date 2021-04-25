package com.example.dea.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.dea.R;
import com.example.dea.entity.DMU;
import com.example.dea.fragment.CCRModelViewFragment;
import com.example.dea.fragment.IO_BCCModelViewFragment;
import com.example.dea.fragment.OO_BCCModelViewFragment;
import com.example.dea.ui.main.PlaceholderFragment;

import java.util.ArrayList;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.CCR,R.string.IOBCC,R.string.OOBCC};
    private final Context mContext;
    private ArrayList<DMU> arrayList;
    private Fragment fragment;

    public SectionsPagerAdapter(Context context, FragmentManager fm,ArrayList<DMU> _arrayList) {
        super(fm);
        mContext = context;
        arrayList=_arrayList;
    }



    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        if (position == 0)
            fragment = CCRModelViewFragment.newInstance(arrayList);
        else
            fragment = IO_BCCModelViewFragment.newInstance(arrayList);
//        else
//            fragment= OO_BCCModelViewFragment.newInstance(arrayList);
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }
    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
        fragment=null;
    }
}