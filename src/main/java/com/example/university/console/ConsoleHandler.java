package com.example.university.console;

import com.example.university.service.DepartmentService;
import com.example.university.service.LectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class ConsoleHandler {

    private final DepartmentService departmentService;
    private final LectorService lectorService;

    private final Map<String, Function<String, String>> commandMap = new HashMap<>();

    public void startConsole() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the University Console!");
        System.out.println("You can ask the following questions:");
        System.out.println("1. Who is head of department {department_name}");
        System.out.println("2. Show {department_name} statistics.");
        System.out.println("3. Show the average salary for the department {department_name}.");
        System.out.println("4. Show count of employee for {department_name}.");
        System.out.println("5. Global search by {template}.");

        initializeCommands();

        while (true) {
            System.out.print("Enter your question: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting the application. Goodbye!");
                break;
            }

            String response = processCommand(input);
            System.out.println(response);
        }
    }

    private void initializeCommands() {
        commandMap.put("Who is head of department",
                departmentService::getHeadOfDepartment);

        commandMap.put("Show",
                command -> {
                    if (command.endsWith("statistics.")) {
                        return departmentService.getStatistics(command.substring(0, command.length() - 11).trim());
                    }
                    if (command.startsWith("the average salary for the department") ) {
                        return departmentService.getAverageSalary(command.substring(37, command.length() - 1).trim());
                    }
                    if (command.startsWith("count of employee for")  ) {
                        return departmentService.getEmployeeCount(command.substring(21, command.length() - 1).trim());
                    }
                    return "Unknown command.";
                });

        commandMap.put("Global search by", command -> lectorService.globalSearch(command.substring(0, command.length() - 1).trim()));
    }

    private String processCommand(String input) {
        for (Map.Entry<String, Function<String, String>> entry : commandMap.entrySet()) {
            if (input.startsWith(entry.getKey())) {
                String argument = input.substring(entry.getKey().length()).trim();
                return entry.getValue().apply(argument);
            }
        }
        return "Unknown command. Please try again.";
    }
}