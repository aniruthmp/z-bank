package io.pivotal.profile.api;

import io.pivotal.profile.domain.Contact;
import io.pivotal.profile.domain.Privacy;
import io.pivotal.profile.service.ProfileService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController(value = "/api/profiles")
public class ProfileApi {

    private ProfileService profileService;

    public ProfileApi(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping(value = "/contact/create", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Contact> createContact(@NonNull Contact contact) {
        return profileService.createContact(contact);
    }

    @GetMapping(value = "/contacts", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Contact> allContact() {
        return profileService.allContact();
    }

    @PutMapping(value = "/contact/random/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Contact> randomContact(@PathVariable(value = "userId") String userId) {
        return profileService.randomContact(userId);
    }

    @GetMapping(value = "/contact/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Contact> getContact(@PathVariable(value = "id") String id) {
        return profileService.getContact(id);
    }

    @PutMapping(value = "/privacy/random/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Privacy> randomPrivacy(@PathVariable(value = "userId") String userId) {
        return profileService.randomPrivacy(userId);
    }

}
