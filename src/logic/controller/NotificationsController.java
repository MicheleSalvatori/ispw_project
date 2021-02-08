package logic.controller;

import java.util.Observable;
import java.util.Observer;

import logic.model.Notifications;

public class NotificationsController extends Observable{

	public NotificationsController() {
        super();
    }
 
    public void announceNotify(Notifications not) {
        setChanged();
        notifyObservers(not);
    }

}
