/**
 * @author SERKAN OZAL
 *         
 *         E-Mail: <a href="mailto:serkanozal86@hotmail.com">serkanozal86@hotmail.com</a>
 *         GitHub: <a>https://github.com/serkan-ozal</a>
 */

package tr.com.serkanozal.jillegal.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;

import org.apache.log4j.Logger;

import sun.management.VMManagement;
import tr.com.serkanozal.jillegal.Jillegal;

@SuppressWarnings("restriction")
public class JillegalUtil {
	
	private static final Logger logger = Logger.getLogger(JillegalUtil.class);
	
	private JillegalUtil() {
        
    }
    
    public static String toHexAddress(long address) {
        return "0x" + Long.toHexString(address).toUpperCase();
    }
    
    public static String toBinaryStringAddress(long address) {
        return "0x" + Long.toBinaryString(address).toUpperCase();
    }
    
	public static String getPidFromRuntimeMBean() throws Exception {
        RuntimeMXBean mxbean = ManagementFactory.getRuntimeMXBean();
        Field jvmField = mxbean.getClass().getDeclaredField("jvm");

        jvmField.setAccessible(true);
        VMManagement management = (VMManagement) jvmField.get(mxbean);
        Method method = management.getClass().getDeclaredMethod("getProcessId");
        method.setAccessible(true);
        Integer processId = (Integer) method.invoke(management);

        return processId.toString();
    }
    
    public static String getVersion() {
    	try {
    		String path = "/META-INF/maven/" + Jillegal.GROUP_ID + "/" + Jillegal.ARTIFACT_ID + "/pom.properties";
        	InputStream stream = JillegalUtil.class.getClassLoader().getResourceAsStream(path);
        	Properties props = new Properties();
			props.load(stream);
			return (String)props.get("version");
		} 
    	catch (IOException e) {
    		logger.error("Error at JillegalUtil.getVersion()", e);
			return null;
		}
    }
    
}
