package ynu.zhanghao.classgradeadmin.db;

public class StudentScoreItem {

    private String studentNo;

    private String classNo;

    private int score;

    public StudentScoreItem(String studentNo, String classNo, int score) {
        this.studentNo = studentNo;
        this.classNo = classNo;
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
}
