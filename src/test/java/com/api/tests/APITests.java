package com.api.tests;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.test.helper.BaseHelper;

public class APITests extends BaseHelper {
	
	@DataProvider(name = "FillWords")
	public static Object[][] fill_StringText() {
		return new Object[][] { { "This is xml text", "xml", "xml", "Op1" },
			{ "This is plain text", "plain", "plain", "Op2" },
			{ "This is json text", "json", "json", "Op3"}};
	}
	
	@Test(dataProvider = "FillWords", description = "")
	public void test_fill_word_different_types(String giventxt, String type, String replacefrom, String replaceto){
		given().
				spec(reqSpec).
				pathParam("type", type).
				pathParam("text", giventxt).
				pathParam("add", replacefrom).
				pathParam("fill_text", replaceto).
		when().
				get("/{type}?text={text}&add={add}&fill_text={fill_text}").
		then().
				statusCode(200).
				assertThat().body(containsString(replaceto));
		
	}
}
