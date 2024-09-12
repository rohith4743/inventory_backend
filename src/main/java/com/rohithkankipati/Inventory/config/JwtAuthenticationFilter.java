package com.rohithkankipati.Inventory.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.rohithkankipati.Inventory.exception.ErrorResponse;
import com.rohithkankipati.Inventory.util.CustomUserDetailsService;
import com.rohithkankipati.Inventory.util.JwtTokenUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		 String header = request.getHeader("Authorization");
		    if (header != null && header.startsWith("Bearer ")) {
		        String jwt = header.substring(7);
		        String userIdString = request.getHeader("X-User-ID");

		        try {
		            Long userId = Long.parseLong(userIdString);

		            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
		                UserDetails userDetails = userDetailsService.loadUserById(userId);
		                if (jwtTokenUtil.validateToken(jwt, userDetails.getUsername())) {
		                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
		                            userDetails, null, userDetails.getAuthorities());
		                    SecurityContextHolder.getContext().setAuthentication(authentication);
		                }
		            }
		        } catch (NumberFormatException e) {
		            logger.error("Invalid user ID format in JWT: " + userIdString);
		            
		            response.setStatus(HttpStatus.BAD_REQUEST.value());
		            response.setContentType("application/json");
		            ErrorResponse error = new ErrorResponse(HttpStatus.UNAUTHORIZED, "Invalid user ID format; must be a numeric value.");
		            
		            response.getWriter().write(error.toString());
		            return;
		        } catch (Exception e) {
		        	logger.error("Invalid JWT Token : " + jwt);
		            response.setStatus(HttpStatus.UNAUTHORIZED.value());
		            response.setContentType("application/json");
		            ErrorResponse error = new ErrorResponse(HttpStatus.UNAUTHORIZED, "Invalid Header Credentials.");
		            response.getWriter().write(error.toString());
		            return;
		        }
		    }
		    filterChain.doFilter(request, response);

	}

}

