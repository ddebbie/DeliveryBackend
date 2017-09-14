package com.ddebbie.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.filter.GenericFilterBean;

import com.ddebbie.utils.Constants;

public class AuthenticationFilter extends GenericFilterBean {
	
	
	private AuthenticationManager authenticationManager;
	private SecurityContextRepository repo;

    public AuthenticationFilter(AuthenticationManager authenticationManager, SecurityContextRepository repo) {
        this.authenticationManager = authenticationManager;
        this.repo	=	repo;
    }

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		try {

			Cookie cookie[] = ((HttpServletRequest) request).getCookies();
			if (cookie != null && cookie.length > 0) {
				for (int i = 0; i < cookie.length; i++) {
					Cookie c = cookie[i];
					if (c.getName().equalsIgnoreCase(Constants.DDEBBIE_SESSION)) {
						SecurityContext sc = processTokenAuthentication(c,
								request, response);
						SecurityContextHolder.setContext(sc);
					}

				}

			}

			chain.doFilter(request, response);
		} catch (InternalAuthenticationServiceException internalAuthenticationServiceException) {
			SecurityContextHolder.clearContext();
			logger.error("Internal authentication service exception",
					internalAuthenticationServiceException);
			httpResponse
					.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} catch (AuthenticationException authenticationException) {
			SecurityContextHolder.clearContext();
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,
					authenticationException.getMessage());
		}

	}

	private SecurityContext processTokenAuthentication(Cookie cookie,
			ServletRequest request, ServletResponse response) {

		HttpRequestResponseHolder holder = new HttpRequestResponseHolder(
				(HttpServletRequest) request, (HttpServletResponse) response);

		SecurityContext contextBeforeChainExecution = repo.loadContext(holder);
		if (contextBeforeChainExecution != null) {

			return contextBeforeChainExecution;
		}

		return SecurityContextHolder.createEmptyContext();

	}

}
