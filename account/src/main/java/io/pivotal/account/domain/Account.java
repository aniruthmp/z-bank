package io.pivotal.account.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.pivotal.account.util.AccountConstants.ACCOUNT_TYPE;
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
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long number;
    private String label;
    private ACCOUNT_TYPE type;
    private String bankId;
    @Column(precision = 2)
    private double postedBalance;
    @Column(precision = 2)
    private double availableBalance;
    @Column(precision = 2)
    private double pendingDebits = 0L;
    @Column(precision = 2)
    private double pendingCredits = 0L;
    private String primaryOwner;
    private String secondaryOwner;
    private String IBAN;
    private Date createdAt;
    private Date updatedAt;

}
