package logic.utilities;

public enum Page {
	
	HOMEPAGE("src/res/fxml/Homepage.fxml", "App - Homepage"), 
	LOGIN("src/res/fxml/Login.fxml", "App - Login"),
	SIGNUP("src/res/fxml/Signup.fxml", "App - Signup");
	
	
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
