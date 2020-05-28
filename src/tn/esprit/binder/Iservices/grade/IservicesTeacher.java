/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.binder.Iservices.grade;

import java.sql.SQLException;
import java.util.List;
import tn.esprit.binder.entities.grades.Teacher;

/**
 *
 * @author Assma
 */
public interface IservicesTeacher<Te> {
    public List<Te> readAll()throws SQLException;
    public void addTeacher(Teacher Te);
    public void updateTeacher(Teacher Te) throws SQLException;
    public void deleteTeacher(int i) throws SQLException;
    public void deleteTeachermodified(int id) throws SQLException;
     public void addTeachermodify(Teacher Te) throws SQLException;
    
    
}
