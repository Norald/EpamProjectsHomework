package com.homework.epam.dao;

import com.homework.epam.entity.SubjectExam;
import com.homework.epam.entity.User;
import com.homework.epam.entity.UserResult;

import java.sql.Date;
import java.util.List;

public interface UserResultDao {
    List<UserResult> getAll();
    UserResult create(User user, SubjectExam subjectExam, int result, Date date);
    void save(UserResult userResult);
    List<UserResult> getAllByUserId(Integer userId);
    List<UserResult> getAllBySubjectExamId(Integer subjectExamId);
    List<UserResult> getAllByUserIdAndBySubjectExamId(Integer userId, Integer subjectExamId);
    void delete(Integer userId, Integer subjectExamId);
    void delete(Integer userId);

}
