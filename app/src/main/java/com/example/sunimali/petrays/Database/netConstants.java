package com.example.sunimali.petrays.Database;

import android.graphics.Bitmap;

public class netConstants {
    //192.168.8.100
    // private static final String ROOT_URL = "http://10.22.163.105/PetRays/Api.php?apicall=";
  //  private static final String ROOT_URL = "http://192.168.8.100/PetRays/Api.php?apicall=";
  private static final String ROOT_URL = "http://192.168.8.100/PetRays/Api.php?apicall=";
   // private static final String ROOT_URL = "http://35.198.222.95/PetRays/Api.php?apicall=";

    public static final String URL_REGISTER = ROOT_URL+"signup";
    public static final String URL_LOGIN = ROOT_URL+"login";
    public static final String URL_UPDATEDP = ROOT_URL+"updatedp";
    public static final String URL_VIEW = ROOT_URL+"view";
    public static final String URL_UPDATE = ROOT_URL+"update";
    public static final String URL_VIEWDP = ROOT_URL+"viewdp";


    //net constant for pets

    private static final String ROOTP_URL = "http://192.168.8.100/PetRays/ApiPets.php?apicall=";

    public static final String URL_VIEWPETS = ROOTP_URL+"view";
    public static final String URL_ADDNEW = ROOTP_URL+"add";
    public static final String URL_FIND = ROOTP_URL+"find";
    public static final String URL_UPDATEPET = ROOTP_URL+"update";
    public static final String URL_DELETE = ROOTP_URL+"delete";
    public static final String URL_VIEWTREAT = ROOTP_URL+"treatments";
    public static final String URL_APPOINTMENT = ROOTP_URL+"appointment";

    public static String DP ;
    public static Bitmap DPbitmap;



}
