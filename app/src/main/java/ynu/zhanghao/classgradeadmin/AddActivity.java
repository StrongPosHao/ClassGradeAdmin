package ynu.zhanghao.classgradeadmin;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int fragmentId = intent.getIntExtra("fragmentId", 0);
        if (fragmentId == 1) {
            setContentView(R.layout.add_class_item);
            addCourse();
        } else if (fragmentId == 2) {
            setContentView(R.layout.add_student_score_item);
            addStudentScore(intent.getStringExtra("courseNo"));
        } else if (fragmentId == 3) {
            setContentView(R.layout.add_student_item);
            addStudent();
        }
    }

    protected void addCourse() {
        Button confirmButton = findViewById(R.id.confirm);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            TextView courseNoText = findViewById(R.id.courseNo);
            TextView courseNameText = findViewById(R.id.courseName);
            TextView capacityText = findViewById(R.id.courseCapacity);
            String courseNo = courseNoText.getText().toString();
            String courseName = courseNameText.getText().toString();
            int capacity = Integer.parseInt(capacityText.getText().toString());
            SQLiteDatabase db = MainActivity.getDb();
            ContentValues values = new ContentValues();
            values.put("courseNo", courseNo);
            values.put("courseName", courseName);
            values.put("capacity", capacity);
            db.insert("course", null, values);
            values.clear();
            Intent intent = new Intent(AddActivity.this, MainActivity.class);
            startActivity(intent);
            }
        });
    }

    // 根据课程号添加学生成绩，会验证学生成绩
    protected void addStudentScore(final String courseNo) {
        Button confirmButton = findViewById(R.id.confirm);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView studentNoText = findViewById(R.id.studentNo);
                TextView studentNameText = findViewById(R.id.studentName);
                TextView studentScoreText = findViewById(R.id.studentScore);
                String studentNo = studentNoText.getText().toString();
                String studentName = studentNameText.getText().toString();
                int studentScore = Integer.parseInt(studentScoreText.getText().toString());
                SQLiteDatabase db = MainActivity.getDb();
                Cursor cursor = db.rawQuery("SELECT * FROM student WHERE" +
                        " student.studentNo = ? AND student.studentName = ?",
                        new String[]{studentNo, studentName});
                if (cursor.moveToFirst()) {
                    ContentValues values = new ContentValues();
                    values.put("studentNo", studentNo);
                    values.put("courseNo", courseNo);
                    values.put("score", studentScore);
                    db.insert("enroll", null, values);
                    values.clear();
                    Intent intent = new Intent(AddActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(AddActivity.this, "该学生不存在！请检查输入是否正确。", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    protected void addStudent() {
        Button confirmButton = findViewById(R.id.confirm);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView studentNoText = findViewById(R.id.studentNo);
                TextView studentNameText = findViewById(R.id.studentName);
                TextView majorText = findViewById(R.id.major);
                String studentNo = studentNoText.getText().toString();
                String studentName = studentNameText.getText().toString();
                String major = majorText.getText().toString();
                SQLiteDatabase db = MainActivity.getDb();
                ContentValues values = new ContentValues();
                values.put("studentNo", studentNo);
                values.put("studentName", studentName);
                values.put("gender", "male");
                values.put("age", 18);
                values.put("grade", "2015");
                values.put("major", major);
                db.insert("student", null, values);
                values.clear();
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }



}
