/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.binder.gui;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import tn.esprit.binder.IService.IServiceCourse;
import tn.esprit.binder.IService.IServiceSubject;
import tn.esprit.binder.Iservices.club.IActivityService;
import tn.esprit.binder.Iservices.club.IClubService;
import tn.esprit.binder.Iservices.club.IParentService;
import tn.esprit.binder.entities.Classes;
import tn.esprit.binder.entities.Course;
import tn.esprit.binder.entities.FOSUser;
import tn.esprit.binder.entities.Pupils;
import tn.esprit.binder.entities.Subject;
import tn.esprit.binder.entities.TimeTable;
import tn.esprit.binder.entities.clubs.activity;
import tn.esprit.binder.entities.clubs.club;
import tn.esprit.binder.entities.clubs.parent;
import tn.esprit.binder.entities.clubs.quantity;
import tn.esprit.binder.entities.grades.Exams;
import tn.esprit.binder.entities.grades.Grade;
import tn.esprit.binder.services.CourseService;
import tn.esprit.binder.services.ServicesClasses;
import tn.esprit.binder.services.ServicesFOS;
import tn.esprit.binder.services.ServicesPupils;
import tn.esprit.binder.services.SubjectService;
import tn.esprit.binder.services.club.ActivityService;
import tn.esprit.binder.services.club.ClubService;
import tn.esprit.binder.services.club.ParentService;
import tn.esprit.binder.services.gradeservice.ServicesExam;
import tn.esprit.binder.services.gradeservice.ServicesGrade;
import tn.esprit.binder.utils.MyConnection;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class HomeBinderController implements Initializable {

    @FXML
    private Button btClass;
    @FXML
    private AnchorPane anchorClass;
    @FXML
    private AnchorPane anchorHome;
    @FXML
    private TableView<Classes> tableClass;
    @FXML
    private TableColumn<Classes, Integer> idClass;
    @FXML
    private TableColumn<Classes, String> nameClass;
    @FXML
    private TableColumn<Classes, String> session;
    @FXML
    private Button btAdd;
    @FXML
    private Button btUpdate;
    @FXML
    private Button btDelete;
    @FXML
    private Button btTimeTable;
    @FXML
    private Button btPupilTable;
    @FXML
    private AnchorPane anchorClassGestion;
    /**
     * Initializes the controller class.
     */

    //Declaration Ichrak 
    private Stage stage;
    public Classes classes;
    public List<Classes> classesL;
    public TableColumn<?, ?> subject;
    TimeTable t = new TimeTable();
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD, BaseColor.RED);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    Classes gr;

    public TimeTable time = new TimeTable();

    public ObservableList<TimeTable> timeTableList;
    final List<TimeTable> timeL = new ArrayList<>();

    int id_class;

    // affichage Exam Asma
    @FXML
    private TableView<Exams> tableExams;
    @FXML
    private TableColumn<Exams, String> Subject;
    @FXML
    private TableColumn<Exams, String> file;
    @FXML
    private TableColumn<Exams, Integer> duration;
    @FXML
    private TableColumn<Exams, String> date;
    @FXML
    private Button btAjouter;
    @FXML
    private Button btSupprimer;
    @FXML
    private Button btModifier;
    @FXML
    private Button btGrade;
    @FXML
    private Button exportToXL;
    @FXML
    private TextField tfRechecher;
    @FXML
    private Button btExam;

    public Exams exa = new Exams();

    public List<Exams> Examss;
    ServicesExam exam = new ServicesExam();

    private Connection cnx;
    private Statement ste;
    @FXML
    private AnchorPane listexams;
    @FXML
    private AnchorPane listgrades;
    @FXML

    private TableView<Grade> tableGrade;
    @FXML
    private TableColumn<Grade, Integer> Examname;
    @FXML
    private TableColumn<Grade, Integer> grade;
    @FXML
    private TableColumn<Grade, String> Teacher;
    @FXML
    private TableColumn<Grade, String> pupil;
    @FXML
    private Button btAjouter1;
    @FXML
    private Button btSupprimer1;
    @FXML
    private Button btModifier1;
    @FXML
    private Button exportToXL1;
    @FXML
    private RadioButton rbTous;
    @FXML
    private ToggleGroup ConsulterValidation;
    @FXML
    private RadioButton rbsupdix;
    @FXML
    private ToggleGroup ConsulterValidation1;
    @FXML
    private RadioButton rbinfdix;
    @FXML
    private ToggleGroup ConsulterValidation2;
    @FXML
    private TextField tfRechecher1;
    @FXML
    private Button btGetStatistics;

    ToggleGroup group = new ToggleGroup();
    public Grade gr1 = new Grade();
    public List<Grade> Grades;
    ServicesGrade ser = new ServicesGrade();
    @FXML
    private AnchorPane CID;

    //samar-----------
    @FXML
    private TableView<club> tab;

    @FXML
    private TableColumn<club, String> nameC;
    @FXML
    private TableColumn<club, String> specialtyC;
    @FXML
    private TableColumn<club, String> responsibleC;
    private TableColumn<club, String> membersC;
    public List<String> listT;

    private IClubService annonceService;
    ObservableList<club> oblist = FXCollections.observableArrayList();
    ClubService es = new ClubService();

    @FXML
    private TextField nameAJ;
    private TextField specialtyAJ;

    @FXML
    private TextField rechercheBar;

    PreparedStatement pst;
    Connection cn = MyConnection.getInstance().getCnx();
    Statement st; //execute la req
    ObservableList<String> list = FXCollections.observableArrayList("music", "sports", "IT", "lecture", "arts", "science");
    @FXML
    private ComboBox<String> specialtyCom;
    @FXML
    private ComboBox<String> teacherC;
    @FXML
    private ProgressIndicator pi;
    private quantity q = new quantity();
    @FXML
    private Button export;
    @FXML
    private Button goToClub;
    @FXML
    private AnchorPane AID;
    @FXML
    private TableView<activity> tabA;
    @FXML
    private TableColumn<activity, String> aboutA;
    @FXML
    private TableColumn<activity, String> durationA;
    @FXML
    private TableColumn<activity, String> locationA;
    @FXML
    private TableColumn<activity, String> dateA;
    @FXML
    private TableColumn<activity, String> clubA;
    @FXML
    private TextField aboutAJ;
    @FXML
    private TextField durationAJ;
    @FXML
    private TextField locationAJ;
    @FXML
    private ComboBox<String> clubAJ;
    @FXML
    private DatePicker dp;
    @FXML
    private TextField rechercheBarA;
    @FXML
    private AnchorPane PID;
    @FXML
    private TextField rechercheBarP;
    @FXML
    private TextField nameAj;
    @FXML
    private TextField mailAJ;
    @FXML
    private TextField phoneAJ;
    @FXML
    private TableView<parent> tabP;
    @FXML
    private TableColumn<parent, String> nameP;
    @FXML
    private TableColumn<parent, String> mailP;
    @FXML
    private TableColumn<parent, String> phoneP;
    @FXML
    private Button goToActivity;
    @FXML
    private Button goToParent;
    public List<String> listA;
    public List<String> listAD;

    public List<String> listP;

    private IParentService annonceServiceP;
    ObservableList<parent> oblistP = FXCollections.observableArrayList();
    ParentService esP = new ParentService();

    private IActivityService annonceServiceA;
    ObservableList<activity> oblistA = FXCollections.observableArrayList();
    ActivityService esA = new ActivityService();
    @FXML
    private Button goToCour;
    @FXML
    private Button goToSubject;
    @FXML
    private Button goToAbscense;
    @FXML
    private Button goToNotification;
    @FXML
    private AnchorPane anchorChour;
    @FXML
    private AnchorPane anchorSubject;
    @FXML
    private AnchorPane anchorAb;
    @FXML
    private AnchorPane anchorNotif;
    @FXML
    private TableView<Course> tvCourses;
    @FXML
    private TableColumn<Course, URL> content;
    @FXML
    private TableColumn<Course, String> teacher;
    @FXML
    private Button btnUpd;
    @FXML
    private Button btnDel;
    @FXML
    private TextField tfuc;
    @FXML
    private TextField tfut;
    @FXML
    private Button btnAdd;
    @FXML
    private TextField tfSearch;
    @FXML
    private ComboBox<String> cbox;
    @FXML
    private Button btnBrowser;
    @FXML
    private WebView wv;
    @FXML
    private Button btnExit;
    @FXML
    private Label l2;
    @FXML
    private TableView<Subject> tvSubjects;
    @FXML
    private TableColumn<Subject, String> nameColomn;
    @FXML
    private TableColumn<Subject, String> teacherscolomn;
    @FXML
    private TableColumn<Subject, String> classescolomn;
    @FXML
    private Button btnUpd2;
    @FXML
    private Button btnDel2;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfTeachers;
    @FXML
    private TextField tfClasses;
    @FXML
    private Button btnAdd2;
    @FXML
    private TextField tfSearch2;

    private IServiceCourse annonceServiceC;
    ObservableList<Course> oblistC = FXCollections.observableArrayList();
    CourseService esC = new CourseService();

    private IServiceSubject annonceService2;
    ObservableList<Subject> oblist2 = FXCollections.observableArrayList();
    SubjectService ss = new SubjectService();

    @FXML
    private TableColumn<Course, String> subjectW;
    public List<String> sub;
    @FXML
    private ComboBox<String> pupilCom;
    public List<String> listPu;
    @FXML
    private Button btGotoTeacher;
    @FXML
    private AnchorPane anchorTeacher;
    @FXML
    private TextField rechercheBarT;
    @FXML
    private TextField tfNameT;
    @FXML
    private TextField tfMail;
    @FXML
    private TextField tfPhone;
    @FXML
    private TableView<?> tabT;
    @FXML
    private TableColumn<?, ?> nameT;
    @FXML
    private TableColumn<?, ?> mailT;
    @FXML
    private TableColumn<?, ?> phoneT;
    @FXML
    private TableColumn<?, ?> specialtyT;
    @FXML
    private TextField tfSpec;
    @FXML
    private TextField tfPassword;

    public HomeBinderController() {
        cnx = MyConnection.getInstance().getCnx();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //asma-----------------------------------------
        try {
            Subject.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getSubject()));
            file.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFile()));
            duration.setCellValueFactory(new PropertyValueFactory<Exams, Integer>("duration"));
            date.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDate()));
            Examss = new ServicesExam().readAll();
            tableExams.setItems((ObservableList<Exams>) Examss);
            listexams.setVisible(false);

            Examname.setCellValueFactory(new PropertyValueFactory<Grade, Integer>("examname"));
            grade.setCellValueFactory(new PropertyValueFactory<Grade, Integer>("Grade"));
            Teacher.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTeacher()));
            pupil.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getPupil()));
            Grades = new ServicesGrade().readAll();
            tableGrade.setItems((ObservableList<Grade>) Grades);
            listgrades.setVisible(false);

            //ichrak----------------------------------------------------------------------
            idClass.setCellValueFactory(new PropertyValueFactory<Classes, Integer>("id"));
            nameClass.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNameClass()));
            session.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getSession()));

            classesL = new ServicesClasses().readAll();
            tableClass.setItems((ObservableList<Classes>) classesL);
            //---------------------------------samar
            CID.setVisible(false);
            AID.setVisible(false);
            PID.setVisible(false);
            annonceServiceP = new ParentService();
            oblistP = FXCollections.observableArrayList(esP.getAll());

            ObservableList observableListP = FXCollections.observableArrayList(oblistP);
            tabP.setItems(observableListP);
            //idA.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameP.setCellValueFactory(new PropertyValueFactory<>("name"));
            mailP.setCellValueFactory(new PropertyValueFactory<>("mail"));
            phoneP.setCellValueFactory(new PropertyValueFactory<>("phone"));
            listPu = new ParentService().readAllP();
            pupilCom.setItems(FXCollections.observableArrayList(listPu));

            //activity
            annonceServiceA = new ActivityService();
            oblistA = FXCollections.observableArrayList(esA.getAll());

            ObservableList observableListA = FXCollections.observableArrayList(oblistA);
            tabA.setItems(observableListA);
            //idA.setCellValueFactory(new PropertyValueFactory<>("id"));
            aboutA.setCellValueFactory(new PropertyValueFactory<>("about"));
            durationA.setCellValueFactory(new PropertyValueFactory<>("duration"));
            locationA.setCellValueFactory(new PropertyValueFactory<>("location"));

            dateA.setCellValueFactory(new PropertyValueFactory<>("dateA"));
            clubA.setCellValueFactory(new PropertyValueFactory<>("clubAC"));
            listA = new ActivityService().readAllT();
            clubAJ.setItems(FXCollections.observableArrayList(listA));

            //club
            q.setLoad(0);
            q.loadProperty().addListener(new ChangeListener<Object>() {
                @Override
                public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
                    pi.progressProperty().bind(q.loadProperty());//unidirectional bnding
                }

            });
            specialtyCom.setItems(list);

            annonceService = new ClubService();
            oblist = FXCollections.observableArrayList(es.getAll());

            ObservableList observableListC = FXCollections.observableArrayList(oblist);
            tab.setItems(observableListC);
            // idC.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameC.setCellValueFactory(new PropertyValueFactory<>("name"));
            specialtyC.setCellValueFactory(new PropertyValueFactory<>("specialty"));
            responsibleC.setCellValueFactory(new PropertyValueFactory<>("responsible"));
            listT = new ClubService().readAllT();

            teacherC.setItems(FXCollections.observableArrayList(listT));

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        rbTous.setToggleGroup(group);
        rbinfdix.setToggleGroup(group);
        rbsupdix.setToggleGroup(group);

        //samar ---------------------------
        //
        anchorChour.setVisible(false);
        anchorSubject.setVisible(false);
        anchorAb.setVisible(false);
        anchorNotif.setVisible(false);

        // TODO
        //wael---------------------------------------------------------------------------------------------
        annonceServiceC = new CourseService();
        oblistC = FXCollections.observableArrayList(esC.showAll());

        ObservableList observableList = FXCollections.observableArrayList(oblistC);
        tvCourses.setItems(observableList);
        subjectW.setCellValueFactory(new PropertyValueFactory<>("subject"));
        content.setCellValueFactory(new PropertyValueFactory<>("content"));
        teacher.setCellValueFactory(new PropertyValueFactory<>("teacher"));

        try {
            // TODO
            sub = new CourseService().readAllSubject();
        } catch (SQLException ex) {
            Logger.getLogger(HomeBinderController.class.getName()).log(Level.SEVERE, null, ex);
        }

        cbox.setItems(FXCollections.observableArrayList(sub));
        wv.setVisible(false);
        /////////

        annonceService2 = new SubjectService();
        oblist2 = FXCollections.observableArrayList(ss.showAllSub());

        ObservableList observableListS = FXCollections.observableArrayList(oblist2);
        tvSubjects.setItems(observableListS);
        nameColomn.setCellValueFactory(new PropertyValueFactory<>("name"));
        teacherscolomn.setCellValueFactory(new PropertyValueFactory<>("teachers"));
        classescolomn.setCellValueFactory(new PropertyValueFactory<>("classes"));
        // TODO
    }

    @FXML
    private void btClassOnClick(ActionEvent event) {
        anchorHome.setVisible(false);
        anchorClass.setVisible(true);
        anchorClassGestion.setVisible(true);
        listexams.setVisible(false);
        listgrades.setVisible(false);
        CID.setVisible(false);
        AID.setVisible(false);
        PID.setVisible(false);
        anchorChour.setVisible(false);
        anchorSubject.setVisible(false);
        anchorAb.setVisible(false);
        anchorNotif.setVisible(false);

    }

    @FXML
    private void btAddOnClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addClass.fxml"));
            Parent root;
            root = loader.load();
            AddClassController afc = loader.getController();
            afc.getStage(this.stage);
            tableClass.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void btUpdateOnClick(ActionEvent event) throws ParseException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Update Class");
        alert.setHeaderText("Updating  " + tableClass.getSelectionModel().getSelectedItem().getNameClass());
        alert.setContentText("Would you like to update  " + tableClass.getSelectionModel().getSelectedItem().getNameClass() + "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                classes = tableClass.getSelectionModel().getSelectedItem();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("updateClass.fxml"));
                Parent root = loader.load();
                UpdateClassController classController = loader.getController();
                classController.setClass(classes);
                tableClass.getScene().setRoot(root);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        if (result.get() == ButtonType.CANCEL) {
            alert.close();
        }
    }

    @FXML
    private void btDeleteOnClick(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Class");
            alert.setHeaderText("Delete  " + tableClass.getSelectionModel().getSelectedItem().getNameClass());
            alert.setContentText("Would you like to delete " + tableClass.getSelectionModel().getSelectedItem().getNameClass() + "?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                new ServicesClasses().deleteClass(tableClass.getSelectionModel().getSelectedItem().getId());
                classesL = new ServicesClasses().readAll();
                tableClass.getItems().clear();
                tableClass.getItems().addAll(classesL);
            }

            if (result.get() == ButtonType.CANCEL) {
                alert.close();
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void btTimeTableOnClick(ActionEvent event) throws ParseException, SQLException {

        try {

            classes = tableClass.getSelectionModel().getSelectedItem();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("addtimeTable.fxml"));

            Parent root = loader.load();
            AddTimeTableController ugc = loader.getController();

            ugc.setGrade(classes);

            tableClass.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @FXML
    private void btPupilsOnClick(ActionEvent event) throws ParseException, SQLException {
        try {

            classes = tableClass.getSelectionModel().getSelectedItem();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("gestionPupil.fxml"));

            Parent root = loader.load();
            GestionPupilController ugc = loader.getController();

            ugc.setGrade(classes);

            tableClass.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void getStage(Stage stage) {
        this.stage = stage;
    }

    public void setTimeTable(Classes c) throws ParseException {

        anchorClass.setVisible(true);
        anchorClassGestion.setVisible(true);
        anchorHome.setVisible(false);

    }

    public void setPupil(Classes c) throws ParseException {

        anchorClass.setVisible(true);
        anchorClassGestion.setVisible(true);
        anchorHome.setVisible(false);

    }

    //------------------------------------Asma-------------------------------------------------------
    @FXML
    private void btAjouterOnClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AjoutExam.fxml"));
            Parent root;
            root = loader.load();
            AjouterExamenController aec = loader.getController();
            aec.getStage(this.stage);
            tableExams.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void btSupprimerOnClick(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Deleting Exam");
            alert.setHeaderText("Deleting " + tableExams.getSelectionModel().getSelectedItem().getId());
            alert.setContentText("Are you sure you want to delete the " + tableExams.getSelectionModel().getSelectedItem().getSubject() + " exam?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                new ServicesExam().deleteExams(tableExams.getSelectionModel().getSelectedItem().getIdint());
                Examss = new ServicesExam().readAll();
                actualiserTable();
            }
            if (result.get() == ButtonType.CANCEL) {
                alert.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void btModifierOnClick(ActionEvent event) throws SQLException {

        try {
            exa = tableExams.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateExamen.fxml"));
            Parent root = loader.load();
            UpdateExamenController uec = loader.getController();
            uec.setExam(exa);
            tableExams.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @FXML
    private void btExportOnClick(ActionEvent event) {
        try {
            String query = "Select * from exam";
            ste = cnx.prepareStatement(query);
            ResultSet rs = ste.executeQuery(query);
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet("exam details");
            XSSFRow header = sheet.createRow(0);
            header.createCell(0).setCellValue("Id");
            header.createCell(1).setCellValue("Subject");
            header.createCell(2).setCellValue("File");
            header.createCell(3).setCellValue("Duration");
            header.createCell(4).setCellValue("Date");

            int index = 1;
            while (rs.next()) {
                XSSFRow row = sheet.createRow(index);
                row.createCell(0).setCellValue(rs.getString("id"));
                row.createCell(1).setCellValue(rs.getString("subject"));
                row.createCell(2).setCellValue(rs.getString("file"));
                row.createCell(3).setCellValue(rs.getString("duration"));
                row.createCell(4).setCellValue(rs.getString("date"));
                index++;
            }
            FileOutputStream fileout = new FileOutputStream("Exams.xlsx");
            wb.write(fileout);
            fileout.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Exam details exported in Excel sheet");
            alert.showAndWait();
            ste.close();
            rs.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void tfRechecherOnKeyReleased(KeyEvent event) {
        try {
            Examss = new ServicesExam().search(tfRechecher.getText());
            actualiserTable();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    public void btExportOnClick1(ActionEvent event) {

        try {
            String query = "Select * from Grade";
            ste = cnx.prepareStatement(query);
            ResultSet rs = ste.executeQuery(query);
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet("grade details");
            XSSFRow header = sheet.createRow(0);
            header.createCell(0).setCellValue("Id");
            header.createCell(1).setCellValue("Exam name");
            header.createCell(2).setCellValue("Grade");
            header.createCell(3).setCellValue("Teacher");
            header.createCell(4).setCellValue("Pupil");
            int index = 1;
            while (rs.next()) {
                XSSFRow row = sheet.createRow(index);
                row.createCell(0).setCellValue(rs.getString("id"));
                row.createCell(1).setCellValue(rs.getString("examname"));
                row.createCell(2).setCellValue(rs.getString("grade"));
                row.createCell(3).setCellValue(rs.getString("teacher"));
                row.createCell(4).setCellValue(rs.getString("pupil"));
                index++;
            }
            FileOutputStream fileout = new FileOutputStream("Grades.xlsx");
            wb.write(fileout);
            fileout.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Grade details exported in Excel sheet");
            alert.showAndWait();
            ste.close();
            rs.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        ;
    }

    @FXML
    private void btExamOnClick(ActionEvent event) {

        anchorHome.setVisible(false);
        anchorClassGestion.setVisible(true);
        listexams.setVisible(true);
        listgrades.setVisible(false);
        CID.setVisible(false);
        AID.setVisible(false);
        PID.setVisible(false);
        anchorChour.setVisible(false);
        anchorSubject.setVisible(false);
        anchorAb.setVisible(false);
        anchorNotif.setVisible(false);
    }

    @FXML
    private void btGradeOnClick(ActionEvent event) {
        anchorHome.setVisible(false);
        anchorClassGestion.setVisible(true);
        listexams.setVisible(false);
        listgrades.setVisible(true);
        CID.setVisible(false);
        AID.setVisible(false);
        PID.setVisible(false);
        anchorChour.setVisible(false);
        anchorSubject.setVisible(false);
        anchorAb.setVisible(false);
        anchorNotif.setVisible(false);

    }

    public void actualiserTable1() {
        tableGrade.getItems().clear();
        tableGrade.getItems().addAll(FXCollections.observableList(Grades));
    }

    public void actualiserTable() {
        tableExams.getItems().clear();
        tableExams.getItems().addAll(FXCollections.observableList(Examss));
    }

    @FXML
    private void rbTousOnSelect(ActionEvent event) {
        if (rbTous.isSelected() && !rbinfdix.isSelected() && !rbsupdix.isSelected()) {
            try {
                Grades = new ServicesGrade().readAll();
                actualiserTable();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    @FXML
    private void rbsupdixOnSelect(ActionEvent event) {
        if (!rbTous.isSelected() && !rbinfdix.isSelected() && rbsupdix.isSelected()) {
            Grades = new ServicesGrade().getsupdix();
            actualiserTable();
        }
    }

    @FXML
    private void rbinfdixOnSelect(ActionEvent event) {
        if (!rbTous.isSelected() && rbinfdix.isSelected() && !rbsupdix.isSelected()) {
            Grades = new ServicesGrade().getinfdix();
            actualiserTable();
        }
    }

    @FXML
    private void btGetStatisticsOnClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PieChart.fxml"));
            Parent root;
            root = (Parent) loader.load();
            PieChartController afc = loader.getController();
            afc.getStage(this.stage);
            tableGrade.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void btAjouterOnClick1(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ajoutGrade.fxml"));
            Parent root;
            root = loader.load();
            AjouterGradeController afc = loader.getController();
            afc.getStage(this.stage);
            tableGrade.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void btSupprimerOnClick1(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Deleting grade");
            alert.setHeaderText("Deleting" + tableGrade.getSelectionModel().getSelectedItem().getId());
            alert.setContentText("Are you sure you want to delete " + tableGrade.getSelectionModel().getSelectedItem().getPupil() + "'s grade?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                new ServicesGrade().deleteGrades(tableGrade.getSelectionModel().getSelectedItem().getIdInt());
                Grades = new ServicesGrade().readAll();
                actualiserTable1();
            }
            if (result.get() == ButtonType.CANCEL) {
                alert.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void btModifierOnClick1(ActionEvent event) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Update grade");
        alert.setHeaderText("Updating" + gr1.getId());
        alert.setContentText("Would you like to update the grade " + tableGrade.getSelectionModel().getSelectedItem().getId() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                gr1 = tableGrade.getSelectionModel().getSelectedItem();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateGrade.fxml"));
                Parent root = loader.load();
                UpdateGradeController ugc = loader.getController();
                ugc.setGrade(gr1);
                tableGrade.getScene().setRoot(root);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        if (result.get() == ButtonType.CANCEL) {
            alert.close();
        }
    }

    public void setGrade(Grade g) throws ParseException {

        anchorClass.setVisible(true);
        anchorClassGestion.setVisible(false);
        anchorHome.setVisible(false);
        listexams.setVisible(false);
        listgrades.setVisible(true);

    }

    public void setExam(Exams e) throws ParseException {

        anchorClass.setVisible(true);
        anchorClassGestion.setVisible(false);
        anchorHome.setVisible(false);
        listexams.setVisible(true);
        listgrades.setVisible(false);

    }

    //sam buttons
    @FXML
    private void supprimer(ActionEvent event) {
        es = new ClubService();
        int index = tab.getSelectionModel().getSelectedItem().getId();
        //System.out.println(index);
        es.deleteClub(index);
        AfficherAll();
    }

    @FXML
    private void rechercher(ActionEvent event) {

        if (!rechercheBar.getText().isEmpty()) {
            tab.setVisible(true);
            annonceService = new ClubService();
            List<club> myList = annonceService.rechercheClub(rechercheBar.getText());
            ObservableList<club> observableList = FXCollections.observableArrayList();

            //idC.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameC.setCellValueFactory(new PropertyValueFactory<>("name"));
            specialtyC.setCellValueFactory(new PropertyValueFactory<>("specialty"));
            responsibleC.setCellValueFactory(new PropertyValueFactory<>("responsible"));

            myList.forEach(e -> {

                observableList.add(e);
                // System.out.println(observableList);
            });
            tab.setItems(observableList);
        } else {
            if (rechercheBar.getText().isEmpty()) {
                tab.getItems().clear();
                tab.getItems().addAll(annonceService.getAll());
            }

        }
    }

    @FXML
    private void ajouter(ActionEvent event) throws SQLException {
        if (validateName()) {

            String name = nameAJ.getText();
            String specialty = specialtyCom.getValue();
            String responsible = teacherC.getValue();

            ClubService sl = new ClubService();
            club l = new club(name, specialty, responsible);
            sl.addClub(l);
            AfficherAll();

            q.setLoad(q.getLoad() + 0.01);//means 10%
        }
    }

    private boolean validateName() {
        Pattern p = Pattern.compile("[a-zA-Z]+");
        Matcher m = p.matcher(nameAJ.getText());
        if (m.find() && m.group().equals(nameAJ.getText())) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("validate your name");
            alert.setHeaderText(null);
            alert.setContentText("please enter a validate name");
            alert.showAndWait();
            return false;

        }

    }

    @FXML
    private void exportToXL(ActionEvent event) throws SQLException, FileNotFoundException, IOException {
        try {
            String query = "Select * from club";
            PreparedStatement statement;
            statement = cn.prepareStatement(query);
            pst = cn.prepareStatement(query);
            //  st = (Statement) pst.executeQuery();
            ResultSet st = pst.executeQuery();
            // ResultSet st = statement.executeQuery();
            //st =  (Statement) pst.executeQuery();

            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet("club details");
            XSSFRow header = sheet.createRow(0);
            header.createCell(0).setCellValue("id");
            header.createCell(1).setCellValue("name");
            header.createCell(2).setCellValue("specialty");
            header.createCell(3).setCellValue("responsible");

            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.setColumnWidth(3, 256 * 25);
            sheet.setColumnWidth(4, 256 * 75);
            sheet.setZoom(150);

            // ResultSet st = statement.executeQuery();
            int index = 1;
            while (st.next()) {
                XSSFRow row = sheet.createRow(index);
                row.createCell(0).setCellValue(st.getString("id"));
                row.createCell(1).setCellValue(st.getString("name"));
                row.createCell(2).setCellValue(st.getString("specialty"));
                row.createCell(3).setCellValue(st.getString("responsible"));

                index++;

            }

            FileOutputStream fileout = new FileOutputStream("clubdetails.xlsx");
            wb.write(fileout);
            fileout.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("database details");
            alert.setHeaderText(null);
            alert.setContentText("club details exported");
            alert.showAndWait();

            pst.close();
            st.close();

        } catch (SQLException ex) {

        }
    }

    private void specialtySelection(ActionEvent event) {

        String choice;
        choice = specialtyCom.getValue();

    }

    @FXML
    private void modifier(ActionEvent event) {

        annonceService = new ClubService();
        try {
            club aa = tab.getSelectionModel().getSelectedItem();
            System.out.println(aa.getId() + "551");
            //  int id_c1 = Integer.parseInt(aa.getId_c());

            int id = aa.getId();
            String name = nameAJ.getText();

            String specialty = specialtyCom.getValue();
            String responsible = teacherC.getValue();

            club a = new club(id, name, specialty, responsible);
            ClubService s2 = new ClubService();
            ClubService s3 = new ClubService();

            club l = new club(name, specialty, responsible);
            s3.deleteClubmodified(id);
            s2.addClubmodify(l);
            AfficherAll();

            try {
                Connection con = (Connection) MyConnection.getInstance().getCnx();

                ResultSet rs = con.createStatement().executeQuery("select * from club");
                while (rs.next()) {
                    oblist.add(new club(rs.getInt("id"), rs.getString("name"), rs.getString("specialty"), rs.getString("responsible")));
                }
            } catch (SQLException ex) {
                Logger.getLogger(ClubController.class.getName()).log(Level.SEVERE, null, ex);
            }

            tab.setItems(oblist);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    private void AfficherAll() {

        annonceService = new ClubService();
        oblist = FXCollections.observableArrayList(es.getAll());

        ObservableList observableList = FXCollections.observableArrayList(oblist);
        tab.setItems(observableList);
        // idC.setCellValueFactory(new PropertyValueFactory<>("id"));
        specialtyC.setCellValueFactory(new PropertyValueFactory<>("specialty"));
        responsibleC.setCellValueFactory(new PropertyValueFactory<>("responsible"));

        nameC.setCellValueFactory(new PropertyValueFactory<>("name"));

    }

    @FXML
    private void goToClubOnClick(ActionEvent event) {
        anchorHome.setVisible(false);
        anchorClassGestion.setVisible(false);
        listexams.setVisible(false);
        listgrades.setVisible(false);
        CID.setVisible(true);
        AID.setVisible(false);
        PID.setVisible(false);
        anchorChour.setVisible(false);
        anchorSubject.setVisible(false);
        anchorAb.setVisible(false);
        anchorNotif.setVisible(false);
    }

    @FXML
    private void rechercherA(ActionEvent event) {
        if (!rechercheBarA.getText().isEmpty()) {
            tabA.setVisible(true);
            annonceServiceA = new ActivityService();
            List<activity> myList = annonceServiceA.rechercheActivity(rechercheBarA.getText());
            ObservableList<activity> observableListA = FXCollections.observableArrayList();

            // idA.setCellValueFactory(new PropertyValueFactory<>("id"));
            aboutA.setCellValueFactory(new PropertyValueFactory<>("about"));
            locationA.setCellValueFactory(new PropertyValueFactory<>("location"));
            durationA.setCellValueFactory(new PropertyValueFactory<>("duration"));

            dateA.setCellValueFactory(new PropertyValueFactory<>("dateA"));
            clubA.setCellValueFactory(new PropertyValueFactory<>("clubAC"));

            myList.forEach(e -> {

                observableListA.add(e);
                // System.out.println(observableList);
            });
            tabA.setItems(observableListA);
        } else {
            if (rechercheBar.getText().isEmpty()) {
                tab.getItems().clear();
                tab.getItems().addAll(annonceService.getAll());
            }

        }
    }

    @FXML
    private void supprimerA(ActionEvent event) {
        esA = new ActivityService();
        int index = tabA.getSelectionModel().getSelectedItem().getId();
        //System.out.println(index);
        esA.deleteActivity(index);
        AfficherAllA();
    }

    @FXML
    private void modifierA(ActionEvent event) {
        annonceServiceA = new ActivityService();
        try {
            activity aa = tabA.getSelectionModel().getSelectedItem();
            System.out.println(aa.getId() + "551");
            //  int id_c1 = Integer.parseInt(aa.getId_c());

            int id = aa.getId();
            String about = aboutAJ.getText();

            String duration = durationAJ.getText();
            String location = locationAJ.getText();
            // String club = clubAJ.getValue();
            String name = clubAJ.getValue();

            LocalDate date = dp.getValue();

            ActivityService sl = new ActivityService();

            int clubId = sl.getClubId(name);

            activity a = new activity(id, clubId, about, duration, location, date.toString(), name);
            ActivityService s2 = new ActivityService();
            ActivityService s3 = new ActivityService();

            activity l = new activity(clubId, about, duration, location, date.toString(), name);
            s3.deleteActivitymodified(id);
            s2.addActivitymodify(l);
            AfficherAllA();

            try {
                Connection con = (Connection) MyConnection.getInstance().getCnx();

                ResultSet rs = con.createStatement().executeQuery("select * from activity");
                while (rs.next()) {
                    oblistA.add(new activity(rs.getInt("id"), rs.getInt("id_club"), rs.getString("about"), rs.getString("duration"), rs.getString("location"), rs.getString("dateA"), rs.getString("clubAC")));
                }
            } catch (SQLException ex) {
                Logger.getLogger(ActivityController.class.getName()).log(Level.SEVERE, null, ex);
            }

            tab.setItems(oblist);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @FXML
    private void addC(ActionEvent event) throws IOException {
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Club.fxml"));
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
    }

    @FXML
    private void rechercherP(ActionEvent event) {
        if (!rechercheBarP.getText().isEmpty()) {
            tabP.setVisible(true);
            annonceServiceP = new ParentService();
            List<parent> myList = annonceServiceP.rechercheParent(rechercheBarP.getText());
            ObservableList<parent> observableList = FXCollections.observableArrayList();

            // idA.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameP.setCellValueFactory(new PropertyValueFactory<>("name"));
            mailP.setCellValueFactory(new PropertyValueFactory<>("mail"));
            phoneP.setCellValueFactory(new PropertyValueFactory<>("phone"));

            myList.forEach(e -> {

                observableList.add(e);
                // System.out.println(observableList);
            });
            tabP.setItems(observableList);
        } else {
            if (rechercheBarP.getText().isEmpty()) {
                tabP.getItems().clear();
                tabP.getItems().addAll(annonceServiceP.getAll());
            }

        }
    }

    @FXML
    private void ajouterP(ActionEvent event) throws SQLException {
        /*
          String name = namePupTxt.getText();
            String date = datePicker.getValue().toString();
            String email = emailTxt.getText();

            pupil = new Pupils(name, date, email, id_class);
            //FOSUser u = new FOSUser(a.getId(), noma.getText(), noma.getText(), maila.getText(),
            maila.getText(), (byte)1, null, enc.encryptPassword(villea.getText()), null, null, null, "a:1:{i:0;s:16:\"ROLE_FOURNISSEUR\";}");  
            FOSUser u = new FOSUser(name, name, email,
            email, (byte) 1, null, name, java.sql.Date.valueOf(LocalDate.now()), null, java.sql.Date.valueOf(LocalDate.now()), "a:1:{i:0;s:10:\"ROLE_PUPILS\";}");
            new ServicesFOS().ajouterUser(u);
            pupil.setId_user(new ServicesFOS().getUserByUsername(u.getUserName()).getId());
            new ServicesPupils().addPupils(pupil);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gestionPupil.fxml"));
            Parent root = loader.load();
            GestionPupilController ugc = loader.getController();
            ugc.setGrade(gr);
            btAdd.getScene().setRoot(root);
            error.setVisible(false);
          errorEmail.setVisible(false);
         */
        if (validateMail() && validatePhone()) {
            //String about =aboutAJ.getText();
            String name = nameAj.getText();
            String phone = phoneAJ.getText();

            String mail = mailAJ.getText();
            String pupilname = pupilCom.getValue();
            parent p = new parent(name, mail, phone);
            FOSUser u = new FOSUser(name, name, mail,
                    mail, (byte) 1, null, name, java.sql.Date.valueOf(LocalDate.now()), null, java.sql.Date.valueOf(LocalDate.now()), "a:1:{i:0;s:10:\"ROLE_PUPILS\";}");
            new ServicesFOS().ajouterUser(u);
            p.setId_user(new ServicesFOS().getUserByUsername(u.getUserName()).getId());

            ParentService sl = new ParentService();
            int pupilId = sl.getPupilId(pupilname);
            System.out.println(pupilId);
            sl.addParent(p);
            int idm=new ParentService().getParentByUsername(name, mail).getId();
            System.out.println(idm+"llllllllll");
            new ServicesPupils().updatePupilsP(pupilId, idm);
            AfficherAllP();

        }
    }

    @FXML
    private void modifierP(ActionEvent event) {

        annonceServiceP = new ParentService();
        try {
            parent aa = tabP.getSelectionModel().getSelectedItem();
            System.out.println(aa.getId() + "551");
            //  int id_c1 = Integer.parseInt(aa.getId_c());

            int id = aa.getId();
            String name = nameAj.getText();

            String mail = mailAJ.getText();
            String phone = phoneAJ.getText();

            String pupilname = pupilCom.getValue();

            ParentService sl = new ParentService();
            int pupilId = sl.getPupilId(pupilname);
            parent a = new parent(name, mail, phone, pupilId);
            ParentService s2 = new ParentService();
            ParentService s3 = new ParentService();

            parent l = new parent(name, mail, phone, pupilId);
            s3.deleteParentmodified(id);
            s2.addParentmodify(l);
            AfficherAllP();

            /* try {
            Connection con =(Connection) MyConnection.getInstance().getCnx();
            
            ResultSet rs = con.createStatement().executeQuery("select * from parent");
          while(rs.next()){
           //oblist.add(new parent(rs.getInt("id"),rs.getString("name"),rs.getString("mail"),rs.getString("phone")));}
             oblistP.add(new parent(rs.getString("name"),rs.getInt("id"),rs.getString("mail"),rs.getString("phone")));}
          } catch (SQLException ex) {
            Logger.getLogger(ParentFormController.class.getName()).log(Level.SEVERE, null, ex);
        }*/
            tabP.setItems(oblistP);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @FXML
    private void supprimerP(ActionEvent event) {
        esP = new ParentService();
        int index = tabP.getSelectionModel().getSelectedItem().getId();
        //System.out.println(index);
        esP.deleteParent(index);
        AfficherAllP();
    }

    @FXML
    private void goToActivityOnClick(ActionEvent event) {
        anchorHome.setVisible(false);
        anchorClassGestion.setVisible(false);
        listexams.setVisible(false);
        listgrades.setVisible(false);
        CID.setVisible(false);
        AID.setVisible(true);
        PID.setVisible(false);
        anchorChour.setVisible(false);
        anchorSubject.setVisible(false);
        anchorAb.setVisible(false);
        anchorNotif.setVisible(false);
    }

    @FXML
    private void goToParentOnClick(ActionEvent event) {
        anchorHome.setVisible(false);
        anchorClassGestion.setVisible(false);
        listexams.setVisible(false);
        listgrades.setVisible(false);
        CID.setVisible(false);
        AID.setVisible(false);
        PID.setVisible(true);
        anchorChour.setVisible(false);
        anchorSubject.setVisible(false);
        anchorAb.setVisible(false);
        anchorNotif.setVisible(false);
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

    private void AfficherAllP() {

        annonceServiceP = new ParentService();
        oblistP = FXCollections.observableArrayList(esP.getAll());

        ObservableList observableListP = FXCollections.observableArrayList(oblistP);
        tabP.setItems(observableListP);
        //idA.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameP.setCellValueFactory(new PropertyValueFactory<>("name"));
        mailP.setCellValueFactory(new PropertyValueFactory<>("mail"));
        phoneP.setCellValueFactory(new PropertyValueFactory<>("phone"));
    }

    @FXML
    private void ajouterA(ActionEvent event) throws SQLException {
        if (validateDuration()) {

            String about = aboutAJ.getText();

            String duration = durationAJ.getText();
            String location = locationAJ.getText();

            LocalDate date = dp.getValue();
            // String name =clubAJ.getValue();
            String name = clubAJ.getValue();

            ActivityService sl = new ActivityService();
            //int clubId ;
            int clubId = sl.getClubId(name);
            System.out.println(name);

            activity l = new activity(clubId, about, duration, location, date.toString(), name);
            sl.addActivity(l);
            AfficherAllA();

            listAD = new ActivityService().readAllA();
            // List <String> listAD=  new ActivityService().readAllA();
            // List<String> to = new ActivityService().readAllA();

            // Recipient's email ID needs to be mentioned. private static final String EMAIL_TO = "jberisamar2@gmail.com, binderschools@gmail.com";
            //final String to ="jberisamar2@gmail.com";
            // Sender's email ID needs to be mentioned
            String from = "binderschools@gmail.com";//change accordingly
            final String username = "binder";//change accordingly
            final String password = "binder123";//change accordingly
            //final String EMAIL_TO = "jberisamar2@gmail.com, binderschools@gmail.com";
            String emails = "";

            for (String mail : listAD) {
                emails += mail + ",";
            }
            System.out.println(emails);

            emails = emails.substring(0, emails.length() - 2);

            // Assuming you are sending email through relay.jangosmtp.net
            String host = "smtp.gmail.com";

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "587");

            // Get the Session object.
            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                @Override
                protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                    return new javax.mail.PasswordAuthentication(from, password);
                }
            });

            try {

                // Create a default MimeMessage object.
                Message message = new MimeMessage(session);

                // Set From: header field of the header.
                message.setFrom(new InternetAddress(from));

                // Set To: header field of the header.
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(emails, false));
                // Set Subject: header field
                message.setSubject("Testing Subject");

                // Now set the actual message
                message.setText(l.message());

                // Send message
                Transport.send(message);

                System.out.println("Sent message successfully....");

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }

        }

    }

    private boolean validateDuration() {
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(durationAJ.getText());
        if (m.find() && m.group().equals(durationAJ.getText())) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("validate the duration");
            alert.setHeaderText(null);
            alert.setContentText("please enter a validate duration");
            alert.showAndWait();
            return false;

        }

    }

    private void AfficherAllA() {

        annonceServiceA = new ActivityService();
        oblistA = FXCollections.observableArrayList(esA.getAll());

        ObservableList observableList = FXCollections.observableArrayList(oblistA);
        tabA.setItems(observableList);
        //idA.setCellValueFactory(new PropertyValueFactory<>("id"));
        aboutA.setCellValueFactory(new PropertyValueFactory<>("about"));

        locationA.setCellValueFactory(new PropertyValueFactory<>("location"));
        System.out.println(locationA.toString());

        durationA.setCellValueFactory(new PropertyValueFactory<>("duration"));

        dateA.setCellValueFactory(new PropertyValueFactory<>("dateA"));
        clubA.setCellValueFactory(new PropertyValueFactory<>("clubAC"));

    }

    @FXML
    private void goToCourOnClick(ActionEvent event) {
        anchorHome.setVisible(false);
        anchorClassGestion.setVisible(false);
        listexams.setVisible(false);
        listgrades.setVisible(false);
        CID.setVisible(false);
        AID.setVisible(false);
        PID.setVisible(false);
        anchorChour.setVisible(true);
        anchorSubject.setVisible(false);
        anchorAb.setVisible(false);
        anchorNotif.setVisible(false);
    }

    @FXML
    private void goToSubjectOnClick(ActionEvent event) {
        anchorHome.setVisible(false);
        anchorClassGestion.setVisible(false);
        listexams.setVisible(false);
        listgrades.setVisible(false);
        CID.setVisible(false);
        AID.setVisible(false);
        PID.setVisible(false);
        anchorChour.setVisible(false);
        anchorSubject.setVisible(true);
        anchorAb.setVisible(false);
        anchorNotif.setVisible(false);
    }

    @FXML
    private void goToAbscenseOnClick(ActionEvent event) {
        anchorHome.setVisible(false);
        anchorClassGestion.setVisible(false);
        listexams.setVisible(false);
        listgrades.setVisible(false);
        CID.setVisible(false);
        AID.setVisible(false);
        PID.setVisible(false);
        anchorChour.setVisible(false);
        anchorSubject.setVisible(false);
        anchorAb.setVisible(true);
        anchorNotif.setVisible(false);
    }

    @FXML
    private void goToNotificationOnClick(ActionEvent event) {
        anchorHome.setVisible(false);
        anchorClassGestion.setVisible(false);
        listexams.setVisible(false);
        listgrades.setVisible(false);
        CID.setVisible(false);
        AID.setVisible(false);
        PID.setVisible(false);
        anchorChour.setVisible(false);
        anchorSubject.setVisible(false);
        anchorAb.setVisible(false);
        anchorNotif.setVisible(true);
    }

    @FXML
    private void updateCourse(ActionEvent event) throws SQLException {

        annonceServiceC = new CourseService();
        Course aa = tvCourses.getSelectionModel().getSelectedItem();
        System.out.println(aa.getId() + "551");
        int idc = aa.getId();
        //String subjec = tfus.getText();
        String subjec = cbox.getSelectionModel().getSelectedItem();
        String conten = tfuc.getText();
        String teache = tfut.getText();
        Course a = new Course(idc, subjec, conten, teache);
        annonceServiceC.updateCourse(a);
        try {
            Connection con = (Connection) MyConnection.getInstance().getCnx();

            ResultSet rs = con.createStatement().executeQuery("select * from course");
            while (rs.next()) {
                oblistC.add(new Course(rs.getInt("id"), rs.getString("subject"), rs.getString("content"), rs.getString("teacher")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(HomeBinderController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tvCourses.setItems(oblistC);

        showAll();

    }

    @FXML
    private void deleteCourse(ActionEvent event) {
        esC = new CourseService();
        int index = tvCourses.getSelectionModel().getSelectedItem().getId();
        esC.deleteCourse(index);
        showAll();
    }

    @FXML
    private void addCourse(ActionEvent event) {
        if ((cbox.getSelectionModel().isEmpty()) || (tfuc.getText().isEmpty())) {
            JOptionPane.showMessageDialog(null, "subject and content shouldn't be empty");

        } else {

            CourseService cs = new CourseService();
            Course c = new Course();
            //c.setSubject(tfus.getText());
            c.setSubject(cbox.getSelectionModel().getSelectedItem());
            c.setContent(tfuc.getText());
            c.setTeacher(tfut.getText());
            cs.addCourse(c);
            cbox.setValue(null);
            tfuc.setText(null);
            showAll();
        }
    }

    @FXML
    private void searchCourse(ActionEvent event) {
        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Course> filteredData = new FilteredList<>(oblistC, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        tfSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(Course -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (Course.getSubject().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                } else if (Course.getTeacher().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<Course> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(tvCourses.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        tvCourses.setItems(sortedData);
    }

    @FXML
    private void browse(ActionEvent event) {
        wv.setVisible(true);
        tvCourses.setVisible(false);
        WebEngine engine = wv.getEngine();
        String stri = tvCourses.getSelectionModel().getSelectedItem().getContent();
        engine.load(stri);
    }

    @FXML
    private void exitBrowser(ActionEvent event) {
        wv.setVisible(false);
        tvCourses.setVisible(true);
    }

    @FXML
    private void updateSubject(ActionEvent event) throws SQLException {
        annonceService2 = new SubjectService();
        Subject aa = tvSubjects.getSelectionModel().getSelectedItem();
        System.out.println(aa.getId() + "551");
        int ids = aa.getId();
        String name = tfName.getText();
        String teachers = tfTeachers.getText();
        String classes = tfClasses.getText();
        Subject a = new Subject(ids, name, teachers, classes);
        annonceService2.updateSubject(a);
        try {
            Connection con = (Connection) MyConnection.getInstance().getCnx();

            ResultSet rs = con.createStatement().executeQuery("select * from subjec");
            while (rs.next()) {
                oblist2.add(new Subject(rs.getInt("id"), rs.getString("name"), rs.getString("teachers"), rs.getString("classes")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(HomeBinderController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tvSubjects.setItems(oblist2);

        showAllSub();
    }

    @FXML
    private void deleteSubject(ActionEvent event) {
        ss = new SubjectService();
        int index = tvSubjects.getSelectionModel().getSelectedItem().getId();
        ss.deleteSubject(index);
        showAllSub();
    }

    @FXML
    private void addSubject(ActionEvent event) {
        SubjectService cs = new SubjectService();
        Subject s = new Subject();
        s.setName(tfName.getText());
        s.setTeachers(tfTeachers.getText());
        s.setClasses(tfClasses.getText());
        cs.addSubject(s);
        showAllSub();
    }

    @FXML
    private void searchSubject(ActionEvent event) {
        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Subject> filteredData = new FilteredList<>(oblist2, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        tfSearch2.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(Subject -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (Subject.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                } else if (Subject.getTeachers().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<Subject> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(tvSubjects.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        tvSubjects.setItems(sortedData);
    }

    private void showAll() {
        annonceServiceC = new CourseService();
        oblistC = FXCollections.observableArrayList(esC.showAll());

        ObservableList observableList = FXCollections.observableArrayList(oblistC);
        tvCourses.setItems(observableList);
        // id.setCellValueFactory(new PropertyValueFactory<>("id"));
        subjectW.setCellValueFactory(new PropertyValueFactory<>("subject"));
        content.setCellValueFactory(new PropertyValueFactory<>("content"));
        teacher.setCellValueFactory(new PropertyValueFactory<>("teacher"));

    }

    private void showAllSub() {
        annonceService2 = new SubjectService();
        oblist2 = FXCollections.observableArrayList(ss.showAllSub());

        ObservableList observableList = FXCollections.observableArrayList(oblist2);
        tvSubjects.setItems(observableList);
        //    idColomn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColomn.setCellValueFactory(new PropertyValueFactory<>("name"));
        teacherscolomn.setCellValueFactory(new PropertyValueFactory<>("teachers"));
        classescolomn.setCellValueFactory(new PropertyValueFactory<>("classes"));

    }

    @FXML
    private void addTeacher(ActionEvent event) {
    }

    @FXML
    private void updateTeacher(ActionEvent event) {
    }

    @FXML
    private void deleteTeacher(ActionEvent event) {
    }

    @FXML
    private void btGoToTeacherOnClick(ActionEvent event) {
    }
}
