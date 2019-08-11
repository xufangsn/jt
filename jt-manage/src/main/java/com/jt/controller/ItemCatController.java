package com.jt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jt.annotation.Cache_Find;
import com.jt.enu.KEY_ENUM;
import com.jt.service.ItemCatService;
import com.jt.vo.EasyUITree;

@RestController
@RequestMapping("/item/cat/")
public class ItemCatController {
	@Autowired
	private ItemCatService itemCatService;
	
	//实现根据id查询商品分类信息
	@RequestMapping("queryItemName")
	public String findItemCatNameById(Long itemCatId) {
		return itemCatService.findItemCatNameById(itemCatId);
	}
	
	//查询全部数据的商品的分类信息
	//需要获取任意名称的参数,为指定参数赋值
	//@RequestParam		name/value接收参数的名称 required = true/false 是否必须传值
	@RequestMapping("list")
	@Cache_Find(key="ITEM_CAT",keyType=KEY_ENUM.AUTO)
	public List<EasyUITree> findItemCatById(
			@RequestParam(value="id",defaultValue="0") Long parentId){
//		if(parentId==null)
//			parentId = 0L; //查询一级商品分类
		return itemCatService.findItemCatById(parentId);
//		return itemCatService.findItemCatByCache(parentId);
	}
}
