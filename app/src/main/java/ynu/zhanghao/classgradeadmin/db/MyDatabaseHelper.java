package ynu.zhanghao.classgradeadmin.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {


    private static final String CREATE_COURSE = "CREATE TABLE IF NOT EXISTS course("
            + "courseNo INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "courseName VARCHAR(20), "
            + "capacity INTEGER);";

    private static final String CREATE_STUDENT = "CREATE TABLE IF NOT EXISTS student("
            + "studentNo CHAR(11) PRIMARY KEY, "
            + "studentName VARCHAR(10), "
            + "gender VARCHAR(4), "
            + "age INTEGER, "
            + "grade CHAR(4), "
            + "major VARCHAR(20))";

    private static final String CREATE_ENROLL = "CREATE TABLE IF NOT EXISTS enroll("
            + "studentNo CHAR(11), "
            + "courseNo INTEGER, "
            + "score INTEGER,"
            + "PRIMARY KEY(studentNo, courseNo), "
            + "FOREIGN KEY(studentNo) REFERENCES student(studentNo), "
            + "FOREIGN KEY(courseNo) REFERENCES course(courseNo))";


    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_COURSE);
        db.execSQL(CREATE_STUDENT);
        db.execSQL(CREATE_ENROLL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
