package com.homework.epam.dao;

import com.homework.epam.entity.SubjectExam;
import com.homework.epam.entity.User;

import java.util.List;

public interface SubjectExamDao {
    void save(SubjectExam subjectExam);
    SubjectExam get(Integer subjectExamId);
    void delete(Integer subjectExamId);
    List<SubjectExam> getAll();
}
