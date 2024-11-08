package tests;

import io.restassured.module.jsv.JsonSchemaValidator;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;

@SuppressWarnings("unchecked")
public class PostsTest extends BaseTest{

    @Test
    public void p1_GetAllPosts(){
        given().
                contentType("application/json").
        when().
                get("/posts").
        then().
                statusCode(200).
                body(JsonSchemaValidator.matchesJsonSchema(new File(System.getProperty("user.dir") + "/src/test/java/Schemas/Posts.json")));
    }

    @Test
    public void p2_GetSinglePost(){
        int postsId = 1;
        given().
                contentType("application/json").
        when().
                get("/posts/" + postsId).
        then().
                statusCode(200).
                body(JsonSchemaValidator.matchesJsonSchema(new File(System.getProperty("user.dir") + "/src/test/java/Schemas/SinglePost.json")));
    }

    @Test
    public void p3_GetInvalidSinglePost(){
        int invalidPostId = 200;
        given().
                contentType("application/json").
        when().
                get("/posts/" + invalidPostId).
        then().
                statusCode(404);
    }

    @Test
    public void p4_NewPost(){
        given().
                contentType("application/json").
                body(new File(System.getProperty("user.dir") + "/src/test/java/JSONInputs/NewPost.json")).
        when().
                post("/posts").
        then().
                statusCode(201).
                body(JsonSchemaValidator.matchesJsonSchema(new File(System.getProperty("user.dir") + "/src/test/java/Schemas/SinglePost.json"))).
                body("id", equalTo(101)).
                body("userId", equalTo(14)).
                body("title", equalTo("Testing with POST")).
                body("body", equalTo("This is a new post with POST"));
    }

    //This test will always fail as it is possible to create a new post with an empty request
    @Test
    public void p5_NewPostWithEmptyRequest(){
        JSONObject request = new JSONObject();
        given().
                contentType("application/json").
                body(request.toJSONString()).
        when().
                post("/posts").
        then().
                statusCode(400);
    }

    @Test
    public void p6_UpdatePostWithPUT(){
        int postId = 100;
        given().
                contentType("application/json").
                body(new File(System.getProperty("user.dir") + "/src/test/java/JSONInputs/UpdatePostWithPUT.json")).
        when().
                put("/posts/" + postId).
        then().
                statusCode(200).
                body(JsonSchemaValidator.matchesJsonSchema(new File(System.getProperty("user.dir") + "/src/test/java/Schemas/SinglePost.json"))).
                body("id", equalTo(postId)).
                body("userId", equalTo(4)).
                body("title", equalTo("Testing with PUT")).
                body("body", equalTo("This is an update with PUT"));
    }

    @Test
    public void p7_UpdateInvalidPostWithPUT(){
        int invalidPostId = 200;
        given().
                contentType("application/json").
                body(new File(System.getProperty("user.dir") + "/src/test/java/JSONInputs/UpdatePostWithPUT.json")).
        when().
                put("/posts/" + invalidPostId).
        then().
                statusCode(500);
    }

    @Test
    public void p8_UpdatePostWithPATCH(){
        int postId = 100;
        given().
                contentType("application/json").
                body(new File(System.getProperty("user.dir") + "/src/test/java/JSONInputs/UpdatePostWithPATCH.json")).
        when().
                patch("/posts/" + postId).
        then().
                statusCode(200).
                body(JsonSchemaValidator.matchesJsonSchema(new File(System.getProperty("user.dir") + "/src/test/java/Schemas/SinglePost.json"))).
                body("id", equalTo(postId)).
                body("body", equalTo("This is an update with PATCH"));
    }

    //This test will always fail as it is possible to update an invalid (non-existent) postId
    @Test
    public void p9_UpdateInvalidPostWithPATCH(){
        int invalidPostId = 200;
        given().
                contentType("application/json").
                body(new File(System.getProperty("user.dir") + "/src/test/java/JSONInputs/UpdatePostWithPATCH.json")).
        when().
                patch("/posts/" + invalidPostId).
        then().
                statusCode(404);
    }

    @Test
    public void p10_DeletePost(){
        int postId = 100;
        given().
                contentType("application/json").
        when().
                delete("/posts/" + postId).
        then().
                statusCode(200);
    }

    //This test will always fail as an error should be returned when trying to delete an invalid (non-existent) postId
    @Test
    public void p11_DeleteInvalidPost(){
        int invalidPostId = 200;
        given().
                contentType("application/json").
        when().
                delete("/posts/" + invalidPostId).
        then().
                statusCode(404);
    }

}
