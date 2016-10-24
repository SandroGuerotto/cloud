package model;
import java.io.File;
import java.util.ArrayList;

/**
 * Holds several CloudFiles in it
 * @author Burim Cakolli
 * @version 2.0
 * @project cloud
 * @package model
 * @created 02.10.2016
 * @lastUpdate 21.10.2016 / by Tim Meier
 */
public class CloudFolder {

	private String name;
	private ArrayList<CloudFile> files; // Change to HashMap ??
	
	public CloudFolder(String name, File file, ArrayList<CloudFile> files) {
		this.name = name;
		this.files = files;
	}//-Standard Constructor	
	
	/* Getter/Setter #Standards */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<CloudFile> getFiles() {
		return files;
	}
	public void setFiles(ArrayList<CloudFile> files) {
		this.files = files;
	}
	
}//-class