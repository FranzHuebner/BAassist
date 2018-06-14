package edu.ba.baassist;


import android.content.Context;
import android.util.Log;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import static edu.ba.baassist.CacheAdapter.fileContext;

/**
 * Class which provides all the information other classes and modules need
 * implements the cache adapter and the connection adapter
 */



public class InfoHandler {


    //Datastorage where every information will be stored


    public static HashMap<String, java.io.Serializable> UserInfoMap = new HashMap<>(12);


        //init
        public InfoHandler(){

        UserInfoMap.put("userCal",null);
        UserInfoMap.put("userFS",null);
        UserInfoMap.put("userCredits",null);
        UserInfoMap.put("userExams",null);
        UserInfoMap.put("userId",null);
        UserInfoMap.put("userHash",null);
        UserInfoMap.put("filterglobal",null);
        UserInfoMap.put("lastUpdateTime", null);
        UserInfoMap.put("updatePeriod",null);
        UserInfoMap.put("cached",false);

        Log.d(InfoHandler.class.getSimpleName(),"initialized the hashmap");

    }



    public static void saveHashmaptoDisk() {

        try
        {

            FileOutputStream fos = fileContext.openFileOutput("Hashmap.ser", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(UserInfoMap);
            oos.close();

            Log.d(InfoHandler.class.getSimpleName(), "Successfully wrote hashmap");

        } catch (IOException e) {

            Log.w(InfoHandler.class.getSimpleName(), "failed to write hashmap " +e);
            e.printStackTrace();
        }


    }

    //Use to check if Variable is equal
    public static boolean compareVar(String key, java.io.Serializable value){

            //enforce key is not null
            if(key == null){

                Log.e(InfoHandler.class.getSimpleName(), "nullptr at key");
                throw new NullPointerException("Argument key is null");

            }


            if(UserInfoMap.containsKey(key)){

                if(UserInfoMap.get(key) != null){


                    if(UserInfoMap.get(key).equals(value)){

                       Log.d(InfoHandler.class.getSimpleName(), "var equals var in hashmap");
                       return true;

                     }else {

                        Log.d(InfoHandler.class.getSimpleName(), "var unequal var in hashmap");
                        return false;
                    }

                 }else {

                    Log.d(InfoHandler.class.getSimpleName(), "value in hashmap is null");
                     return false;
                }
            }else{

                Log.e(InfoHandler.class.getSimpleName(), "key not found");
                throw new NullPointerException("Argument key is null");

            }
    }

    //Used to replace variables in our hashmap
    //don´t use replace because it requires API Level 24
    public static void updateVar(String key, java.io.Serializable value){

            if(UserInfoMap.containsKey(key)){

                UserInfoMap.remove(key);
                UserInfoMap.put(key,value);
                Log.d(InfoHandler.class.getSimpleName(), "Successfully changed a variable!");

            }else {
                Log.w(InfoHandler.class.getSimpleName(), "can´t find key -> can´t change variable!");
            }

            //save to disk
            saveHashmaptoDisk();

    }


    public static void relaoadMapfromCache() {

        try {

            FileInputStream fileInputStream = new FileInputStream(fileContext.getFilesDir()+"/Hashmap.ser");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            UserInfoMap = (HashMap) objectInputStream.readObject();

        }

        catch(ClassNotFoundException | IOException | ClassCastException e) {

            Log.w(InfoHandler.class.getSimpleName(), "failed :"+e);
            e.printStackTrace();
        }
    }


    //compare maps for updates
    public static boolean compareMap(HashMap<String, java.io.Serializable> map1, HashMap map2) {

        if(map1.hashCode() == map2.hashCode() && (map1.equals(map2))){

            Log.d(InfoHandler.class.getSimpleName(),"Hashmaps equal");
            return true;

        }else {

            Log.d(InfoHandler.class.getSimpleName(),"Hashmaps not equal");
            return false;
        }

    }


    //update mechanism
    public static boolean updateMap(HashMap map1){

            if(compareMap(UserInfoMap,map1)){

                Log.d(InfoHandler.class.getSimpleName(),"Hashmaps equal -> no need for update");
                return false;

            }else {

                Log.d(InfoHandler.class.getSimpleName(),"Hashmap updated");
                return false;
            }

    }
}


