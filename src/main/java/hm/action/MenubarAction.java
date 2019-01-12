package hm.action;

import javax.swing.SpringLayout;

import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class MenubarAction extends Action {

	@Override
	public void handle(ActionEvent event) {

		TabPane pane;
		Tab tab;
		if (event.getSource() instanceof MenuItem) {
			MenuItem menuItem = (MenuItem) event.getSource();
			String id = menuItem.getId();

			switch (id) {
			case "OPEN_CONSOLE":
				pane = (TabPane) getElement("interfaceOneUI");
				tab = new Tab("Console");
				pane.getTabs().add(tab);
				pane.getSelectionModel().select(tab);
				break;
			case "OPEN_TERMINAL":
				pane = (TabPane) getElement("interfaceOneUI");
				tab = new Tab("Terminal");
				pane.getTabs().add(tab);
				pane.getSelectionModel().select(tab);
				break;

			default:
				break;
			}
		}

	}

}
