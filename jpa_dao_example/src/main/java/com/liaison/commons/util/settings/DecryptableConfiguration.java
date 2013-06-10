package com.liaison.commons.util.settings;

import org.apache.commons.configuration.Configuration;

public interface DecryptableConfiguration extends Configuration {
	/**
	 * Means to get a decrypted char[] form of an encrypted property.
	 * Remember, please do not use Strings to handle sensitive data.
	 * 
	 * @param key name of property
	 * @param encryptionRequired 
	 * @return 
	 * If property is encrypted: Returns decrypted value
	 * If encryption not required and property is not encrypted: Returns still unencrypted value
	 * @throws SOMESORT OF RUNTIME ERROR If encryption required and property not encrypted.
	 */
	public char[] getDecryptedCharArray(String key, boolean encryptionRequired);

	/**
	 * Checks to see if key is advertised as encrypted.
	 * 
	 * NOTE:
	 * Perhaps we do so by means of looking for "child key" with ending in
	 * ".isEncrypted". 
	 * Only a true value of associated with this key will return true.
	 */
	public boolean isEncryptedProperty(String key);
	
}
