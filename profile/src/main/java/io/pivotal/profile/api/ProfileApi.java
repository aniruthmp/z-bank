package io.pivotal.profile.api;

import io.pivotal.profile.domain.Contact;
import io.pivotal.profile.domain.Privacy;
import io.pivotal.profile.model.Profile;
import io.pivotal.profile.service.ProfileService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static io.pivotal.profile.util.ProfileConstants.*;

@RestController
@RequestMapping(value = PROFILE_ROOT)
public class ProfileApi {

    private ProfileService profileService;

    public ProfileApi(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping(value = CONTACTS, produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Contact> allContact() {
        return profileService.allContact();
    }

    @PutMapping(value = CONTACT_RANDOM, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Contact> randomContact(@PathVariable(value = "userId") String userId) {
        return profileService.randomContact(userId);
    }

    @GetMapping(value = CONTACT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Contact> getContact(@PathVariable(value = "userId") String userId) {
        return profileService.getContact(userId);
    }

    @GetMapping(value = PRIVACY, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Privacy> getPrivacy(@PathVariable(value = "userId") String userId) {
        return profileService.getPrivacy(userId);
    }

    @GetMapping(value = PRIVACIES, produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Privacy> allPrivacy() {
        return profileService.allPrivacy();
    }

    @PutMapping(value = PRIVACY_RANDOM, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Privacy> randomPrivacy(@PathVariable(value = "userId") String userId) {
        return profileService.randomPrivacy(userId);
    }

    @GetMapping(value = USERID, produces = MediaType.APPLICATION_JSON_VALUE)
    public Profile getProfile(@PathVariable(value = "userId") String userId) {
        return profileService.getProfile(userId);
    }

    @DeleteMapping(value = USERID, produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteProfile(@PathVariable(value = "userId") String userId) {
        profileService.deleteProfile(userId);
    }

}