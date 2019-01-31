# Framework name : api-test-framework-testng

	The API is based on customer onboarding

| Method | Api Endpoint | Call Details |
|--------|--------------|--------------|
| POST | api/customer | Post call to add customer details |
| GET | api/customer/{id} | Get call to get particular customer details |

## Notes To Run Test Cases : 

+ Test Scenarios are identified in different functional aspects of the api call.
	Examples : functional validations, request validations, response validations, http status validations.
+ All above test packages are created under : 

|Package name| Package location|
|------------|-----------------|
| functionalvalidationtests | [functional-validation-test-location](https://github.com/ranadeep-banik137/api-test-framework-testNg/tree/FB-API-TEST-V1/src/test/java/com/project/api/testscenarios/functionalvalidationtests) |
| requestvalidationtests | [request-validation-test-location](https://github.com/ranadeep-banik137/api-test-framework-testNg/tree/FB-API-TEST-V1/src/test/java/com/project/api/testscenarios/requestvalidationtests) |
| responsevalidationtests | [response-validation-test-location](https://github.com/ranadeep-banik137/api-test-framework-testNg/tree/FB-API-TEST-V1/src/test/java/com/project/api/testscenarios/responsevalidationtests) |
| httpstatusvalidationtests | [http-status-validation-test-location](https://github.com/ranadeep-banik137/api-test-framework-testNg/tree/FB-API-TEST-V1/src/test/java/com/project/api/testscenarios/httpstatusvalidationtests) |


+ Test class names has been categorized as per api call

| Class name | Http Method |
|------------|-------------|
| CustomerOnboarding_PostCall.java | POST |
| CustomerOnboarding_GetCall.java | GET |


+ A Java restassured DSL has been created as a parent framework for executing the test flow.
Need to import/add the jar in java libraries while setting up the the framework in IDE.

	Either it has to be added externally or has to set in The pom.xml along with the base directory path
	Example : 
	In pom.xml we have To add a dependency along with the system base directory
	
		<dependency>
	    	<groupId>api-test-framework</groupId>
	    	<artifactId>test-v1</artifactId>
	    	<scope>system</scope>
	    	<version>1.0.0</version>
	    	<systemPath>${basedir}\src\test\resources\jar-suite\api-test-framework-model-version-0.0.03.jar</systemPath>
		</dependency>

|DSL Jar file name| File location|
|-----------------|--------------|
| api-test-framework-model-version-0.0.03.jar | [DSL Jar File Location](https://github.com/ranadeep-banik137/api-test-framework-testNg/blob/FB-API-TEST-V1/src/test/resources/jar-suite/api-test-framework-model-version-0.0.03.jar) |

+ API Endpoint URI (Eg : /api/customers/{id}) has been initiated in test framework as per REST ASSURED Endpoint pattern
where RestAssured.BASEPATH to be defined to ("/api") and RestAssured.BASE_URI is defined to ("/customer/{id}"). 

+ To run the test and hit the api we first have to set the environment variable ("URL") which is the endpoint Url for the api.
Based on the previous comments, We Append this three URLs (URL+BASEPATH + BASEURI) to get The total API endPoint.

	Example: 
	
| URL | BASEPATH | BASEURI |
|-----|----------|---------|
|https://www.servicecall.hp.cn.dgms.com | /api | /customers |

+ Test has also been Categorized as per entity (it is the key of categorization)

		For maven run we have to set -Dentity={anyname through which we can categorize the test call}
	
		For TestNg execution : set an environment variable as entity and set the value.

+ Entity categorization has been done just to categorize the required data such as Request Body file In properties file, schemas, test data files based on the API type/call
	
		Example : we have identified all the testdata inside src/main/resources/{defined entity value}/.....
	Refer to the framework.
	
+ All test scenario details are mentioned above all the scenarios.

## Run Tests Through TestNG :

+ Step 1 ->
	set environment variable "URL" - Pass the url to which api to be called
+ Step 2 ->
	entity Variable is already put in the static block of every test class as property variable Cannot be set while testNg run.
+ Step 3 -> 
	xml files available in "src/test/resources/test-suites/.." location. Need to customize as per the TEST run requirement. Introduced example xmls over there. You can add multiple testNg Files based on exeution form.
+ Step 4 ->
	groups need to be introduced inside xml files as it categorizes which API Call to test for.
	
## Run Tests Through Maven : 

+ Step 1 -> 
	set environment variable "URL" - Pass the url to which api to be called
+ Step 2 -> 
	entity can be set in maven goals while initiating maven build. Eg : clean test -Dentity=getcustomer
+ Step 3 -> 
	particular xml files can be hit while build. You can provide the xml file name need to execute on maven goals (Mandatorily). Eg : clean test -Dtest-execution-xml={name of The xml file}	
	Build plugin has been introduced in pom.xml
		
		<plugin>
	      <groupId>org.apache.maven.plugins</groupId>
	      <artifactId>maven-surefire-plugin</artifactId>
	      <version>${maven-surefire-plugin-version}</version>
	      	<configuration>
	      		<suiteXmlFiles>
	      			<suiteXmlFile>${test-executor-file-path}/${test-execution-xml}.xml</suiteXmlFile>
	      		</suiteXmlFiles>
	      	</configuration>
	      </plugin>
+ Step 4 -> 
	Execution will be Successfully done.
	
+ NOTES : Particular test method can also be hit through maven goal command : 

		clean test -Dentity=getcustomer -Dtest=com.project.api.testscenarios.functionalvalidationtests.CustomerOnboarding_GetCall#{method_name}

## Schemas Available For API Call: 
	
	We have assumed its request and response body schema as below format: 
	
#### Links to Schema : 
+ GET-RESPONSE-SCHEMA :	[GET-Response-Schema-File](https://github.com/ranadeep-banik137/api-test-framework-testNg/blob/FB-API-TEST-V1/src/main/resources/getcustomer/schemas/response-JSON-schema.json)
+ POST-RESPONSE-SCHEMA : [POST-Response-Schema-File](https://github.com/ranadeep-banik137/api-test-framework-testNg/blob/FB-API-TEST-V1/src/main/resources/postcustomer/schemas/response-JSON-schema.json)
+ POST-REQUEST-SCHEMA : [POST-Reuest-Schema-File](https://github.com/ranadeep-banik137/api-test-framework-testNg/blob/FB-API-TEST-V1/src/main/resources/postcustomer/schemas/request-JSON-schema.json)