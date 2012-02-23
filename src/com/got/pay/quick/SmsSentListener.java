package com.got.pay.quick;

public interface SmsSentListener {
	
	public void onSmsSent(String toPhoneNumber, String content);
	
}
