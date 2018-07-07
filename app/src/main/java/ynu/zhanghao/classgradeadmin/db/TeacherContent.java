package ynu.zhanghao.classgradeadmin.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeacherContent {

    public static final List<TeacherItem> ITEMS = new ArrayList<TeacherItem>();

    public static final Map<String, TeacherItem> ITEM_MAP = new HashMap<String, TeacherContent.TeacherItem>();

    public static final int COUNT = 25;

    static {
        // Add some sample items
        for (int i = 0; i < COUNT; i++) {
            addItem(createTeacherItem(i));
        }
    }

    private static void addItem(TeacherItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.teacherNo, item);
    }

    private static TeacherItem createTeacherItem(int position) {
        return new TeacherItem(String.valueOf(position), "ZhangHao", "male", 10, "strongposhao@gmail.com", "ynu");
    }


    public static class TeacherItem {
        private String teacherNo;

        private String name;

        private String gender;

        private int age;

        private String email;

        private String school;

        public TeacherItem(String teacherNo, String name, String gender, int age, String email, String school) {
            this.teacherNo = teacherNo;
            this.name = name;
            this.gender = gender;
            this.age = age;
            this.email = email;
            this.school = school;
        }


        @Override
        public String toString() {
            return name;
        }

        public String getTeacherNo() {
            return teacherNo;
        }

        public void setTeacherNo(String teacherNo) {
            this.teacherNo = teacherNo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getSchool() {
            return school;
        }

        public void setSchool(String school) {
            this.school = school;
        }
    }
}
