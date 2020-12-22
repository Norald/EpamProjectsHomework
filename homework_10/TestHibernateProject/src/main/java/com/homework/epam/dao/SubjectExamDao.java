package com.homework.epam.dao;

import com.homework.epam.entity.SubjectExam;
import com.homework.epam.entity.User;

import java.util.List;

public interface SubjectExamDao {
    SubjectExam create(String name, String description, String name_ua, String description_ua);
    void save(SubjectExam subjectExam);
    SubjectExam get(Integer subjectExamId);
    void delete(Integer subjectExamId);
    List<SubjectExam> getAll();
}
