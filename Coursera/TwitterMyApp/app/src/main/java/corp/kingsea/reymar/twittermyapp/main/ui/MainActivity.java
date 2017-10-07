package corp.kingsea.reymar.twittermyapp.main.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.twitter.sdk.android.Twitter;

import butterknife.Bind;
import butterknife.ButterKnife;
import corp.kingsea.reymar.twittermyapp.hashtags.ui.HashtagsFragment;
import corp.kingsea.reymar.twittermyapp.LoginActivity;
import corp.kingsea.reymar.twittermyapp.R;
import corp.kingsea.reymar.twittermyapp.images.ui.ImagesFragment;
import corp.kingsea.reymar.twittermyapp.main.ui.adapters.MainSectionPagerAdapter;

public class MainActivity extends AppCompatActivity {
//traigo estos elementos de activitymain.xml
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tabs)
    TabLayout tabs;
    @Bind(R.id.container)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);//habilito el actionbar de activitymain.xml
        setupAdapter();
    }

    private void setupAdapter() {
        Fragment[]fragments = new Fragment[]{new ImagesFragment(), new HashtagsFragment()};
        String[] titles = new String[]{getString(R.string.main_header_images), getString(R.string.main_header_hashtags)};
        MainSectionPagerAdapter adapter = new MainSectionPagerAdapter(getSupportFragmentManager(),
                titles, fragments);

        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {//si el item seleccionado es el asignado en action_logout
            logout();
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        Twitter.logOut();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
