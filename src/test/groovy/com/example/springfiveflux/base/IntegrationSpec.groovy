package com.example.springfiveflux.base

import com.example.springfiveflux.app.FiveFluxApplication
import org.junit.Before
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
import spock.lang.Specification

import javax.inject.Inject

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment

@ContextConfiguration
@SpringBootTest(
        classes = [FiveFluxApplication],
        webEnvironment = WebEnvironment.MOCK
)
abstract class IntegrationSpec extends Specification {

    @Inject
    ApplicationContext context

    WebTestClient client

    @Before
    void setupSecurity() {

        client = WebTestClient
                .bindToApplicationContext(context).build()
    }

}
