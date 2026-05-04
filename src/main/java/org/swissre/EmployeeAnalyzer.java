package org.swissre;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * This is the entry point of our system. It demonstrates how to provide an input file, and perform operations like
 * finding managers earning more/less and having long reporting lines.
 * This uses the methods in the EmployeeProcessor interface.
 */
public class EmployeeAnalyzer {
    public static void main(String[] args) {

        // Check if input file is provided
        if (args.length < 1) {
            System.out.println("Error: Please provide input file with full-path.");
            return;
        }

        EmployeeProcessorImpl employeeProcessor = new EmployeeProcessorImpl();

        // Load input from file and populate employee information
        int readCount = 0;
        String line = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(args[0]));
            line = br.readLine();

            if (line == null || line.isEmpty()) {
                System.out.println("Employee data missing! \nPlease fix the input data in the file.");
                return;
            }

            System.out.println("Reading input from " + args[0] + "\n");
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String id = values[0];
                String firstName = values[1];
                String lastName = values[2];
                int salary = Integer.parseInt(values[3]);
                String managerId = null;
                if (values.length > 4) {
                    managerId = values[4];
                }

                Employee emp = new Employee(id, firstName, lastName, salary, managerId, new ArrayList<>());
                if (employeeProcessor.addEmployee(emp)) {
                    System.out.println("Added employee: " + emp);
                    readCount++;
                } else {
                    System.out.println("Error: Could not add employee(" + id + ") as managerId is null/non-existent");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\nRead " + readCount + " employees from input file!\n");

        // Analyze salary of employees to filter out managers earning more
        Map<String, Double> employeesEarningMore = employeeProcessor.managersEarningMore();
        System.out.println("\nManagers earning more:\n");
        if (employeesEarningMore != null && !employeesEarningMore.isEmpty()) {
            System.out.println("Manager ID   | Amount");
            employeesEarningMore.forEach((k, v) -> System.out.printf("%12s | %12s\n", k, v));
        } else {
            System.out.println("No employees earn more than the company policy");
        }

        // Analyze salary of employees to filter out managers earning less
        Map<String, Double> employeesEarningLess = employeeProcessor.managersEarningLess();
        System.out.println("\nManagers earning less:\n");
        if (employeesEarningLess != null && !employeesEarningLess.isEmpty()) {
            System.out.println("Manager ID   | Amount");
            employeesEarningLess.forEach((k, v) -> System.out.printf("%-12s | %-12s\n", k, v));
        } else {
            System.out.println("No employees earn less than the company policy");
        }

        // Analyze long reporting lines in the organization
        Map<String, Integer> employeesWithLongReportingLines = employeeProcessor.getEmployeesWithLongReportingLines();
        System.out.println("\nEmployees with long reporting lines:\n");
        if (employeesWithLongReportingLines != null && !employeesWithLongReportingLines.isEmpty()) {
            System.out.println("Employee ID  | No of managers");
            employeesWithLongReportingLines.forEach((k, v) -> System.out.printf("%-12s | %-12s\n", k, v));
        } else {
            System.out.println("No long reporting lines in the company");
        }
    }
}