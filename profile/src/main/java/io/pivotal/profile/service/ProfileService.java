package io.pivotal.profile.service;

import com.github.javafaker.Faker;
import io.pivotal.profile.domain.Contact;
import io.pivotal.profile.repository.ContactRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ProfileService {

    private ContactRepo contactRepo;

    public ProfileService(ContactRepo contactRepo) {
        this.contactRepo = contactRepo;
    }

    public Mono<Contact> create(Contact contact) {
        log.info("Came inside create for {}", contact);
        return contactRepo.save(contact);
    }

    public Flux<Contact> all() {
        return contactRepo.findAll();
    }

    public Mono<Contact> get(String id) {
        log.info("Came inside get for {}", id);
        return contactRepo.findById(id);
    }

    public Mono<Contact> random() {
        log.info("Came inside random");
        Faker faker = new Faker();
        Contact contact = Contact.builder()
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
}
