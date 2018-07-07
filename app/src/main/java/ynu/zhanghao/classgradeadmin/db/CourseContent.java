package ynu.zhanghao.classgradeadmin.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseContent {

    public static final List<CourseItem> ITEMS = new ArrayList<>();

    public static final Map<String, CourseItem> ITEM_MAP = new HashMap<>();

    public static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 0; i < COUNT; i++) {
            addItem(createCourseItem(i));
        }
    }

    private static void addItem(CourseItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.teacherNo, item);
    }

    private static CourseItem createCourseItem(int position) {
        return new CourseItem(String.valueOf(position), "移动应用开发", 50, "1");
    }


    public static class CourseItem {
        private String courseNo;

        private String name;

        private int capacity;

        private String teacherNo;

        public CourseItem(String courseNo, String name, int capacity, String teacherNo) {
            this.courseNo = courseNo;
            this.name = name;
            this.capacity = capacity;
            this.teacherNo = teacherNo;
        }

        @Override
        public String toString() {
            return name;
        }

        public String getCourseNo() {
            return courseNo;
        }

        public void setCourseNo(String courseNo) {
            this.courseNo = courseNo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCapacity() {
            return capacity;
        }

        public void setCapacity(int capacity) {
            this.capacity = capacity;
        }

        public String getTeacherNo() {
            return teacherNo;
        }

        public void setTeacherNo(String teacherNo) {
            this.teacherNo = teacherNo;
        }
    }
}
