package org.swissre;

import java.util.Map;

/**
 * This interface defines the core functionalities of an employee.
 *
 * @author tarun
 * @version 1.0
 */
public interface EmployeeProcessor {

    double UPPER_THRESHOLD = 1.5;
    double LOWER_THRESHOLD = 1.2;
    int MAX_REPORTING_MANAGERS = 4;

    boolean addEmployee(Employee emp);

    Employee findEmployee(String id);

    Map<String, Double> managersEarningMore();

    Map<String, Double> managersEarningLess();

    Map<String, Integer> getEmployeesWithLongReportingLines();
}
