/**
 * 
 */
package sg.com.octus.test.salomax.model.dao.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Helper class to simples user for security purposes.
 * 
 * @author salomax
 */
public class UserDetailsHelper implements UserDetails {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 4901867006565418557L;

	/**
	 * Username.
	 */
	private String username;
	
	/**
	 * Password.
	 */
	private String password;
	
	/**
	 * Contructor passing username and password.
	 */
	public UserDetailsHelper(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	/**
	 * Is enable.
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	/**
	 * Is credentials non expired.
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	/**
	 * Is accont non locked.
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	/**
	 * Is account non expired.
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	/**
	 * Return username.
	 */
	@Override
	public String getUsername() {
		return this.username;
	}
	
	/**
	 * Return password.
	 */
	@Override
	public String getPassword() {
		return this.password;
	}
	
	/**
	 * Return authorities. In case, there is just one "ROLE_USER"
	 * for tests intention.
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		return authorities;
	}

}
