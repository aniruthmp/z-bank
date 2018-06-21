package io.pivotal.profile.service;

import com.github.javafaker.Faker;
import io.pivotal.profile.domain.*;
import io.pivotal.profile.model.Profile;
import io.pivotal.profile.repository.ContactRepo;
import io.pivotal.profile.repository.PrivacyRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@Slf4j
public class ProfileService {

    private ContactRepo contactRepo;
    private PrivacyRepo privacyRepo;
    private MongoOperations mongoOperations;

    public ProfileService(ContactRepo contactRepo, PrivacyRepo privacyRepo, MongoOperations mongoOperations) {
        this.contactRepo = contactRepo;
        this.privacyRepo = privacyRepo;
        this.mongoOperations = mongoOperations;
    }

    public Mono<Contact> createContact(Contact contact) {
        log.info("Came inside createContact for {}", contact);
        return contactRepo.save(contact);
    }

    public Flux<Contact> allContact() {
        return contactRepo.findAll();
    }

    public Mono<Contact> getContact(String userId) {
        log.info("Came inside get for {}", userId);
        return contactRepo.findByUserId(userId);
    }

    public Mono<Contact> randomContact(String userId) {
        log.info("Came inside randomContact for userId: {}", userId);
        Faker faker = new Faker();
        Contact contact = Contact.builder()
                .userId(userId)
                .phone(Phone.builder()
                        .mobilePhoneNumber(faker.phoneNumber().cellPhone())
                        .homePhoneNumber(faker.phoneNumber().phoneNumber())
                        .build())
                .email(Email.builder()
                        .primaryEmailId(faker.internet().safeEmailAddress())
                        .secondaryEmailId(faker.internet().emailAddress())
                        .build())
                .address(Address.builder()
                        .addressLine1(faker.address().streetAddress())
                        .city(faker.address().city())
                        .state(faker.address().state())
                        .zip(faker.address().zipCode())
                        .build())
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

    public Profile getProfile(String userId) {
        log.info("Came inside getProfile for userId: {}", userId);
        Profile profile = Profile.builder()
                .userId(userId)
                .build();
        log.info("Querying Contact for userId: {}", userId);
        Contact contact = contactRepo.findByUserId(userId).block(Duration.ofSeconds(1L));
        log.info("Querying Privacy for userId: {}", userId);
        Privacy privacy = privacyRepo.findByUserId(userId).block(Duration.ofSeconds(1L));
        profile.toBuilder().contact(contact)
                .privacy(privacy)
                .build();
        return profile;
    }

    public void deleteProfile(String userId) {
        log.info("Came inside deleteProfile for userId: {}", userId);
        log.info("Deleting Contact for userId: {}", userId);

        Query deleteQuery = new Query();
        deleteQuery.addCriteria(Criteria.where("userId").is(userId));
        mongoOperations.findAllAndRemove(deleteQuery, Contact.class);

        log.info("Deleting Privacy for userId: {}", userId);
        mongoOperations.findAllAndRemove(deleteQuery, Privacy.class);

    }

    public Flux<Privacy> allPrivacy() {
        return privacyRepo.findAll();
    }

    public Mono<Privacy> getPrivacy(String userId) {
        log.info("Came inside get for {}", userId);
        return privacyRepo.findByUserId(userId);
    }
}
