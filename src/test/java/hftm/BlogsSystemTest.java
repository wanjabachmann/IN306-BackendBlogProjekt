package hftm;

import java.io.StringReader;

import org.jboss.resteasy.reactive.RestResponse.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.ws.rs.core.MediaType;

@QuarkusTest
@TestInstance(Lifecycle.PER_CLASS)                        // Sicherstellen, dass die Tests in der gleichen Instanz ausgeführt werden
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)     // Ermöglicht, dass die Tests in der richtigen Reihenfolge ausgeführt werden
public class BlogsSystemTest {
    private static final String NEW_BLOG_TITLE = "Testing";
    private static final String NEW_BLOG_CONTENT = "Testing with Quarkus easy!";

    private int initialBlogsCount = 0;
    private long newBlogId;

    @Test
    @Order(1)
    void getIntitialBlogsCount() {

        RestAssured
        .when()
            .get("/blogs")
        .then()
            .statusCode(Status.NOT_FOUND.getStatusCode());
    }

    @Test
    @Order(2)
    void addBlogItem() {
        String newBlogJson = """
                {
                    "title": "%s",
                    "content": "%s"
                }
                """.formatted(NEW_BLOG_TITLE, NEW_BLOG_CONTENT);

        // Wir gehen davon aus, dass unser Blog-Resource die Location des neuen Blog-Items im Header zurückgibt
        String location =
            RestAssured
                .given()
                    .body(newBlogJson)
                    .contentType(MediaType.APPLICATION_JSON)
                .when()
                    .post("/blogs")
                .then()
                    .statusCode(Status.CREATED.getStatusCode())
                    .extract().header("Location");

        // ID aus dem Location-Path extrahieren
        String[] segments = location.split("/");
        newBlogId = Long.parseLong(segments[segments.length - 1]);
        Assertions.assertTrue(newBlogId > 0);
    }

    @Test
    @Order(3)
    void countBlogItems() {
        String responseBody =
            RestAssured
                .when()
                    .get("/blogs")
                .then()
                    .statusCode(Status.OK.getStatusCode())
                    .extract().body().asString();

        JsonArray jsonBlogArray = Json.createReader(new StringReader(responseBody)).readArray();
        Assertions.assertEquals(initialBlogsCount + 1, jsonBlogArray.size());
    }

    @Test
    @Order(4)
    void readNewBlog() {
        String responseBody =
            RestAssured
                .when()
                    .get("/blogs/" + newBlogId)
                .then()
                    .statusCode(Status.OK.getStatusCode())
                    .extract().body().asString();

        JsonObject jsonBlogObject = Json.createReader(new StringReader(responseBody)).readObject();

        Assertions.assertEquals(NEW_BLOG_TITLE, jsonBlogObject.getString("title"));
        Assertions.assertEquals(NEW_BLOG_CONTENT, jsonBlogObject.getString("content"));
    }
}
