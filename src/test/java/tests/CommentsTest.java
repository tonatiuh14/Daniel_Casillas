package tests;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class CommentsTest extends BaseTest{

    @Test
    public void c1_GetAllCommentsFromSinglePost(){
        int postId = 4;
        given().
                contentType(ContentType.JSON).
        when().
                get("/posts/"+postId+"/comments").
        then().
                statusCode(200).
                body(JsonSchemaValidator.matchesJsonSchema(new File(System.getProperty("user.dir") + "/src/test/java/Schemas/PostComments.json")));
    }

    //This test will always fail as an invalid (non-existent) post should not have comments
    @Test
    public void c2_GetAllCommentsFromInvalidSinglePost(){
        int invalidPostId = 101;
        given().
                contentType(ContentType.JSON).
        when().
                get("/posts/" + invalidPostId + "/comments").
        then().
                statusCode(404);
    }

    @Test
    public void c3_GetCommentsFromPost(){
        int postId = 14;
        given().
                contentType(ContentType.JSON).
        when().
                get("/comments?postId=" + postId).
        then().
                statusCode(200).
                body(JsonSchemaValidator.matchesJsonSchema(new File(System.getProperty("user.dir") + "/src/test/java/Schemas/PostComments.json")));
    }

    //This test will always fail as an invalid (non-existent) post shouldn't have comments
    @Test
    public void c4_GetCommentsFromInvalidPost(){
        int invalidPostId = 201;
        given().
                contentType(ContentType.JSON).
        when().
                get("/comments?postId=" + invalidPostId).
        then().
                statusCode(404);
    }

}
