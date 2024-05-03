package com.askme.controller;

import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class FrontCtl extends HandlerInterceptorAdapter {

	@Autowired
	MessageSource messageSource;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		if (session.getAttribute("userLogin") == null) {

			/*
			 * Cookie cookie= WebUtils.getCookie(request,
			 * "org.springframework.web.servlet.i18n.CookieLocaleResolver.LOCALE");
			 * 
			 * String locale= cookie.getValue();
			 */

			Locale locale = LocaleContextHolder.getLocale();

			System.out.println(locale);

			String msg = messageSource.getMessage("message.error.sessionexpired", null, locale);

			request.setAttribute("error", msg);
			RequestDispatcher rd = request.getRequestDispatcher("/Login");
			rd.forward(request, response);
			return false;
		}
		return true;
	}

}
