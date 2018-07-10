package ynu.zhanghao.classgradeadmin;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.util.ArrayList;
import java.util.List;

import ynu.zhanghao.classgradeadmin.db.CourseItem;
import ynu.zhanghao.classgradeadmin.db.MyDatabaseHelper;
import ynu.zhanghao.classgradeadmin.db.StudentItem;
import ynu.zhanghao.classgradeadmin.db.StudentScoreItem;
import ynu.zhanghao.classgradeadmin.fragments.ScoreFragment;
import ynu.zhanghao.classgradeadmin.fragments.StudentFragment;
import ynu.zhanghao.classgradeadmin.fragments.TeacherClassFragment;

public class MainActivity extends BaseActivity
        implements StudentFragment.OnListFragmentInteractionListener,
        TeacherClassFragment.OnListFragmentInteractionListener,
        ScoreFragment.OnListFragmentInteractionListener {

    MyDatabaseHelper dbHelper;

    private static SQLiteDatabase db;

    protected BottomNavigationBar navigationBar;

    private DrawerLayout drawerLayout;

    protected Toolbar toolbar;

    private List<Fragment> fragmentList;

    protected List<CourseItem> courseItemList;

    protected List<StudentScoreItem> studentScoreItemList;

    protected List<StudentItem> studentItemList;

    private String courseNo;

    ScoreFragment scoreFragment = ScoreFragment.newInstance(1);

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
                .addItem(new BottomNavigationItem(R.drawable.ic_group_black_24dp, "学生"));
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
        fragmentList.add(scoreFragment);
        fragmentList.add(StudentFragment.newInstance(1));

        fragmentManager = getSupportFragmentManager();

        ShowFragment(0);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                Fragment currentFragment = getVisibleFragment();
                if (currentFragment instanceof TeacherClassFragment) {
                    intent.putExtra("fragmentId", 1);
                } else if (currentFragment instanceof ScoreFragment){
                    intent.putExtra("fragmentId", 2);
                    intent.putExtra("courseNo", courseNo);
                } else if (currentFragment instanceof StudentFragment) {
                    intent.putExtra("fragmentId", 3);
                }
                startActivity(intent);
            }
        });

        createDatabase();
//        insertStudentData();
//        insertCourseData();
//        insertStudentCourseData();
        courseItemList = listAllCourseData();
        TeacherClassFragment.setCourseItemList(courseItemList);

        studentScoreItemList = listAllStudentScore("0");
        ScoreFragment.setStudentScoreItems(studentScoreItemList);

        studentItemList = listAllStudent();
        StudentFragment.setStudentList(studentItemList);

    }


    public void ShowFragment(int position) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentList.get(position);
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }

    public void createDatabase() {
        dbHelper = new MyDatabaseHelper(this, "CourseAdmin.db", null, 2);
        db = dbHelper.getWritableDatabase();
    }

    public void insertCourseData() {
        ContentValues values = new ContentValues();
        for (int i = 0; i < 50; i++) {
            values.put("courseNo", i);
            values.put("courseName", "course" + i);
            values.put("capacity", 50);
            db.insert("course", null, values);
            values.clear();
        }
    }

    public void insertStudentData() {
        ContentValues values = new ContentValues();
        for (int i = 0; i < 50; i++) {
            values.put("studentNo", i);
            values.put("studentName", "ZhangHao");
            values.put("gender", "male");
            values.put("age", 18);
            values.put("grade", "2015");
            db.insert("student", null, values);
            values.clear();
        }
    }

    public void insertStudentCourseData() {
        ContentValues values = new ContentValues();
        for (int i = 0; i < 50; i++) {
            values.put("courseNo", 1);
            values.put("studentNo", i);
            values.put("score", Integer.toString((i + 60)));
            db.insert("enroll", null, values);
            values.clear();
        }
    }

    public static List<CourseItem> listAllCourseData() {
        Cursor cursor = db.query("course", null, null, null, null, null, null);
        List<CourseItem> courseItemList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String courseNo = cursor.getString(cursor.getColumnIndex("courseNo"));
                String courseName = cursor.getString(cursor.getColumnIndex("courseName"));
                int capacity = cursor.getInt(cursor.getColumnIndex("capacity"));
                CourseItem courseItem = new CourseItem(courseNo, courseName, capacity);
                courseItemList.add(courseItem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return courseItemList;
    }

    public List<StudentScoreItem> listAllStudentScore(String courseNo) {
        Cursor cursor = db.rawQuery("SELECT enroll.courseNo, enroll.studentNo, course.courseName, student.studentName, enroll.score FROM enroll, student, course WHERE" +
                " enroll.studentNo = student.studentNo AND course.courseNo = enroll.courseNo AND enroll.courseNo = ?", new String[] {courseNo});
        List<StudentScoreItem> studentScoreItemList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String studentNo = cursor.getString(cursor.getColumnIndex("studentNo"));
                String courseName = cursor.getString(cursor.getColumnIndex("courseName"));
                String studentName = cursor.getString(cursor.getColumnIndex("studentName"));
                int score = cursor.getInt(cursor.getColumnIndex("score"));
                StudentScoreItem studentScoreItem = new StudentScoreItem(studentNo, courseNo,
                        studentName, courseName, score);
                studentScoreItemList.add(studentScoreItem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return studentScoreItemList;
    }

    public List<StudentItem> listAllStudent() {
        Cursor cursor = db.rawQuery("SELECT * FROM student", new String[]{});
        List<StudentItem> studentItemList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String studentNo = cursor.getString(cursor.getColumnIndex("studentNo"));
                String studentName = cursor.getString(cursor.getColumnIndex("studentName"));
                String major = cursor.getString(cursor.getColumnIndex("major"));
                StudentItem studentItem = new StudentItem(studentNo, studentName, "male", 18, "2015", major);
                studentItemList.add(studentItem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return studentItemList;
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


    public static SQLiteDatabase getDb() {
        return db;
    }

    public AppCompatActivity getMainActivity() {
        return MainActivity.this;
    }

    public Fragment getVisibleFragment() {
        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isVisible()) {
                return fragment;
            }
        }
        return null;
    }

    public BottomNavigationBar getNavigationBar() {
        return navigationBar;
    }

    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onListFragmentInteraction(CourseItem courseItem) {

    }

    @Override
    public void onListFragmentInteraction(StudentItem item) {

    }

    @Override
    public void onListFragmentInteraction(StudentScoreItem course) {

    }
}
