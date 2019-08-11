package com.jt.service;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUIData;

public interface ItemService {

	EasyUIData findItemByPage(Integer page, Integer rows);

	void saveItem(Item item, ItemDesc itemDesc);

	void updateItem(Item item, ItemDesc itemDesc);

	void deleteItem(Long[] ids);

	void reshelfItem(Long[] ids);

	void instockItem(Long[] ids);

	ItemDesc findItemDescById(Long itemId);

	Item findItemById(Long id);



}
