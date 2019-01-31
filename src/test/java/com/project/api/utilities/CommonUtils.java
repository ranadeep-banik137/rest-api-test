package com.project.api.utilities;

import java.util.logging.Logger;

import com.project.api.constants.PayloadFields;

import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;
import junit.framework.Assert;

/**
 * CommonUtils class consist Of all common validation methods
 * @author Ranadeep Banik
 * @email ranadeep.banik137@yahoo.com
 *
 */

public class CommonUtils {
	
	private static Logger LOGGER = Logger.getLogger(CommonUtils.class.getName());
	//private static String requestBody;
	private static String responseBody;
	private static JsonPath response;
	
	public static void validateGetSuccessResponse(ValidatableResponse response) {
		CommonUtils.responseBody = response.extract().response().asString();
		CommonUtils.validateGetSuccessResponse(CommonUtils.responseBody);
	}
	
	public static void validateGetSuccessResponse(String response) {
		CommonUtils.response = JsonPath.from(response);
		Assert.assertNotNull(CommonUtils.response.getString(PayloadFields.IS_VALID));
		Assert.assertTrue(Boolean.parseBoolean(CommonUtils.response.getString(PayloadFields.IS_VALID)));
		LOGGER.info("All response details validated successfully");
	}

}
