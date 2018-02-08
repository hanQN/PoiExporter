package com.gempoll.store.beetl.func;

import java.util.Map;

import org.beetl.core.Context;
import org.beetl.core.Function;


public class MapValue implements Function{

	@SuppressWarnings("unchecked")
	@Override
	public Object call(Object[] paras, Context ctx) {
		// TODO Auto-generated method stub		
		Map<String, Object> map = (Map<String, Object>)paras[0];
		String index = (String)paras[1];
		if(paras.length>2 && null!=paras[2] && null!=map.get(index) && !map.get(index).toString().isEmpty()){
			if(paras[2].toString().equalsIgnoreCase("string")){
				return map.get(index).toString();
			}
			if(paras[2].toString().equalsIgnoreCase("long")){
				return Long.parseLong(map.get(index).toString());
			}
			if(paras[2].toString().equalsIgnoreCase("int")){
				return Integer.parseInt(map.get(index).toString());
			}
			if(paras[2].toString().equalsIgnoreCase("double")){
				return Double.parseDouble(map.get(index).toString());
			}
		}
		return map.get(index);
	}

}
