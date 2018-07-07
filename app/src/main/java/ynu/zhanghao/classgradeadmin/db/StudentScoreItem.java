package ynu.zhanghao.classgradeadmin.db;

public class StudentScoreItem {

    private String studentNo;

    private String classNo;

    private String studentName;

    private String className;

    private int score;

    public StudentScoreItem(String studentNo, String classNo,
                            String studentName, String className, int score) {
        this.studentNo = studentNo;
        this.classNo = classNo;
        this.studentName = studentName;
        this.className = className;
        this.score = score;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getClassNo() {
        return classNo;
    }

    public void setClassNo(String classNo) {
        this.classNo = classNo;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
