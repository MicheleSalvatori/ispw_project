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
import logic.bean.CourseBean;
import logic.bean.ProfessorBean;
import logic.bean.RequestBean;
import logic.bean.StudentBean;
import logic.bean.UserBean;
import logic.controller.JoinCourseController;
import logic.controller.LoginController;
import logic.controller.SignupController;
import logic.exceptions.CancelException;
import logic.exceptions.RecordNotFoundException;
import logic.utilities.AlertController;
import logic.utilities.AppProperties;
import logic.utilities.Page;
import logic.utilities.PageLoader;
import logic.utilities.Role;
import logic.view.card.element.CourseCard;
import logic.view.card.element.CourseCard.Type;

public class ProfilePageView implements Initializable {
	
	@FXML
	private VBox vboxScroll;
	
	@FXML
	private Button btnAdd, btnRemove, btnShowPass, btnChangePass, btnAvatar;
	
	@FXML
	private Rectangle rect;
	
	@FXML
	private Label labelUsername, labelName, labelPassword, labelSurname, labelEmail;
	
	private Boolean show = false;
	
	private LoginController loginController;
	private SignupController signupController;
	private JoinCourseController joinCourseController;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		labelUsername.setText(UserBean.getInstance().getUsername());
		labelName.setText(UserBean.getInstance().getName());
		labelSurname.setText(UserBean.getInstance().getSurname());
		labelEmail.setText(UserBean.getInstance().getEmail());
		labelPassword.setText("**********");
		
		if (UserBean.getInstance().getRole() == Role.PROFESSOR) {
			btnAdd.setVisible(false);
			btnRemove.setVisible(false);
		}
		
		String img = "/res/png/avatar/profile/" + AppProperties.getInstance().getProperty("avatar").toString() + ".png";
		setAvatar(img);
		
		loadCourses();
	}
	
	@FXML
	private void changePass(ActionEvent event) throws ClassNotFoundException, IOException {
		String password;
		
		while (true) {
			
			try {
				// Open alert and insert new password
				password = AlertController.changePassword();
	
				// Password inserted is the same to current password
				if (password.compareTo(UserBean.getInstance().getPassword()) == 0) {
					AlertController.infoAlert("You have inserted same password.\nInsert another password.");
				}
				
				// Inserted new password
				else {
					break;
				}
				
			// Clicked Cancel Button
			} catch (CancelException e) {
				System.out.println(e.getMessage());
				return;
			}
		}
		
		UserBean userBean = new UserBean();
		userBean.setUsername(UserBean.getInstance().getUsername());
		userBean.setPassword(password);
		
		if (AlertController.confirmationAlert("Do you want to change your password?\nYou will be logged out.")) {
			signupController = new SignupController();
			
			try {
				signupController.changePassword(userBean);
				logout(event);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			} catch (RecordNotFoundException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	@FXML
	private void removeCourse(ActionEvent event) throws SQLException {
		
		joinCourseController = new JoinCourseController();
		
		UserBean userBean = new UserBean();
		userBean.setUsername(UserBean.getInstance().getUsername());

		try {
			List<CourseBean> courses = joinCourseController.getStudentCourses(userBean);
			
			List<String> names = new ArrayList<>();
			for (CourseBean course : courses) {
				names.add(course.getName());
			}
			
			int index = AlertController.courseRequest(names);
			if (index != -1) {
				
				CourseBean courseBean = courses.get(index);
				
				StudentBean studentBean = new StudentBean();
				studentBean.setEmail(UserBean.getInstance().getEmail());
				studentBean.setName(UserBean.getInstance().getName());
				studentBean.setPassword(UserBean.getInstance().getPassword());
				studentBean.setSurname(UserBean.getInstance().getSurname());
				studentBean.setUsername(UserBean.getInstance().getUsername());
				
				RequestBean requestBean = new RequestBean();
				requestBean.setStudent(studentBean);
				requestBean.setCourse(courseBean);
				
				joinCourseController.removeCourse(requestBean);
				
				AlertController.infoAlert("Course " + courseBean.getAbbreviation() + " removed.");
				loadCourses();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (RecordNotFoundException e) {
			System.out.println(e.getMessage());
			AlertController.infoAlert("You don't have any courses available.");
			return;
		}
	}
	
	@FXML
	private void addCourse(ActionEvent event) throws SQLException {
		
		joinCourseController = new JoinCourseController();
		
		UserBean userBean = new UserBean();
		userBean.setUsername(UserBean.getInstance().getUsername());
		
		List<CourseBean> courses = null;
		try {
			courses = joinCourseController.getAvailableCourses(userBean);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (RecordNotFoundException e) {
			AlertController.infoAlert("You don't have any courses to add.");
			return;
		}
		
		List<String> names = new ArrayList<>();
		for (CourseBean course : courses) {
			names.add(course.getName());
		}
		
		int index = AlertController.courseRequest(names);
		if (index != -1) {
			
			CourseBean courseBean = courses.get(index);
			
			StudentBean studentBean = new StudentBean();
			studentBean.setEmail(UserBean.getInstance().getEmail());
			studentBean.setName(UserBean.getInstance().getName());
			studentBean.setPassword(UserBean.getInstance().getPassword());
			studentBean.setSurname(UserBean.getInstance().getSurname());
			studentBean.setUsername(UserBean.getInstance().getUsername());
			
			RequestBean requestBean = new RequestBean();
			requestBean.setStudent(studentBean);
			requestBean.setCourse(courseBean);

			joinCourseController.sendRequest(requestBean);
			
			AlertController.infoAlert("Request sended to course's professor.\nYou will receive a notification when the request will be approved");
			loadCourses();
		}	
	}
	
	@FXML
	private void showPass() {

		if (Boolean.FALSE.equals(show)) {
			labelPassword.setText(UserBean.getInstance().getPassword());
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
	
	@FXML
	private void changeAvatar(ActionEvent event) throws IOException {
		AppProperties.getInstance().setProperty("avatar", "avatar10");
		PageLoader.getInstance().buildPage(Page.PROFILE);
	}
	
	private void setAvatar(String res) {
		ImagePattern pattern = new ImagePattern(new Image(res, 200, 200, false, false));
		rect.setFill(pattern);
	}
	
	public void loadCourses() {
		vboxScroll.getChildren().clear();
		
		joinCourseController = new JoinCourseController();

		List<CourseBean> courses;
		List<CourseBean> requests;
		List<ProfessorBean> professors;
	
		try {
			
			courses = joinCourseController.getCourses(UserBean.getInstance());
			for (CourseBean courseBean : courses) {
				professors = joinCourseController.getCourseProfessors(courseBean);
				CourseCard courseCard = new CourseCard(courseBean, professors, Type.FOLLOW);
				vboxScroll.getChildren().add(courseCard);
			}
				
		} catch (RecordNotFoundException e) {
			System.out.println(e.getMessage());
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			
			if (UserBean.getInstance().getRole() == Role.STUDENT) {
				requests = joinCourseController.getRequestedCourses(UserBean.getInstance());
				for (CourseBean courseBean : requests) {
					professors = joinCourseController.getCourseProfessors(courseBean);
					CourseCard courseCard = new CourseCard(courseBean, professors, Type.REQUEST);
					vboxScroll.getChildren().add(courseCard);
				}
			}
	
		} catch (RecordNotFoundException e) {
			System.out.println(e.getMessage());
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteRequest(RequestBean requestBean) {
		if (AlertController.confirmationAlert("Do you want to cancel this request?")) {
			joinCourseController = new JoinCourseController();
			joinCourseController.deleteRequest(requestBean);
			
			AlertController.infoAlert("Request of course '" + requestBean.getCourse().getAbbreviation() + "' deleted.");
			loadCourses();
		}
	}
	
	private void logout(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
		System.out.println("Logout");
		
		loginController = new LoginController();
		loginController.logout();
		PageLoader.getInstance().buildPage(Page.LOGIN);
	}
}
