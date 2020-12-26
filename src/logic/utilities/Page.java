package logic.utilities;

public enum Page {
	
	HOMEPAGE("src/res/fxml/page/HomePage.fxml", "App - Homepage"), 
	LOGIN("src/res/fxml/page/LoginPage.fxml", "App - Login"),
	SIGNUP("src/res/fxml/page/SignupPage.fxml", "App - Signup"), 
	FORUM("src/res/fxml/page/ForumPage.fxml", "App - Forum"), 
	NEWQUESTION("src/res/fxml/page/NewQuestionPage.fxml", "App - Insert new question"),
	QUESTION("src/res/fxml/page/QuestionPage.fxml", "App - Forum"),
	NEWS("src/res/fxml/page/NewsPage.fxml", "App - News"),
	PROFILE("src/res/fxml/page/ProfilePage.fxml", "App - Profile"),
	PROFESSOR("src/res/fxml/page/ProfessorPage.fxml", "App - Professor"),
	EXAM("src/res/fxml/page/ExamPage.fxml", "App - Exams"), 
	LESSON("src/res/fxml/page/LessonPage.fxml", "App - Lesson"),
	ASSIGNMENT("src/res/fxml/page/AssignmentPage.fxml", "App - Assignment"), 
	COURSE("src/res/fxml/page/CoursePage.fxml", "App - Course");

	private String res;
	private String stageTitle;
	
	Page(String res, String title) {
		this.res = res;
		this.stageTitle = title;
	}
	
	public String getRes() {
		return res;
	}

	public void setRes(String res) {
		this.res = res;
	}

	public String getStageTitle() {
		return stageTitle;
	}

	public void setStageTitle(String stageTitle) {
		this.stageTitle = stageTitle;
	}
}
