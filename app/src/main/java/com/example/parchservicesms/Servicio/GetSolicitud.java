package com.example.parchservicesms.Servicio;

import android.os.AsyncTask;

import com.example.parchservicesms.Model.Solicitud;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

public class GetSolicitud  extends AsyncTask<String,Integer,Boolean> {
    private String url = "http://192.168.0.206:3000/api/getsolicitud/";
    public Solicitud[] solicitudes;
    public String estado;
    public GetSolicitud(String estado) {
        this.estado = estado;
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        boolean resul = true;
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        post.setHeader("content-type", "application/json");
        try
        {
            JSONObject dato = new JSONObject();
            dato.put("fecha", ObtenerFecha());
            dato.put("estado", estado);
            StringEntity entity = new StringEntity(dato.toString());
            post.setEntity(entity);
            HttpResponse resp = httpClient.execute(post);
            String respStr = EntityUtils.toString(resp.getEntity());
            Gson gson = new Gson();
            Solicitud[] solicitudes = gson.fromJson(respStr, Solicitud[].class);
            this.solicitudes = solicitudes;
            resul = true;
        }
        catch(Exception ex)
        {
            resul = false;
        }
        return resul;
    }

    private String ObtenerFecha(){
        return  new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }
    protected void onPostExecute(Boolean result) {
    }
}
