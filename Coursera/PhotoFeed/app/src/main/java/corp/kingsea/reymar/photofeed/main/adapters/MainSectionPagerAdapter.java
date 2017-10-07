package corp.kingsea.reymar.photofeed.main.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * se va a usar este adaptador para menejar los fragmentos
 */

public class MainSectionPagerAdapter extends FragmentPagerAdapter {

    private String[]titles;
    private Fragment[]fragments;
    //el constructor tiene un arreglo de titulos y otro de fragmentos
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
