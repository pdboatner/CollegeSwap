package edu.ua.collegeswap.viewModel;

import edu.ua.collegeswap.view.DetailTextbook;

/**
 * Created by Patrick on 3/4/2015.
 */
public class Textbook extends Listing {

    private String courseSubject;
    private int courseNumber;

    public Textbook() {
        super();
    }

    @Override
    public Class getDetailActivityClass() {
        return DetailTextbook.class;
    }

    public String getCourseSubject() {
        return courseSubject;
    }

    public void setCourseSubject(String courseSubject) {
        this.courseSubject = courseSubject;
    }

    public int getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(int courseNumber) {
        this.courseNumber = courseNumber;
    }
}
