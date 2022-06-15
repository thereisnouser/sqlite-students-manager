package com.thereisnouserwebsite;

import com.thereisnouserwebsite.exception.InvalidInputNumberException;
import com.thereisnouserwebsite.service.StudentService;
import com.thereisnouserwebsite.util.ColumnKey;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentService service = StudentService.getInstance();

    public static void main(String[] args) {
        int operationNumber = 0;

        do {
            System.out.println("\n\n--> Supported operations: <--\n");
            System.out.println("1: Create a student");
            System.out.println("2: Delete a student");
            System.out.println("3: List all students");
            System.out.println("4: Exit");
            System.out.print("\nEnter a number of operation you want to execute: ");

            try {
                final String input = scanner.nextLine();
                operationNumber = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("\n!!! Operation number is invalid !!!");
                continue;
            }

            switch (operationNumber) {
                case 1:
                    createStudent();
                    break;

                case 2:
                    deleteStudentById();
                    break;


                case 3:
                    printAllStudents();
                    break;

                case 4:
                    break;

                default:
                    System.out.println("\n!!! There is no such number in the list !!!");
                    break;
            }
        } while (operationNumber != 4);
    }

    private static void printAllStudents() {
        final List<String> students = service.getAllStudents();

        System.out.println("\n--- BEGIN ---\n");
        for (String student : students) {
            System.out.println(student);
        }
        System.out.println("\n--- END ---");
    }

    private static void createStudent() {
        String name;
        String surname;
        String patronymic;
        String age;
        String groupId;

        System.out.print("Enter " + ColumnKey.NAME_KEY + ": ");
        name = scanner.nextLine();
        System.out.print("Enter " + ColumnKey.SURNAME_KEY + ": ");
        surname = scanner.nextLine();
        System.out.print("Enter " + ColumnKey.PATRONYMIC_KEY + ": ");
        patronymic = scanner.nextLine();
        System.out.print("Enter " + ColumnKey.AGE_KEY + ": ");
        age = scanner.nextLine();
        System.out.print("Enter " + ColumnKey.GROUP_ID_KEY + ": ");
        groupId = scanner.nextLine();

        try {
            service.createStudent(name, surname, patronymic, age, groupId);
        } catch (InvalidInputNumberException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void deleteStudentById() {
        System.out.print("Enter an '" + ColumnKey.ID_KEY + "' of a student you want to delete: ");

        final String input = scanner.nextLine();
        try {
            service.deleteStudentById(input);
        } catch (InvalidInputNumberException e) {
            System.out.println(e.getMessage());
        }
    }
}
