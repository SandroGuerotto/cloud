package model;
import java.io.File;

/**
 * Holds a file in it
 * @author Burim Cakolli
 * @version 2.0
 * @project cloud
 * @package model
 * @created 02.10.2016
 * @lastUpdate 21.10.2016 / by Tim Meier
 */
public class CloudFile {

	private String name;
	private String type;
	private File file;
	
	public CloudFile(String name, String type, File file) {
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