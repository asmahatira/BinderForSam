/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.binder.services.club;

import tn.esprit.binder.Iservices.club.IParentService;
import tn.esprit.binder.utils.MyConnection;
import tn.esprit.binder.entities.clubs.parent;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;
import tn.esprit.binder.entities.FOSUser;
import tn.esprit.binder.services.ServicesFOS;

/**
 *
 * @author Sam
 */
public class ParentService implements IParentService {
    
   Connection cn = MyConnection.getInstance().getCnx();
    Statement st; //execute la req
    PreparedStatement pst;
    FileInputStream fls;

    @Override
    public void addParent(parent p) throws SQLException {
        
        
         try {
 
 
           Connection con =MyConnection.getInstance().getCnx();
            //Excecute la requete et envoie dans ResultSet

            String RequeteAjout = "INSERT INTO `parent`( `id`,`name`, `mail`,`phone`,`id_user`) VALUES ('"+p.getId()+"','"+p.getName()+"','"+p.getMail()+"','"+p.getPhone()+"','"+p.getId_user()+"')";
            /*TFPrenom.getText(), TFNom.getText(), TFPoste.getText(), TFDate_de_naissance.getText() , TFNationalite.getText()*/      
           pst = con.prepareStatement(RequeteAjout);
            pst.executeUpdate(RequeteAjout);
            JOptionPane.showMessageDialog(null,"Ajout avec succes");
        
          
            //TEST DEBUG///

        } 
        
        catch (SQLException e) {
            System.out.println("Ajout impossible à effectuer.\nErreur :" + e);
        }
    
        
        
    }

    @Override
    public void deleteParent(int id) {
        String req = "delete from parent where id =?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = cn.prepareStatement(req);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null,"supprition avec succes");

        } catch (SQLException ex) {
            ex.printStackTrace();
          
        }
    }

    @Override
    public boolean updateParent(parent p) throws SQLException {
        
         String req = "UPDATE `parent` SET `name`= '"+ p.getName()+"',`mail`= '"+ p.getMail()+"',`phone`= '"+ p.getPhone()+"' ";
         st = cn.createStatement();
        if(st.executeUpdate(req) == 1)
        {
            System.out.println("succes !");
        return true;   
        }
        else 
        {
            System.out.println(" introuvable");
            return false;
        }
        
        
        
    }

    @Override
    public List<parent> getAll() {
        List<parent> parents = new ArrayList<>();
        String req = "select * from parent ";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = cn.prepareStatement(req);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                parent p = new parent(
                        
                         
                        
                       resultSet.getString("name"),
                        resultSet.getInt("id"),
                        resultSet.getString("mail"),
                        resultSet.getString("phone")
                        
                );
                parents.add(p); 
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return parents;
    }
    
        
        
        
        
    

    @Override
    public List<parent> rechercheParent(String str) {
      List<parent> parent=new ArrayList<parent>();
        String sql = "SELECT * FROM parent WHERE name LIKE ? ";
        PreparedStatement statement;
        
        try {

         statement= cn.prepareStatement(sql);
            statement.setString(1,"%" + str + "%");
            //statement.setString(2, "%" + str + "%");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
               parent p = new parent();
               // c.setId(rs.getInt(1));
                p.setName(rs.getString(2));
                 p.setMail(rs.getString(3));
                  p.setPhone(rs.getString(4));
                
               parent.add(p);
            }
        } catch (SQLException ex) {
            
        }
        return parent;
    }

    @Override
    public void deleteParentmodified(int id) {
        String req = "delete from parent where id =?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = cn.prepareStatement(req);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
           

        } catch (SQLException ex) {
            ex.printStackTrace();
          
        }
    }

    @Override
    public void addParentmodify(parent p) throws SQLException {
        try {
 
 
           Connection con =MyConnection.getInstance().getCnx();
            //Excecute la requete et envoie dans ResultSet
 
 
 
            String RequeteAjout = "INSERT INTO `parent`( `id`,`name`, `mail`,`phone`) VALUES ('"+p.getId()+"','"+p.getName()+"','"+p.getMail()+"','"+p.getPhone()+"')";
            /*TFPrenom.getText(), TFNom.getText(), TFPoste.getText(), TFDate_de_naissance.getText() , TFNationalite.getText()*/
              
       
           pst = con.prepareStatement(RequeteAjout);
            pst.executeUpdate(RequeteAjout);
             JOptionPane.showMessageDialog(null,"modifier avec succes");
           
        
          
            //TEST DEBUG///

        } 
        
        catch (SQLException e) {
            System.out.println("update impossible à effectuer.\nErreur :" + e);
        }
    }
    
    /*
     public ObservableList<String> readAllT() throws SQLException {
        ObservableList<String> arr = FXCollections.observableArrayList();
        pst = cn.prepareStatement("SELECT fullname FROM teacher");
       ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            
            String subject = rs.getString("fullname");
            System.out.println(subject+"vvvvvvvvv");
            arr.add(subject);
        }
        return arr;
     }*/
    //populer le combo box par les nom des pupils
    public ObservableList<String> readAllP() throws SQLException {
        ObservableList<String> arr = FXCollections.observableArrayList();
        pst = cn.prepareStatement("SELECT fullname FROM pupils");
       ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            
            String subject = rs.getString("fullname");
            System.out.println(subject+"vvvvvvvvv");
            arr.add(subject);
        }
        return arr;
    }
    //retourner l'id de pupil ou on as selectionné le nom dans le combo box
    public int getPupilId(String fullname) throws SQLException {
       int arr = 0;
        pst = cn.prepareStatement( "SELECT * FROM `pupils` WHERE `fullname` = '"+fullname+"'");
       ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            
            return rs.getInt("id");
            
        }
        return arr;
    }
      
    public parent getParentByUsername(String username,String mail) {
        try {
            PreparedStatement pt = cn.prepareStatement("select * from parent where name = ? and mail = ?");
            pt.setString(1, username);
            pt.setString(2, mail);

            ResultSet rs = pt.executeQuery();
            if (rs.next()) {
                parent p = new parent(rs.getString(2),rs.getInt(1) , rs.getString(3), rs.getString(4));
                System.out.println(p+"hooooooooooooooooh");
                return p;
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServicesFOS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    
}
