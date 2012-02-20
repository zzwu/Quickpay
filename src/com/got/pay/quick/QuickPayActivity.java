package com.got.pay.quick;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class QuickPayActivity extends Activity {

	private Button btnSendSMS;
	private EditText txtPhoneNo;
	private EditText txtMessage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		btnSendSMS = (Button) findViewById(R.id.btnSendSMS);
		txtPhoneNo = (EditText) findViewById(R.id.txtPhoneNo);
		txtMessage = (EditText) findViewById(R.id.txtMessage);

		btnSendSMS.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String phoneNo = txtPhoneNo.getText().toString();
				String message = txtMessage.getText().toString();
				pay(phoneNo, message);
			}
		});
	}
	
	private void pay(String phoneNo, String message) {
		//TODO pay
	}
}
