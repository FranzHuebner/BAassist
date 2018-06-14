package edu.ba.baassist;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Adapter-class to interact with the internal memory to save the userdata.
 */

public class CacheAdapter {

    //Define context == our application.
    public static Context fileContext;

    //Save calender to memory.
    boolean saveCaltoMem(String cal){

        String fileName = "userCal";

        FileOutputStream outputStream;

        try {
            outputStream = fileContext.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(cal.getBytes());
            outputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //save semester to memory.
    boolean saveFstoMem(String fs){

        String fileName = "userFs";

        FileOutputStream outputStream;

        try {
            outputStream = fileContext.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(fs.getBytes());
            outputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //save credits to memory.
    boolean saveCredittoMem(String credits){

        String fileName = "userCredits";

        FileOutputStream outputStream;

        try {
            outputStream = fileContext.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(credits.getBytes());
            outputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //Save exams to memory.
    boolean saveExamstoMem(String exams){

        String fileName = "userExams";

        FileOutputStream outputStream;

        try {
            outputStream = fileContext.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(exams.getBytes());
            outputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //Save user id to memory.
    boolean saveUserGlobaltoMem(String user){

        String fileName = "userGlobal";

        FileOutputStream outputStream;

        try {
            outputStream = fileContext.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(user.getBytes());
            outputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //Save the hash to internal memory.
    boolean saveHashGlobaltoMem(String hash){

        String fileName = "HashGlobal";

        FileOutputStream outputStream;

        try {
            outputStream = fileContext.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(hash.getBytes());
            outputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //Save the GroupFilter to internal memory.
    public boolean saveFiltertoMem(String hash){

        String fileName = "userFilter";

        FileOutputStream outputStream;

        try {
            outputStream = fileContext.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(hash.getBytes());
            outputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //Check if a file is existent.
    public boolean getFileExistence(String fileName){
        File file = fileContext.getFileStreamPath(fileName);
        return file.exists();
    }

    //Method to get the calender from memory.
    String getCalfromMem() throws FileNotFoundException {
       
        if (getFileExistence("userCal")){
           
           String fileName = "userCal";
           FileInputStream fis;
           fis = fileContext.openFileInput(fileName);
           InputStreamReader isr = new InputStreamReader(fis);
           BufferedReader bufferedReader = new BufferedReader(isr);
           StringBuilder sb = new StringBuilder();
           String line;
           String output;
           try {
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                    output =sb.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                    output=e.toString();
                }
                return output;
            
                }else {
                    return "Datei nicht vorhanden.";   
                }
    }

    //Method to get the semester from memory.
    String getFsfromMem() throws FileNotFoundException {

        if (getFileExistence("userFs")){

            String fileName = "userFs";
            FileInputStream fis;
            fis = fileContext.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            String output;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                output =sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
                output=e.toString();
            }
            return output;

        }else {
            return "Datei nicht vorhanden.";
        }
    }

    //Method to get the credits from memory.
    String getCreditsfromMem() throws FileNotFoundException {

        if (getFileExistence("userCredits")){

            String fileName = "userCredits";
            FileInputStream fis;
            fis = fileContext.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            String output;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                output =sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
                output=e.toString();
            }
            return output;

        }else {
            return "Datei nicht vorhanden.";
        }
    }

    //Method to get exam status from memory.
    String getExamsfromMem() throws FileNotFoundException {

        if (getFileExistence("userExams")){

            String fileName = "userExams";
            FileInputStream fis;
            fis = fileContext.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            String output;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                output =sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
                output=e.toString();
            }
            return output;

        }else {
            return "Datei nicht vorhanden.";
        }
    }

    //Method to get the user id from memory.
    String getUserGlobalfromMem() throws FileNotFoundException {

        if (getFileExistence("userGlobal")){

            String fileName = "userGlobal";
            FileInputStream fis;
            fis = fileContext.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            String output;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                output =sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
                output=e.toString();
            }
            return output;

        }else {
            return "Datei nicht vorhanden.";
        }
    }

    //Get the global hash of the User from memory.
    String getHashGlobalfromMem() throws FileNotFoundException {

        if (getFileExistence("HashGlobal")){

            String fileName = "HashGlobal";
            FileInputStream fis;
            fis = fileContext.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            String output;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                output =sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
                output=e.toString();
            }
            return output;

        }else {

            return "Datei nicht vorhanden.";
        }

    }

    //Method to get the Groupfilter from memory.
   public String getFilterfromMem() throws FileNotFoundException {

        if (getFileExistence("userFilter")){

            String fileName = "userFilter";
            FileInputStream fis;
            fis = fileContext.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            String output;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                output =sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
                output=e.toString();
            }
            return output;

        }else {
            return "Datei nicht vorhanden.";
        }
    }

    //Method to check if the data is up to date and replace it with newer one if given.
    public String checkDiff(String input, String cname) throws FileNotFoundException {
        if (getFileExistence(cname)){

            FileOutputStream outputStream;
            FileInputStream inputStream;

            inputStream= fileContext.openFileInput(cname);
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            String output;

            try {
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                output =sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
                output=e.toString();
            }

            if (output.equals(input)){

                return "Kein Unterschied.";

            }else {
                fileContext.deleteFile(cname);
                try {
                    outputStream = fileContext.openFileOutput(cname, Context.MODE_PRIVATE);
                    outputStream.write(input.getBytes());
                    outputStream.close();
                    //set connadapter

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "Datei überschrieben.";
            }

        }else{

            return "File nicht gefunden.";
        }
    }

    //Delete an entry in the cache.
    public boolean deleteEntry(String input){
        if (getFileExistence(input)){

            fileContext.deleteFile(input);
            return true;

        }else {
            return false;
        }
    }
}
