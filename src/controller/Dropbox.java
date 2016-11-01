package controller;

import com.dropbox.core.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Data;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

/**
 * @author :   Sasa Markovic
 * @version :   1.0
 * @Created :   03.10.2016
 * @Project :   cloud
 * @Package :   controller
 * @LastUpdated :	Sandro - Grösse in Humansize(MB, GB) und Datum formatiert
 * @Description :   Connector/uploader/downloader/getter from Dropbox
 */

public class Dropbox {
    //Wichtigsten Variabeln
    private DbxAppInfo appInfo;
    private DbxRequestConfig config;
    private DbxWebAuthNoRedirect webAuth;
    private String authtoken;
    private String accesstoken;
    private DbxClient client;
    private ObservableList<Data> list = FXCollections.observableArrayList();
    private String currentPath;

    //Controller
    public Dropbox() throws IOException, URISyntaxException, DbxException {
        appInfo = new DbxAppInfo("4ib2r751sawik1x", "xwcn09oaicgpo0d");
        config = new DbxRequestConfig("Testsecurecloudapp", Locale.getDefault().toString());
        webAuth = new DbxWebAuthNoRedirect(config, appInfo);
        currentPath = "/";

        firstlogin();
        makearchives();
        getarchives();
    }

    public void updatecurrentPath(String foldername) {
        StringBuilder temp = new StringBuilder(currentPath);
        temp.append(foldername);
        temp.append("/");

        this.currentPath = temp.toString();
    }

    //Erstellt die variable "list"
    public void makearchives() throws DbxException {
        list.removeAll();
        DbxEntry.WithChildren files = client.getMetadataWithChildren(currentPath);
        for (DbxEntry file : files.children) {
            if (!file.isFolder()) {

                Data dummy = new Data();
                dummy.setdata_type(getextension(file.name));
                dummy.setdata_size(file.asFile().humanSize);
                dummy.setdata_name(file.name);
                dummy.setdatacreate(convertDateToString(file.asFile().clientMtime));
                dummy.setdata_last(convertDateToString(file.asFile().lastModified));
                list.add(dummy);
                dummy = null;
            } else {
                Data dummy = new Data();
                dummy.setdata_type("Folder");
                dummy.setdata_size("");
                dummy.setdata_name(file.name);
                dummy.setdatacreate("");
                dummy.setdata_last("");
                list.add(dummy);
                dummy = null;
            }
        }

    }

    //bei registrationen oder invaliden accesstokens
    public void firstlogin() throws IOException, URISyntaxException, DbxException {
        Scanner sc = new Scanner(System.in);
        Desktop.getDesktop().browse(new URI(webAuth.start()));

        System.out.println("Kopieren Sie den Text und f�gen Sie ihn ein!");
        authtoken = sc.nextLine();

        accesstoken = webAuth.finish(authtoken).accessToken;

        client = new DbxClient(config, accesstoken);
    }

    protected String convertLongtoString(Long args) {

        String temp = Long.toString(args);

        return temp;
    }

    protected String convertDateToString(Date indate) {
        String dateString = null;
        SimpleDateFormat sdfr = new SimpleDateFormat("dd.MM.yyyy");
        try {
            dateString = sdfr.format(indate);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return dateString;
    }

    public String getextension(String fileName) {
        String extension = "";

        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i + 1);
            return extension;
        }
        return null;
    }

    public String downloadFile(String path) {
        String endPath = "";
        try {
            FileOutputStream outputStream = new FileOutputStream(path);
            DbxEntry.File downloadedFile = client.getFile("/" + path, null,
                    outputStream);
            outputStream.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String currentDir = System.getProperty("user.dir");
        File newfile = new File(currentDir + "\\reset.css");
        endPath = newfile.getAbsolutePath();
        return endPath;
    }

    public void uploadFile(String path) throws IOException, DbxException {
        File inputFile = new File(path);
        FileInputStream inputStream = new FileInputStream(inputFile);
        try {
            DbxEntry.File uploadedFile = client.uploadFile("/" + inputFile.getName(),
                    DbxWriteMode.add(), inputFile.length(), inputStream);
            System.out.println("Uploaded: " + uploadedFile.toString());
        } finally {
            inputStream.close();
        }
    }


    /**
     * adds an uploaded file to the obeservable list
     * updates tableview
     *
     * @param file uploaded file that must be added
     */
    public void addFiletoList(File file) {
        Data dummy = new Data();
        dummy.setdata_type(getextension(file.getName()));
        dummy.setdata_size(humanReadableByteCount(file.length(), true));
        dummy.setdata_name(file.getName() + ".aes");
        dummy.setdatacreate(convertDateToString(new Date()));
        dummy.setdata_last("");
        list.add(dummy);
    }

    /**
     * converts bytes in human readable byte
     *
     * @param bytes size from file
     * @param si    method can return value in SI units or binary
     * @return human readable file size
     */
    private String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }


    public void login(String token) {
        client = new DbxClient(config, token);
    }

    public void logout() {
        client = null;
    }

    public void setappInfo(DbxAppInfo args) {
        appInfo = args;
    }

    public void setconfig(DbxRequestConfig args) {
        config = args;
    }

    public void setwebAuth(DbxWebAuthNoRedirect args) {
        webAuth = args;
    }

    public void setauthtoken(String args) {
        authtoken = args;
    }

    public void setclient(DbxClient args) {
        client = args;
    }

    public DbxAppInfo getappInfo() {
        return appInfo;
    }

    public DbxRequestConfig getconfig() {
        return config;
    }

    public DbxWebAuthNoRedirect getwebAuth() {
        return webAuth;
    }

    public String getauthtoken() {
        return authtoken;
    }

    public DbxClient getclient() {
        return client;
    }

    public ObservableList<Data> getarchives() {
        return list;
    }
}
