package com.example.parchservicesms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.example.parchservicesms.Model.Solicitud;
import com.example.parchservicesms.Model.SolicitudExito;
import com.example.parchservicesms.Servicio.UpdateSolicitud;

public class SMSReceiver extends BroadcastReceiver {
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = "SmsBroadcastReceiver";

    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Intent Received " + intent.getAction());
        String msg = "";
        String numero = "";
        if (intent.getAction() == SMS_RECEIVED) {
            Bundle dataBundle = intent.getExtras();

            if (dataBundle != null) {
                Object[] pdus = (Object[]) dataBundle.get("pdus");
                final SmsMessage[] mensaje = new SmsMessage[pdus.length];

                for (int i = 0; i < mensaje.length; i++) {
                    if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M) {
                        String format = dataBundle.getString("format");
                        mensaje[i] = SmsMessage.createFromPdu((byte[]) pdus[i],format);
                    }else{
                        mensaje[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    }

                    numero = mensaje[i].getOriginatingAddress();
                    msg = mensaje[i].getMessageBody();
                    ActualizarEstadoUbicacion(numero,msg);
                }
                Toast.makeText(context, "mensaje :" + msg +"\nNumero: "+numero, Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void ActualizarEstadoUbicacion(String numero,String body){
        String[]posicion = ConvertirGPS(body);
        Solicitud solicitudExito = new Solicitud();
        solicitudExito.setNumero(numero);
        solicitudExito.setX(posicion[0]);
        solicitudExito.setY(posicion[1]);
        solicitudExito.setEstado("FINALIZADO");
        UpdateSolicitud updateSolicitud = new UpdateSolicitud(solicitudExito);
        updateSolicitud.execute();
    }

    private String[] ConvertirGPS(String body)
    {
        String[] posisicion = new String[2];


    return posisicion;
    }
}
