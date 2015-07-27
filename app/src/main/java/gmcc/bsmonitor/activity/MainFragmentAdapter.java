package gmcc.bsmonitor.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by lijun on 15/7/24.
 */
public class MainFragmentAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragments;
    private List<String> labels;

    public MainFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> labelList) {
        super(fm);
        this.fragments = fragmentList;
        this.labels = labelList;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return labels.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
