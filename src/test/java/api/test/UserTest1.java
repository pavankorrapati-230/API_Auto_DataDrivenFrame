package api.test;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;

public class UserTest1 {
	Faker fake;
	User userpayload;
	
	@BeforeClass
	public void setUpData() {
		fake=new Faker();
		userpayload = new User();
		userpayload.setId(fake.idNumber().hashCode());
		userpayload.setFirstName(fake.name().firstName());
		userpayload.setLastName(fake.name().lastName());
		userpayload.setUsername(fake.name().username());
		userpayload.setEmail(fake.internet().safeEmailAddress());
		userpayload.setPhone(fake.phoneNumber().cellPhone());
		userpayload.setPassword(fake.internet().password());
		
	}
	@Test
	public void testUser() {
		Response response=UserEndPoints.createUser(userpayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
	}
}
