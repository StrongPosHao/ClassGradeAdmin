package ynu.zhanghao.classgradeadmin;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ChangeActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

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
                values.put("name", courseName);
                values.put("capacity", capacity);
                db.update("course", values, "courseNo = ?", new String[] {initCourseNo});
                Intent intent1 = new Intent(ChangeActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });

    }
}
