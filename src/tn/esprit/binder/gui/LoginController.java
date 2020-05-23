/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.binder.gui;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class LoginController implements Initializable {

    @FXML
    private TextField txturname;
    @FXML
    private Button btnok;
    @FXML
    private PasswordField txtpass;

    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    private Stage stage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void login(ActionEvent event) throws SQLException, IOException {

        //JOptionPane.showMessageDialog(null, "hi");
        String uname = txturname.getText();
        String pass = txtpass.getText();

        if (uname.equals("") && txtpass.equals("")) {

            JOptionPane.showMessageDialog(null, "username or password blank");
        } else {

            try {
                Class.forName("com.mysql.jdbc.Driver");

                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/binder2", "root", "");
                pst = con.prepareStatement("select * from fos_user where email=? and password=?");
                pst.setString(1, uname);
                pst.setString(2, pass);

                rs = pst.executeQuery();

                if (rs.next()) {
                    String role = rs.getString("roles");
                    if (role.equals("a:1:{i:0;s:10:\"ROLE_ADMIN\";}")) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("homeBinder.fxml"));

                        Parent root = loader.load();
                        HomeBinderController ugc = loader.getController();

                        btnok.getScene().setRoot(root);
                    } else {
                        JOptionPane.showMessageDialog(null, "You don't have permission to access");
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Login failed");
                    txturname.setText("");
                    txtpass.setText("");
                    txturname.requestFocus();

                }

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
