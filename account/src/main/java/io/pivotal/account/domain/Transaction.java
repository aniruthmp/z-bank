package io.pivotal.account.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.pivotal.account.util.AccountConstants.TRANSACTION_STATUS;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long accountNumber;
    private String bankId;
    private TRANSACTION_STATUS status;

    @Embedded
    private Details details;
    private Date createdAt;
    private Date updatedAt;
}
