package com.nangman.company.presentation;

import com.nangman.company.application.dto.request.CompanyPostRequest;
import com.nangman.company.application.dto.response.CompanyGetResponse;
import com.nangman.company.application.dto.response.CompanyPostResponse;
import com.nangman.company.application.service.CompanyService;
import com.nangman.company.common.interceptor.Auth;
import com.nangman.company.domain.enums.UserRole;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;

    @Auth(role = {UserRole.MASTER, UserRole.MANAGER})
    @PostMapping
    public ResponseEntity<CompanyPostResponse> createCompany(
            @RequestBody CompanyPostRequest request) {
        return ResponseEntity.ok(companyService.createCompany(request));
    }

    @GetMapping("/{companyId}")
    public ResponseEntity<CompanyGetResponse> getCompany(@PathVariable UUID companyId) {
        return ResponseEntity.ok(companyService.getCompany(companyId));
    }

}
