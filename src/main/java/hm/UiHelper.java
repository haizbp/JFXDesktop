package hm;

import java.util.Map;

import hm.action.Action;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Tooltip;

public class UiHelper {

	public static final String STYLE_SHEET_PREFIX = "stylesheet";
	public static final String CLASS_PREFIX = "class";

	public static Button createButton(Map<String, Object> settings) {

		Button button = new Button();
		button.setTooltip(new Tooltip(String.valueOf(settings.get("title"))));
		button.getStyleClass().addAll((String[]) settings.get("clazz"));
		button.setId(String.valueOf(settings.get("id")));

		return button;
	}

	public static Menu createMenu(Map<String, Object> settings) {

		Menu menu = new Menu();
		menu.setText(String.valueOf(settings.get("title")));
		menu.getStyleClass().addAll((String[]) settings.get("clazz"));
		menu.setId(String.valueOf(settings.get("id")));

		return menu;
	}

	public static MenuItem createMenuItem(Map<String, Object> settings)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {

		MenuItem menu = new MenuItem();
		menu.setText(String.valueOf(settings.get("title")));
		menu.getStyleClass().addAll((String[]) settings.get("clazz"));
		menu.setId(String.valueOf(settings.get("id")));

		Action action = (Action) Class.forName(String.valueOf(settings.get("action"))).newInstance();
		action.setLoader((FXMLLoader) AppHelper.getNode("loader"));
		
		menu.setOnAction(action);
		
		return menu;
	}

	public static Separator createSeparator() {
		Separator sep = new Separator();
		sep.setOrientation(Orientation.VERTICAL);

		return sep;
	}

	public static SeparatorMenuItem createSeparatorMenuItem() {
		SeparatorMenuItem sep = new SeparatorMenuItem();
		return sep;
	}

}
