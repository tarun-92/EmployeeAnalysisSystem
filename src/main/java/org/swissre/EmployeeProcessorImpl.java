package org.swissre;

import java.util.*;

/**
 * This class implements the EmployeeProcessor interface.
 * It provides the logic for all employee related operations like adding an employee, finding an employee,
 * retrieving managers earning more/less and employees with long reporting lines.
 *
 * @author tarun
 * @version 1.0
 */
public class EmployeeProcessorImpl implements EmployeeProcessor {

    private Employee ceo;

    public EmployeeProcessorImpl() {
        this.ceo = null;
    }

    /**
     * Adds the employee to the organization structure.
     * First employee is treated as CEO.
     * Other employees with no manager / unknown managers are rejected
     *
     * @param emp Employee object
     * @return true if employee was added to the org structure, false otherwise
     */
    public boolean addEmployee(Employee emp) {
        if (ceo == null) {
            ceo = emp;
            return true;
        } else {
            String managerId = emp.getManagerId();
            Employee manager = findEmployee(managerId);
            if (manager == null) {
                System.out.println("Manager (" + managerId + ") of given employee " + emp.getId() + " not found!");
                return false;
            }
            manager.getSubordinates().add(emp);
        }
        return true;
    }

    /**
     * Finds the manager using the identifier
     *
     * @param id Identifier of the manager
     * @return Employee object of the manager
     */
    public Employee findEmployee(String id) {
        if (ceo == null) {
            return null;
        }

        Queue<Employee> queue = new LinkedList<>();
        queue.add(ceo);

        while (!queue.isEmpty()) {
            int numSubordinates = queue.size();

            while (numSubordinates > 0) {
                Employee curr = queue.poll();
                if (curr != null) {
                    if (curr.getId().equals(id)) {
                        return curr;
                    }
                    queue.addAll(curr.getSubordinates());
                }
                numSubordinates--;
            }
        }

        return null;
    }

    /**
     * Walks through the entire organization, and finds managers earning more than 50 percent
     * of the average of their subordinates
     * @return Map of manager ID and how much they earn above the company policy
     */
    public Map<String, Double> managersEarningMore() {
        if (ceo == null) {
            System.out.println("Error: Employee tree empty!");
            return null;
        }

        Map<String, Double> result = new HashMap<>();
        Queue<Employee> queue = new LinkedList<>();
        queue.add(ceo);

        while (!queue.isEmpty()) {
            Employee curr = queue.poll();
            List<Employee> subordinates = curr.getSubordinates();
            if (!subordinates.isEmpty()) {
                double averageSalaryOfSubordinates = subordinates.
                        stream().
                        mapToInt(Employee::getSalary).
                        average().
                        orElse(0);
                double upperLimit = UPPER_THRESHOLD * averageSalaryOfSubordinates;

                if (curr.getSalary() > upperLimit) {
                    result.put(curr.getId(), curr.getSalary() - upperLimit);
                }
            }
            queue.addAll(subordinates);
        }
        return result;
    }

    /**
     * Walks through the organization, and finds employees earning less than 20 percent
     * of the average of their subordinates
     * @return Map of manager ID and how much they earn less than the company policy
     */
    public Map<String, Double> managersEarningLess() {
        if (ceo == null) {
            System.out.println("Error: Employee tree empty!");
            return null;
        }

        Map<String, Double> result = new HashMap<>();
        Queue<Employee> queue = new LinkedList<>();
        queue.add(ceo);

        while (!queue.isEmpty()) {
            Employee curr = queue.poll();
            List<Employee> subordinates = curr.getSubordinates();
            if (!subordinates.isEmpty()) {
                double averageSalaryOfSubordinates = subordinates.
                        stream().
                        mapToInt(Employee::getSalary).
                        average().
                        orElse(0);
                double lowerLimit = LOWER_THRESHOLD * averageSalaryOfSubordinates;

                if (curr.getSalary() < lowerLimit) {
                    result.put(curr.getId(), lowerLimit - curr.getSalary());
                }
            }
            queue.addAll(subordinates);
        }
        return result;
    }

    /**
     * Walks through organization, and finds employees with more than MAX_REPORTING_MANAGERS between
     * themselves and the CEO
     * @return Map of employee and number of managers between them and CEO
     */
    public Map<String, Integer> getEmployeesWithLongReportingLines() {
        if (ceo == null) {
            System.out.println("Error: Employee tree empty!");
            return null;
        }

        Map<String, Integer> employeesWithLongReportingLine = new HashMap<>();
        Queue<Employee> queue = new LinkedList<>();
        queue.add(ceo);
        int managersInBetween = -1;

        while (!queue.isEmpty()) {
            int numSubordinates = queue.size();

            while (numSubordinates > 0) {
                Employee curr = queue.poll();
                if (curr != null) {
                    if (managersInBetween > MAX_REPORTING_MANAGERS) {
                        employeesWithLongReportingLine.put(curr.getId(), managersInBetween);
                    }
                    queue.addAll(curr.getSubordinates());
                }
                numSubordinates--;
            }
            managersInBetween++;
        }
        return employeesWithLongReportingLine;
    }
}