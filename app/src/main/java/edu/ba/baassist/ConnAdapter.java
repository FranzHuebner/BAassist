package edu.ba.baassist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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
 * Connection adapter for Campus-Dual.
 * Provide basic functionality to communicate with the website.
 */


public class ConnAdapter {

    //Global Params to provide faster animations.
    private static Object userCal;
    public static Object userFs;
    public static Object userCredits;
    public static Object userExams;
    private static Object userGlobal;
    private static Object hashGlobal;

    private static Object filterGlobal;

    //Public setter methods.
        static void setUserCal(String cal){
            userCal = cal;
        }

        static void setUserFs(String fs){
            userFs = fs;
    }

    static void setUserExams(String exams){
        userExams = exams;
    }

    static void setUserCredits(String credits){
        userCredits = credits;
    }

    static void setGlobalId(String id){
        userGlobal = id;
    }

    static void setGlobalHash(String hash){
        hashGlobal = hash;
    }

    public static void setFilterGlobal(String filter){
        filterGlobal = filter;
    }


    //Public getter-methods.
    public static String getUserCal(){
        return ((String) userCal);
    }

    public static String getUserFs(){
        return ((String) userFs);
    }

    public static String getUserExams(){
        return ((String) userExams);
    }

    public static String getUserCredits(){
        return ((String) userCredits);
    }

    public static String getGlobalId(){
        return ((String) userGlobal);
    }

    public static String getGlobalHash(){
        return ((String) hashGlobal);
    }

    public static String getUserFilter(){
        return ((String) filterGlobal);
    }

    //Function to clean the output of Campus Dual, because in their database are stored wrong encodings
    private static String cleanContent(String Input){
        Input = Input.replace("\\u00c4","Ä");
        Input = Input.replace("\\u00e4","ä");
        Input = Input.replace("\\u00d6","Ö");
        Input = Input.replace("\\u00f6","ö");
        Input = Input.replace("\\u00dc","Ü");
        Input = Input.replace("\\u00fc","ü");
        Input = Input.replace("\\u00df","ß");

        return Input;
    }

    private static String getUrlContents(String theUrl) {
        trustAllCertificates();
        StringBuilder content = new StringBuilder();

        try{

            //params need to be inside try
            URL url = new URL(theUrl);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            BufferedReader bufferedReader;
            bufferedReader = new BufferedReader(new InputStreamReader( urlConnection.getInputStream(),"UTF8"));
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

        //UTF
        String cleanContentString = content.toString();
        cleanContentString = cleanContent(cleanContentString);

        //Return the body of the website
        return cleanContentString;

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
    public static boolean isReachable(String urlInput) throws IOException {
        trustAllCertificates();
        HttpsURLConnection conn = (HttpsURLConnection) new URL(urlInput).openConnection();
        conn.setRequestMethod("HEAD");
        conn.setRequestProperty( "Accept-Encoding","");
        int response = conn.getResponseCode();
        //HTTP -> OK (200) -> (304).
        return ((response == HttpsURLConnection.HTTP_OK) || (response ==304) || (response ==100));
    }

    //Check if we can login with the provided data.
    static boolean loginConnection(String username, String hash){

       boolean trueFail =true;
       String output; //Buffer for response.
       String url = "https://selfservice.campus-dual.de/dash/getfs?user="+username+"&hash="+hash+""; //Check web
       String failWord = "Fehlermeldung";

       output = getUrlContents(url); //Get content as string.

       if(output.contains(failWord)){ //Check string if it contains the word.
           trueFail = false; //Wrong information.
       }
       return trueFail; //Return value.
    }

    //Get current semester.
    static String getSemester(String username, String hash) {
        String url ="https://selfservice.campus-dual.de/dash/getfs?user="+username+"&hash="+hash;
        return getUrlContents(url);
    }

    //Get the credits of the user.
    public static String getCredits(String username, String hash){
        String url ="https://selfservice.campus-dual.de/dash/getcp?user="+username+"&hash="+hash;
        return getUrlContents(url);
    }

    //Get the finished exams of the user.
    static String getExams(String username, String hash){
        String url ="https://selfservice.campus-dual.de/dash/getexamstats?user="+username+"&hash="+hash;
        return getUrlContents(url);
    }

    //Get the cal of the user from start to end.
    static String getCal(String username, String hash, String start, String end) {
        String url = "https://selfservice.campus-dual.de/room/json?userid="+username+"&hash="+hash+"&start="+start+"&end="+end;
        return getUrlContents(url);
    }

    //Function to form the url to download the cal.
    static String startTime(){
        long milli = System.currentTimeMillis();
        milli=milli/1000;
        return String.valueOf(milli);
    }

    //Function to form the url to download the cal.
    static String endTime(long input){
        return String.valueOf(input);
    }

    //Convert unix time to date.
    public static Date convertUnixtoNormalDate(long timeStamp){
        return new Date(timeStamp *1000);
    }
    //Convert unix time to normal time.
    public static String convertUnixtoNormalTimeString(long timeStamp){
        java.util.Date date=new java.util.Date(timeStamp *1000);
        SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm");
        return localDateFormat.format(date);
    }

    //Function to give you the actual date.
    public static String convertUnixtoNormalDateString(long timeStamp){
        java.util.Date date=new java.util.Date(timeStamp *1000);
        SimpleDateFormat localDateFormat = new SimpleDateFormat("EEEE, dd.MM.yyyy");
        return localDateFormat.format(date);
    }
  
    //Function to set the time of a date to midnight, used to compare dates without time.
    public static Date setTimeToMidnight(Date date) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime( date );
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    //Refreshing global variables.
    public static void refreshValues(){

        //Setting variables to refresh the cal.
        String startT = String.valueOf(startTime());
        String endT = String.valueOf(endTime(Long.valueOf(startT)+3024000));

        //Get the new values.
        String output = getCal(getGlobalId(),getGlobalHash(),startT,endT);
        String output2= getCredits(getGlobalId(),getGlobalHash());
        String output3= getExams(getGlobalId(),getGlobalHash());
        String output4= getSemester(getGlobalId(),getGlobalHash());

        //Setting the new values.
        setUserFs(output4);
        setUserCredits(output2);
        setUserExams(output3);
        setUserCal(output);
    }

}
