package whitebox;

import static org.junit.Assert.*;

import org.datacontract.schemas._2004._07.PrettySecureCloud_Model.ServiceType;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import controller.Dropbox;

public class DropboxTest {
	private Dropbox dropbox;
	private ServiceType st;
	private static String APP_KEY = "zjo93sh8pv6vxvx";
	private static String APP_SECRET = "dg90qyud91le78u";
	
	@BeforeClass
	public void start(){
		System.out.println("Dropbox.java wird getestet!");
	}
	@Before
	public void setup(){
		
	}
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
