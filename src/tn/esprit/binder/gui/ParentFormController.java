/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.binder.gui;

import tn.esprit.binder.Iservices.club.IParentService;
import tn.esprit.binder.services.club.ActivityService;
import tn.esprit.binder.services.club.ParentService;
import tn.esprit.binder.utils.MyConnection;
import tn.esprit.binder.entities.clubs.parent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import static com.sun.jmx.mbeanserver.Util.cast;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import tn.esprit.binder.entities.FOSUser;
import tn.esprit.binder.entities.Pupils;
import tn.esprit.binder.services.ServicesFOS;
import tn.esprit.binder.services.ServicesPupils;

/**
 * FXML Controller class
 *
 * @author Sam
 */
public class ParentFormController implements Initializable {

    @FXML
    private TextField nameAj;
    @FXML
    private TextField mailAJ;
    @FXML
    private TextField phoneAJ;
    @FXML
    private TableView<parent> tab;
    @FXML
    private TableColumn<parent, String> nameP;
    @FXML
    private TableColumn<parent, String> mailP;
    @FXML
    private TableColumn<parent, String> phoneP;
    @FXML
    private TextField rechercheBar;

    public List<String> listPu;

    //@FXML
    // private ComboBox<String> pupilCom;
    public List<String> listP;

    private IParentService annonceServiceP;
    ObservableList<parent> oblistP = FXCollections.observableArrayList();
    ParentService esP = new ParentService();

    PreparedStatement pst;
    Connection cn = MyConnection.getInstance().getCnx();
    Statement st; //execute la req
    @FXML
    private Button addParentButton;
    @FXML
    private ComboBox<String> pupilCom;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {

            // listPu=  new ParentService().readAllP();
            annonceServiceP = new ParentService();
            oblistP = FXCollections.observableArrayList(esP.getAll());

            ObservableList observableList = FXCollections.observableArrayList(oblistP);
            tab.setItems(observableList);
            //idA.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameP.setCellValueFactory(new PropertyValueFactory<>("name"));
            mailP.setCellValueFactory(new PropertyValueFactory<>("mail"));
            phoneP.setCellValueFactory(new PropertyValueFactory<>("phone"));

            listPu = new ParentService().readAllP();

            pupilCom.setItems(FXCollections.observableArrayList(listPu));

        } catch (Exception e) {
        }
        // pupilCom.setItems(FXCollections.observableArrayList(listPu));
        //pupilCom.setItems(FXCollections.observableArrayList(listPu));

        // TODO
    }

    @FXML
    private void rechercher(ActionEvent event) {
        if (!rechercheBar.getText().isEmpty()) {
            tab.setVisible(true);
            annonceServiceP = new ParentService();
            List<parent> myList = annonceServiceP.rechercheParent(rechercheBar.getText());
            ObservableList<parent> observableList = FXCollections.observableArrayList();

            // idA.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameP.setCellValueFactory(new PropertyValueFactory<>("name"));
            mailP.setCellValueFactory(new PropertyValueFactory<>("mail"));
            phoneP.setCellValueFactory(new PropertyValueFactory<>("phone"));

            myList.forEach(e -> {

                observableList.add(e);
                // System.out.println(observableList);
            });
            tab.setItems(observableList);
        } else {
            if (rechercheBar.getText().isEmpty()) {
                tab.getItems().clear();
                tab.getItems().addAll(annonceServiceP.getAll());
            }

        }
    }

    @FXML
    private void ajouter(ActionEvent event) throws SQLException {

        if (validateMail() && validatePhone()) {

            //String about =aboutAJ.getText();
            String name = nameAj.getText();
            String phone = phoneAJ.getText();

            String mail = mailAJ.getText();
            String pupilname = pupilCom.getValue();

            ParentService sl = new ParentService();
            int pupilId = sl.getPupilId(pupilname);
            System.out.println(pupilId);
            parent p = new parent(name, mail, phone, pupilId);

            sl.addParent(p);
            AfficherAll();
        }

    }

    @FXML
    private void supprimer(ActionEvent event) {
        esP = new ParentService();
        int index = tab.getSelectionModel().getSelectedItem().getId();
        //System.out.println(index);
        esP.deleteParent(index);
        AfficherAll();
    }

    @FXML
    private void modifier(ActionEvent event) {
        annonceServiceP = new ParentService();
        try {
            parent aa = tab.getSelectionModel().getSelectedItem();
            System.out.println(aa.getId() + "551");
            //  int id_c1 = Integer.parseInt(aa.getId_c());

            int id = aa.getId();
            String name = nameAj.getText();

            String mail = mailAJ.getText();
            String phone = phoneAJ.getText();

            String pupilname = pupilCom.getValue();

            ParentService sl = new ParentService();
            int pupilId = sl.getPupilId(pupilname);

            parent a = new parent(name, mail, phone,pupilId);
            ParentService s2 = new ParentService();
            ParentService s3 = new ParentService();

            parent l = new parent(name, mail, phone,pupilId);
            s3.deleteParentmodified(id);
            s2.addParentmodify(l);
            AfficherAll();

            try {
                Connection con = (Connection) MyConnection.getInstance().getCnx();

                ResultSet rs = con.createStatement().executeQuery("select * from parent");
                while (rs.next()) {
                    //oblist.add(new parent(rs.getInt("id"),rs.getString("name"),rs.getString("mail"),rs.getString("phone")));}
                    oblistP.add(new parent(rs.getString("name"), rs.getInt("id"), rs.getString("mail"), rs.getString("phone")));
                }
            } catch (SQLException ex) {
                Logger.getLogger(ParentFormController.class.getName()).log(Level.SEVERE, null, ex);
            }

            tab.setItems(oblistP);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    private void AfficherAll() {

        annonceServiceP = new ParentService();
        oblistP = FXCollections.observableArrayList(esP.getAll());

        ObservableList observableList = FXCollections.observableArrayList(oblistP);
        tab.setItems(observableList);
        //idA.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameP.setCellValueFactory(new PropertyValueFactory<>("name"));
        mailP.setCellValueFactory(new PropertyValueFactory<>("mail"));
        phoneP.setCellValueFactory(new PropertyValueFactory<>("phone"));
    }

    private boolean validateMail() {
        Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher m = p.matcher(mailAJ.getText());
        if (m.find() && m.group().equals(mailAJ.getText())) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("validate your mail");
            alert.setHeaderText(null);
            alert.setContentText("please enter a valid mail");
            alert.showAndWait();
            return false;

        }

    }

    private boolean validatePhone() {
        Pattern p = Pattern.compile("(5)?[0-9]{8}");
        Matcher m = p.matcher(phoneAJ.getText());
        if (m.find() && m.group().equals(phoneAJ.getText())) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("validate your phone");
            alert.setHeaderText(null);
            alert.setContentText("please enter a valid phone number");
            alert.showAndWait();
            return false;

        }

    }
}
