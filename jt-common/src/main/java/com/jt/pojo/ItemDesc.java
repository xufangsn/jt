package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@TableName("tb_item_desc")
@Data
@Accessors(chain=true)
public class ItemDesc extends BasePojo {
	
	private static final long serialVersionUID = 1016593866051306734L;
	//	item_id              bigint(10) not null comment '商品ID',
//	   item_desc            text,
	@TableId
	private Long itemId;
	private String itemDesc;
}
