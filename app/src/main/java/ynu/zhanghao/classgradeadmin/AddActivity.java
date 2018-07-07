package ynu.zhanghao.classgradeadmin;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Button addButton = findViewById(R.id.confirm);
        addButton.setOnClickListener(new View.OnClickListener() {
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
                values.put("name", courseName);
                values.put("capacity", capacity);
                db.insert("course", null, values);
                values.clear();
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

//    protected void addCourse() {
//
//    }



}
