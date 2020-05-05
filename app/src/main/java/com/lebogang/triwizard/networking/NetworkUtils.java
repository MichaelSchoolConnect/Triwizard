package com.lebogang.triwizard.networking;

import android.content.Context;
import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private static Context context;

    public NetworkUtils(Context context){
        NetworkUtils.context = context;
    }

    /**
     * Checks internet connectivity using InetAddress as opposed to ConnectivityManager.
     *
     * @return The Boolean to confirm if we're connected to the internet or not.
     */
    public static boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return ipAddr.equals("");

        } catch (Exception e) {
            return true;
        }
    }

    /**
     * Builds the URL used to query GitHub.
     *
     * @return The URL to use to query the Harry Potter API.
     */
    public static URL buildUrl(String endpoint) {
        //We are building a url this way to cinvert Android based url to Java.
        //Also the methods buildUpon and build() reduce
        Uri buildUri = Uri.parse(endpoint).buildUpon().build();
        URL url = null;
        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        //Create HTTPURLConnection object.
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            //Open
            InputStream in = urlConnection.getInputStream();

            //This buffers the data, handles character encoding and allocates and de-allocates the
            //buffers as needed.
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            //If there's still something in the stream...
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                //keep on reading...
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    //Acts as a "generic" - doesn't take any other primitive data type - method.
    //Used to pass parameters that belong to right API call for Id types(Character & House)
    public static String genericEndpoint(String route, String id){
        return "https://www.potterapi.com/v1/"
                + route + "/"
                + id + "?key=$2a$10$BD8PoevCo5yISOJlHy47geFYut1/qYjjtKHLQeHW2ZUD.3RndOJXa";
    }
}
