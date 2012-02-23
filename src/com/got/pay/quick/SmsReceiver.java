package com.got.pay.quick;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SmsReceiver extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		Object[] messages = (Object[]) bundle.get("pdus");
		SmsMessage[] smsMessages = new SmsMessage[messages.length];
		SmsSender sender = new SmsSender();
		for (int n = 0; n < messages.length; n++) {
			smsMessages[n] = SmsMessage.createFromPdu((byte[])messages[n]);
			Toast.makeText(context, 
					"收到来自：" + smsMessages[n].getOriginatingAddress() 
					+ ", 的短信： " + smsMessages[n].getMessageBody(), 
					Toast.LENGTH_LONG).show();
			if (isComfirmSms(smsMessages[n].getMessageBody())) {
				sender.sendSMS(Contants.toPhone, getConfirmReply(smsMessages[n].getMessageBody()));
			}
			if (isResultSms(smsMessages[n].getMessageBody())) {
				Intent i = new Intent();
			}
			
		}
	}

	private boolean isResultSms(String messageBody) {
		return messageBody.indexOf("充值") > -1;
	}

	private String getConfirmReply(String messageBody) {
		return "0";
	}

	private boolean isComfirmSms(String messageBody) {
		return messageBody.indexOf("发送") > -1;
	}
	
}