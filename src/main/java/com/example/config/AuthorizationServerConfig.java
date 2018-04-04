package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
    private AuthenticationManager authenticationManager;
	
	@Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) {
		endpoints.tokenStore(tokenStore())
			.authenticationManager(authenticationManager);
    }

    //认证服务器配置，用于设定哪个客户端（ClientId）是否拥有用户（user） 所赋予的权限
	//客户端（用ClientId 来标志）想要访问用户放在 resources 资源服务器上的资源
	//首先，Client要去认证服务器做认证，认证的最终结果是获得一个 access-token 授权码
	//然后，携带着 该 access-token 再去 资源服务器 获取资源
	//资源服务器拿到 Client 带来的 access-token ,对其解析，解析成功在给与该资源
	@Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory() 
	        .withClient("client")  //指定可以访问资源的 Client 客户端是哪个
	        .secret("clientpassword")	//且，该客户端需要提供的 密码是什么
	        .scopes("read", "write")	//该客户端具有的权利：读写
	        .authorizedGrantTypes("password")  //client 进行认证的方式以密码形式
	        .accessTokenValiditySeconds(3600);
    }
	
	@Bean
	public TokenStore tokenStore() {
		return new InMemoryTokenStore();
	}

}
