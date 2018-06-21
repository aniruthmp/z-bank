package io.pivotal.profile.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Address {
    @NonNull
    private String addressLine1;
    private String addressLine2;
    @NonNull
    private String city;
    @NonNull
    private String state;
    @NonNull
    private String zip;

}
