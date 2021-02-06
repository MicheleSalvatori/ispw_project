package logic.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logic.bean.CommunicationBean;
import logic.exceptions.RecordNotFoundException;
import logic.model.Communication;
import logic.model.dao.CommunicationDAO;

public class PostCommunicationController {

	public void postCommunication(CommunicationBean bean) throws SQLException {
		Communication communication = new Communication();
		communication.setDate(bean.getDate());
		communication.setText(bean.getText());
		communication.setTitle(bean.getTitle());
		CommunicationDAO.saveCommunication(communication);

	}

	public List<CommunicationBean> getCommunications() throws SQLException, RecordNotFoundException {
		List<Communication> comunications = CommunicationDAO.getCommunications();
		List<CommunicationBean> comunicationBeans = new ArrayList<>();

		for (Communication com : comunications) {
			CommunicationBean c = new CommunicationBean();
			c.setDate(com.getDate());
			c.setId(com.getId());
			c.setText(com.getText());
			c.setTitle(com.getTitle());
			comunicationBeans.add(c);
		}

		return comunicationBeans;
	}
	
}
