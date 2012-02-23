package com.got.pay.quick;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class QuickPayActivity extends Activity implements SmsSentListener, SmsReceivedListener {

	private Button btnSendSMS;
	private Spinner coinSpinner;
	private EditText txtName;
	
	private SmsSender sender = new SmsSender();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		btnSendSMS = (Button) findViewById(R.id.btnSendSMS);
		coinSpinner = (Spinner) findViewById(R.id.coinSpinner);
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
	            this, R.array.planets_array, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    coinSpinner.setAdapter(adapter);
		
		txtName = (EditText) findViewById(R.id.txtName);
		sender.addSentListener(this);

		btnSendSMS.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String phoneNo = Contants.toPhone;
				String message = txtName.getText().toString() + coinSpinner.getItemAtPosition(coinSpinner.getSelectedItemPosition()).toString();
				sender.sendSMS(phoneNo, message);
				//pay(phoneNo, message);
			}
		});
	}
	
	@SuppressWarnings("unused")
	private void pay(String phoneNo, String message) {
		
		String SENT = "SMS_SENT";
		String DELIVERED = "SMS_DELIVERED";

		//发送监控
		PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
		//接收监控
		PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);

		// ---when the SMS has been sent---
		registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context arg0, Intent arg1) {
				switch (getResultCode()) {
				case Activity.RESULT_OK:
					Toast.makeText(getBaseContext(), "短信已发送",
							Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
					Toast.makeText(getBaseContext(), "Generic failure",
							Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_NO_SERVICE:
					Toast.makeText(getBaseContext(), "找不到网络服务",
							Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_NULL_PDU:
					Toast.makeText(getBaseContext(), "Null PDU",
							Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_RADIO_OFF:
					Toast.makeText(getBaseContext(), "Radio off",
							Toast.LENGTH_SHORT).show();
					break;
				}
			}
		}, new IntentFilter(SENT));

		// ---when the SMS has been delivered---
		registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context arg0, Intent arg1) {
				switch (getResultCode()) {
				case Activity.RESULT_OK:
					Toast.makeText(getBaseContext(), "SMS delivered",
							Toast.LENGTH_SHORT).show();
					break;
				case Activity.RESULT_CANCELED:
					Toast.makeText(getBaseContext(), "SMS not delivered",
							Toast.LENGTH_SHORT).show();
					break;
				}
			}
		}, new IntentFilter(DELIVERED));
		
		SmsManager smsManager = SmsManager.getDefault();
		smsManager.sendTextMessage(phoneNo, null, message, sentPI, deliveredPI);
	}

	@Override
	public void onSmsReceived(SmsMessage smsMessage) {
		Toast.makeText(getBaseContext(), 
				"收到来自：" + smsMessage.getOriginatingAddress() 
				+ ", 的短信： " + smsMessage.getMessageBody(), 
				Toast.LENGTH_LONG).show();
	}

	@Override
	public void onSmsSent(String toPhoneNumber, String content) {
		Toast.makeText(getBaseContext(), "短信已发送", Toast.LENGTH_SHORT).show();
	}
	
	
}
