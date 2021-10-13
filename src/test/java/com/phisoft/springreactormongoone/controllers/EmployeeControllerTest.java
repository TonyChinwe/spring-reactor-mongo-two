package com.phisoft.springreactormongoone.controllers;

import com.phisoft.springreactormongoone.dto.EmployeeDto;
import com.phisoft.springreactormongoone.services.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebFluxTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private EmployeeService employeeService;

    @Test
    void getEmployees() {
        Flux<EmployeeDto> employeeDtoFlux=Flux.just(new EmployeeDto("a2b3","Tony",20000L),
                new EmployeeDto("a1b4","Peter",30000L),
                new EmployeeDto("a3b2","Larry",40000L));
        when(employeeService.getEmployees()).thenReturn(employeeDtoFlux);

        Flux<EmployeeDto> responseBody = webTestClient.get().uri("/employee").exchange()
                .expectStatus().isOk()
                .returnResult(EmployeeDto.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNext(new EmployeeDto("a2b3","Tony",20000L))
                .expectNext(new EmployeeDto("a1b4","Peter",30000L))
                .expectNext(new EmployeeDto("a3b2","Larry",40000L))
                .verifyComplete();

    }

    @Test
    void streamEmployees() {
        Flux<EmployeeDto> employeeDtoFlux=Flux.just(new EmployeeDto("a2b3","Tony",20000L),
                new EmployeeDto("a1b4","Peter",30000L),
                new EmployeeDto("a3b2","Larry",40000L));
        when(employeeService.getEmployees()).thenReturn(employeeDtoFlux);
        Flux<EmployeeDto> responseBody = webTestClient.get().uri("/employee/stream").exchange()
                .expectStatus().isOk()
                .returnResult(EmployeeDto.class)
                .getResponseBody().delayElements(Duration.ofSeconds(1));
        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNext(new EmployeeDto("a2b3","Tony",20000L))
                .expectNext(new EmployeeDto("a1b4","Peter",30000L))
                .expectNext(new EmployeeDto("a3b2","Larry",40000L))
                .thenCancel();
    }

    @Test
    void getEmployee() {
    Mono<EmployeeDto> employeeDtoMono=Mono.just(new EmployeeDto("a2b3","Tony",20000L));
    when(employeeService.getEmployee(any())).thenReturn(employeeDtoMono);

        Flux<EmployeeDto> responseBody = webTestClient.get().uri("/employee/a2b3").exchange()
                .expectStatus().isOk()
                .returnResult(EmployeeDto.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNextMatches(employeeDto -> employeeDto.getId().equals("a2b3"))
                .verifyComplete();



    }

    @Test
    void getSalaryRange() {

        Flux<EmployeeDto> employeeDtoFlux=Flux.just(new EmployeeDto("a2b3","Tony",20000L),
                new EmployeeDto("a1b4","Peter",30000L),
                new EmployeeDto("a3b2","Larry",40000L));
        when(employeeService.getSalaryInRange(10000L,50000L)).thenReturn(employeeDtoFlux);

        Flux<EmployeeDto> responseBody = webTestClient.get().uri("/employee/salary-range?min=10000&max=50000").exchange()
                .expectStatus().isOk()
                .returnResult(EmployeeDto.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNext(new EmployeeDto("a2b3","Tony",20000L))
                .expectNext(new EmployeeDto("a1b4","Peter",30000L))
                .expectNext(new EmployeeDto("a3b2","Larry",40000L))
                .verifyComplete();

    }

    @Test
    void save() {
        Mono<EmployeeDto>employeeDtoMono=Mono.just(new EmployeeDto("a2b3","Tony",20000L));
        when(employeeService.saveEmployee(employeeDtoMono)).thenReturn(employeeDtoMono);
        webTestClient.post().uri("/employee/save")
                .body(Mono.just(employeeDtoMono),EmployeeDto.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void update() {
        Mono<EmployeeDto>employeeDtoMono=Mono.just(new EmployeeDto("a2b3","Tony",20000L));
        when(employeeService.updateEmployee(employeeDtoMono,"a2b3")).thenReturn(employeeDtoMono);
        webTestClient.put().uri("/employee/update/a2b3")
                .body(Mono.just(employeeDtoMono),EmployeeDto.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void delete() {
     given(employeeService.deleteEmployee(any())).willReturn(Mono.empty());
        webTestClient.delete().uri("/employee/delete/a2b3")
                .exchange()
                .expectStatus().isOk();
    }
}