package controllers;

import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.NOT_FOUND;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

//Výpis 41
//Testy pro ItemsController
public class ItemsControllerTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    @Test
    public void testIndex() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/items");

        Result result = route(app, request);
        assertEquals(OK, result.status());
    }


    @Test
    public void testSeeOtherAdminsCreate() {
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(POST)
                .uri("/admin/items/create");
        Result result = route(app, request);
        assertEquals(SEE_OTHER, result.status());
    }

    @Test
    public void testSeeOtherUsersCreate() {
        Http.RequestBuilder request = Helpers.fakeRequest().method(POST).uri("/items/1/reviews/create");
        Result result = route(app, request);
        assertEquals(SEE_OTHER, result.status());
    }

    @Test
    public void testBadRoute() {
        Http.RequestBuilder request = Helpers.fakeRequest().method(GET).uri("/x/y/z");

        Result result = route(app, request);
        assertEquals(NOT_FOUND, result.status());
    }

}
