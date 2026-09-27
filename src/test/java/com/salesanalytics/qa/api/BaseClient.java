package com.salesanalytics.qa.api;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public abstract class BaseClient {


    protected <T> T get(RequestSpecification spec, String endpoint, int expectedStatus, Class<T> responseClass) {
        return given()
                .spec(spec)
                .when()
                .get(endpoint)
                .then()
                .statusCode(expectedStatus)
                .extract().as(responseClass);
    }

    protected Response get(RequestSpecification spec, String endpoint) {
        return given()
                .spec(spec)
                .when()
                .get(endpoint);
    }
}
