package io.pivotal.profile.api;

import io.pivotal.profile.domain.Contact;
import io.pivotal.profile.domain.Privacy;
import io.pivotal.profile.model.Profile;
import io.pivotal.profile.service.ProfileService;
import lombok.NonNull;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/profile")
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

    @GetMapping(value = "/contact/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Contact> getContact(@PathVariable(value = "userId") String userId) {
        return profileService.getContact(userId);
    }

    @GetMapping(value = "/privacy/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Privacy> getPrivacy(@PathVariable(value = "userId") String userId) {
        return profileService.getPrivacy(userId);
    }

    @GetMapping(value = "/privacies", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Privacy> allPrivacy() {
        return profileService.allPrivacy();
    }

    @PutMapping(value = "/privacy/random/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Privacy> randomPrivacy(@PathVariable(value = "userId") String userId) {
        return profileService.randomPrivacy(userId);
    }

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Profile getProfile(@PathVariable(value = "userId") String userId) {
        return profileService.getProfile(userId);
    }

    @DeleteMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteProfile(@PathVariable(value = "userId") String userId) {
        profileService.deleteProfile(userId);
    }

}

//https://api.openbankproject.com/obp/v1.2.1/banks/postbank/accounts/f9315a52-330a-470c-8146-c51292c68f9d/public/transactions
//https://raw.githubusercontent.com/OpenBankProject/OBP-API/develop/src/main/scala/code/api/sandbox/example_data/2016-04-28/example_import.json