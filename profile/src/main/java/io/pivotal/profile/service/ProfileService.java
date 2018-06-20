package io.pivotal.profile.service;

import com.github.javafaker.Faker;
import io.pivotal.profile.domain.Contact;
import io.pivotal.profile.domain.Privacy;
import io.pivotal.profile.repository.ContactRepo;
import io.pivotal.profile.repository.PrivacyRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ProfileService {

    private ContactRepo contactRepo;
    private PrivacyRepo privacyRepo;

    public ProfileService(ContactRepo contactRepo, PrivacyRepo privacyRepo) {
        this.contactRepo = contactRepo;
        this.privacyRepo = privacyRepo;
    }

    public Mono<Contact> createContact(Contact contact) {
        log.info("Came inside createContact for {}", contact);
        return contactRepo.save(contact);
    }

    public Flux<Contact> allContact() {
        return contactRepo.findAll();
    }

    public Mono<Contact> getContact(String id) {
        log.info("Came inside get for {}", id);
        return contactRepo.findById(id);
    }

    public Mono<Contact> randomContact(String userId) {
        log.info("Came inside randomContact for userId: {}", userId);
        Faker faker = new Faker();
        Contact contact = Contact.builder()
                .userId(userId)
                .mobilePhoneNumber(faker.phoneNumber().cellPhone())
                .homePhoneNumber(faker.phoneNumber().phoneNumber())
                .primaryEmailId(faker.internet().safeEmailAddress())
                .secondaryEmailId(faker.internet().emailAddress())
                .addressLine1(faker.address().streetAddress())
                .city(faker.address().city())
                .state(faker.address().state())
                .zip(faker.address().zipCode())
                .build();
        return contactRepo.save(contact);
    }

    public Mono<Privacy> randomPrivacy(String userId) {
        log.info("Came inside randomPrivacy for userId: {}", userId);
        Faker faker = new Faker();
        Privacy privacy = Privacy.builder()
                .userId(userId)
                .bShareAmongZBank(faker.random().nextBoolean())
                .bReceiveText(faker.random().nextBoolean())
                .bReceivePhone(faker.random().nextBoolean())
                .bReceiveMail(faker.random().nextBoolean())
                .bReceiveEmail(faker.random().nextBoolean())
                .build();
        return privacyRepo.save(privacy);
    }
}
