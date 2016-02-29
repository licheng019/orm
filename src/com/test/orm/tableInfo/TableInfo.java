package com.test.orm.tableInfo;

import java.util.List;

public class TableInfo {
	private String className;
	private String tableName;
	private Id id;
	private List<Property> plist;
	private List<OneToMany> onelist;
	private List<ManyToOne> manylist;
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public Id getId() {
		return id;
	}
	public void setId(Id id) {
		this.id = id;
	}
	public List<Property> getPlist() {
		return plist;
	}
	public void setPlist(List<Property> plist) {
		this.plist = plist;
	}
	public List<OneToMany> getOnelist() {
		return onelist;
	}
	public void setOnelist(List<OneToMany> onelist) {
		this.onelist = onelist;
	}
	public List<ManyToOne> getManylist() {
		return manylist;
	}
	public void setManylist(List<ManyToOne> manylist) {
		this.manylist = manylist;
	}
	
}
