package com.example.sunimali.petrays;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/*public class viewBackgroundTask extends AsyncTask<String,Void,String> {

    Context context;
    viewBackgroundTask(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        //progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String method = params[0];
        String viewProfileUrl = netConstants.USER_LOGIN_SERVER_URL;
        if(method.equals("view")){
            try{
                String id = params[1];
                URL url = new URL(viewProfileUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String PostData = URLEncoder.encode("id","UTF-8") + "=" + URLEncoder.encode(id,"UTF-8");

                bufferedWriter.write(PostData);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result = "";
                String line;
                while((line=bufferedReader.readLine())!=null){
                    result+=line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;


            }catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {

       /* if(progressDialog.isShowing()) {
            progressDialog.dismiss();
        }*/

        //Toast.makeText(LoginActivity.this, result , Toast.LENGTH_SHORT).show();
    /*    if(result.contains("Success")){
            SetSharedPref("1", LoginEmail.getText().toString());
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }else{
            SetSharedPref("0", "");
            Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
//                alertDialog.setMessage(result);
//                alertDialog.show();
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}*/