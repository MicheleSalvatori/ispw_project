package logic.controller;

import logic.bean.RequestBean;
import logic.model.dao.RequestDAO;

public class JoinCourseController {

	public boolean sendRequest(RequestBean requestBean) {
		return RequestDAO.insertRequest(requestBean);
	}
	
	public boolean removeCourse(RequestBean requestBean) {
		return RequestDAO.deleteFollow(requestBean);
	}
	
	public boolean deleteRequest(RequestBean requestBean) {
		return RequestDAO.deleteRequest(requestBean);
	}
}
