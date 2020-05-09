package com.example.parchservicesms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.telephony.SmsManager;
import com.example.parchservicesms.Model.Solicitud;
import com.example.parchservicesms.Servicio.GetSolicitud;
import com.example.parchservicesms.Servicio.UpdateSolicitud;


public class SMSSend {

    private final String solicitudUbicacion = "prueba";

    public Boolean Obtener() {
        Solicitud[] solicitudes = ObtenerSolicitudes();
        if(solicitudes!=null) {
            for (int i = 0; i < solicitudes.length; i++) {
                SendSMSMessage(solicitudes[i].getNumero(), solicitudUbicacion);
                ActualizarEstadoPendiente(solicitudes[i]);
            }
        }
        return true;
    }

    private Solicitud[] ObtenerSolicitudes(){
        try {
            GetSolicitud solicitud = new GetSolicitud("ABIERTA");
            solicitud.execute();
            Thread.sleep(1000);
            return solicitud.solicitudes;
        }catch (InterruptedException ex){
            return null;
        }
    }

    private void ActualizarEstadoPendiente(Solicitud solicitud){
        solicitud.setEstado("PENDIENTE");
        UpdateSolicitud updateSolicitud = new UpdateSolicitud(solicitud);
        updateSolicitud.execute();
    }

    protected void SendSMSMessage(String phoneNo, String message ) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNo, null, message, null, null);
    }
}
