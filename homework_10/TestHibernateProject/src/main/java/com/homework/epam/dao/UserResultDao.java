package com.homework.epam.dao;

import com.homework.epam.entity.UserResult;

import java.util.List;

public interface UserResultDao {
    List<UserResult> getAll();
    void save(UserResult userResult);
    List<UserResult> getAllByUserId(Integer userId);
    List<UserResult> getAllBySubjectExamId(Integer subjectExamId);
    List<UserResult> getAllByUserIdAndBySubjectExamId(Integer userId, Integer subjectExamId);
    void delete(Integer userId, Integer subjectExamId);
    void delete(Integer userId);

}
