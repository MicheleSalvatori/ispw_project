package logic.controller;

import logic.bean.RequestBean;
import logic.model.dao.RequestDAO;

public class AcceptRequestController {

	public boolean acceptRequest(RequestBean requestBean) {
		if (deleteRequest(requestBean)) {
			return RequestDAO.insertFollow(requestBean);
		}
		return false;
	}
	
	public boolean declineRequest(RequestBean requestBean) {
		return deleteRequest(requestBean);
	}
	
	public boolean deleteRequest(RequestBean requestBean) {
		return RequestDAO.deleteRequest(requestBean);
	}
}
