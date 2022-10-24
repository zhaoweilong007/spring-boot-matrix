package com.zwl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * 开启websocket的支持
 */
@Configuration  
public class WebSocketConfig extends WebSecurityConfigurerAdapter {
    @Bean  
    public ServerEndpointExporter serverEndpointExporter(){  
        return new ServerEndpointExporter();
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/socketServer/**");
    }
}
