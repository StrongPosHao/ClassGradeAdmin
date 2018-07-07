package ynu.zhanghao.classgradeadmin.db;

public class StudentItem {

    private String studentNo;

    private String name;

    private String gender;

    private int age;

    private String grade;

    public StudentItem(String studentNo, String name, String gender, int age, String grade) {
        this.studentNo = studentNo;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.grade = grade;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
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

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}

