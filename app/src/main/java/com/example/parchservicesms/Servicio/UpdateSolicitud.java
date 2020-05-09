package com.example.parchservicesms.Servicio;

import android.os.AsyncTask;
import com.example.parchservicesms.Model.Solicitud;
import org.json.JSONObject;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;


public class UpdateSolicitud  extends AsyncTask<String,Integer,Boolean> {

    private String url = "http://192.168.0.206:3000/api/ubicacion/";
    private Solicitud solicitud;

    public UpdateSolicitud(Solicitud solicitud){
        this.solicitud = solicitud;
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        boolean resul;
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        post.setHeader("content-type", "application/json");
        try
        {
            JSONObject dato = new JSONObject();
            dato.put("id", solicitud.get_id());
            dato.put("x", solicitud.getX());
            dato.put("y", solicitud.getY());
            dato.put("estado", solicitud.getEstado());
            StringEntity entity = new StringEntity(dato.toString());
            post.setEntity(entity);
            httpClient.execute(post);
            resul = true;
        }
        catch(Exception ex)
        {
            resul = false;
        }
        return resul;
    }

    protected void onPostExecute(Boolean result) {
    }
}
