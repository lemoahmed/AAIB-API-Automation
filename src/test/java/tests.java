import org.json.simple.JSONObject;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class tests {

    String id;


    @Test(priority = 1)
    public void createUser(){

        JSONObject body = new JSONObject();
        body.put("name","morpheus");
        body.put("job","leader");

        id=given()
                .header("x-api-key","reqres-free-v1")
                .contentType("application/json")
                .body(body)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .statusCode(201)
                .log().all()
                .extract()
                .path("id");
    }


    // THE ENDPOINT HAS AN ISSUE WHERE IT DOES NOT ACCEPT DYNAMICALLY GENERATED USER ID
    // AS THE FREE VERSION USES MOCK DATA SO THE CREATED USER CAN'T BE FOUND

    @Test(priority = 3)
    public void getUser()
    {
       given()
               .header("x-api-key","reqres-free-v1")
               .when()
               .get("https://reqres.in/api/users/"+id)
               .then()
               .statusCode(200)
               .log().all();
    }


    @Test(priority = 2)
    public void updateUser()
    {
        System.out.println(id);
        JSONObject body = new JSONObject();
        body.put("name","morpheus");
        body.put("job","zion resident");

        given()
                .header("x-api-key","reqres-free-v1")
                .body(body)
                .when()
                .put("https://reqres.in/api/users/"+id)
                .then()
                .statusCode(200)
                .log().all();
    }

    @Test(priority = 4)
    public void deleteUser()
    {
        given()
                .header("x-api-key","reqres-free-v1")
                .when()
                .delete("https://reqres.in/api/users/"+id)
                .then()
                .statusCode(204);
    }

    @Test(priority = 5)
    public void checkDeleted()
    {
        given()
                .header("x-api-key","reqres-free-v1")
                .when().get("https://reqres.in/api/users/"+id)
                .then().statusCode(404);
    }

}
