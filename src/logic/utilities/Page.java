package logic.utilities;

public enum Page {
	
	HOMEPAGE("src/res/fxml/page/HomePage.fxml", ".myUniversity - Homepage"), 
	LOGIN("src/res/fxml/page/LoginPage.fxml", ".myUniversity - Login"),
	SIGNUP("src/res/fxml/page/SignupPage.fxml", ".myUniversity - Signup"), 
	FORUM("src/res/fxml/page/ForumPage.fxml", ".myUniversity - Forum"), 
	NEWQUESTION("src/res/fxml/page/NewQuestionPage.fxml", ".myUniversity - Insert new question"),
	NEWASSIGNMENT("src/res/fxml/page/NewAssignmentPage.fxml", ".myUniversity - Insert new assignment"),
	QUESTION("src/res/fxml/page/QuestionPage.fxml", ".myUniversity - Question"),
	ANSWER_QUESTION("src/res/fxml/page/AnswerPage.fxml", ".myUniversity - Answer a Question"),
	COMMUNICATIONS("src/res/fxml/page/CommunicationsPage.fxml", ".myUniversity - News"),
	PROFILE("src/res/fxml/page/ProfilePage.fxml", ".myUniversity - Profile"),
	PROFESSOR("src/res/fxml/page/ProfessorPage.fxml", ".myUniversity - Professor"),
	EXAM("src/res/fxml/page/ExamPage.fxml", ".myUniversity - Exams"), 
	LESSON("src/res/fxml/page/LessonPage.fxml", ".myUniversity - Lesson"),
	ASSIGNMENT("src/res/fxml/page/AssignmentPage.fxml", ".myUniversity - Assignment"), 
	COURSE("src/res/fxml/page/CoursePage.fxml", ".myUniversity - Course"),
	SCHEDULE("src/res/fxml/page/SchedulePage.fxml", ".myUniversity - Schedule"),
	REQUEST("src/res/fxml/page/RequestPage.fxml", ".myUniversity - Request"),
	SCHEDULED_LESSONS("src/res/fxml/page/ScheduledPage.fxml", ".myUniversity - Scheduled Lessons"),
	SCHEDULED_EXAMS("src/res/fxml/page/ScheduledPage.fxml", ".myUniversity - Scheduled Exams"),
	POST_COMMUNICATION("src/res/fxml/page/PostCommunicationPage.fxml", ".myUniversity - Post Communication"),
	ADMINISTRATION_PAGE("src/res/fxml/page/AdministrationPage.fxml", ".myUniversity - Administration;"),
	NEWPROFESSOR("src/res/fxml/page/NewProfessorPage.fxml", ".myUniversity - Add Professor"),
	NEWCOURSE("src/res/fxml/page/NewCoursePage.fxml", ".myUniversity - Add Course");

	private String res;
	private String stageTitle;
	
	Page(String res, String title) {
		this.res = res;
		this.stageTitle = title;
	}
	
	public String getRes() {
		return res;
	}

	public String getStageTitle() {
		return stageTitle;
	}
}
