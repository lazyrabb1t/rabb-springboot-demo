package xyz.lazyrabbit.shiro.token;

import org.apache.shiro.authc.AuthenticationToken;

public class JwtToken implements AuthenticationToken {

	private static final long serialVersionUID = -5502201416850163554L;
	private String token;

	public JwtToken(String token) {
		this.token = token;
	}

	@Override
	public Object getPrincipal() {
		return token;
	}

	@Override
	public Object getCredentials() {
		return token;
	}
}
