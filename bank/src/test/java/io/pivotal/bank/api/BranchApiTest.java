package io.pivotal.bank.api;

import io.pivotal.bank.domain.Branch;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import static io.pivotal.bank.util.BankConstants.BRANCHES;
import static io.pivotal.bank.util.BankConstants.BRANCH_ROOT;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BranchApiTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    public void t1_allBranches() {
        webTestClient
                .get()
                .uri(BRANCH_ROOT + BRANCHES)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(Branch.class);
    }

    @Test
    public void t2_deleteBranches() {
        webTestClient
                .delete()
                .uri(BRANCH_ROOT + BRANCHES)
                .exchange()
                .expectStatus().is2xxSuccessful();
    }

}