package hm.action;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;

public abstract class Action implements EventHandler<ActionEvent> {

	protected FXMLLoader loader;

	public FXMLLoader getLoader() {
		return loader;
	}

	public void setLoader(FXMLLoader loader) {
		this.loader = loader;
	}
	
	public Object getElement(String id) {
		return loader.getNamespace().get(id);
	}

}
