package com.endre.java.java_ee_exam.backend.service;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ServiceTestBase {

    @Autowired
    private ResetService deleteService;

    @Before
    public void cleanDatabase(){
        deleteService.resetDatabase();
    }
}
