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
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;
import tn.esprit.binder.Iservices.grade.IservicesTeacher;
import tn.esprit.binder.entities.clubs.parent;
import tn.esprit.binder.entities.grades.Teacher;
import tn.esprit.binder.utils.MyConnection;

/**
 *
 * @author Assma
 */
public class ServicesTeacher implements IservicesTeacher<Teacher>{
      private Connection cnx;
    private Statement ste;

    public ServicesTeacher() {
        cnx = MyConnection.getInstance().getCnx();
    }

    @Override
    public List<Teacher> readAll() throws SQLException {
    ObservableList<Teacher> arr = FXCollections.observableArrayList();
        ste = cnx.createStatement();
        ResultSet rs = ste.executeQuery("SELECT * from teacher");
        while (rs.next()) {
            int id = rs.getInt(1);
            String fullname = rs.getString("fullname");
            String email = rs.getString("email");
            String password = rs.getString("password");
            String phonenumber = rs.getString("phonenumber");
            String specialty = rs.getString("specialty");
            Teacher te = new Teacher(id, fullname, email, password, phonenumber, specialty);
            arr.add(te);
        }
        return arr;
    }

    @Override
    public void addTeacher(Teacher Te) {
 try {
            ste = cnx.createStatement();
            //"INSERT INTO `parent`( `id`,`name`, `mail`,`phone`,`id_user`) VALUES ('"+p.getId()+"','"+p.getName()+"','"+p.getMail()+"','"+p.getPhone()+"','"+p.getId_user()+"')";
            String requeteInsert = "INSERT INTO teacher VALUES (NULL, '"+ Te.getFullname()+ "', '"+ Te.getEmail()+ "', '"+ Te.getPassword()+"', '"+ Te.getPhonenumber()+ "', '"+ Te.getSpecialty() + "','"+Te.getId_user()+"')";
            ste.executeUpdate(requeteInsert);
        } catch (SQLException ex1) {
            System.out.println(ex1.getMessage());
        }
            
    }
    @Override
    public void deleteTeachermodified(int id) {
        String req = "delete from teacher where id =?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = cnx.prepareStatement(req);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
           

        } catch (SQLException ex) {
            ex.printStackTrace();
          
        }
    }

    @Override
    public void addTeachermodify(Teacher Te) throws SQLException {
        try {
 
 
           Connection con =MyConnection.getInstance().getCnx();
            //Excecute la requete et envoie dans ResultSet
 
 
 
            String RequeteAjout = "INSERT INTO teacher VALUES (NULL, '"+"','"+Te.getFullname()+"','"+Te.getEmail()+"','"+Te.getPassword()+"','"+Te.getPhonenumber()+"','"+Te.getSpecialty()+"')";
            /*TFPrenom.getText(), TFNom.getText(), TFPoste.getText(), TFDate_de_naissance.getText() , TFNationalite.getText()*/
              
       
           ste = con.prepareStatement(RequeteAjout);
            ste.executeUpdate(RequeteAjout);
             JOptionPane.showMessageDialog(null,"Modified successfully!");
           
        
          
            //TEST DEBUG///

        } 
        
        catch (SQLException e) {
            System.out.println("update impossible à effectuer.\nErreur :" + e);
        }
    }
    
    @Override
    public void updateTeacher(Teacher Te) throws SQLException {
     ste = cnx.createStatement();
        String req = "update teacher set fullname = '"
                + Te.getFullname()
                + "',email='"
                + Te.getEmail()
                + "',password='"
                + Te.getPassword()
                + "',phonenumber='"
                + Te.getPhonenumber()
                 + "',specialty='"
                + Te.getSpecialty()
                + "' where id = '"
                + Te.getIdTeacher() + "'";
        if (ste.executeUpdate(req) == 1) {
            System.out.println("modification effectué");
        }
    }

    @Override
    public void deleteTeacher(int i) throws SQLException {
              try {
            String req = "DELETE FROM teacher WHERE id=" + i;
            PreparedStatement pt;
            pt = cnx.prepareStatement(req);
            pt.executeUpdate(req);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
