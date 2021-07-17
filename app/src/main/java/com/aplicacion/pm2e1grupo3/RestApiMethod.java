package com.aplicacion.pm2e1grupo3;

public class RestApiMethod {
    private static final String ipaddress = "192.168.1.21";
    public static final String ApiPostUrl = "http://"+ipaddress+"/Examen/crear.php";
    public static final String ApiGetUrl = "http://"+ipaddress+"/Examen/lista.php";
    public static final String ApiDeleteUrl = "http://"+ipaddress+"/Examen/eliminar.php";
    public static final String ApiPutUrl = "http://"+ipaddress+"/Examen/actualizar.php";

}
