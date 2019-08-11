package com.jt.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.ItemCat;
import com.jt.util.ObjectMapperUtil;

@RestController
public class JSONPController {
	//@RequestMapping("/web/testJSONP")
	public String testJSONP01(String callback) {
		ItemCat item = new ItemCat();
		item.setId(998L);
		item.setName("滴滴");
		String json = ObjectMapperUtil.toJSON(item);
		return callback+"("+json+")";
	} 
	
	@RequestMapping("/web/testJSONP")
	public JSONPObject testJSONP02(String callback) {
		ItemCat item = new ItemCat();
		item.setId(998L);
		item.setName("滴滴");
		JSONPObject object = new JSONPObject(callback,item);
		return object;
	}
}
