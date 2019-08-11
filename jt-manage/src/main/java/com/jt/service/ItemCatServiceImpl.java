package com.jt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.ItemCatMapper;
import com.jt.pojo.ItemCat;
import com.jt.vo.EasyUITree;


@Service
public class ItemCatServiceImpl implements ItemCatService {
	
	/*
	 * Autowired private ShardedJedis jedis;
	 */

	@Autowired
	private ItemCatMapper itemCatMapper;

	@Override
	public String findItemCatNameById(Long itemCatId) {
		ItemCat itemCat = itemCatMapper.selectById(itemCatId);
		
		return itemCat.getName();
	}

	@Override
	public List<EasyUITree> findItemCatById(Long parentId) {
		
		List<EasyUITree> listEasyUITree = new ArrayList<>();
		QueryWrapper<ItemCat> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("parent_id", parentId);
		List<ItemCat> listItemCat = itemCatMapper.selectList(queryWrapper);
		
		for (ItemCat itemCat : listItemCat) {
			EasyUITree easyUITree = new EasyUITree();
			easyUITree.setId(itemCat.getId());
			easyUITree.setText(itemCat.getName());
			easyUITree.setState(itemCat.getIsParent() ? "closed":"open");
			listEasyUITree.add(easyUITree);
		}
		
		return listEasyUITree;
	}

	/*
	 *  public List<EasyUITree> findItemCatByCache(Long parentId) { String
	 * key = "ITEM_CAT_"+parentId; String result = jedis.get(key); List<EasyUITree>
	 * treeList = new ArrayList<EasyUITree>(); if(StringUtils.isEmpty(result)) {
	 * //如果为空,查询数据库 treeList = findItemCatById(parentId); //将数据转化为Json String json =
	 * ObjectMapperUtil.toJSON(treeList); jedis.setex(key,7*24*3600, json);
	 * System.out.println("业务查询数据库"); }else { //表示缓存中有数据 treeList =
	 * ObjectMapperUtil.toObject(result,treeList.getClass());
	 * System.out.println("业务查询redis缓存"); }
	 * 
	 * return treeList; }
	 */

}
