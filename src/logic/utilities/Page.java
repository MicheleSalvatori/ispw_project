package logic.utilities;

public enum Page {
	
	
	LOGIN("src/res/fxml/LoginUser.fxml", "App - Login");
	private String stageTitle;
	private String res;

	public String getStageTitle() {
		return stageTitle;
	}

	public void setStageTitle(String stageTitle) {
		this.stageTitle = stageTitle;
	}

	public String getRes() {
		return res;
	}

	public void setRes(String res) {
		this.res = res;
	}

	Page(String res, String stageTitle) {
		this.res = res;
		this.stageTitle = stageTitle;
		
	}
}
