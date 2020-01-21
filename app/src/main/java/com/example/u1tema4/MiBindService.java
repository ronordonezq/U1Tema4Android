package com.example.u1tema4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Toast;

import java.util.Date;

public class MiBindService extends AppCompatActivity {
  MyLocalService localService; private boolean isBound = false;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_mi_bind_service);
  }
  @Override
  protected void onStart() {
    super.onStart();
    Intent intent = new Intent(this, MyLocalService.class);
    bindService(intent, connection, Context.BIND_AUTO_CREATE);
  }
  @Override
  protected void onStop() {
    super.onStop();
    if (isBound) {
      unbindService(connection);
      isBound = false;
    }
  }
  private ServiceConnection connection = new ServiceConnection() {
    @Override
    public void onServiceConnected(ComponentName className, IBinder service) {
      MyLocalService.LocalBinder binder = (MyLocalService.LocalBinder) service;
      localService = binder.getService();
      isBound = true;
    }
    @Override
    public void onServiceDisconnected(ComponentName arg0) {isBound = false; }
  };

  public void mostrarfecha(View view) {
    if (isBound) {
      Date date = localService.getCurrentDate();
      Toast.makeText(this, String.valueOf(date), Toast.LENGTH_SHORT).show();
    }
  }
}
