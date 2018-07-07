package ynu.zhanghao.classgradeadmin;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.util.ArrayList;
import java.util.List;

import ynu.zhanghao.classgradeadmin.db.CourseItem;
import ynu.zhanghao.classgradeadmin.db.MyDatabaseHelper;
import ynu.zhanghao.classgradeadmin.db.StudentScoreItem;
import ynu.zhanghao.classgradeadmin.db.TeacherContent;
import ynu.zhanghao.classgradeadmin.fragments.ScoreFragment;
import ynu.zhanghao.classgradeadmin.fragments.SettingsFragment;
import ynu.zhanghao.classgradeadmin.fragments.TeacherClassFragment;

public class MainActivity extends AppCompatActivity
        implements SettingsFragment.OnListFragmentInteractionListener,
        TeacherClassFragment.OnListFragmentInteractionListener,
        ScoreFragment.OnListFragmentInteractionListener {

    MyDatabaseHelper dbHelper;

    private SQLiteDatabase db;

    private BottomNavigationBar navigationBar;

    private DrawerLayout drawerLayout;

    private Toolbar toolbar;

    private List<Fragment> fragmentList;

    private List<CourseItem> courseItemList;

    private List<StudentScoreItem> studentScoreItemList;

    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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


        createDatabase();
        insertStudentData();
        insertCourseData();
        insertStudentCourseData();
        courseItemList = listAllCourseData();
        TeacherClassFragment.setCourseItemList(courseItemList);

        studentScoreItemList = listAllStudentScore();
        Log.d("D", Integer.toString(courseItemList.size()));
        ScoreFragment.setStudentScoreItems(studentScoreItemList);


//        List<TeacherContent.TeacherItem> teacherItemList = listAllData();
//        SettingsFragment.setTeacherList(teacherItemList);
    }

    private void ShowFragment(int position) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentList.get(position);
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }

    public void createDatabase() {
        dbHelper = new MyDatabaseHelper(this, "CourseAdmin.db", null, 1);
        db = dbHelper.getWritableDatabase();
    }

    public void insertCourseData() {
        ContentValues values = new ContentValues();
        for (int i = 0; i < 5; i++) {
            values.put("courseNo", i);
            values.put("name", "course" + i);
            values.put("capacity", 50);
            db.insert("course", null, values);
            values.clear();
        }
    }

    public void insertStudentData() {
        ContentValues values = new ContentValues();
        for (int i = 0; i < 5; i++) {
            values.put("studentNo", i);
            values.put("name", "ZhangHao");
            values.put("gender", "male");
            values.put("age", 18);
            values.put("grade", "2015");
            db.insert("student", null, values);
            values.clear();
        }
    }

    public void insertStudentCourseData() {
        ContentValues values = new ContentValues();
        for (int i = 0; i < 5; i++) {
            values.put("courseNo", 1);
            values.put("studentNo", i);
            values.put("score", Integer.toString((i + 60)));
            db.insert("enroll", null, values);
            values.clear();
        }
    }

    public List<CourseItem> listAllCourseData() {
        Cursor cursor = db.query("course", null, null, null, null, null, null);
        List<CourseItem> courseItemList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String courseNo = cursor.getString(cursor.getColumnIndex("courseNo"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                int capacity = cursor.getInt(cursor.getColumnIndex("capacity"));
                CourseItem courseItem = new CourseItem(courseNo, name, capacity);
                courseItemList.add(courseItem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return courseItemList;
    }

    public List<StudentScoreItem> listAllStudentScore() {
        Cursor cursor = db.query("enroll", null, null, null, null, null, null);
        List<StudentScoreItem> studentScoreItemList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String courseNo = cursor.getString(cursor.getColumnIndex("courseNo"));
                String studentNo = cursor.getString(cursor.getColumnIndex("studentNo"));
                int score = cursor.getInt(cursor.getColumnIndex("score"));
                StudentScoreItem studentScoreItem = new StudentScoreItem(courseNo, studentNo, score);
                studentScoreItemList.add(studentScoreItem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return studentScoreItemList;
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
    public void onListFragmentInteraction(CourseItem courseItem) {

    }

    @Override
    public void onListFragmentInteraction(TeacherContent.TeacherItem item) {

    }

    @Override
    public void onListFragmentInteraction(StudentScoreItem course) {

    }
}
