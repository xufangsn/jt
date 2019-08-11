package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import com.jt.vo.EasyUIData;
import com.jt.vo.SysResult;

@Controller
@RequestMapping("/item/")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	@RequestMapping("query")
	@ResponseBody
	public EasyUIData findItemByPage(Integer page,Integer rows) {
		return itemService.findItemByPage(page,rows);
	}
	
	@RequestMapping("save")
	@ResponseBody
	public SysResult saveItem(Item item,ItemDesc itemDesc) {
		try {
			//实现数据新增
			itemService.saveItem(item,itemDesc);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}
	
	//修改商品信息
	@RequestMapping("update")
	@ResponseBody
	public SysResult updateItem(Item item,ItemDesc itemDesc) {
		try {
			itemService.updateItem(item,itemDesc);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
		
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public SysResult deleteItem(Long[] ids) {
		
		try {
			itemService.deleteItem(ids);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}
	
	@RequestMapping("reshelf")
	@ResponseBody
	public SysResult reshelfItem(Long[] ids) {
		try {
			itemService.reshelfItem(ids);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}
	
	@RequestMapping("instock")
	@ResponseBody
	public SysResult instockItem(Long[] ids) {
		try {
			itemService.instockItem(ids);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}
	
	@RequestMapping("query/item/desc/{itemId}")
	@ResponseBody
	public SysResult findItemDescById(@PathVariable Long itemId) {
		try {
			
			ItemDesc itemDesc = itemService.findItemDescById(itemId);
			
			return SysResult.ok(null, itemDesc);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}
	
}
