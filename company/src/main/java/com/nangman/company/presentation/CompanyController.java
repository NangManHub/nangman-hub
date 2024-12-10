package com.nangman.company.presentation;

import com.nangman.company.application.dto.request.CompanyPostRequest;
import com.nangman.company.application.dto.request.CompanyPutRequest;
import com.nangman.company.application.dto.response.CompanyGetResponse;
import com.nangman.company.application.dto.response.CompanyPostResponse;
import com.nangman.company.application.dto.response.CompanyPutResponse;
import com.nangman.company.application.service.CompanyService;
import com.nangman.company.common.interceptor.Auth;
import com.nangman.company.domain.enums.UserRole;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @Auth(role = {UserRole.MASTER, UserRole.MANAGER, UserRole.AGENT})
    @PutMapping("/{companyId}")
    public ResponseEntity<CompanyPutResponse> modifyCompany(@PathVariable UUID companyId,
                                                            @RequestBody CompanyPutRequest request) {
        return ResponseEntity.ok(companyService.modifyCompany(companyId, request));
    }

    @Auth(role = {UserRole.MASTER, UserRole.MANAGER})
    @DeleteMapping("/{companyId}")
    public ResponseEntity<Void> deleteCompany(@PathVariable UUID companyId) {
        companyService.deleteCompany(companyId);
        return ResponseEntity.ok().build();
    }

}
