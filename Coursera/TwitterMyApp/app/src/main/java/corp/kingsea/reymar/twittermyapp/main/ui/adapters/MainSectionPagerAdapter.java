package corp.kingsea.reymar.twittermyapp.main.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by reyma on 2/07/2016.
 */

public class MainSectionPagerAdapter extends FragmentPagerAdapter {

    private String[]titles;
    private Fragment[]fragments;

    public MainSectionPagerAdapter(FragmentManager fm, String[] titles, Fragment[] fragments) {
        super(fm);
        this.titles = titles;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragments[position];
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return this.titles[position];
    }

    @Override
    public int getCount() {
        return this.fragments.length;
    }
}
