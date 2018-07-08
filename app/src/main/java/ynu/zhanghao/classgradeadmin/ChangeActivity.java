package ynu.zhanghao.classgradeadmin;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ChangeActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int fragmentId = intent.getIntExtra("fragmentId", 0);
        if (fragmentId == 1) {
            setContentView(R.layout.add_class_item);
            changeCourse();
        } else if (fragmentId == 2) {
            setContentView(R.layout.add_student_score_item);
            changeStudentScore();
        }
    }

    protected void changeCourse() {
        final TextView courseNoText = findViewById(R.id.courseNo);
        final TextView courseNameText = findViewById(R.id.courseName);
        final TextView capacityText = findViewById(R.id.courseCapacity);
        Intent intent = getIntent();
        final String initCourseNo = intent.getStringExtra("courseNo");
        String initCourseName = intent.getStringExtra("courseName");
        int initCapacity = intent.getIntExtra("capacity", 0);
        courseNoText.setText(initCourseNo);
        courseNameText.setText(initCourseName);
        capacityText.setText(Integer.toString(initCapacity));

        Button confirmButton = findViewById(R.id.confirm);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseNo = courseNoText.getText().toString();
                String courseName = courseNameText.getText().toString();
                int capacity = Integer.parseInt(capacityText.getText().toString());
                SQLiteDatabase db = MainActivity.getDb();
                ContentValues values = new ContentValues();
                values.put("courseNo", courseNo);
                values.put("courseName", courseName);
                values.put("capacity", capacity);
                db.update("course", values, "courseNo = ?", new String[]{initCourseNo});
                Intent intent1 = new Intent(ChangeActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });
    }

    protected void changeStudentScore() {
        final TextView studentNoText = findViewById(R.id.studentNo);
        final TextView studentNameText = findViewById(R.id.studentName);
        final TextView studentScoreText = findViewById(R.id.studentScore);

        Intent intent = getIntent();
        final String courseNo = intent.getStringExtra("courseNo");
        final String initStudentNo = intent.getStringExtra("studentNo");
        String initStudentName = intent.getStringExtra("studentName");
        int initStudentScore = intent.getIntExtra("score", -1);
        studentNoText.setText(initStudentNo);
        studentNameText.setText(initStudentName);
        studentScoreText.setText(Integer.toString(initStudentScore));

        Button confirmButton = findViewById(R.id.confirm);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = MainActivity.getDb();
                String studentNo = studentNoText.getText().toString();
                String studentName = studentNameText.getText().toString();
                Cursor cursor = db.rawQuery("SELECT * FROM student " +
                        "WHERE student.studentNo = ? AND student.studentName = ?",
                        new String[]{studentNo, studentName});
                int studentScore = Integer.parseInt(studentScoreText.getText().toString());
                if (cursor.moveToFirst()) {
                    ContentValues values = new ContentValues();
                    values.put("studentNo", studentNo);
                    values.put("courseNo", courseNo);
                    values.put("score", studentScore);
                    db.update("enroll", values, "studentNo = ? AND courseNo = ?", new String[]{initStudentNo, courseNo});
                    ChangeActivity.this.finish();
                } else {
                    Toast.makeText(ChangeActivity.this, "该学生不存在！请检查输入是否正确。", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


}
