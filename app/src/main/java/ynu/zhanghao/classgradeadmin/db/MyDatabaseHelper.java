package ynu.zhanghao.classgradeadmin.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {

//    private static final String CREATE_TEACHER = "CREATE TABLE IF NOT EXISTS teacher("
//            + "teacherNo CHAR(5) PRIMARY KEY, "
//            + "name VARCHAR(10), "
//            + "gender VARCHAR(4), "
//            + "age INTEGER, "
//            + "email VARCHAR(64), "
//            + "school VARCHAR(20))";

    private static final String CREATE_COURSE = "CREATE TABLE IF NOT EXISTS course("
            + "courseNo INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "name VARCHAR(20), "
            + "capacity INTEGER);";

    private static final String CREATE_STUDENT = "CREATE TABLE IF NOT EXISTS student("
            + "studentNo CHAR(11) PRIMARY KEY, "
            + "name VARCHAR(10), "
            + "gender VARCHAR(4), "
            + "age INTEGER, "
            + "grade CHAR(4))";

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
//        db.execSQL(CREATE_TEACHER);
        db.execSQL(CREATE_COURSE);
        db.execSQL(CREATE_STUDENT);
        db.execSQL(CREATE_ENROLL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS TEACHER");
//        db.execSQL("DROP TABLE IF EXISTS COURSE");
//        db.execSQL("DROP TABLE IF EXISTS STUDENT");
//        db.execSQL("DROP TABLE IF EXISTS ENROLL");
//        onCreate(db);
    }
}
