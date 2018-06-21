package io.pivotal.profile.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.pivotal.profile.domain.Contact;
import io.pivotal.profile.domain.Privacy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Profile {

    @JsonIgnore
    private String userId;
    private Contact contact;
    private Privacy privacy;
}
