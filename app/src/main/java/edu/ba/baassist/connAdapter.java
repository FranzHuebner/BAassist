package edu.ba.baassist;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Connection Adapter for Campus Dual
 */

public class connAdapter {

    //function to get the wrap the content provided by campus-dual to a string
    public static String getUrlContents(String theUrl) {

        StringBuilder content = new StringBuilder();

        try{
            //params need to be inside try
            URL url = new URL(theUrl);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;

            while ((line = bufferedReader.readLine()) != null)
            {
                content.append(line).append("\n"); //loop to read input into string
            }
            bufferedReader.close();

        }catch(Exception e) {
            e.printStackTrace();
        }

        return content.toString(); //body of the website to string
    }

    //trusting every CA-Certificate
    public static void trustAllCertificates() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }

                        @Override
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception ignored) {
        }
    }

    // check if the website is online
   public static boolean isReachable(String urlinput) throws IOException {

       HttpsURLConnection conn = (HttpsURLConnection) new URL(urlinput).openConnection();
       conn.setRequestMethod("HEAD");
       conn.setRequestProperty( "Accept-Encoding","");
       int response = conn.getResponseCode();
       int retest = response;
        //HTTP -> OK (200) -> (304) ok for other site
       return ((response == HttpsURLConnection.HTTP_OK) || (response ==304) || (response ==100));
   }

    //Check if we can login with the provided data
    public static boolean logInconnection(String username, String hash){

       boolean trueFail =true;
       String output; //buffer to check against the failword
       String Url = "https://selfservice.campus-dual.de/dash/getfs?user="+username+"&hash="+hash+""; //check web
       String failWord = "Fehlermeldung";

       output = getUrlContents(Url); //get content as string

       if(output.contains(failWord)){ //check string for the failword
           trueFail = false; // wrong information
       }

       return trueFail;
    }

    //get current Semester
    public static String getsemester(String username, String hash) {

       String Url ="https://selfservice.campus-dual.de/dash/getfs?user="+username+"&hash="+hash;

        return getUrlContents(Url);
    }

    //get the credits of the User
    static public String getcredits(String username, String hash){

        String Url ="https://selfservice.campus-dual.de/dash/getcp?user="+username+"&hash="+hash;

        return getUrlContents(Url);
    }

    //get the finished exams of the user
    public static String getexams(String username, String hash){

        String Url ="https://selfservice.campus-dual.de/dash/getexamstats?user="+username+"&hash="+hash;

        return getUrlContents(Url);

    }

    //get the calc of the user from start to end
    public static String getcal(String username, String hash, String start, String end) {

       String Url = "https://selfservice.campus-dual.de/room/json?userid=" + username + "&hash=" + hash + "&start=" + start + "&end=" + end;

        return getUrlContents(Url);

    }
}
