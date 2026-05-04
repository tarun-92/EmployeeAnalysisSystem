import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.swissre.Employee;
import org.swissre.EmployeeProcessorImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeAnalyzerTest {

    @Test
    @DisplayName("Test create employees")
    public void testEmployeeCreationSuccess() {
        System.out.println("1. Test to create employees");
        EmployeeProcessorImpl employeeProcessor = new EmployeeProcessorImpl();

        Employee emp = new Employee("100", "Anne", "Gibbs", 100000, null, new ArrayList<>());
        boolean result = employeeProcessor.addEmployee(emp);
        assertTrue(result);

        Employee emp1 = new Employee("200", "Carlo", "Warner", 80000, "100", new ArrayList<>());
        result = employeeProcessor.addEmployee(emp1);
        assertTrue(result);
    }

    @Test
    @DisplayName("Fail test creating employees without manager or non-existent manager")
    public void testEmployeeCreationFailure() {
        System.out.println("2. Test to create employees without manager or invalid manager ID");
        EmployeeProcessorImpl employeeProcessor = new EmployeeProcessorImpl();

        Employee emp = new Employee("100", "Anne", "Gibbs", 100000, null, new ArrayList<>());
        boolean result = employeeProcessor.addEmployee(emp);
        assertTrue(result);

        Employee emp1 = new Employee("200", "Carlo", "Warner", 80000, null, new ArrayList<>());
        result = employeeProcessor.addEmployee(emp1);
        assertFalse(result);

        Employee emp2 = new Employee("200", "Carlo", "Warner", 80000, "random", new ArrayList<>());
        result = employeeProcessor.addEmployee(emp2);
        assertFalse(result);
    }

    @Test
    @DisplayName("Test with correct salary ratio")
    public void testCorrectSalaryForManager() {
        System.out.println("3. Test with correct salary ratio and ensure no employees are flagged");
        EmployeeProcessorImpl employeeProcessor = new EmployeeProcessorImpl();

        Employee emp = new Employee("100", "Anne", "Gibbs", 110000, null, new ArrayList<>());
        employeeProcessor.addEmployee(emp);
        Employee emp1 = new Employee("200", "James", "Franklin", 85000, "100", new ArrayList<>());
        employeeProcessor.addEmployee(emp1);
        Employee emp2 = new Employee("300", "Ernest", "Newman", 65000, "200", new ArrayList<>());
        employeeProcessor.addEmployee(emp2);
        Employee emp3 = new Employee("400", "Damian", "Bright", 50000, "300", new ArrayList<>());
        employeeProcessor.addEmployee(emp3);

        Map<String, Double> employeesEarningLess = employeeProcessor.managersEarningLess();
        assertTrue(employeesEarningLess.isEmpty());

        Map<String, Double> employeesEarningMore = employeeProcessor.managersEarningMore();
        assertTrue(employeesEarningMore.isEmpty());
    }

    @Test
    @DisplayName("Test to find manager's earning more")
    public void testManagerEarningMore() {
        System.out.println("4. Test to filter manager's earning more than the company policy");
        EmployeeProcessorImpl employeeProcessor = new EmployeeProcessorImpl();

        Employee emp = new Employee("100", "Anne", "Gibbs", 150000, null, new ArrayList<>());
        employeeProcessor.addEmployee(emp);
        Employee emp1 = new Employee("200", "James", "Franklin", 85000, "100", new ArrayList<>());
        employeeProcessor.addEmployee(emp1);
        Employee emp2 = new Employee("300", "Ernest", "Newman", 80000, "200", new ArrayList<>());
        employeeProcessor.addEmployee(emp2);
        Employee emp3 = new Employee("400", "Damian", "Bright", 50000, "300", new ArrayList<>());
        employeeProcessor.addEmployee(emp3);

        Map<String, Double> employeesEarningMore = employeeProcessor.managersEarningMore();

        Map<String, Double> expectedEmployeesEarningMore = new HashMap<>();
        expectedEmployeesEarningMore.put("100", 22500.0);
        expectedEmployeesEarningMore.put("300", 5000.0);

        assertEquals(expectedEmployeesEarningMore, employeesEarningMore);
    }

    @Test
    @DisplayName("Test to find manager's earning less")
    public void testManagerEarningLess() {
        System.out.println("5. Test to filter manager's earning less than the company policy");
        EmployeeProcessorImpl employeeProcessor = new EmployeeProcessorImpl();

        Employee emp = new Employee("100", "Anne", "Gibbs", 110000, null, new ArrayList<>());
        employeeProcessor.addEmployee(emp);
        Employee emp1 = new Employee("200", "James", "Franklin", 60000, "100", new ArrayList<>());
        employeeProcessor.addEmployee(emp1);
        Employee emp2 = new Employee("300", "Ernest", "Newman", 55000, "200", new ArrayList<>());
        employeeProcessor.addEmployee(emp2);
        Employee emp3 = new Employee("400", "Damian", "Bright", 50000, "300", new ArrayList<>());
        employeeProcessor.addEmployee(emp3);

        Map<String, Double> employeesEarningLess = employeeProcessor.managersEarningLess();

        Map<String, Double> expectedEmployeesEarningLess = new HashMap<>();
        expectedEmployeesEarningLess.put("200", 6000.0);
        expectedEmployeesEarningLess.put("300", 5000.0);

        assertEquals(expectedEmployeesEarningLess, employeesEarningLess);
    }

    @Test
    @DisplayName("Test with no long reporting lines")
    public void testNoLongReportingLines() {
        System.out.println("6. Test to find no long reporting lines");
        EmployeeProcessorImpl employeeProcessor = new EmployeeProcessorImpl();

        Employee emp1 = new Employee("100", "Anne", "Gibbs", 110000, null, new ArrayList<>());
        employeeProcessor.addEmployee(emp1);

        Employee emp21 = new Employee("200", "James", "Franklin", 85000, "100", new ArrayList<>());
        employeeProcessor.addEmployee(emp21);
        Employee emp22 = new Employee("201", "Duncan", "Lyons", 85000, "100", new ArrayList<>());
        employeeProcessor.addEmployee(emp22);
        Employee emp23 = new Employee("202", "Hayley", "Williams", 85000, "100", new ArrayList<>());
        employeeProcessor.addEmployee(emp23);

        Employee emp31 = new Employee("300", "Ernest", "Newman", 65000, "200", new ArrayList<>());
        employeeProcessor.addEmployee(emp31);
        Employee emp32 = new Employee("301", "Victor", "Vang", 65000, "200", new ArrayList<>());
        employeeProcessor.addEmployee(emp32);
        Employee emp33 = new Employee("302", "Shawm", "Michael", 65000, "201", new ArrayList<>());
        employeeProcessor.addEmployee(emp33);
        Employee emp34 = new Employee("303", "Lucius", "Malfoy", 65000, "201", new ArrayList<>());
        employeeProcessor.addEmployee(emp34);
        Employee emp35 = new Employee("304", "Jon", "Snow", 65000, "202", new ArrayList<>());
        employeeProcessor.addEmployee(emp35);
        Employee emp36 = new Employee("305", "Ariel", "Winters", 65000, "202", new ArrayList<>());
        employeeProcessor.addEmployee(emp36);


        Employee emp41 = new Employee("400", "Damian", "Bright", 50000, "300", new ArrayList<>());
        employeeProcessor.addEmployee(emp41);
        Employee emp42 = new Employee("401", "James", "Bond", 50000, "301", new ArrayList<>());
        employeeProcessor.addEmployee(emp42);
        Employee emp43 = new Employee("402", "Elizabeth", "Grey", 50000, "302", new ArrayList<>());
        employeeProcessor.addEmployee(emp43);
        Employee emp44 = new Employee("403", "Emily", "Blunt", 50000, "303", new ArrayList<>());
        employeeProcessor.addEmployee(emp44);
        Employee emp45 = new Employee("404", "Steven", "Jones", 50000, "304", new ArrayList<>());
        employeeProcessor.addEmployee(emp45);
        Employee emp46 = new Employee("405", "James", "Hope", 50000, "305", new ArrayList<>());
        employeeProcessor.addEmployee(emp46);

        Map<String, Integer> employeesWithLongReportingLines = employeeProcessor.getEmployeesWithLongReportingLines();
        assertTrue(employeesWithLongReportingLines.isEmpty());
    }

    @Test
    @DisplayName("Test with long reporting lines")
    public void testLongReportingLines() {
        System.out.println("7. Test to find long reporting lines");
        EmployeeProcessorImpl employeeProcessor = new EmployeeProcessorImpl();

        Employee emp1 = new Employee("100", "Anne", "Gibbs", 110000, null, new ArrayList<>());
        employeeProcessor.addEmployee(emp1);
        Employee emp2 = new Employee("200", "James", "Franklin", 85000, "100", new ArrayList<>());
        employeeProcessor.addEmployee(emp2);
        Employee emp3 = new Employee("300", "Duncan", "Lyons", 85000, "200", new ArrayList<>());
        employeeProcessor.addEmployee(emp3);
        Employee emp4 = new Employee("400", "Hayley", "Williams", 85000, "300", new ArrayList<>());
        employeeProcessor.addEmployee(emp4);
        Employee emp5 = new Employee("500", "Ernest", "Newman", 65000, "400", new ArrayList<>());
        employeeProcessor.addEmployee(emp5);
        Employee emp6 = new Employee("600", "Victor", "Vang", 65000, "500", new ArrayList<>());
        employeeProcessor.addEmployee(emp6);
        Employee emp7 = new Employee("700", "Shawm", "Michael", 65000, "600", new ArrayList<>());
        employeeProcessor.addEmployee(emp7);
        Employee emp8 = new Employee("800", "Lucius", "Malfoy", 65000, "700", new ArrayList<>());
        employeeProcessor.addEmployee(emp8);

        Map<String, Integer> employeesWithLongReportingLines = employeeProcessor.getEmployeesWithLongReportingLines();

        Map<String, Integer> expectedEmployeesWithLongReportingLines = new HashMap<>();
        expectedEmployeesWithLongReportingLines.put("700", 5);
        expectedEmployeesWithLongReportingLines.put("800", 6);

        assertEquals(expectedEmployeesWithLongReportingLines, employeesWithLongReportingLines);
    }
}
