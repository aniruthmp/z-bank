package io.pivotal.profile.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Document
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Contact  {
    @Id
    private String id;

    @NonNull
    private String userId;

    private Email email;
    private Phone phone;
    private Address address;
}
