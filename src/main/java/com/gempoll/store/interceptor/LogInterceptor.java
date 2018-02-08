package com.gempoll.store.interceptor;


import java.util.Date;
import java.util.Enumeration;
import java.util.Map;

import com.gempoll.store.tools.ToolUtils;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class LogInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation inv) {
		//保存日志
		/*
		String ip = ToolUtils.getIpAddr(inv.getController().getRequest());
		Date now = new Date();
		String action = inv.getActionKey();
		String sql = "SELECT COUNT(id) num FROM voc_buick";
		Buick b = Buick.dao.findFirst(sql);
		long num = b.getLong("num");
		Record r = new Record();
		r.set("ip", ip);
		r.set("action", action);
		r.set("time", now);
		r.set("num", num);

		Db.save("voc_log", r);
		*/
		inv.invoke();
	}

}
