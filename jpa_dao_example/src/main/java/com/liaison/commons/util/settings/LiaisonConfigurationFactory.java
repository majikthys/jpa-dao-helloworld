package com.liaison.commons.util.settings;


/**
 * 
 * Factory for retrieving the DecryptableConfiguration for property interaction
 * 
 * @author jeremyfranklin-ross
 *
 */
public class LiaisonConfigurationFactory {

	//Using example configuration.. however it could instead be an implementation backed with 
	//an archaius ConcurrentCompositeConfiguration, for example: 
	//    (ConcurrentCompositeConfiguration) DynamicPropertyFactory.getInstance().getBackingConfigurationSource();

	private static final DecryptableConfiguration decryptableConfiguration = new LiaisonConfigurationExample();
	
	
	private LiaisonConfigurationFactory() {}
   
	/**
	 * 
	 * @return
	 */
	public static DecryptableConfiguration getConfiguration() {
		return decryptableConfiguration;
	}	
	
	
	
}
