package io.pivotal.account.api;

import com.wix.mysql.EmbeddedMysql;
import io.pivotal.account.domain.Account;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.distribution.Version.v5_7_latest;
import static io.pivotal.account.util.AccountConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Slf4j
public class AccountApiTests {

    @Autowired
    TestRestTemplate restTemplate;

    private static final String BANK_ID = "TEST_BANK_ID";
    private static Map<String, String> params;

    @BeforeClass
    public static void setup() {
        params = new HashMap<>();
        params.put("bankId", BANK_ID);
        EmbeddedMysql mysqld = anEmbeddedMysql(v5_7_latest)
                .start();
    }

    @Test
    public void t1_randomAccount() {
        ResponseEntity<Account> account = restTemplate.exchange(ACCOUNT_ROOT + RANDOM_ACCOUNT, HttpMethod.PUT,
                null, Account.class, params);
        assertNotNull(account.getBody());
        assertNotNull(account.getBody().getId());
        assertEquals("Asserting bankId", BANK_ID, account.getBody().getBankId());
    }

    @Test
    public void t2_allAccounts() {
//        ParameterizedTypeReference<List<Account>> outList = new ParameterizedTypeReference<List<Account>>() {};
        ResponseEntity<List<Account>> accounts = restTemplate.exchange(ACCOUNT_ROOT + ACCOUNTS,
                HttpMethod.GET,null, new ParameterizedTypeReference<List<Account>>() {});

        assertNotNull(accounts.getBody());
        assertTrue("Asserting return list is not 0", !accounts.getBody().isEmpty());
    }

    @Test
    public void t3_getAccount() {
        ResponseEntity<List<Account>> accounts = restTemplate.exchange(ACCOUNT_ROOT + ACCOUNT,
                HttpMethod.GET,null, new ParameterizedTypeReference<List<Account>>() {}, params);

        assertNotNull(accounts.getBody());
        assertTrue("Asserting return list is not 0", !accounts.getBody().isEmpty());
    }

    @Test
    public void t4_deleteAccount() {
        ResponseEntity<Void> deleteResponse = restTemplate.exchange(ACCOUNT_ROOT + ACCOUNT,
                HttpMethod.DELETE,null, Void.class, params);

        assertEquals(HttpStatus.ACCEPTED, deleteResponse.getStatusCode());
    }

    @Test
    public void t5_deleteAccounts() {
        ResponseEntity<Void> deleteResponse = restTemplate.exchange(ACCOUNT_ROOT + ACCOUNTS,
                HttpMethod.DELETE,null, Void.class);

        assertEquals(HttpStatus.ACCEPTED, deleteResponse.getStatusCode());
    }

}
