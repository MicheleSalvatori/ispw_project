package logic.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logic.bean.CourseBean;
import logic.bean.StudentBean;
import logic.bean.UserBean;
import logic.bean.VerbalizedBean;
import logic.exceptions.RecordNotFoundException;
import logic.model.Course;
import logic.model.Student;
import logic.model.Verbalized;
import logic.model.dao.CourseDAO;
import logic.model.dao.VerbalizedDAO;

public class ViewVerbalizedExamsController {
	
	public List<VerbalizedBean> getVerbalizedExams(UserBean userBean) throws SQLException, RecordNotFoundException {
		
		List<Verbalized> verbs = VerbalizedDAO.getVerbalizedExams(userBean.getUsername());
		List<VerbalizedBean> verbsBean = new ArrayList<>();
		
		for (Verbalized verb : verbs) {
			
			Course course = verb.getCourse();
			CourseBean courseBean = new CourseBean();
			courseBean.setAbbreviation(course.getAbbrevation());
			courseBean.setCredits(course.getCredits());
			courseBean.setGoal(course.getGoal());
			courseBean.setName(course.getName());
			courseBean.setPrerequisites(course.getPrerequisites());
			courseBean.setReception(course.getReception());
			courseBean.setSemester(course.getSemester());
			courseBean.setYear(course.getYear());
			
			Student student = verb.getStudent();
			StudentBean studentBean = new StudentBean();
			studentBean.setEmail(student.getEmail());
			studentBean.setName(student.getName());
			studentBean.setPassword(student.getPassword());
			studentBean.setSurname(student.getSurname());
			studentBean.setUsername(student.getUsername());

			VerbalizedBean verbBean = new VerbalizedBean();
			verbBean.setCourse(courseBean);
			verbBean.setDate(verb.getDate());
			verbBean.setGrade(verb.getGrade());
			verbBean.setStudent(studentBean);
			
			verbsBean.add(verbBean);
		}
		
		return verbsBean;
	}
	
	public int countVerbalizedExams(UserBean userBean) throws SQLException, RecordNotFoundException {
		return VerbalizedDAO.countVerbalizedExams(userBean.getUsername());
	}
	
	// Calculate GPA
	public double GPA(List<VerbalizedBean> verbs) {
		double tot = 0;
		for (VerbalizedBean verbBean : verbs) {
			tot += verbBean.getGrade();
		}

		return tot / verbs.size();
	}

	// Calculate WPA
	public double WPA(List<VerbalizedBean> verbs) {
		double tot = 0;
		int credits = 0;
		for (VerbalizedBean verbBean : verbs) {
			tot += verbBean.getGrade() * Integer.parseInt(verbBean.getCourse().getCredits());
			credits += Integer.parseInt(verbBean.getCourse().getCredits());
		}

		return round(tot / credits);
	}

	// Round double by 2 decimal places
	private double round(double value) {
		BigDecimal bd = BigDecimal.valueOf(value);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	public List<CourseBean> getCourses(UserBean userBean) throws SQLException, RecordNotFoundException {
		
		List<Course> courses = CourseDAO.getNotVerbalizedCourses(userBean.getUsername());
		List<CourseBean> coursesBean = new ArrayList<>();
		
		for (Course course : courses) {

			CourseBean courseBean = new CourseBean();
			courseBean.setAbbreviation(course.getAbbrevation());
			courseBean.setCredits(course.getCredits());
			courseBean.setGoal(course.getGoal());
			courseBean.setName(course.getName());
			courseBean.setPrerequisites(course.getPrerequisites());
			courseBean.setReception(course.getReception());
			courseBean.setSemester(course.getSemester());
			courseBean.setYear(course.getYear());
			
			coursesBean.add(courseBean);
		}
			
		return coursesBean;
	}
	
	public boolean saveVerbalizedExam(VerbalizedBean verb) {
		Student student = new Student();
		student.setUsername(verb.getStudent().getUsername());
		
		Course course = new Course();
		course.setAbbrevation(verb.getCourse().getAbbreviation());
		
		Verbalized verbExam = new Verbalized(student, course, verb.getGrade(), verb.getDate());
		
		return VerbalizedDAO.insert(verbExam);
	}

	public boolean deleteVerbalizedExam(VerbalizedBean verb) {
		Student student = new Student();
		student.setUsername(verb.getStudent().getUsername());
		
		Course course = new Course();
		course.setAbbrevation(verb.getCourse().getAbbreviation());
		
		Verbalized verbExam = new Verbalized();
		verbExam.setCourse(course);
		verbExam.setStudent(student);
		
		return VerbalizedDAO.delete(verbExam);
	}
}
