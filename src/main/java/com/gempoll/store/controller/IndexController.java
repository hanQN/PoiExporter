package com.gempoll.store.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import com.gempoll.store.tools.SelfPoiExporter;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

@ControllerBind(controllerKey="/store/index")
public class IndexController extends BaseController {
	public void index(){
		//new Thread(new SocketClientDemo()).start();
	}
	//导出
	public void export(){
		String sql = "select * from information ";
		List<Record> list = Db.find(sql);
		File file = new File(PathKit.getWebRootPath()+"/files/Information.xlsx");
		if(file.exists()){
			file.delete();
		}
		//导出数据的表头
		String[] headers = new String[]{"数据源","品牌","结算日期","订单类型","车型","标准化车型","推送时间",
				"开始时间","推送日期","期数"};
		//表头对应字段
		String[] columns = new String[]{"source","brand","account_date","bill_type","model","standard_model",
				"add_time","start_time","month","period"};
		Workbook workbook = SelfPoiExporter
				.data(list)
				.sheetName("sheet0")
				.columns(columns)
				.headers(headers)
				.export();
		try {
			FileOutputStream fos = new FileOutputStream(file);
			workbook.write(fos);
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
