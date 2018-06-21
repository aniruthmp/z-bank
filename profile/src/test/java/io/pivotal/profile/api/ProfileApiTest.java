package io.pivotal.profile.api;

import io.pivotal.profile.domain.Contact;
import io.pivotal.profile.domain.Privacy;
import io.pivotal.profile.model.Profile;
import io.pivotal.profile.service.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProfileApiTest {

    @Autowired
    ProfileService profileService;

    private static String TEST_USER_ID = "test_user";

    @Test
    public void z_deleteProfile() {
        log.info("## Clear this userId: {} record", TEST_USER_ID);
        profileService.deleteProfile(TEST_USER_ID);
        assertThat(profileService.getPrivacy(TEST_USER_ID).block()).isNull();
        assertThat(profileService.getContact(TEST_USER_ID).block()).isNull();
    }

    @Test
    public void t1_randomPrivacy() {
        Mono<Privacy> privacyMono = profileService.randomPrivacy(TEST_USER_ID);
        assertThat(privacyMono.block().getId()).isNotBlank();
    }

    @Test
    public void t2_randomContact() {
        Mono<Contact> contactMono = profileService.randomContact(TEST_USER_ID);
        assertThat(contactMono.block().getId()).isNotBlank();
    }

    @Test
    public void t3_allContact() {
        List<Contact> contacts = profileService.allContact().collectList().block();
        assertThat(contacts).isNotEmpty();
    }

    @Test
    public void t4_allPrivacy() {
        List<Privacy> privacies = profileService.allPrivacy().collectList().block();
        assertThat(privacies).isNotEmpty();
    }

    @Test
    public void t5_getContact() {
        Mono<Contact> contactMono = profileService.getContact(TEST_USER_ID);
        assertThat(contactMono.block().getId()).isNotBlank();
    }

    @Test
    public void t6_getPrivacy() {
        Mono<Privacy> privacyMono = profileService.getPrivacy(TEST_USER_ID);
        assertThat(privacyMono.block().getId()).isNotBlank();
    }

    @Test
    public void t7_getProfile() {
        Profile profile = profileService.getProfile(TEST_USER_ID);
        assertThat(profile.getUserId()).isEqualTo(TEST_USER_ID);
    }

}