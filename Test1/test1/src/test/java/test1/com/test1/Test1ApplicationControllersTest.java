package test1.com.test1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.sql.Timestamp;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import test1.com.test1.model.User;
import test1.com.test1.object.UserRequest;
import test1.com.test1.object.UserStatusRequest;
import test1.com.test1.object.UserStatusResponse;

@SpringBootTest
public class Test1ApplicationControllersTest {
	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	private static final String IMAGE_UPLOAD_URL = "/images/upload",
		USERS_URL = "/users/";

	private String mapToJson(Object obj) throws JsonProcessingException {
		var objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

	private <T> T mapFromJson(String json, Class<T> clazz)
      	throws JsonParseException, JsonMappingException, IOException {      
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.readValue(json, clazz);
    }

	@PostConstruct
    public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

	@Test
	public void imageUploadTest() throws Exception {
		var mockMultipartFile = new MockMultipartFile("picUpl",
			"image1.jpg",
			"image/",
			"image1".getBytes());		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.multipart(IMAGE_UPLOAD_URL).file(mockMultipartFile))
			.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(201, status);
	}

	@Test
	public void createUserTest() throws Exception {
		var userRequest = new UserRequest()
			.setName("test1")
			.setEmail("test1@mail.ru")
			.setStatus(false);
		String userJson = mapToJson(userRequest);		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(USERS_URL + "create")
			.contentType(MediaType.APPLICATION_JSON)
			.content(userJson))
			.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(201, status);		
	}

	@Test
	public void getUserByIdTest() throws Exception {
		final int userId = 0;	

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(USERS_URL + "{id}", userId)
			.accept(MediaType.APPLICATION_JSON))
			.andReturn();
		var response = mvcResult.getResponse();
		int status = response.getStatus();
		assertEquals(200, status);

		String userString = response.getContentAsString();
		User user = mapFromJson(userString, User.class);
		assertEquals(user.getId(), userId);
	}

	@Test
	public void changeStatusUserTest() throws Exception {
		final int userId = 0;
		final boolean newStatus = false;

		var userStatusRequest = new UserStatusRequest()
			.setId(userId)
			.setOnline(false);
		String userStatusRequestJson = mapToJson(userStatusRequest);		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(USERS_URL + "changeStatus")
			.contentType(MediaType.APPLICATION_JSON)
			.content(userStatusRequestJson))
			.andReturn();
		var response = mvcResult.getResponse();
		int status = response.getStatus();
		assertEquals(200, status);

		String userStatusResponseString = response.getContentAsString();
		UserStatusResponse userStatusResponsde = mapFromJson(userStatusResponseString, UserStatusResponse.class);
		assertEquals(userStatusResponsde.getId(), userId);
		assertEquals(userStatusResponsde.getNewStatus(), newStatus);
	}

	@Test
	public void getAllUserTest() throws Exception {
		//var t = Timestamp.valueOf("2022-08-10 00:00:00");
		//var w = new Timestamp(1660078800000L);
		final long timestampId = 1660078800000L; //2022-08-10 00:00:00
		final boolean isOnline = false;

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(USERS_URL + "?id={id}&online={online}",
				timestampId, isOnline)
			.accept(MediaType.APPLICATION_JSON))
			.andReturn();
		var response = mvcResult.getResponse();
		int status = response.getStatus();
		assertEquals(200, status);

		var userListResponseString = response.getContentAsString();
		User[] userList = mapFromJson(userListResponseString, User[].class);
		assertTrue(userList.length > 0);
	}
}
