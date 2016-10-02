/**
 * @author :   Burim Cakolli
 * Turns coffee & pizza into Software
 * Created          :   02.10.2016
 * Project          :   cloud
 * Package          :   model
 * @version 		:   1.0
 * LastUpdated      :   02.10.2016 / by Burim Cakolli
 * Description      :
 * 
 */
package model;

import java.util.ArrayList;

public class Folder {

	private String name;
	private File folder;
	private ArrayList<File> files;
	
	public Folder(String name, File folder, ArrayList<File> files) {
		this.name = name;
		this.folder = folder;
		this.files = files;
	}//-Standard Constructor	
	
	/* Getter/Setter #Standards */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public File getFolder() {
		return folder;
	}
	public void setFolder(File folder) {
		this.folder = folder;
	}
	public ArrayList<File> getFiles() {
		return files;
	}
	public void setFiles(ArrayList<File> files) {
		this.files = files;
	}
	
	
	
}//-class
