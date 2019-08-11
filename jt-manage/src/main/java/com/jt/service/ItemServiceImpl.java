package com.jt.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.ItemDescMapper;
import com.jt.mapper.ItemMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUIData;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemDescMapper itemDescMapper;

	@Override
	public EasyUIData findItemByPage(Integer page, Integer rows) {
		int total = itemMapper.selectCount(null);
		int start = (page-1)*rows;
		List<Item> list = itemMapper.findItemByPage(start,rows);
		EasyUIData easyUIData = new EasyUIData();
		easyUIData.setRows(list);
		easyUIData.setTotal(total);
		return easyUIData;
	}

	@Transactional	//添加事务控制
	@Override
	public void saveItem(Item item,ItemDesc itemDesc) {
		//封装插入商品基本信息
		item.setStatus(1).setCreated(new Date()).setUpdated(item.getCreated());
		itemMapper.insert(item);
		//封装插入商品大文本信息
		itemDesc.setItemId(item.getId())
		.setCreated(new Date())
		.setUpdated(itemDesc.getCreated());
		
		itemDescMapper.insert(itemDesc);
	}

	/**
	 * propagation 事务传播属性
	 * 		默认值 REQUIRED	必须添加事务
	 * 		REQUIRES_NEW 	必须新建一个事务
	 * 		SUPPORTS		事务支持的
	 * 		NEVER			绝不加事务
	 * 
	 * Spring中默认的事务控制策略:
	 * 		1.检查异常/编译异常	不负责事务控制
	 * 		2.运行时异常/error	回滚事务
	 * 
	 * rollbackFor = "包名.类名.名称.class" 按照指定的异常回滚事务
	 * noRollbackFor = "包名.类名.名称.class" 按照指定的异常不回滚事务
	 * 
	 * readOnly = true 只读 不允许修改数据库
	 */
	@Transactional
	@Override
	public void updateItem(Item item,ItemDesc itemDesc) {
		item.setUpdated(new Date());
		itemMapper.updateById(item);
		//根据item id更新 itemDesc数据
		itemDesc.setItemId(item.getId()).setUpdated(item.getUpdated());
		itemDescMapper.updateById(itemDesc);
	}

	@Transactional
	@Override
	public void deleteItem(Long[] ids) {
		
		//1.手动删除
		//itemMapper.deleteItem(ids);
		List<Long> itemList = Arrays.asList(ids);
		itemMapper.deleteBatchIds(itemList);
		//同时也删除itemDesc
		itemDescMapper.deleteBatchIds(itemList);
		
	}

	@Override
	public void reshelfItem(Long[] ids) {
		Item item = new Item();
		//修改状态为1 上架
		item.setStatus(1).setUpdated(new Date());
		
		UpdateWrapper<Item> updateWrapper = new UpdateWrapper<>();
		List<Long> itemList = Arrays.asList(ids);
		updateWrapper.in("id", itemList);
		itemMapper.update(item, updateWrapper);
		
	}

	@Override
	public void instockItem(Long[] ids) {
		Item item = new Item();
		//修改状态为2 下架
		item.setStatus(2).setUpdated(new Date());
		
		UpdateWrapper<Item> updateWrapper = new UpdateWrapper<>();
		List<Long> itemList = Arrays.asList(ids);
		updateWrapper.in("id", itemList);
		itemMapper.update(item, updateWrapper);
	}

	@Override
	public ItemDesc findItemDescById(Long itemId) {
		return itemDescMapper.selectById(itemId);
	}

	@Override
	public Item findItemById(Long id) {
		return itemMapper.selectById(id);
	}


}
