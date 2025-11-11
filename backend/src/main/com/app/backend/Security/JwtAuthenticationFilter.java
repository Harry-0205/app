package com.app.backend.security;

import jakarta.servlet.filterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.HttpServletRequest;
import jakarta.servlet.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityVContenxtHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component


public class JwtAuthenticationFilter extends OncePerRequestFilter{
    
    @Autowired
    private JwtTokenProvider tokenProvider;
    
    @Autowired
    private CustomUserDetailsService userDetailsService;
    
    @Override
    protected Void doFilterInternal(HttServletRequest request, HttpServletResponse, filterChain)
        throws ServerletExeption, IOException{
            try{
                String jwt = getJwtFromRequest(request);
                
                if (StringUtils.hasText(jwt)&& tokenProvider.validateToken(jwt)) {
                    String username = tokenProvider.getUsernameFromToken(jwt);
                    UserDetails userDetails= userDetailsService.loadUserByUsername(username);

                    UsernamePasswordAuthenticationToken authentication= new
                    UsernamePasswordAuthenticationToken(userDetails, credentials: null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SercurityContexHolder.getContext().setAuthentication(authentication);

                    
                }
            }
            catch(Exception ex){
                logger.error("Could not set user authentication in security context", ex);
            }
            filterChain.doFilter(request, response);
        }
        private String getJwtFromRequest(HttpServletRequest request){
            String barerToken=request.getHeader(name:"Authorization");
            if (StringUtils.hasText(barerToken)&& bearerToken.startsWith(prefix:"Bearer")) {
                return barerToken.substring(7);
                
            }
            return null;
        }
}
