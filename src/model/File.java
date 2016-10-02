package model;

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

public class File {

	private String name;
	private String type;
	private File file;
	
	public File(String name, String type, File file) {
		this.name = name;
		this.type = type;
		this.file = file;
	}//-Standard Constructor	
	
	/* Getter/Setter #Standards */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	
}//-class
