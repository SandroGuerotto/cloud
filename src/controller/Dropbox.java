package controller;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuthNoRedirect;
import com.dropbox.core.DbxWriteMode;

import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener;
import javafx.collections.FXCollections;

import model.Data;
import org.datacontract.schemas._2004._07.PrettySecureCloud_Model.CloudService;
import org.datacontract.schemas._2004._07.PrettySecureCloud_Model.ServiceType;
import org.datacontract.schemas._2004._07.PrettySecureCloud_Model.User;

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

    /**
     * @param applicationInfo
     * @throws IOException
     * @throws URISyntaxException
     * @throws DbxException
     */
    public Dropbox(ServiceType applicationInfo) throws IOException, URISyntaxException, DbxException {

        appInfo = new DbxAppInfo(applicationInfo.getKey(), applicationInfo.getSecret());
        config = new DbxRequestConfig("Pretty Secure Cloud", Locale.getDefault().toString());
        webAuth = new DbxWebAuthNoRedirect(config, appInfo);
        currentPath = "/";

        firstlogin();
        makearchives();
        getarchives();
    }

    /**
     * @param foldername
     */
    public void updatecurrentPath(String foldername) {
        StringBuilder temp = new StringBuilder(currentPath);
        temp.append(foldername);
        temp.append("/");

        this.currentPath = temp.toString();
    }

    /**
     * @throws DbxException
     */
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

    /**
     * @throws IOException
     * @throws URISyntaxException
     * @throws DbxException
     */
    public void firstlogin() throws IOException, URISyntaxException, DbxException {
        Scanner sc = new Scanner(System.in);
        Desktop.getDesktop().browse(new URI(webAuth.start()));

        System.out.println("Kopieren Sie den Text und f�gen Sie ihn ein!");
        authtoken = sc.nextLine();

        accesstoken = webAuth.finish(authtoken).accessToken;

        client = new DbxClient(config, accesstoken);
    }

    /**
     * @param args
     * @return
     */
    protected String convertLongtoString(Long args) {

        String temp = Long.toString(args);

        return temp;
    }

    /**
     * @param indate
     * @return
     */
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

    /**
     * @param fileName
     * @return
     */
    public String getextension(String fileName) {
        String extension = "";

        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i + 1);
            return extension;
        }
        return null;
    }

    /**
     * @param path
     * @return
     */
    public String  downloadFile(String path) throws IOException, DbxException {
        String endPath = "";
        FileOutputStream outputStream = new FileOutputStream(path);
        DbxEntry.File downloadedFile = client.getFile("/" + path, null,
                outputStream);
        outputStream.close();

        String currentDir = System.getProperty("user.dir");
        File newfile = new File(currentDir + "\\" + path);
        endPath = newfile.getAbsolutePath();
        return endPath;
    }

    /**
     * @param path
     * @throws IOException
     * @throws DbxException
     */
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

    /**
     * @param token
     */
    public void login(String token) {
        client = new DbxClient(config, token);
    }

    /**
     *
     */
    public void logout() {
        client = null;
    }

    /**
     * @param args
     */
    public void setappInfo(DbxAppInfo args) {
        appInfo = args;
    }

    /**
     * @param args
     */
    public void setconfig(DbxRequestConfig args) {
        config = args;
    }

    /**
     * @param args
     */
    public void setwebAuth(DbxWebAuthNoRedirect args) {
        webAuth = args;
    }

    /**
     * @param args
     */
    public void setauthtoken(String args) {
        authtoken = args;
    }

    /**
     * @param args
     */
    public void setclient(DbxClient args) {
        client = args;
    }

    /**
     * @return
     */
    public DbxAppInfo getappInfo() {
        return appInfo;
    }

    /**
     * @return
     */
    public DbxRequestConfig getconfig() {
        return config;
    }

    /**
     * @return
     */
    public DbxWebAuthNoRedirect getwebAuth() {
        return webAuth;
    }

    /**
     * @return
     */
    public String getauthtoken() {
        return authtoken;
    }

    /**
     * @return
     */
    public DbxClient getclient() {
        return client;
    }

    /**
     * @return
     */
    public ObservableList<Data> getarchives() {
        return list;
    }
}
