package com.example.yashwanth.eeveadmin;

/**
 * Created by Belal on 10/24/2015.
 */
public class Config {

    //Address of our scripts of the CRUD
    public static final String URL_ADD="http://10.0.0.2/CRUD/emp/addEmp.php";
    public static final String URL_GET_ALL = "http://10.0.0.2/CRUD/emp/getAllEmp.php";
    public static final String URL_GET_EMP = "http://10.0.0.2/CRUD/emp/getEmp.php?id=";
    public static final String URL_UPDATE_EMP = "http://10.0.0.2/CRUD/emp/updateEmp.php";
    public static final String URL_DELETE_EMP = "http://10.0.0.2/CRUD/emp/deleteEmp.php?id=";
    public static final String URL_ADD_TRIP="http://www.instrosoft.com/eeve/EeVe_Master/emp/addTrip.php";
    public static final String URL_SAMPLE_POST="http://www.instrosoft.com/eeve/EeVe_Master/hotel/samplePost.php";

    public static final String URL_GET_ALL_ACTIVE_TRIPS="http://www.instrosoft.com/eeve/EeVe_Master/hotel/activeTrips.php?email=\"";
    public static final String URL_GET_ALL_ACTIVE_TRIPS2="http://www.instrosoft.com/eeve/EeVe_Master/hotel/activeTrips.php";
    public static final String URL_GET_TRIP_HISTORY="http://www.instrosoft.com/eeve/EeVe_Master/hotel/tripHistory.php?email=\"";
    public static final String URL_GET_ALL_CLIENTS="http://www.instrosoft.com/eeve/EeVe_Master/hotel/viewClients.php";
    public static final String URL_GET_ACTIVE_TRIP="http://www.instrosoft.com/eeve/EeVe_Master/hotel/getTrip.php?id=";
    public static final String URL_CLOSE_TRIP = "http://www.instrosoft.com/eeve/EeVe_Master/hotel/closeTrip.php";
    public static final String URL_HOTEL_PROFILE = "http://www.instrosoft.com/eeve/EeVe_Master/hotel/getHotelProfile.php?id=\"";



    //10.0.0.2 is the IPV4 address of my PC. To get your IPV4 open cmd, type ipconfig and press enter.
    //You will get the IP address of your PC. Copy that IPV4 and paste it in the above link

    //Keys that will be used to send the request to php scripts
    public static final String KEY_EMP_ID = "id";
    public static final String KEY_EMP_NAME = "name";
    public static final String KEY_EMP_DESG = "desg";
    public static final String KEY_EMP_SAL = "salary";

    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_ID = "id";
    public static final String TAG_TRIP_ID = "trip_id";
    public static final String TAG_USER_ID = "user_id";
    public static final String TAG_HOTEL_ID = "hotel_id";
    public static final String TAG_HOTEL_NAME = "hotel_name";
    public static final String TAG_HOTEL_ADDRESS = "address";
    public static final String TAG_MODEL = "model";
    public static final String TAG_STATUS = "status";
    public static final String TAG_START = "start";
    public static final String TAG_END = "end";
    public static final String TAG_NAME = "name";
    public static final String TAG_DESG = "desg";
    public static final String TAG_SAL = "salary";
    public static final String TAG_REG_NO = "reg_no";
    public static final String TAG_LOGIN_ID = "login_id";
    public static final String TAG_EMAIL = "email";

    //employee id to pass with intent
    public static final String EMP_ID = "emp_id";
}
