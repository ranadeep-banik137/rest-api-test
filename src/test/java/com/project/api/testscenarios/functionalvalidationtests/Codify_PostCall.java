package com.project.api.testscenarios.functionalvalidationtests;

import java.lang.reflect.Method;
import java.util.logging.Logger;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.project.api.constants.ApiCallConfig;
import com.project.api.constants.Constants;
import com.project.api.constants.EnvironmentVariables;
import com.project.api.constants.FlatFile;
import com.project.api.utilities.CommonUtils;
import com.project.app.api_test_v1.restFiles.PostApiCall;
import com.project.app.api_test_v1.utilities.DataUtil;
import com.project.app.api_test_v1.utilities.RestCalls;

public class Codify_PostCall {

	private static Logger LOGGER = Logger.getLogger(Codify_PostCall.class.getName());
	RestCalls restCall;
	String baseUri;
	String basePath;
	
	/* Setting Up the config entity through system property variables
	 * So that all the data Type I need to hit The API Is fetched from 
	 * src/main/resources/{mentioned system property}/......
	 */
	static {
		if (System.getProperty(EnvironmentVariables.ENTITY) == null) {
			System.setProperty(EnvironmentVariables.ENTITY, ApiCallConfig.CODIFY);
		}
	}

	/**
	 * basic configuration set up done for Initializing the call
	 */
	@BeforeMethod
	private void setUp(Method method) {
		this.baseUri = DataUtil.fetchDataFromTestDataFile(Constants.BASE_URI);
		this.basePath = DataUtil.fetchDataFromTestDataFile(Constants.BASE_PATH);
		this.restCall = new PostApiCall(this.basePath, this.baseUri);
		this.restCall.loadPayloadString(FlatFile.CODIFY_REQUEST_JSON);
		this.restCall.setAllRestAssuredParameters();
		LOGGER.info("Method initiating ".concat(method.getName()));
	}
	
	/**
	 * Tested get call On a assumed email validator API (url Provided externally)
	 * Expected Output : SUCCESS
	 */
	@Test
	public void successPostCall() {
		this.restCall.postCall();
		this.restCall.assertResponseCode(HttpStatus.SC_OK);
		this.restCall.matchResponseWithSchema(FlatFile.GET_CUSTOMER_RESPONSE_SCHEMA);
		CommonUtils.validateGetSuccessResponse(this.restCall.getResponseJson());
	}
	
	/**
	 * Tested get call On a assumed email validator API (url Provided externally)
	 * Expected Output : SUCCESS
	 */
	@Test
	public void successPostCallWithOtherString() {
		this.restCall.updatePayloadField("text", RandomStringUtils.randomAscii(150));
		this.restCall.postCall();
		this.restCall.assertResponseCode(HttpStatus.SC_OK);
		this.restCall.matchResponseWithSchema(FlatFile.GET_CUSTOMER_RESPONSE_SCHEMA);
		CommonUtils.validateGetSuccessResponse(this.restCall.getResponseJson());
	}
}
