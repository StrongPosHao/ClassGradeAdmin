package ynu.zhanghao.classgradeadmin.db;


public class CourseItem {

    private String courseNo;

    private String name;

    private int capacity;

    public CourseItem(String courseNo, String name, int capacity) {
        this.courseNo = courseNo;
        this.name = name;
        this.capacity = capacity;
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

}
