package edu.ba.baassist;


import android.os.StrictMode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


import edu.ba.fragments.StatusFragment;

/**
 * Connection Adapter for Campus Dual.
 * Provide basic functionality to communicate with the website.
 */


public class connAdapter {

    //Global Params to provide faster animations.
    public static Object UserCalc;
    public static Object UserFs;
    public static Object UserCredits;
    public static Object UserExams;
    public static Object UserGlobal;
    public static Object HashGlobal;

    //Public setter methods.
    public static void setUserCalc(String Calc){
        UserCalc = Calc;
    }

    public static void setUserFs(String FS){
       UserFs = FS;
    }

    public static void setUserExams(String Exams){
        UserExams = Exams;
    }

    public static void setUserCredits(String Credits){
        UserCredits = Credits;
    }

    public static void setGlobalId(String Id){
        UserGlobal = Id;
    }

    public static void setGlobalHash(String Hash){
        HashGlobal = Hash;
    }

    //Public getter-methods.
    public static String getUserCalc(){
        String back = (String) UserCalc;
        return (back);
    }

    public static String getUserFs(){
        String back = (String) UserFs;
        return (back);
    }

    public static String getUserExams(){
        String back = (String) UserExams;
        return (back);
    }

    public static String getUserCredits(){
        String back = (String) UserCredits;
        return (back);
    }

    public static String getGlobalId(){
        String back = (String) UserGlobal;
        return (back);
    }

    public static String getGlobalHash(){
        String back = (String) HashGlobal;
        return (back);
    }

    //Function to open the streamreader and wrap the response to a string.
    public static String getUrlContents(String theUrl) {
        trustAllCertificates();
        StringBuilder content = new StringBuilder();

        try{
            //params need to be inside try
            URL url = new URL(theUrl);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;

            //Read buffer to string.
            while ((line = bufferedReader.readLine()) != null)
            {
                content.append(line).append("\n"); //loop to read input into string
            }
            bufferedReader.close();

        }catch(Exception e) {
            e.printStackTrace();
        }

        //Return the body of the website
        return content.toString();
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

    //Check if the website is online.
   public static boolean isReachable(String urlinput) throws IOException {
       trustAllCertificates();
       HttpsURLConnection conn = (HttpsURLConnection) new URL(urlinput).openConnection();
       conn.setRequestMethod("HEAD");
       conn.setRequestProperty( "Accept-Encoding","");
       int response = conn.getResponseCode();
       //HTTP -> OK (200) -> (304) ok for other site
       return ((response == HttpsURLConnection.HTTP_OK) || (response ==304) || (response ==100));
   }

    //Check if we can login with the provided data.
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

    //Get current Semester.
    public static String getsemester(String username, String hash) {
        String Url ="https://selfservice.campus-dual.de/dash/getfs?user="+username+"&hash="+hash;
        return getUrlContents(Url);
    }

    //Get the credits of the User.
    static public String getcredits(String username, String hash){
        String Url ="https://selfservice.campus-dual.de/dash/getcp?user="+username+"&hash="+hash;

        return getUrlContents(Url);
    }

    //Get the finished exams of the user.
    public static String getexams(String username, String hash){

        String Url ="https://selfservice.campus-dual.de/dash/getexamstats?user="+username+"&hash="+hash;

        return getUrlContents(Url);

    }

    //Get the calc of the user from start to end.
    public static String getcal(String username, String hash, String start, String end) {
        String Url = "https://selfservice.campus-dual.de/room/json?userid="+username+"&hash="+hash+"&start="+start+"&end="+end;

        return getUrlContents(Url);

    }
    //Function to form the url to download the cal.
    public static String StartTime(){
        long milli = System.currentTimeMillis();
        milli=milli/1000;
        return String.valueOf(milli);
    }
    //Function to form the url to download the cal.
    public static String EndTime(long input){
        return String.valueOf(input);
    }
    //Convert normal time to unix time.
    public static String convertNormaltoUnix(Date input){
        long zw=input.getTime();
        return String.valueOf(zw);
    }
    //Convert unix time to normal time.
    public static String convertUnixtoNormal(long timeStamp){
        java.util.Date time=new java.util.Date((long)timeStamp*1000);
        return time.toString();
    }


}
