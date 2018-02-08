package com.gempoll.store.plugin;

import com.gempoll.store.tools.ToolSqlXml;
import com.jfinal.plugin.IPlugin;


public class SqlXmlPlugin implements IPlugin {
	
	public SqlXmlPlugin() {
		
	}
	
	public boolean start() {
		ToolSqlXml .init(true);
		return true;
	}
	
	public boolean stop() {
		ToolSqlXml.destory();
		return true;
	}
	
}
