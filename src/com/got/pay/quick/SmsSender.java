package com.got.pay.quick;

import java.util.ArrayList;
import java.util.List;

import android.telephony.SmsManager;

public class SmsSender {
	
	private List<SmsSentListener> sentListener = new ArrayList<SmsSentListener>();

	public void sendSMS(String toPhoneNumber, String message) {
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(toPhoneNumber, null, message, null, null);
		for (SmsSentListener l : sentListener) {
			l.onSmsSent(toPhoneNumber, message);
		}
	}
	
	public void addSentListener(SmsSentListener l) {
		sentListener.add(l);
	}
}
