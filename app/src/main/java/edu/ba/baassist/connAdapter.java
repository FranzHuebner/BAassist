package edu.ba.baassist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.lang.*;
/**
 * Connection Adapter for Campus Dual
 */

public class connAdapter {

    // check if the website is online
  static public boolean isReachable(String urlinput) throws IOException {

        String myUrl = urlinput;
        URL url = new URL(myUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        int response = conn.getResponseCode();

        //HTTP -> OK (200)
        if(response== 200) {
            return true;  // information correct
        } else {
            return false; // information wrong
        }
    }

    //Check if we can login with the provided data
   static public boolean loginconnection(String username, String hash){

        URL url;
        HttpURLConnection urlConnection ;
        String myUsername =username;
        String myHash     =hash;


        try {
            url = new URL("https://selfservice.campus-dual.de/dash/getfs?user="+myUsername+"&hash="+myHash);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(5000);
            urlConnection.connect();

            //Get the body-response
            String bodyOutput;
            BufferedReader br = new BufferedReader(new InputStreamReader((urlConnection.getInputStream())));
            StringBuilder  sb = new StringBuilder();
            String bodyFull;
            boolean  trueFail = false;
            String   failWord = "Unbehandelter Fehler:";

            //read the response body to check if we get any errors
            while ((bodyOutput = br.readLine()) != null) {
                sb.append(bodyOutput);
                sb.toString();
            }

            bodyFull = sb.toString();
            trueFail = bodyFull.contains(failWord);

            if(trueFail){
                return true; // Data is correct
            }else {
                return false; // Userinput is wrong
            }

                //exception needed for URL formatting
                } catch (Exception e) {
                     return false; // Login failed
                    }

    }
    //get current Semester
   static public String getsemester(String username, String hash) {

        String myUsername = username;
        String myHash = hash;
        String ErrorWord = "Es ist ein fehler bei der Abfrage aufgetreten!";
        URL url;
        HttpURLConnection urlConnection;

        try {
            url = new URL("https://selfservice.campus-dual.de/dash/getfs?user=" + myUsername + "&hash=" + myHash);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(5000);
            urlConnection.connect();

            //Get the body-response
            String bodyOutput;
            BufferedReader br = new BufferedReader(new InputStreamReader((urlConnection.getInputStream())));
            StringBuilder sb = new StringBuilder();
            String bodyFull;

            //merge it to one string
            while ((bodyOutput = br.readLine()) != null) {
                sb.append(bodyOutput);
                sb.toString();
            }

            bodyFull = sb.toString();

            return bodyFull;
            //exception needed for URL formatting
        }   catch (Exception e) {
                return ErrorWord;
            }
    }

    //get the credits of the User
    static public String getcredits(String username, String hash){

        String myUsername =username;
        String myHash     =hash;
        String ErrorWord  ="Es ist ein fehler bei der Abfrage aufgetreten!";
        URL url;
        HttpURLConnection urlConnection ;

        try {
            url = new URL("https://selfservice.campus-dual.de/dash/getcp?user="+myUsername+"&hash="+myHash);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(5000);
            urlConnection.connect();

            //Get the body-response
            String bodyOutput;
            BufferedReader br = new BufferedReader(new InputStreamReader((urlConnection.getInputStream())));
            StringBuilder  sb = new StringBuilder();
            String bodyFull;

            //merge it to one string
            while ((bodyOutput = br.readLine()) != null) {
                sb.append(bodyOutput);
                sb.toString();
            }

            bodyFull = sb.toString();

            return bodyFull;
            //exception needed for URL formatting
        } catch (Exception e) {
            return ErrorWord;
        }
    }

    //get the finished exams of the user
    static public String getexams(String username, String hash){

        String myUsername =username;
        String myHash     =hash;
        String ErrorWord  ="Es ist ein fehler bei der Abfrage aufgetreten!";
        URL url;
        HttpURLConnection urlConnection ;

        try {
            url = new URL("https://selfservice.campus-dual.de/dash/getexamstats?user="+myUsername+"&hash="+myHash);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(5000);
            urlConnection.connect();

            //Get the body-response
            String bodyOutput;
            BufferedReader br = new BufferedReader(new InputStreamReader((urlConnection.getInputStream())));
            StringBuilder  sb = new StringBuilder();
            String bodyFull;

            //merge it to one string
            while ((bodyOutput = br.readLine()) != null) {
                sb.append(bodyOutput);
                sb.toString();
            }

            bodyFull = sb.toString();

            return bodyFull;
            //exception needed for URL formatting
        } catch (Exception e) {
            return ErrorWord;
        }
    }

   static public String getcal(String username, String hash, String start, String end){

        String myUsername =username;
        String myHash     =hash;
        String myDateStart=start;
        String myDateEnd  =end;

        String ErrorWord  ="Es ist ein fehler bei der Abfrage aufgetreten!";
        URL url;
        HttpURLConnection urlConnection ;

        try {
            url = new URL("https://selfservice.campus-dual.de/room/json?userid="+myUsername+"&hash="+myHash+"&start="+myDateStart+"&end="+myDateEnd);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(5000);
            urlConnection.connect();

            //Get the body-response
            String bodyOutput;
            BufferedReader br = new BufferedReader(new InputStreamReader((urlConnection.getInputStream())));
            StringBuilder  sb = new StringBuilder();
            String bodyFull;

            //merge it to one string
            while ((bodyOutput = br.readLine()) != null) {
                sb.append(bodyOutput);
                sb.toString();
            }

            bodyFull = sb.toString();

            return bodyFull;
            //exception needed for URL formatting
        } catch (Exception e) {
            return ErrorWord;
        }
    }
}
