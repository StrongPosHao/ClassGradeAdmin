package ynu.zhanghao.classgradeadmin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.util.ArrayList;
import java.util.List;

import ynu.zhanghao.classgradeadmin.db.CourseContent;
import ynu.zhanghao.classgradeadmin.db.MyDatabaseHelper;
import ynu.zhanghao.classgradeadmin.fragments.ScoreFragment;
import ynu.zhanghao.classgradeadmin.fragments.SettingsFragment;
import ynu.zhanghao.classgradeadmin.fragments.TeacherClassFragment;
import ynu.zhanghao.classgradeadmin.fragments.dummy.DummyContent;

public class MainActivity extends AppCompatActivity
        implements SettingsFragment.OnListFragmentInteractionListener,
        TeacherClassFragment.OnListFragmentInteractionListener,
        ScoreFragment.OnListFragmentInteractionListener,
        TemporaryFragment.OnListFragmentInteractionListener {

    MyDatabaseHelper dbHelper;

    private BottomNavigationBar navigationBar;

    private DrawerLayout drawerLayout;

    private Toolbar toolbar;

    private List<Fragment> fragmentList;

    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createDatabase();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("title");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        }
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationBar = findViewById(R.id.navBar);
        navigationBar.addItem(new BottomNavigationItem(R.drawable.ic_school_black_24dp, "教学班"))
                .addItem(new BottomNavigationItem(R.drawable.ic_event_note_black, "成绩"))
                .addItem(new BottomNavigationItem(R.drawable.ic_settings_black_24dp, "设置"));
        navigationBar.setFirstSelectedPosition(0);
        navigationBar.initialise();

        navigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                ShowFragment(position);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
        fragmentList = new ArrayList<>();
        fragmentList.add(TeacherClassFragment.newInstance(1));
        fragmentList.add(ScoreFragment.newInstance(1));
        fragmentList.add(SettingsFragment.newInstance(1));

        fragmentManager = getSupportFragmentManager();

        ShowFragment(0);
    }

    private void ShowFragment(int position) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentList.get(position);
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }

    public void createDatabase() {
        dbHelper = new MyDatabaseHelper(this, "CourseAdmin.db", null, 1);
        dbHelper.getWritableDatabase();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
        }
        return true;
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }

    @Override
    public void onListFragmentInteraction(CourseContent.CourseItem courseItem) {

    }

    @Override
    public void onListFragmentInteraction(ynu.zhanghao.classgradeadmin.dummy.DummyContent.DummyItem item) {

    }
}
