package com.liaison.commons.util;

public class PrintUtil
{
	public static void timePrint( String strDesc, long iTime )
	{
		long iEnd = System.currentTimeMillis();
		float fTime = (float)((iEnd - iTime) / 1000.0);
		System.out.println( strDesc + ": " + fTime );
	}
}
