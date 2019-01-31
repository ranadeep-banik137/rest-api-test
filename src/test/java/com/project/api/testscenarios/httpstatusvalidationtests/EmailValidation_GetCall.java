package com.project.api.testscenarios.httpstatusvalidationtests;

import java.lang.reflect.Method;
import java.security.InvalidAlgorithmParameterException;
import java.util.logging.Logger;

import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.project.api.constants.ApiCallConfig;
import com.project.api.constants.Constants;
import com.project.api.constants.EnvironmentVariables;
import com.project.api.constants.PathParamConstants;
import com.project.app.api_test_v1.restFiles.GetApiCall;
import com.project.app.api_test_v1.utilities.DataUtil;
import com.project.app.api_test_v1.utilities.RestCalls;

import io.restassured.path.json.JsonPath;
import junit.framework.Assert;

public class EmailValidation_GetCall {

	private static Logger LOGGER = Logger.getLogger(EmailValidation_GetCall.class.getName());
	RestCalls restCall;
	String baseUri;
	String basePath;
	
	/* Setting Up the config entity through system property variables
	 * So that all the data Type I need to hit The API Is fetched from 
	 * src/main/resources/{mentioned system property}/......
	 */
	static {
		if (System.getProperty(EnvironmentVariables.ENTITY) == null) {
			System.setProperty(EnvironmentVariables.ENTITY, ApiCallConfig.EMAIL_VALIDATION);
		}
	}

	/**
	 * basic configuration set up done for Initializing the call
	 */
	@BeforeMethod
	private void setUp(Method method) {
		this.baseUri = DataUtil.fetchDataFromTestDataFile(Constants.BASE_URI);
		this.basePath = DataUtil.fetchDataFromTestDataFile(Constants.BASE_PATH);
		
		this.restCall = new GetApiCall(this.basePath, this.baseUri);
		this.restCall.setAllRestAssuredParameters();
		try {
			this.restCall.modifyPathParam(PathParamConstants.EMAIL, DataUtil.fetchDataFromTestDataFile(PathParamConstants.EMAIL));
		} catch (InvalidAlgorithmParameterException exceptionObject) {
			exceptionObject.printStackTrace();
		}
		LOGGER.info("Method initiating ".concat(method.getName()));
	}
	
	/**
	 * Tested delete call On a assumed email validator API (url Provided externally)
	 * Expected Output : 404
	 */
	@Test
	public void testWithDifferentCallMethod() {
		this.restCall.deleteCall();
		this.restCall.assertResponseCode(HttpStatus.SC_NOT_FOUND);
		Assert.assertNotNull(JsonPath.from(this.restCall.getResponseJson()).get("message"));
	}
}
