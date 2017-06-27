package edu.ba.baassist;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Connection Adapter for Campus Dual.
 * Provide basic functionality to communicate with the website.
 */


public class connAdapter {

    //Global Params to provide faster animations.
    private static Object UserCal;
    private static Object UserFs;
    private static Object UserCredits;
    private static Object UserExams;
    public static Object UserGlobal;
    public static Object HashGlobal;

    //Public setter methods.
    static void setUserCal(String Cal){
        UserCal = Cal;
    }

    static void setUserFs(String FS){
       UserFs = FS;
    }

    static void setUserExams(String Exams){
        UserExams = Exams;
    }

    static void setUserCredits(String Credits){
        UserCredits = Credits;
    }

    static void setGlobalId(String Id){
        UserGlobal = Id;
    }

    static void setGlobalHash(String Hash){
        HashGlobal = Hash;
    }

    //Public getter-methods.
    public static String getUserCal(){
        return ((String) UserCal);
    }

    public static String getUserFs(){
        return ((String) UserFs);
    }

    public static String getUserExams(){
        return ((String) UserExams);
    }

    public static String getUserCredits(){
        return ((String) UserCredits);
    }

    static String getGlobalId(){
        return ((String) UserGlobal);
    }

    static String getGlobalHash(){
        return ((String) HashGlobal);
    }

    //Function to open the streamreader and wrap the response to a string.
    private static String getUrlContents(String theUrl) {
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

    //Trusting every CA-Certificate.
    private static void trustAllCertificates() {
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
        //HTTP -> OK (200) -> (304).
        return ((response == HttpsURLConnection.HTTP_OK) || (response ==304) || (response ==100));
    }

    //Check if we can login with the provided data.
    static boolean logInconnection(String username, String hash){

       boolean trueFail =true;
       String output; //Buffer for response.
       String Url = "https://selfservice.campus-dual.de/dash/getfs?user="+username+"&hash="+hash+""; //Check web
       String failWord = "Fehlermeldung";

       output = getUrlContents(Url); //Get content as string.

       if(output.contains(failWord)){ //Check string if it contains the word.
           trueFail = false; //Wrong information.
       }
       return trueFail; //Return value.
    }

    //Get current Semester.
    static String getsemester(String username, String hash) {
        String Url ="https://selfservice.campus-dual.de/dash/getfs?user="+username+"&hash="+hash;
        return getUrlContents(Url);
    }

    //Get the credits of the User.
    static String getcredits(String username, String hash){
        String Url ="https://selfservice.campus-dual.de/dash/getcp?user="+username+"&hash="+hash;
        return getUrlContents(Url);
    }

    //Get the finished exams of the user.
    static String getexams(String username, String hash){
        String Url ="https://selfservice.campus-dual.de/dash/getexamstats?user="+username+"&hash="+hash;
        return getUrlContents(Url);
    }

    //Get the cal of the user from start to end.
    static String getcal(String username, String hash, String start, String end) {
        String Url = "https://selfservice.campus-dual.de/room/json?userid="+username+"&hash="+hash+"&start="+start+"&end="+end;

        return getUrlContents(Url);

    }
    //Function to form the url to download the cal.
    static String StartTime(){
        long milli = System.currentTimeMillis();
        milli=milli/1000;
        return String.valueOf(milli);
    }

    //Function to form the url to download the cal.
    static String EndTime(long input){
        return String.valueOf(input);
    }

    //Convert normal time to unix time. -> Later usage.
    /* public static String convertNormaltoUnix(Date input){
        long zw=input.getTime();
        return String.valueOf(zw);
    }
   */

    //Convert unix time to date.
    public static Date convertUnixtoNormalDate(long timeStamp){
        java.util.Date date=new java.util.Date(timeStamp *1000);
        return date;
    }
    //Convert unix time to normal time.
    public static String convertUnixtoNormalTimeString(long timeStamp){
        java.util.Date date=new java.util.Date(timeStamp *1000);
        SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm");
        String time = localDateFormat.format(date);
        return time.toString();
    }


    //Function to give you the actual date.
    public static String convertUnixtoNormalDateString(long timeStamp){
        java.util.Date date=new java.util.Date(timeStamp *1000);
        SimpleDateFormat localDateFormat = new SimpleDateFormat("EEEE, dd.MM.yyyy");
        String dateString = localDateFormat.format(date);
        return dateString.toString();
    }
    //Function to set the time of a date to Midnight, used to compare Dates without Time.
    public static Date setTimeToMidnight(Date date) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime( date );
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }


}
