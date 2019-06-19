package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements PermissionsManager.IPermissionManager, ContactsManager.IContractManager {

    PermissionsManager permissionsManager;
    TextView tvContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvContact=findViewById(R.id.tvContact);
        permissionsManager = new PermissionsManager(this, this);
        permissionsManager.checkPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.checkResult(requestCode,permissions,grantResults);
    }

    @Override
    public void onPermissionResult(boolean isGranted) {
        Log.d("TAG", "onPermissionResult: YEP" );
        if(isGranted) {
            getContacts();
        } else {
            Toast.makeText(this, "Can not process", Toast.LENGTH_SHORT).show();
        }
    }

    public void getContacts() {
        ContactsManager contactsManager = new ContactsManager(this);
        contactsManager.getContacts();
    }

    @Override
    public void onContactsRecieved(List<Contact> contactsList) {
        StringBuilder str=new StringBuilder();
        for(Contact contact : contactsList) {

            Log.d("TAG", "onContactsRecieved: " + contact.toString());
            str.append(contact.toString()+"\n");
        }
        tvContact.setText(str.toString());
    }
}
