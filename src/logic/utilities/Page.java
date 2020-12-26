package logic.utilities;

public enum Page {
	
	HOMEPAGE("src/res/fxml/Homepage.fxml", "App - Homepage"), 
	LOGIN("src/res/fxml/Login.fxml", "App - Login"),
	SIGNUP("src/res/fxml/Signup.fxml", "App - Signup"), 
	FORUM("src/res/fxml/ForumPage.fxml", "App - Forum"), 
	NEWQUESTION("src/res/fxml/NewQuestionPage.fxml", "App - Insert new question"),
	QUESTION("src/res/fxml/QuestionPage.fxml", "App - Forum"),
	NEWS("src/res/fxml/NewsPage.fxml", "App - News"),
	PROFILE("src/res/fxml/ProfilePage.fxml", "App - Profile"),
	PROFESSOR("src/res/fxml/ProfessorPage.fxml", "App - Professor"),
	EXAM("src/res/fxml/ExamPage.fxml", "App - Exams"), 
	LESSON("src/res/fxml/LessonPage.fxml", "App - Lesson"),
	ASSIGNMENT("src/res/fxml/AssignmentPage.fxml", "App - Assignment"), 
	COURSE("src/res/fxml/CoursePage.fxml", "App - Course");

	
	
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
