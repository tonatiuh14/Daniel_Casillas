package tests;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import static io.restassured.RestAssured.baseURI;


public class BaseTest {

    @BeforeSuite
    public void setup(){
        baseURI = "https://jsonplaceholder.typicode.com";
    }

    @AfterSuite
    public void tearDown(){
        System.out.println("Suite tested");
    }


}
