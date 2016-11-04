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

import exception.DeleteException;
import exception.ExceptionType;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import model.Data;
import org.datacontract.schemas._2004._07.PrettySecureCloud_Model.ServiceType;

/**
 * Dropbox core API
 *
 * @author :   Sasa Markovic
 * @version :   1.5
 * @Created :   01.11.2016
 * @Project :   cloud
 * @Package :   controller
 * @LastUpdated :	04.11.2016 / by Sandro Guerotto
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
    private static final String downloadPath = System.getProperty("user.home") + "\\Downloads\\";
    private static final String dbxPath = "/psc";
    private static final String encryptExtension = ".aes";

    /**
     * Constructor: Initializes the dropbox object.
     *
     * @param applicationInfo Database Application Information
     * @throws IOException
     * @throws URISyntaxException
     * @throws DbxException
     */
    public Dropbox(ServiceType applicationInfo) throws IOException, URISyntaxException, DbxException {

        appInfo = new DbxAppInfo(applicationInfo.getKey(), applicationInfo.getSecret());
        config = new DbxRequestConfig("Pretty Secure Cloud", Locale.getDefault().toString());
        webAuth = new DbxWebAuthNoRedirect(config, appInfo);
    }

    /**
     * Creates a observable with metadata of all files in the current directory.
     *
     * @throws DbxException
     */
    public void makearchives() throws DbxException {
        list.removeAll();
        DbxEntry.WithChildren files = client.getMetadataWithChildren(dbxPath);
        for (DbxEntry file : files.children) {
            if (!file.isFolder()) {
                Data dummy = new Data();
                dummy.setdata_type(getExtension(file.name.replace( encryptExtension, "")));
                dummy.setdata_size(file.asFile().humanSize);
                dummy.setdata_name(file.name.replace(".aes", ""));
                dummy.setdatacreate(convertDateToString(file.asFile().clientMtime));
                dummy.setdata_last(convertDateToString(file.asFile().lastModified));
                list.add(dummy);
            } else {
                Data dummy = new Data();
                dummy.setdata_type("Folder");
                dummy.setdata_size("");
                dummy.setdata_name(file.name);
                dummy.setdatacreate("");
                dummy.setdata_last("");
                list.add(dummy);
            }
        }

    }

    /**
     * at firstlogin a new folder gets created if its not existing
     */
    public void createdefaultFolderinDbx() {
        try {
            client.createFolder(dbxPath);
        } catch (DbxException e) {
            e.printStackTrace();
        }
    }

    public void firstlogin() throws IOException, URISyntaxException, DbxException {
        Scanner sc = new Scanner(System.in);
        Desktop.getDesktop().browse(new URI(webAuth.start()));

        System.out.println("Kopieren Sie den Text und fügen Sie ihn ein!");
        authtoken = sc.nextLine();
        sc.close();
        accesstoken = webAuth.finish(authtoken).accessToken;

        client = new DbxClient(config, accesstoken);
        createdefaultFolderinDbx();
    }


    /**
     * This method converts "Date" variable in to "String" variable.
     *
     * @param indate Date variable
     * @return String    Date converted into String
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
     * Returns the file extension.
     *
     * @param fileName Full filename, without slash/backslash
     * @return String    Extension of the file, example: '.jar, .zip, .docx'
     */
    public String getExtension(String fileName) {
        int i = fileName.lastIndexOf('.');
        if (fileName.lastIndexOf('.') > 0) {
            return fileName.substring(i + 1);
        }
        return null;
    }

    /**
     * Downloads a specific file from dropbox and returns the absolutdirectory of it.
     *
     * @param dropboxfile
     * @return apsolutePath of the downloaded file
     */
    public String downloadFile(String dropboxfile) throws IOException, DbxException {
        String fullpath = downloadPath + dropboxfile + encryptExtension;
        FileOutputStream outputStream = new FileOutputStream(fullpath);
        client.getFile(dbxPath + "/" + dropboxfile + encryptExtension, null, outputStream);
        outputStream.close();
        return fullpath;
    }

    /**
     * Uploads a file in dropbox.
     *
     * @param path
     * @throws IOException
     * @throws DbxException
     */
    public void uploadFile(String path) throws IOException, DbxException {
        File inputFile = new File(path);
        FileInputStream inputStream = new FileInputStream(inputFile);
        try {
            client.uploadFile(dbxPath + "/" + inputFile.getName(), DbxWriteMode.add(), inputFile.length(), inputStream);
        } finally {
            inputStream.close();
        }
    }

    public void deleteFile(Data data) throws DeleteException {
        try {
            client.delete(dbxPath + "/" + data.getdata_name() + encryptExtension);
            list.remove(data);
        } catch (DbxException e) {
            throw new DeleteException();
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
        dummy.setdata_type(getExtension(file.getName()));
        dummy.setdata_size(humanReadableByteCount(file.length(), true));
        dummy.setdata_name(file.getName().replace(encryptExtension, ""));
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
     * If there's a service connected with the user, this method logs the user in.
     *
     * @param token
     */
    public void login(String token) {
        client = new DbxClient(config, token);
    }

    /**
     * Logs the client out.
     */
    public void logout() {
        client = null;
    }

    /**
     * Setter
     *
     * @param args
     */
    public void setappInfo(DbxAppInfo args) {
        appInfo = args;
    }

    /**
     * Setter
     *
     * @param args
     */
    public void setconfig(DbxRequestConfig args) {
        config = args;
    }

    /**
     * Setter
     *
     * @param args
     */
    public void setwebAuth(DbxWebAuthNoRedirect args) {
        webAuth = args;
    }

    /**
     * Setter
     *
     * @param args
     */
    public void setauthtoken(String args) {
        authtoken = args;
    }

    /**
     * Setter
     *
     * @param args
     */
    public void setclient(DbxClient args) {
        client = args;
    }

    /**
     * Getter
     *
     * @return
     */
    public DbxAppInfo getappInfo() {
        return appInfo;
    }

    /**
     * Getter
     *
     * @return
     */
    public DbxRequestConfig getconfig() {
        return config;
    }

    /**
     * Getter
     *
     * @return
     */
    public DbxWebAuthNoRedirect getwebAuth() {
        return webAuth;
    }

    /**
     * Getter
     *
     * @return
     */
    public String getauthtoken() {
        return authtoken;
    }

    /**
     * Getter
     *
     * @return
     */
    public DbxClient getclient() {
        return client;
    }

    /**
     * Getter
     *
     * @return
     */
    public ObservableList<Data> getarchives() {
        return list;
    }

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }


}
