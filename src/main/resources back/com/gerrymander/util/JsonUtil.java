package com.gerrymander.util;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class JsonUtil {
	private static ObjectMapper mapper;
	static {
		mapper = new ObjectMapper();
	}
	
	/** Convert Java Object into Json
	 * @param 
	 * @param object that you want to convert into Json
	 * @return
	 */
	public static String convertJavaToJson(Object object) {
		String jsonResult = "";
	  	try {
			jsonResult = mapper.writeValueAsString(object);
		} catch (JsonGenerationException e) {
			System.out.println("Exception Occured while converting Java Object into Json: "+e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			System.out.println("Exception Occured while converting Java Object into Json: "+e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Exception Occured while converting Java Object into Json: "+e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonResult;
	}
	
	public static <Type> Type convertJsonToJava(String jsonString,Class<Type> cls) {
		Type result = null;
		try {
			result = mapper.readValue(jsonString, cls);
		} catch (JsonParseException e) {
			System.out.println("Exception Occured while converting Json into Java Object: "+e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			System.out.println("Exception Occured while converting Json into Java Object: "+e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Exception Occured while converting Json into Java Object: "+e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
}
