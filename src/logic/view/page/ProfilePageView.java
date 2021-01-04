package logic.view.page;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import logic.Session;
import logic.bean.RequestBean;
import logic.bean.UserBean;
import logic.controller.JoinCourseController;
import logic.controller.LoginController;
import logic.controller.SignupController;
import logic.model.Course;
import logic.model.Professor;
import logic.model.Student;
import logic.model.dao.CourseDAO;
import logic.model.dao.ProfessorDAO;
import logic.utilities.AlertController;
import logic.utilities.Page;
import logic.utilities.PageLoader;
import logic.utilities.Role;
import logic.view.card.element.CourseCard;

public class ProfilePageView implements Initializable {
	
	@FXML
	private VBox vboxScroll;
	
	@FXML
	private Button btnAdd, btnRemove, btnShowPass, btnChangePass;
	
	@FXML
	private Rectangle rect;
	
	@FXML
	private Label labelUsername, labelName, labelPassword, labelSurname, labelEmail;
	
	private Boolean show = false;
	
	@FXML
	private void changePass(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
		String password;
		
		while (true) {
			password = AlertController.changePassword(event);
			
			if (password == null) {
				return;
			}
			
			else if (password.compareTo(Session.getSession().getPassword()) == 0) {
				AlertController.buildInfoAlert("You have inserted same password.\nInsert another password.", "password", event);
			}
			
			else {
				break;
			}
		}
		
		UserBean userBean = new UserBean();
		userBean.setUsername(Session.getSession().getUsername());
		userBean.setPassword(password);
		
		if (AlertController.confirmation("Do you want to change your password?\nYou will be logged out.", event)) {
			SignupController signupController = new SignupController();
			signupController.changePassword(userBean);
			logout(event);
		}
	}
	
	@FXML
	private void removeCourse(ActionEvent event) throws SQLException {
		List<Course> courses = CourseDAO.getStudentCourses(Session.getSession().getUsername());
		
		if (courses == null) {
			AlertController.buildInfoAlert("You don't have any courses available.", "delete", event);
			return;
		}
		
		List<String> names = new ArrayList<>();
		for (Course course : courses) {
			names.add(course.getName());
		}
		
		int index = AlertController.courseRequest(event, names);
		if (index != -1) {
			Course course = courses.get(index);
			RequestBean requestBean = new RequestBean();
			requestBean.setStudent((Student) Session.getSession().getUserLogged());
			requestBean.setCourse(course);
			
			JoinCourseController joinCourseController = new JoinCourseController();
			joinCourseController.removeCourse(requestBean);
			
			AlertController.buildInfoAlert("Course " + course.getAbbrevation() + " removed.", "delete", event);
			loadCourses();
		}
	}
	
	@FXML
	private void addCourse(ActionEvent event) throws SQLException {
		List<Course> courses = CourseDAO.getAvailableCourses(Session.getSession().getUsername());
		
		if (courses == null) {
			AlertController.buildInfoAlert("You don't have any courses to add.", "delete", event);
			return;
		}
		
		List<String> names = new ArrayList<>();
		for (Course course : courses) {
			names.add(course.getName());
		}
		
		int index = AlertController.courseRequest(event, names);
		if (index != -1) {
			Course course = courses.get(index);
			RequestBean requestBean = new RequestBean();
			requestBean.setStudent((Student) Session.getSession().getUserLogged());
			requestBean.setCourse(course);
			
			JoinCourseController joinCourseController = new JoinCourseController();
			joinCourseController.sendRequest(requestBean);
			
			AlertController.buildInfoAlert("Request sended to course's professor.\nYou will receive a notification when the request will be approved", "request", event);
		}
	}
	
	@FXML
	private void showPass() {

		if (!show) {
			labelPassword.setText(Session.getSession().getUserLogged().getPassword());
			btnShowPass.getStyleClass().remove("button-show");
			btnShowPass.getStyleClass().add("button-no-show");
			show = true;
		}
		else {
			labelPassword.setText("**********");
			btnShowPass.getStyleClass().remove("button-no-show");
			btnShowPass.getStyleClass().add("button-show");
			show = false;
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		labelUsername.setText(Session.getSession().getUserLogged().getUsername());
		labelName.setText(Session.getSession().getUserLogged().getName());
		labelSurname.setText(Session.getSession().getUserLogged().getSurname());
		labelEmail.setText(Session.getSession().getUserLogged().getEmail());
		labelPassword.setText("**********");
		
		if (Session.getSession().getType() == Role.PROFESSOR) {
			btnAdd.setVisible(false);
			btnRemove.setVisible(false);
		}
		
		setAvatar("/res/png/avatar.png");
		
		loadCourses();
	}
	
	private void setAvatar(String res) {
		ImagePattern pattern = new ImagePattern(new Image(res, 200, 200, false, false));
		rect.setFill(pattern);
	}
	
	private void loadCourses() {
		vboxScroll.getChildren().clear();
		
		try {
			List<Course> courses;
			if (Session.getSession().getType() == Role.STUDENT) {
				courses = CourseDAO.getStudentCourses(Session.getSession().getUserLogged().getUsername());
			}
			else if (Session.getSession().getType() == Role.PROFESSOR) {
				courses = CourseDAO.getProfessorCourses(Session.getSession().getUserLogged().getUsername());
			}
			else {
				return;
			}
			
			for (Course course : courses) {
				List<Professor> professor = ProfessorDAO.getCourseProfessors(course.getAbbrevation());
				CourseCard courseCard = new CourseCard(course.getAbbrevation(), professor.get(0).getName() + " " + professor.get(0).getSurname(), "3", "3");
				//CourseCard courseCard = new CourseCard(course);
				vboxScroll.getChildren().add(courseCard);
			}
			
		} catch (NullPointerException e) {
			System.out.println("NULL");
			Label label = new Label("No course available");
			vboxScroll.getChildren().add(label);
			return;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void logout(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
		System.out.println("Logout");
		
		LoginController loginController = new LoginController();
		loginController.logout();
		PageLoader.getInstance().buildPage(Page.LOGIN, event);
	}
}
