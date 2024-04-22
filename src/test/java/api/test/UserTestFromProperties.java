package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import api.endpoints.UserEndPointsFromProperties;
import api.payload.*;
import io.restassured.response.Response;

public class UserTestFromProperties {
	Faker faker;
	User userPayload;
	public Logger logger;

	@BeforeClass
	public void setupData() {
		faker = new Faker();
		userPayload = new User();
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
		logger=LogManager.getLogger(this.getClass());
	}

	@Test(priority = 1)
	public void testUser() {
		logger.info("**********Creating User************");
		Response response = UserEndPointsFromProperties.createUser(userPayload);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 200);
        logger.info("**********User Created************");
	}

	@Test(priority = 2)
	public void testGetUserByName() {
		logger.info("**********Reading User Info************");
		Response response = UserEndPointsFromProperties.readUser(this.userPayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("**********User Info is displayed************");
	}

	@Test(priority = 3)
	public void testUpdateUserByName() {
		logger.info("**********Updating User************");
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());

		Response response = UserEndPointsFromProperties.updateUser(this.userPayload.getUsername(), userPayload);
		response.then().log().body();

		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("**********User is Updated************");
		Response responseAfterUpdate = UserEndPointsFromProperties.readUser(this.userPayload.getUsername());
		responseAfterUpdate.then().log().all();

		Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);
	}
	@Test(priority=4)
	public void testDeleteUserByName() {
		logger.info("**********Deleting User************");
		Response responsedeleted=UserEndPointsFromProperties.deleteUser(this.userPayload.getUsername());
		responsedeleted.then().log().all();
		
		Assert.assertEquals(responsedeleted.getStatusCode(), 200);
		logger.info("**********User is deleted************");
	}
}
