package com.thereisnouserwebsite.service;

import com.thereisnouserwebsite.dao.StudentDao;
import com.thereisnouserwebsite.exception.InvalidInputNumberException;

import java.util.List;

public class StudentService {

    private static final StudentService INSTANCE = new StudentService();
    private static final StudentDao dao = StudentDao.getInstance();

    private StudentService() {
    }

    public static StudentService getInstance() {
        return INSTANCE;
    }

    public List<String> getAllStudents() {
        return dao.getAllStudents();
    }

    public void createStudent(final String name,
                              final String surname,
                              final String patronymic,
                              final String age,
                              final String groupId) throws InvalidInputNumberException {
        final int ageToInt = parseStringToIntOrThrowException(age);
        final int groupIdToInt = parseStringToIntOrThrowException(groupId);

        dao.createStudent(name, surname, patronymic, ageToInt, groupIdToInt);
    }

    public void deleteStudentById(final String id) throws InvalidInputNumberException {
        final int idToInt = parseStringToIntOrThrowException(id);
        dao.deleteStudentById(idToInt);
    }

    private static int parseStringToIntOrThrowException(final String input) throws InvalidInputNumberException {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new InvalidInputNumberException("\n!!! '" + input + "' value is invalid for a number field !!!");
        }
    }
}
