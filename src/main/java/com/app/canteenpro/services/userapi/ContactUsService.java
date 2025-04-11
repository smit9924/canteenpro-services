package com.app.canteenpro.services.userapi;

import com.app.canteenpro.DataObjects.ContactUsSubjectUpsertDTO;
import com.app.canteenpro.DataObjects.ContactUsUpsertDto;
import com.app.canteenpro.database.repositories.ContactUsRepo;
import com.app.canteenpro.database.repositories.ContactUsSubjectRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ContactUsService {
    @Autowired
    private ContactUsRepo contactUsRepo;

    @Autowired
    private ContactUsSubjectRepo contactUsSubjectRepo;

    public void saveContactUsQuery(ContactUsUpsertDto contactUsUpsertDto) {

    }

    public List<ContactUsSubjectUpsertDTO> getAllSubjects() {
        List<ContactUsSubjectUpsertDTO> contactUsSubjects = contactUsSubjectRepo.findAll().stream().map((subject) -> {
            return ContactUsSubjectUpsertDTO.builder()
                    .guid(subject.getGuid())
                    .subject(subject.getSubject())
                    .build();
        }).toList();

        return contactUsSubjects;
    }
}
