package com.test.helper;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.*;
import java.lang.reflect.Method;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class BaseHelper {

	protected static RequestSpecification reqSpec;
	protected ExtentReports report = new ExtentReports();
	/*
	 * = new ExtentReports( "./automationreport.html", true);
	 */
	protected ExtentTest test;

	@BeforeMethod
	public void createRequestSpec(Method method) {
		reqSpec = new RequestSpecBuilder().setBaseUri("http://www.purgomalum.com/").setBasePath("/service").build();
		report.attachReporter(new ExtentHtmlReporter("./automationreport.html"));
		test = report.createTest(method.getName());
	}

	@AfterMethod
	public void endTestResult(Method method) {
		ITestResult testResult;
		Status logStatus;
		testResult = Reporter.getCurrentTestResult();
		switch (testResult.getStatus()) {
		case ITestResult.FAILURE:
			logStatus = Status.FAIL;
			break;
		case ITestResult.SKIP:
			logStatus = Status.SKIP;
			break;
		default:
			logStatus = Status.PASS;
			break;
		}
		test.log(logStatus, " " + method.getName() +" with parameters " + method.getParameters()[0].getName().toString() +" Test ended with " + logStatus);
		report.flush();
	}
}
