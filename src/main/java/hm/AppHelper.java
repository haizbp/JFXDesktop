/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hm;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import hm.model.MenuModel;
import hm.model.NavModel;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.HBox;

/**
 *
 * @author USER
 */
@Component
public class AppHelper {

	private Environment environment;
	private ObjectMapper mapper = new ObjectMapper();
	private static Map<String, Object> nodes = new HashMap<>();

	public void initUI() throws JsonParseException, JsonMappingException, IOException, URISyntaxException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		addingStylesheet();
		loadMenu();
		initNav();
	}

	private void addingStylesheet() throws URISyntaxException, MalformedURLException {
		File[] listOfFiles = get(currentDirectory() + "/config/resources/fxml/css");

		for (File file : listOfFiles) {
			((Scene) nodes.get("scene")).getStylesheets().add(file.toURI().toURL().toString());
		}
	}

	private void loadMenu() throws JsonParseException, JsonMappingException, IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		File[] listOfFiles = get(currentDirectory() + "/config/resources/menubar");

		List<MenuModel> menuModels;
		for (File file : listOfFiles) {
			menuModels = mapper.readValue(file,
					new TypeReference<List<MenuModel>>() {
					});

			addMenubar(nodes.get("menubarUI"), menuModels);
		}

	}
	
	private void addMenubar(Object node, List<MenuModel> menuModels) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		if(menuModels == null)
			return;
		
		Object o;
		Map<String, Object> settings;
		for (MenuModel menuModel : menuModels) {
			settings = new HashMap<>();
			settings.put("title", menuModel.getTitle());
			settings.put("action", menuModel.getAction());
			settings.put("clazz", menuModel.getClazz().split(","));
			settings.put("action", menuModel.getAction());
			settings.put("id", menuModel.getId());
			
			switch (menuModel.getType()) {
			case "menu":
				o = UiHelper.createMenu(settings);
				break;
			case "itm":
				o = UiHelper.createMenuItem(settings);
				break;
			default:
				o = UiHelper.createSeparatorMenuItem();
				break;
			}
			
			if(node instanceof MenuBar) {
				((MenuBar) node).getMenus().add((Menu)o);
			}else if(node instanceof Menu){
				((Menu) node).getItems().add((MenuItem)o);
			}
			
			addMenubar(o, menuModel.getSub());

		}
	}

	private void initNav() throws JsonParseException, JsonMappingException, IOException {
		File[] listOfFiles = get(currentDirectory() + "/config/resources/nav");
		List<NavModel> navConfigurations;
		Map<String, Object> settings;
		Button button;
		for (File file : listOfFiles) {
			navConfigurations = mapper.readValue(file, new TypeReference<List<NavModel>>() {
			});
			for (NavModel nav : navConfigurations) {
				if ("itm".equalsIgnoreCase(nav.getType())) {
					settings = new HashMap<>();
					settings.put("title", nav.getTitle());
					settings.put("action", nav.getAction());
					settings.put("clazz", nav.getClazz().split(","));
					button = UiHelper.createButton(settings);
					((HBox) nodes.get("navUI")).getChildren().add(button);
				} else {
					((HBox) nodes.get("navUI")).getChildren().add(UiHelper.createSeparator());
				}
			}
		}

	}

	public AppHelper(Environment environment) {
		this.environment = environment;
	}

	public <T> T getValue(String key, Class<T> clazz) {
		return environment.getProperty(key, clazz);
	}

	public String getAppTitle() {
		return environment.getProperty("configuration.apptitle");
	}

	public String getFxmlHome() {
		return environment.getProperty("configuration.fxmlhome");
	}

	public int getWindowX() {
		return environment.getProperty("configuration.window.x", Integer.class);
	}

	public int getWindowY() {
		return environment.getProperty("configuration.window.y", Integer.class);
	}

	public String currentDirectory() {
		return System.getProperty("user.dir");
	}

	public static void addNode(String key, Object node) {
		nodes.put(key, node);
	}

	public static Object getNode(String key) {
		return nodes.get(key);
	}
	
	private File[] get(String path) {
		File folder = new File(path);
		return folder.listFiles();
	}
}
