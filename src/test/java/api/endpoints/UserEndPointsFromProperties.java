package api.endpoints;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.ResourceBundle;

import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

//UserEndPoints.java ---> Created for perform CRUD operations (Create, Read, Update, Delete) on the user api.

public class UserEndPointsFromProperties {
	
	
	//This method is to read the urls's from properties file
	static ResourceBundle getUrl(){
		ResourceBundle routes = ResourceBundle.getBundle("routes"); //Load the properties file //name of the properties file.
		return routes;
	}
	
	
	public static Response createUser(User payload){ //User is imported from payload package
		
		String post_url= getUrl().getString("post_url");
		
		Response response=given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(payload)
		.when()
			.post(post_url);
		
		return response;
	}
	
	public static Response readUser(String userName){
		
		String get_url= getUrl().getString("get_url");
		Response response= given()
			.pathParam("username", userName)
		.when()
			.get(get_url);
			
		return response;	
	}
	
	
	public static Response updateUser(String userName, User payload){
		
		String put_url= getUrl().getString("put_url");
		
		Response response=given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.pathParam("username", userName)
			.body(payload)
		.when()
			.put(put_url);
		return response;
	}
	
	public static Response deleteUser(String userName){
		
		String delete_url= getUrl().getString("delete_url");
		
		Response response= given()
			.pathParam("username", userName)
		.when()
			.delete(delete_url);
		return response;	
	}	
}
