package hm.model;

import java.io.Serializable;
import java.util.List;

public class MenuModel implements Serializable {

	private String id;
	private String title;
	private String action;
	private String clazz;
	private String type;
	private List<MenuModel> sub;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<MenuModel> getSub() {
		return sub;
	}

	public void setSub(List<MenuModel> sub) {
		this.sub = sub;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
