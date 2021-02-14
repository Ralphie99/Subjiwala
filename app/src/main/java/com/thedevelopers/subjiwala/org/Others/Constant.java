package com.thedevelopers.subjiwala.org.Others;

public class Constant {



    public static String getLogin_URL(String username,String password)
    {
        String uri = "http://subjiwala.org/android_login.php?username="+username+"&pass="+password;

        uri = uri.replaceAll(" ", "%20");

        return uri;

    }

    public static int Category_flag = 0;
    public static int Cart_flag = 0;
}
