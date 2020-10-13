package com.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Configuration
public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Override
	protected void handle(HttpServletRequest request, HttpServletResponse response, 
			Authentication authentication) throws IOException, ServletException {
			
			String url = determineUrl(authentication);
		
			if(response.isCommitted()) {
				return;
			}
	
			RedirectStrategy strategy = new DefaultRedirectStrategy();
			strategy.sendRedirect(request, response, url);
			
	}

	
	private String determineUrl(Authentication authentication) {
		
		String targetUrl = "/login?error=true";
		
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		List<String> roles = new ArrayList<>();
		for(GrantedAuthority authority:authorities) {
			roles.add(authority.getAuthority());
		}
		
		if(roles.contains("ROLE_CUSTOMER")) {
			targetUrl="/customerHome";
		} else {
			targetUrl="/registerCategory";
		}
		
		return targetUrl;
	}

	

	
	
}
