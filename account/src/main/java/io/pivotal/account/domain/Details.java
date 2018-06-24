package io.pivotal.account.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.pivotal.account.util.AccountConstants.TRANSACTION_TYPE;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Embeddable
public class Details {
    private TRANSACTION_TYPE type;
    private String vendor;
    private String vendorUrl;

    @Column(length = 1000)
    private String description;
    private Date posted;
    private Date completed;
    @Column(precision = 2)
    private double value;
}
