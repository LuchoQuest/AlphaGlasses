package com.example.android.smartglass;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

/* Implementacion para la navegación */
/* implements BottomNavigationView.OnNavigationItemSelectedListene r*/
public class CustomProcess extends AppCompatActivity {

    String address = null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    //SPP UUID. Look for it
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent newint = getIntent();
        address = newint.getStringExtra(MainActivity.EXTRA_ADDRESS); //receive the address of the bluetooth device

        setContentView(R.layout.activity_custom_process);

        new ConnectBT().execute(); //Call the class to connect

/*        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);*/

        Button conectarButton = findViewById(R.id.escribir); // Cambia esto al ID correcto de tu botón "Conectar"
        conectarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mostrar el diálogo aquí
                showCustomDialog();
            }
        });
    }

/*    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_1:
                // Lógica para navegar a la pantalla correspondiente al primer elemento del menú
                Intent intent1 = new Intent(this, CustomProcess.class);
                startActivity(intent1);
                return true;

            case R.id.menu_item_2:
                // Lógica para navegar a la pantalla correspondiente al primer elemento del menú
                Intent intent2 = new Intent(this, PantallaDispositivo.class);
                startActivity(intent2);
                return true;

            case R.id.menu_item_3:
                // Lógica para navegar a la pantalla correspondiente al primer elemento del menú
                Intent intent3 = new Intent(this, PantallaLlamadas.class);
                startActivity(intent3);
                return true;

            default:
                return false;
        }
    }*/

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_text, null);
        final EditText editText = dialogView.findViewById(R.id.editText);
        Button sendButton = dialogView.findViewById(R.id.button_send);

        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();
        dialog.show();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = editText.getText().toString();
                // Aquí puedes enviar el mensaje utilizando el BluetoothSocket
                if (!message.isEmpty() && btSocket != null) {
                    try {
                        btSocket.getOutputStream().write(message.getBytes());
                        dialog.dismiss(); // Cerrar el diálogo después de enviar el mensaje
                    } catch (IOException e) {
                        e.printStackTrace();
                        // Manejar errores si ocurren al enviar el mensaje
                    }
                }
            }
        });
    }

    private void Disconnect()
    {
        if (btSocket!=null) //If the btSocket is busy
        {
            try
            {
                btSocket.close(); //close connection
            }
            catch (IOException e)
            { msg("Error");}
        }
        finish(); //return to the first layout

    }

    // fast way to call Toast
    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }


    public void getRecipe(View view) {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("Recipie for soup".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    public void getMsg(View view) {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("  Lucho: Hola Como     estas?".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    public void getLocation(View view) {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("  Sena CBA Mosquera".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    public void getHotels(View view) {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("  Metros Pizza, Subway".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    public void getCalls(View view) {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("  Enrique esta         llamando.....".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    public void getReadings(View view) {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("13.3V".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(CustomProcess.this, "Connecting...", "Please wait!!!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try
            {
                if (btSocket == null || !isBtConnected)
                {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                finish();
            }
            else
            {
                msg("Connected.");
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }
}
