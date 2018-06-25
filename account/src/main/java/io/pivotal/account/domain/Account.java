package io.pivotal.account.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    private String type;
    private String bankId;
    private double postedBalance;
    private double availableBalance;
    private double pendingDebits = 0L;
    private double pendingCredits = 0L;
    private String userId;
    private String primaryOwner;
    private String secondaryOwner;
    private String IBAN;
    private Date createdAt;
    private Date updatedAt;

}
