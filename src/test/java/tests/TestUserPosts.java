package tests;

import Utils.EmailUtil;
import Utils.ListUtil;
import domain.Comment;
import domain.Post;
import domain.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import retryAnalyzer.RetryAnalyzer;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
public class TestUserPosts {
    User user;
    ArrayList<Post> postsByDelphine;
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }
    @Test(priority = 0, description = "Get User", retryAnalyzer = RetryAnalyzer.class)
    public void GetUser(){
        try {
            Response response =  given().contentType(ContentType.JSON).get( "/users");
            if (response.statusCode() != 200)
                response.prettyPrint();

            response.then().assertThat().statusCode(200);
            List<User> users = response.jsonPath().getList("", User.class);
            ListUtil utils = new ListUtil();
            user = (User) utils.findInList("Delphine", users);
            if (user == null)
                Assert.fail("User: Delphine not found");
        }
        catch (Exception ex){
            Assert.fail(ExceptionUtils.getStackTrace(ex));
        }
    }
    @Test(priority = 0, description = "Find Posts", retryAnalyzer = RetryAnalyzer.class, dependsOnMethods = {"GetUser"}) //In case of user not found, it'll not execute other test cases
    public void FindPosts(){
        try {
            Response response =  given().contentType(ContentType.JSON).get( "/posts");
            if (response.statusCode() != 200)
                response.prettyPrint();

            response.then().assertThat().statusCode(200);
            List<Post> listPost = response.jsonPath().getList("", Post.class);
            postsByDelphine = new ArrayList<Post>();
            for (Post post: listPost) {

                if (post.getUserId() == user.getId()) {
                    postsByDelphine.add(post);
                }
            }

        }
        catch (Exception ex){
            Assert.fail(ExceptionUtils.getStackTrace(ex));
        }
    }
    @Test(priority = 0, description = "Find Comments", retryAnalyzer = RetryAnalyzer.class, dependsOnMethods = {"FindPosts"})
    public void FindComments(){
        try {
            EmailUtil emailUtil = new EmailUtil();
            List<Comment> comments = new ArrayList<>();
            List<String> invalidEmails = new ArrayList<>();

            for (Post post:  postsByDelphine) {
                Response response = given().contentType(ContentType.JSON).get("/comments?postId=" + post.getId());
                if (response.statusCode() != 200)
                    response.prettyPrint();

                response.then().assertThat().statusCode(200);
                List<Comment> listComments = response.jsonPath().getList("", Comment.class);
                comments.addAll(listComments);
            }
            for (Comment comment: comments) {
                Boolean flag = emailUtil.validateEmail(comment.getEmail());
                if (!flag)
                    invalidEmails.add(comment.getEmail());
            }
            if(invalidEmails.size() == 0)
                System.out.println("There are no invalid emails");
            else
                System.out.println("Invalid emails are " + invalidEmails);
        }
        catch (Exception ex){
            Assert.fail(ExceptionUtils.getStackTrace(ex));
        }
    }
}

