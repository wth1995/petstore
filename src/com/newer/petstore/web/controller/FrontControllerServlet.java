package com.newer.petstore.web.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 前端控制器（拦截所有的请求）
 * 
 * @author wtao
 *
 */
public class FrontControllerServlet extends HttpServlet {

	HashMap<String, Class> map = new HashMap<>();

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		// 设置 URL 和 Controller 的映射（使用注解）
		map.put("/register.action", RegisterController.class);
		map.put("/login.action", LoginController.class);
//		map.put("", RegisterController.class);
//		map.put("", RegisterController.class);
		
		System.out.println(map);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// /petstore/user/login.action
		String url = request.getRequestURI();
		String urlName = url.substring(url.lastIndexOf("/"));
		
		// 从映射中获得 url 对于的 控制器类
		Class controllerClass = map.get(urlName);
		if (controllerClass != null) {
			try {
				// 使用反射创建该类型的实例: 基于接口编程
				Controller controller = (Controller) controllerClass.newInstance();
				// 调用执行
				String page = controller.execute(request, response);
				// 响应的重定向
				response.sendRedirect(page);
				
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		} else {
			response.sendRedirect("error/404.html");
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 调用 doGet
		doGet(request, response);
	}

}
