package com.example.sunimali.petrays.Database;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class BackgroundTaskPets extends AsyncTask<String,Void,String> {
    Context context;


    public BackgroundTaskPets(Context context){
        this.context = context;

    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        //get url
        String RegisterUrl = netConstants.URL_ADDNEW;

        //get method
        String method = params[0];

        //check function......................................................//

       //add new pet.... ........................................................
        if(method.equals("add")){
            //get parmas
            String age = params[1];
            String name = params[2];
            String weight = params[3];
            String species = params[4];
            String colour = params[5];
           // String image = params[6];
            String pet_owner_id = params[7];

            try {
                //make connection
                //get buffredwriter
                //parse variables
                URL url = new URL(RegisterUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));

                //parse data
                String data = URLEncoder.encode("age", "UTF-8") + "=" + URLEncoder.encode(age, "UTF-8") + "&" +
                        URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&" +
                        URLEncoder.encode("weight", "UTF-8") + "=" + URLEncoder.encode(weight, "UTF-8") + "&" +
                        URLEncoder.encode("species", "UTF-8") + "=" + URLEncoder.encode(species, "UTF-8") + "&" +
                        URLEncoder.encode("colour", "UTF-8") + "=" + URLEncoder.encode(colour, "UTF-8") + "&" +
                       // URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(image, "UTF-8") + "&" +
                        URLEncoder.encode("pet_owner_id", "UTF-8") + "=" + URLEncoder.encode(pet_owner_id, "UTF-8");


                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                inputStream.close();

                return "new pet added..!!";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //update pet details.......................................................
        if(method.equals("update")){
            String id = params[1];
            String name = params[2];
            String age = params[3];
            String weight = params[4];


            try {
                URL url = new URL(netConstants.URL_UPDATEPET);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));

                String data =
                        URLEncoder.encode("pet_owner_id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8") + "&" +
                                URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&" +
                        URLEncoder.encode("age", "UTF-8") + "=" + URLEncoder.encode(age, "UTF-8") + "&" +
                        URLEncoder.encode("weight", "UTF-8") + "=" + URLEncoder.encode(weight, "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                inputStream.close();

                return "update Succesful..!!";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //remove a pet....................................................
        if(method.equals("delete")){

            String name = params[1];
            String id = params[2];

            try {
                URL url = new URL(netConstants.URL_DELETE);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));

                String data =
                        URLEncoder.encode("pet_owner_id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8") + "&" +
                        URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                inputStream.close();

                return "pet removed successfully..!!";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        //send appoinment request......................................
        if(method.equals("appointment")){
            String time = params[1];
            String date = params[2];
            String address = params[3];
            String type = params[4];
            String desc = params[5];
            String pet_owner_id = params[6];

            try {
                //make coection
                URL url = new URL(netConstants.URL_APPOINTMENT);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));

                //parse data
                String data = URLEncoder.encode("time", "UTF-8") + "=" + URLEncoder.encode(time, "UTF-8") + "&" +
                        URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8") + "&" +
                        URLEncoder.encode("address", "UTF-8") + "=" + URLEncoder.encode(address, "UTF-8") + "&" +
                        URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8") + "&" +
                        URLEncoder.encode("desc", "UTF-8") + "=" + URLEncoder.encode(desc, "UTF-8") + "&" +
                        URLEncoder.encode("pet_owner_id", "UTF-8") + "=" + URLEncoder.encode(pet_owner_id, "UTF-8");


                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                inputStream.close();

                return "Appontment request sent..!!";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        //make tosat to see results
        Toast.makeText(context,result,Toast.LENGTH_SHORT).show();

    }
}
