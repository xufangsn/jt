package com.jt.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.pojo.User;
import com.jt.service.DubboCartService;
import com.jt.util.UserThreadLocal;
import com.jt.vo.SysResult;

//因为要跳转页面
@Controller
@RequestMapping("/cart")
public class CartController {

	
	@Reference(timeout = 3000,check = false)
	private DubboCartService cartService;
	/**
	 * 1.实现商品列表信息展现
	 * 2.页面取值:${cartList}
	 */
	@RequestMapping("/show")
	public String findCartList(Model model,HttpServletRequest request) {
		//User user = (User) request.getAttribute("JT_USER");
		User user = UserThreadLocal.get();
		Long userId = user.getId();//暂时写死
		List<Cart> cartList = cartService.findCartListByUserId(userId);
		model.addAttribute("cartList", cartList);
		
		return "cart";//返回页面
	}
	
	/**
	 * 实现购物车数量的修改
	 * 如果url参数中使用restFul风格
	 * @param cart
	 * @return
	 */
	
	@RequestMapping("/update/num/{itemId}/{num}")
	@ResponseBody
	public SysResult updateCartNum(Cart cart) {
		try {
			User user = UserThreadLocal.get();
			Long userId = user.getId();
			cart.setUserId(userId);
			cartService.updateCartNum(cart);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}
	
	/**
	 * 实现购物车的删除操作
	 * @return
	 */
	@RequestMapping("/delete/{itemId}")
	public String deleteCartByItemId(Cart cart){
		User user = UserThreadLocal.get();
		Long userId = user.getId();
		cart.setUserId(userId);
		cartService.deleteCart(cart);
		
		return "redirect:/cart/show.html";
		
	}
	
	@RequestMapping("/add/{itemId}")
	public String insertCart(Cart cart) {
		User user = UserThreadLocal.get();
		Long userId = user.getId();
		cart.setUserId(userId);
		cartService.insertCart(cart);
		
		//新增数据之后,展现购物车列表信息
		return "redirect:/cart/show.html";
	}
}
