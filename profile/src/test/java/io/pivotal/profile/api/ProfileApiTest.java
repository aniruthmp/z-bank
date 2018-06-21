package io.pivotal.profile.api;

import io.pivotal.profile.domain.Contact;
import io.pivotal.profile.domain.Privacy;
import io.pivotal.profile.model.Profile;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import static io.pivotal.profile.util.ProfileConstants.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Slf4j
public class ProfileApiTest {

    @Autowired
    WebTestClient webTestClient;

    private static String TEST_USER_ID = "test_user";

    @Test
    public void t1_randomPrivacy() {
        webTestClient
                .put()
                .uri(PROFILE_ROOT + PRIVACY_RANDOM, TEST_USER_ID)
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody(Privacy.class);
    }

    @Test
    public void t2_randomContact() {
        webTestClient
                .put()
                .uri(PROFILE_ROOT + CONTACT_RANDOM, TEST_USER_ID)
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody(Contact.class);
    }

    @Test
    public void t3_allContact() {
        webTestClient
                .get()
                .uri(PROFILE_ROOT + CONTACTS)
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(Contact.class);
    }

    @Test
    public void t4_allPrivacy() {
        webTestClient
                .get()
                .uri(PROFILE_ROOT + PRIVACIES)
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(Privacy.class);
    }

    @Test
    public void t5_getContact() {
        webTestClient
                .get()
                .uri(PROFILE_ROOT + CONTACT, TEST_USER_ID)
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody(Contact.class);
    }

    @Test
    public void t6_getPrivacy() {
        webTestClient
                .get()
                .uri(PROFILE_ROOT + PRIVACY, TEST_USER_ID)
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody(Privacy.class);
    }

    @Test
    public void t7_getProfile() {
        webTestClient
                .get()
                .uri(PROFILE_ROOT + USERID, TEST_USER_ID)
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody(Profile.class);
    }

    @Test
    public void z_deleteProfile() {
        log.info("## Clear this userId: {} record", TEST_USER_ID);
        webTestClient
                .delete()
                .uri(PROFILE_ROOT + USERID, TEST_USER_ID)
                .exchange()
                .expectStatus().is2xxSuccessful();
    }

}