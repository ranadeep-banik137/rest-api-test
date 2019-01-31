package com.project.api.testscenarios.functionalvalidationtests;

import java.lang.reflect.Method;
import java.security.InvalidAlgorithmParameterException;
import java.util.logging.Logger;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.project.api.constants.ApiCallConfig;
import com.project.api.constants.Constants;
import com.project.api.constants.EnvironmentVariables;
import com.project.api.constants.FlatFile;
import com.project.api.constants.PathParamConstants;
import com.project.api.constants.PayloadFields;
import com.project.api.constants.TestDataKey;
import com.project.api.utilities.CommonUtils;
import com.project.app.api_test_v1.restFiles.GetApiCall;
import com.project.app.api_test_v1.utilities.DataUtil;
import com.project.app.api_test_v1.utilities.RestCalls;

import io.restassured.path.json.JsonPath;

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
	 * Tested get call On a assumed email validator API (url Provided externally)
	 * Expected Output : SUCCESS
	 */
	@Test
	public void successGetCall() {
		this.restCall.getCall();
		this.restCall.assertResponseCode(HttpStatus.SC_OK);
		this.restCall.matchResponseWithSchema(FlatFile.GET_CUSTOMER_RESPONSE_SCHEMA);
		CommonUtils.validateGetSuccessResponse(this.restCall.getResponseJson());
	}
	
	/**
	 * Tested get call On a assumed email validator API (url Provided externally)
	 * Expected Output : SUCCESS
	 */
	@Test
	public void successGetCallWithOtherValidEmail() {
		try {
			this.restCall.modifyPathParam(PathParamConstants.EMAIL, DataUtil.fetchDataFromTestDataFile(TestDataKey.VALID_EMAIL_1));
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}
		this.restCall.getCall();
		this.restCall.assertResponseCode(HttpStatus.SC_OK);
		this.restCall.matchResponseWithSchema(FlatFile.GET_CUSTOMER_RESPONSE_SCHEMA);
		CommonUtils.validateGetSuccessResponse(this.restCall.getResponseJson());
	}
	
	/**
	 * Tested get call On a assumed email validator API (url Provided externally)
	 * Expected Output : FAIL
	 * @throws InvalidAlgorithmParameterException 
	 */
	@Test
	public void getCallWithInvalidEmail() {
		try {
			this.restCall.modifyPathParam(PathParamConstants.EMAIL, RandomStringUtils.randomAscii(15));
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}
		this.restCall.getCall();
		this.restCall.assertResponseCode(HttpStatus.SC_OK);
		this.restCall.matchResponseWithSchema(FlatFile.GET_CUSTOMER_RESPONSE_SCHEMA);
		Assert.assertTrue(!JsonPath.from(this.restCall.getResponseJson()).getBoolean(PayloadFields.IS_VALID));
	}
}
