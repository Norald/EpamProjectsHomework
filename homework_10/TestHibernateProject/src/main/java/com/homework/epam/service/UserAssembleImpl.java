package com.homework.epam.service;

import com.homework.epam.dao.SubjectExamDao;
import com.homework.epam.dao.UserDao;
import com.homework.epam.dto.UserDto;
import com.homework.epam.dto.UserResultDto;
import com.homework.epam.entity.SubjectExam;
import com.homework.epam.entity.User;
import com.homework.epam.entity.UserResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserAssembleImpl implements UserAssembler {

    @Autowired
    SubjectExamDao subjectExamDao;

    @Autowired
    UserDao userDao;

    @Override
    public User assemble(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setPassword(dto.getPassword());
        user.setIdn(dto.getIdn());
        user.setEmail(dto.getEmail());
        user.setUserRoleId(dto.getUserRoleId());
        user.setBlocked(dto.isBlocked());
        Set<UserResultDto> listResultsDto= dto.getUserResults();
        Set<UserResult> listResults = new HashSet<>(listResultsDto.size());
        for(UserResultDto userResultDto : listResultsDto){
            listResults.add(assemble(userResultDto));
        }
        user.setUserResults(listResults);
        return user;
    }

    @Override
    public UserDto assemble(User entity) {
        UserDto userDto = new UserDto();
        userDto.setId(entity.getId());
        userDto.setEmail(entity.getEmail());
        userDto.setPassword(entity.getPassword());
        userDto.setBlocked(entity.isBlocked());
        userDto.setIdn(entity.getIdn());
        userDto.setUserRoleId(entity.getUserRoleId());
        Set<UserResult> listResults= entity.getUserResults();
        Set<UserResultDto> userResultDtoList = new HashSet<>(listResults.size());
        for(UserResult userResult : listResults){
            userResultDtoList.add(assemble(userResult));
        }
        userDto.setUserResults(userResultDtoList);
        return userDto;
    }

    public UserResult assemble(UserResultDto dto) {
        UserResult userResult = new UserResult();
        userResult.setDateOfExam(dto.getDateOfExam());
        userResult.setResult(dto.getSubjectExamId());

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setBlocked(dto.isBlocked());
        user.setIdn(dto.getIdn());
        user.setUserRoleId(dto.getUserRoleId());

        SubjectExam subjectExam = new SubjectExam();
        subjectExam.setName(dto.getSe_name());
        subjectExam.setDescription(dto.getSe_description());
        subjectExam.setName_ua(dto.getSe_name_ua());
        subjectExam.setDescription_ua(dto.getSe_description_ua());

        userResult.setUser(user);
        userResult.setSubjectExam(subjectExam);
        return userResult;
    }

    public UserResultDto assemble(UserResult userResult) {
        UserResultDto userResultDto = new UserResultDto();
        userResultDto.setUserId(userResult.getUser().getId());
        userResultDto.setSubjectExamId(userResult.getSubjectExam().getId());
        userResultDto.setResult(userResult.getResult());
        userResultDto.setDateOfExam(userResult.getDateOfExam());
        return userResultDto;
    }
}
