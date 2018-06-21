package io.pivotal.bank.api;

import io.pivotal.bank.domain.Branch;
import io.pivotal.bank.service.BranchService;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BranchApiTest {

    @Autowired
    BranchService branchService;

    @Test
    public void t1_allBranches() {
        List<Branch> branches = branchService.allBranches().collectList().block();
        assertThat(branches).isNotEmpty();
    }

    @Test
    public void t2_deleteBranches() {
        branchService.deleteBranches().block();
        assertThat(branchService.allBranches().collectList().block()).isEmpty();
    }

}