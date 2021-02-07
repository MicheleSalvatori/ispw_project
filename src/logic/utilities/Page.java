package logic.utilities;

public enum Page {
	
	HOMEPAGE("src/res/fxml/page/HomePage.fxml", "App - Homepage"), 
	LOGIN("src/res/fxml/page/LoginPage.fxml", "App - Login"),
	SIGNUP("src/res/fxml/page/SignupPage.fxml", "App - Signup"), 
	FORUM("src/res/fxml/page/ForumPage.fxml", "App - Forum"), 
	NEWQUESTION("src/res/fxml/page/NewQuestionPage.fxml", "App - Insert new question"),
	NEWASSIGNMENT("src/res/fxml/page/NewAssignmentPage.fxml", "App - Insert new assignment"),
	QUESTION("src/res/fxml/page/QuestionPage.fxml", "App - Question"),
	ANSWER_QUESTION("src/res/fxml/page/AnswerPage.fxml", "App - Answer a Question"),
	COMMUNICATIONS("src/res/fxml/page/CommunicationsPage.fxml", "App - News"),
	PROFILE("src/res/fxml/page/ProfilePage.fxml", "App - Profile"),
	PROFESSOR("src/res/fxml/page/ProfessorPage.fxml", "App - Professor"),
	EXAM("src/res/fxml/page/ExamPage.fxml", "App - Exams"), 
	LESSON("src/res/fxml/page/LessonPage.fxml", "App - Lesson"),
	ASSIGNMENT("src/res/fxml/page/AssignmentPage.fxml", "App - Assignment"), 
	COURSE("src/res/fxml/page/CoursePage.fxml", "App - Course"),
	SCHEDULE("src/res/fxml/page/SchedulePage.fxml", "App - Schedule"),
	REQUEST("src/res/fxml/page/RequestPage.fxml", "App - Request"),
	SCHEDULED_LESSONS("src/res/fxml/page/ScheduledPage.fxml", "App - Scheduled Lessons"),
	SCHEDULED_EXAMS("src/res/fxml/page/ScheduledPage.fxml", "App - Scheduled Exams"),
	POST_COMMUNICATION("src/res/fxml/page/PostCommunicationPage.fxml", "App - Post Communication"),
	ADMINISTRATION_PAGE("src/res/fxml/page/AdministrationPage.fxml", "App - Administration;");

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
