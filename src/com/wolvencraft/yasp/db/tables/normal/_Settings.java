package com.wolvencraft.yasp.db.tables.normal;

public enum _Settings implements _NormalTable {
	
	TableName("settings"),
	Key("key"),
	Value("value");
	
	_Settings(String columnName) {
		this.columnName = columnName;
	}
	
	private String columnName;
	
	@Override
	public String toString() { return columnName; }
}
