package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests {

	Faker faker;
	User userPayload;
	
	public Logger logger; //for logs
	
	@BeforeClass
	public void setupData() {
		
		faker = new Faker();
		userPayload = new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPhone(faker.phoneNumber().cellPhone());	
		
		//logs
		logger=LogManager.getLogger(this.getClass());
	}
	
	@Test(priority=1)
	public void testpostUser() {
		
		logger.info("************ Creating User **********");
		Response response=UserEndPoints.createUser(userPayload);
		
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("************ User is Created**********");
	}
	
	@Test(priority=2)
	public void testGetUserByName() {
		
		logger.info("************ Getting User Info **********");
		
		Response response = UserEndPoints.readUser(this.userPayload.getUsername());
		
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("************ User Info is received **********");
	}
	
	@Test(priority=3)
	public void testUpdateUserByName() {
		
		//Update data using payload
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		
		logger.info("************ Updating User By Name **********");
		
		Response response=UserEndPoints.updateUser(this.userPayload.getUsername(), userPayload);
		
		response.then().log().all();
		response.then().log().body().statusCode(200); //another way of doing the validation
		Assert.assertEquals(response.getStatusCode(), 200);
		
		//Checking the data after update
		
		Response responseAfterUpdate = UserEndPoints.readUser(this.userPayload.getUsername());
		Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);
		
		logger.info("************ Updating User By Name Successful **********");
	}
	
	@Test(priority=4)
	public void testDeleteUserByName() {
		
		logger.info("************ Deleting User By Name **********");
		
		Response response = UserEndPoints.deleteUser(this.userPayload.getUsername());
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("************ Deleted User By Name Success **********");
	}
}

















