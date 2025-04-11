package com.app.canteenpro.contollers;

import com.app.canteenpro.DataObjects.ContactUsSubjectUpsertDTO;
import com.app.canteenpro.responses.ApiResponse;
import com.app.canteenpro.services.userapi.ContactUsService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/contactus")
public class ContactUsController {
    @Autowired
    private ContactUsService contactUsService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<?>> saveContactUsQuery() {
        ApiResponse<?> apiResponse = new ApiResponse<>(false, true, "", "");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/subjects")
    public ResponseEntity<ApiResponse<List<ContactUsSubjectUpsertDTO>>> getAllSubjects() {
        List<ContactUsSubjectUpsertDTO> subjects = contactUsService.getAllSubjects();
        ApiResponse<List<ContactUsSubjectUpsertDTO>> apiResponse = new ApiResponse<List<ContactUsSubjectUpsertDTO>>(subjects, true, "", "");
        return ResponseEntity.ok(apiResponse);
    }
}
