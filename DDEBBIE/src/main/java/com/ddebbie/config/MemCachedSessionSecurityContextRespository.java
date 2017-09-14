package com.ddebbie.config;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;

import com.ddebbie.cache.CacheManager;
import com.ddebbie.utils.Constants;

/**
 * @author Ram
 * 13-Sep-2017
 */
@Component
public class MemCachedSessionSecurityContextRespository implements
		SecurityContextRepository {
    @Autowired
    CacheManager cacheManager;

	protected SecurityContext generateNewContext() {
		return SecurityContextHolder.createEmptyContext();
	}

	@Override
	public SecurityContext loadContext(
			HttpRequestResponseHolder requestResponseHolder) {

		HttpServletRequest httpServletRequest = requestResponseHolder
				.getRequest();
		HttpServletResponse httpServletResponse = requestResponseHolder
				.getResponse();

		Cookie cookie[] = httpServletRequest.getCookies();
		SecurityContext context = null;
		if (cookie != null && cookie.length > 0) {
			for (int i = 0; i < cookie.length; i++) {
				Cookie cookiee = cookie[i];
				if (cookiee.getName().equalsIgnoreCase(Constants.DDEBBIE_SESSION)) {
					Cookie requestCookie = cookie[i];
					if (cacheManager
							.getCache(
									com.ddebbie.cache.CacheRegionType.USER_SESSION_CACHE)
							.getValue(requestCookie.getValue()) != null) {
						Authentication userAuthentication = (Authentication) cacheManager
								.getCache(
										com.ddebbie.cache.CacheRegionType.USER_SESSION_CACHE)
								.getValue(requestCookie.getValue());
						SecurityContext scContext = new SecurityContextImpl();
						scContext.setAuthentication(userAuthentication);
						context = scContext;
						break;
					} else {
						context = generateNewContext();
					}
				} else {
					context = generateNewContext();
				}
			}

		} else {
			context = generateNewContext();
		}

		return context;
	}

	@Override
	public void saveContext(SecurityContext context,
			HttpServletRequest request, HttpServletResponse response) {
	}

	@Override
	public boolean containsContext(HttpServletRequest request) {
		Cookie cookie[] = request.getCookies();
		if (cookie != null && cookie.length > 0) {
			for (int i = 0; i < cookie.length; i++) {
				Cookie cookiee = cookie[i];
				if (cookiee.getName().equalsIgnoreCase(Constants.DDEBBIE_SESSION)) {
					Cookie requestCookie = cookie[i];
					if (cacheManager
							.getCache(
									com.ddebbie.cache.CacheRegionType.USER_SESSION_CACHE)
							.getValue(requestCookie.getValue()) != null) {
						return true;
					} else {
						return false;
					}
				}
			}
		}

		return false;
	}

}
