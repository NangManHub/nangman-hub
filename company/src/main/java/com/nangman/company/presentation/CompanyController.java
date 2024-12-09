package com.nangman.company.presentation;

import com.nangman.company.application.dto.request.CompanyPostRequest;
import com.nangman.company.application.dto.response.CompanyPostResponse;
import com.nangman.company.application.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<CompanyPostResponse> createCompany(
            @RequestBody CompanyPostRequest request) {
        // TODO: 접근 권한 제어 로직 추가
        return ResponseEntity.ok(companyService.createCompany(request));
    }

}
