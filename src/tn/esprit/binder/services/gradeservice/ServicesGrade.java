/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.binder.services.gradeservice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.esprit.binder.Iservices.grade.IservicesGrades;
import tn.esprit.binder.entities.grades.Grade;
import tn.esprit.binder.utils.MyConnection;

/**
 *
 * @author Assma
 */
public class ServicesGrade implements IservicesGrades<Grade> {

    private Connection cnx;
    private Statement ste;

    public ServicesGrade() {
        cnx = MyConnection.getInstance().getCnx();
    }

 
    @Override
    public ObservableList<Integer> readexam() throws SQLException {
        ObservableList<Integer> arr = FXCollections.observableArrayList();
        ste = cnx.createStatement();
        ResultSet rs = ste.executeQuery("SELECT * FROM exam");
        while (rs.next()) {
            int examname = rs.getInt(0);
            //System.out.println(examname);
            arr.add(examname);
        }
        return arr;
    }
    public int getExamId(String subject) throws SQLException {
       int arr = 0;
        ste = cnx.createStatement();
       ResultSet rs = ste.executeQuery("SELECT * FROM exam WHERE subject = '"+subject+"'");
        while (rs.next()) {
            
            return rs.getInt("id");
            
        }
        return arr;
    }
    
    public ObservableList<String> readAllE() throws SQLException {
        ObservableList<String> arr = FXCollections.observableArrayList();
        ste = cnx.createStatement();
       ResultSet rs = ste.executeQuery("SELECT subject FROM exam");
        while (rs.next()) {
            
            String subject = rs.getString("subject");
            System.out.println(subject+"vvvvvvvvv");
            arr.add(subject);
        }
        return arr;
    }

    @Override
    public ObservableList<Grade> readAll() throws SQLException {
        ObservableList<Grade> arr = FXCollections.observableArrayList();
        ste = cnx.createStatement();
        ResultSet rs = ste.executeQuery("SELECT * FROM grade");
        while (rs.next()) {
            int id = rs.getInt(1);
            int examname = rs.getInt("examname");
            Double grade = rs.getDouble(3);
            String teacher = rs.getString("teacher");
            String pupil = rs.getString("pupil");
            Grade gr = new Grade(id, examname, grade, teacher, pupil);
            arr.add(gr);
        }
        return arr;
    }

  
    public ObservableList<Grade> readAllV2() throws SQLException {
        ObservableList<Grade> arr = FXCollections.observableArrayList();
        ste = cnx.createStatement();
        ResultSet rs = ste.executeQuery("SELECT * FROM grade g INNER JOIN exams e on g.examname=e.id  where e.examname=? ;");
        while (rs.next()) {
          
            int id = rs.getInt(1);
            int examname = rs.getInt("examname");
            Double grade = rs.getDouble(3);
            String teacher = rs.getString("teacher");
            String pupil = rs.getString("pupil");
        
            Grade gr = new Grade(id, examname, grade, teacher, pupil);
            arr.add(gr);
        }
        return arr;
    }
    

    @Override
    public void addGrades(Grade g) {
        try {
            ste = cnx.createStatement();
            String req = "INSERT INTO grade VALUES (NULL, '" + g.getExamname() + "','" + g.getGrade()+ "','" + g.getTeacher() + "','" + g.getPupil() + "')";
            ste.executeUpdate(req);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @Override
    public void updateGrades(Grade g) throws SQLException {
        ste = cnx.createStatement();
        String req = "update grade set Examname = '"
                + g.getExamname()
                + "',Grade='"
                + g.getGrade()
                + "',Teacher='"
                + g.getTeacher()
                + "',pupil='"
                + g.getPupil()
                + "' where id = '"
                + g.getId() + "'";
        if (ste.executeUpdate(req) == 1) {
            System.out.println("modification effectué");
        } 
    }

    @Override
    public void deleteGrades(int i) {
        try {
            String req = "DELETE FROM grade WHERE id=" + i;
            PreparedStatement pt;
            pt = cnx.prepareStatement(req);
            pt.executeUpdate(req);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
    public List<Grade> search(String t) throws SQLException {
        List<Grade> Grades = new ArrayList<>();
        Grades = readAll();
        List<Grade> Gr = Grades.stream()
                .filter(a -> a.getGrade().toString().contains(t) || a.getTeacher().contains(t)||a.getPupil().contains(t))
                .collect(Collectors.toList());
        return Gr;
    }

    @Override
    public List<Grade> getsupdix() {
        List<Grade> arr = new ArrayList();
        try {
            ste = cnx.createStatement();
            String req = "select * from grade where grade >= 10.00";
            ResultSet rs = ste.executeQuery(req);
            while (rs.next()) {
                int id = rs.getInt(1);
                int examname = rs.getInt("examname");
                Double grade = rs.getDouble(3);
                String teacher = rs.getString("teacher");
                String pupil = rs.getString("pupil");
                Grade gr = new Grade(id, examname, grade, teacher, pupil);
                arr.add(gr);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return arr;

    }

    @Override
    public List<Grade> getinfdix() {
        List<Grade> arr = new ArrayList();
        try {
            ste = cnx.createStatement();
            String req = "select * from grade where grade < 10.00";
            ResultSet rs = ste.executeQuery(req);
            while (rs.next()) {
                int id = rs.getInt(1);
                int examname = rs.getInt("examname");
                Double grade = rs.getDouble(3);
                String teacher = rs.getString("teacher");
                String pupil = rs.getString("pupil");
                Grade gr = new Grade(id, examname, grade, teacher, pupil);
                arr.add(gr);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return arr;
    }
    
    

 
        
}
