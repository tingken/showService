/**
 * 
 */
package com.tingken.test.api;

/**
 * @author tingken.com
 *
 */
public class AuthResult {

	private String loginId;
	private String showPageAddress;
	public String getShowPageAddress() {
		return showPageAddress;
	}
	public void setShowPageAddress(String showPageAddress) {
		this.showPageAddress = showPageAddress;
	}
	/**
	 * @return the loginId
	 */
	public String getLoginId() {
		return loginId;
	}
	/**
	 * @param loginId the loginId to set
	 */
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
}
