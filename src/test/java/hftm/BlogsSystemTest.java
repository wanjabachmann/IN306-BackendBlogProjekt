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
import io.quarkus.test.keycloak.client.KeycloakTestClient;
import io.restassured.RestAssured;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.ws.rs.core.MediaType;

@QuarkusTest
@TestInstance(Lifecycle.PER_CLASS) // Sicherstellen, dass die Tests in der gleichen Instanz ausgeführt werden
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Ermöglicht, dass die Tests in der richtigen Reihenfolge
                                                      // ausgeführt werden
public class BlogsSystemTest {

    KeycloakTestClient keycloakClient = new KeycloakTestClient();

    private static final String NEW_BLOG_TITLE = "Testing";
    private static final String NEW_BLOG_CONTENT = "Testing with Quarkus easy!";

    private static final String NEW_AUTHOR_FIRSTNAME = "Hans";
    private static final String NEW_AUTHOR_LASTNAME = "Muster";

    private static final String NEW_COMMENT_CREATOR = "USER20r";
    private static final String NEW_COMMENT_CONTENT = "This is a really long Content as a test.";

    private int initialBlogsCount = 0;
    private long newBlogId;
    private long newAuthorId;
    private long newCommentId;

    /*
     * Blogs
     */

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

        // Wir gehen davon aus, dass unser Blog-Resource die Location des neuen
        // Blog-Items im Header zurückgibt
        String location = RestAssured
                .given()
                .auth().oauth2(getAccessTokenAlice())
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
        String responseBody = RestAssured
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
        String responseBody = RestAssured
                .when()
                .get("/blogs/" + newBlogId)
                .then()
                .statusCode(Status.OK.getStatusCode())
                .extract().body().asString();

        JsonObject jsonBlogObject = Json.createReader(new StringReader(responseBody)).readObject();

        Assertions.assertEquals(NEW_BLOG_TITLE, jsonBlogObject.getString("title"));
        Assertions.assertEquals(NEW_BLOG_CONTENT, jsonBlogObject.getString("content"));
    }

    /*
     * Authors
     */

    @Test
    @Order(5)
    void getIntitialAuthorsCount() {

        RestAssured
                .when()
                .get("/authors")
                .then()
                .statusCode(Status.NOT_FOUND.getStatusCode());
    }

    @Test
    @Order(6)
    void addAuthorItem() {
        String newAuthorJson = """
                {
                    "firstname": "%s",
                    "lastname": "%s"
                }
                """.formatted(NEW_AUTHOR_FIRSTNAME, NEW_AUTHOR_LASTNAME);

        // Wir gehen davon aus, dass unser Blog-Resource die Location des neuen
        // Blog-Items im Header zurückgibt
        String location = RestAssured
                .given()
                .auth().oauth2(getAccessTokenAlice())
                .body(newAuthorJson)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .post("/authors")
                .then()
                .statusCode(Status.CREATED.getStatusCode())
                .extract().header("Location");

        // ID aus dem Location-Path extrahieren
        String[] segments = location.split("/");
        newAuthorId = Long.parseLong(segments[segments.length - 1]);
        Assertions.assertTrue(newAuthorId > 0);
    }

    @Test
    @Order(7)
    void countAuthorItems() {
        String responseBody = RestAssured
                .when()
                .get("/authors")
                .then()
                .statusCode(Status.OK.getStatusCode())
                .extract().body().asString();

        JsonArray jsonAuthorArray = Json.createReader(new StringReader(responseBody)).readArray();
        Assertions.assertEquals(initialBlogsCount + 1, jsonAuthorArray.size());
    }

    @Test
    @Order(8)
    void readNewAuthor() {
        String responseBody = RestAssured
                .when()
                .get("/authors/" + newAuthorId)
                .then()
                .statusCode(Status.OK.getStatusCode())
                .extract().body().asString();

        JsonObject jsonAuthorObject = Json.createReader(new StringReader(responseBody)).readObject();

        Assertions.assertEquals(NEW_AUTHOR_FIRSTNAME, jsonAuthorObject.getString("firstname"));
        Assertions.assertEquals(NEW_AUTHOR_LASTNAME, jsonAuthorObject.getString("lastname"));
    }

    /*
     * Comments
     */

    @Test
    @Order(9)
    void addCommentToBlog() {
        String newCommentJson = """
                {
                    "content": "%s",
                    "creator": "%s"
                }
                """.formatted(NEW_COMMENT_CONTENT, NEW_COMMENT_CREATOR);

        // Wir gehen davon aus, dass unser Comment-Resource die Location des neuen
        // Comment-Items im Header zurückgibt
        String location = RestAssured
                .given()
                .auth().oauth2(getAccessTokenAlice())
                .body(newCommentJson)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .post("/blogs/" + newBlogId + "/comments")
                .then()
                .statusCode(Status.CREATED.getStatusCode())
                .extract().header("Location");

        // ID aus dem Location-Path extrahieren
        String[] segments = location.split("/");
        newCommentId = Long.parseLong(segments[segments.length - 1]);
        Assertions.assertTrue(newCommentId > 0);
    }

    @Test
    @Order(10)
    void countCommentItems() {
        String responseBody = RestAssured
                .when()
                .get("/blogs/" + newBlogId + "/comments")
                .then()
                .statusCode(Status.OK.getStatusCode())
                .extract().body().asString();

        JsonArray jsonCommentArray = Json.createReader(new StringReader(responseBody)).readArray();
        Assertions.assertEquals(1, jsonCommentArray.size());
    }

    @Test
    @Order(11)
    void readNewComment() {
        String responseBody = RestAssured
                .given()
                .when()
                .get("/blogs/" + newBlogId + "/comments")
                .then()
                .statusCode(Status.OK.getStatusCode())
                .extract().body().asString();

        JsonArray jsonCommentArray = Json.createReader(new StringReader(responseBody)).readArray();

        JsonObject jsonCommentObject = jsonCommentArray.getJsonObject(0);

        Assertions.assertEquals(NEW_COMMENT_CONTENT, jsonCommentObject.getString("content"));
        Assertions.assertEquals(NEW_COMMENT_CREATOR, jsonCommentObject.getString("creator"));
    }

    @Test
    @Order(12)
    void removeCommentItem() {
        // Delete the comment with the ID `newCommentId`
        RestAssured
                .given()
                .auth().oauth2(getAccessTokenAlice())
                .when()
                .delete("/blogs/comments/" + newCommentId)
                .then()
                .statusCode(Status.NO_CONTENT.getStatusCode());
    }

    @Test
    @Order(13)
    public void unauthorizedAccess() {
        // Simulate unauthorized user trying to access a secured resource
        String newBlogJson = """
                {
                    "title": "%s",
                    "content": "%s"
                }
                """.formatted(NEW_BLOG_TITLE, NEW_BLOG_CONTENT);
        RestAssured
                .given()
                .body(newBlogJson)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .post("/blogs")
                .then()
                .statusCode(401);
    }

    @Test
    @Order(14)
    void updateBlog() {
        String updatedTitle = "Updated Title";
        String updatedContent = "Updated content.";

        String updatedBlogJson = """
                {
                    "title": "%s",
                    "content": "%s"
                }
                """.formatted(updatedTitle, updatedContent);

        // Send PUT request to update the blog with newBlogId
        RestAssured
                .given()
                .auth().oauth2(getAccessTokenAlice())
                .body(updatedBlogJson)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .put("/blogs/" + newBlogId)
                .then()
                .statusCode(200);

        // Verify the updated blog's content
        String responseBody = RestAssured
                .when()
                .get("/blogs/" + newBlogId)
                .then()
                .statusCode(200)
                .extract().body().asString();

        JsonObject jsonBlogObject = Json.createReader(new StringReader(responseBody)).readObject();
        Assertions.assertEquals(updatedTitle, jsonBlogObject.getString("title"));
        Assertions.assertEquals(updatedContent, jsonBlogObject.getString("content"));
    }

    // get Token from Alice to peform the test as admin user
    protected String getAccessTokenAlice() {
        return keycloakClient.getAccessToken("alice", "1234", "backend-service");
    }

}
