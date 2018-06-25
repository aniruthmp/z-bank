package io.pivotal.account.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Embeddable
public class Details {
    private String type;
    private String vendor;
    private String vendorUrl;

    private String description;
    private Date posted;
    private Date completed;
    private double value;
}
