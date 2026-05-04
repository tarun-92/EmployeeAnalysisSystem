# Employee Analysis System

This project was developed to carry out basic analysis on a company's organizational structure. The system takes in a list of employees in the csv format. Using this information, an in-memory chart of the organization is developed based on Tree structure. The Employee object becomes a node, and based on the managerId, they are connected. Internally, the system stores the subordinates and the manager for each employee for easy traversal. 

We currently have two goals:
1. Compare the salary of the managers and subordinates. A good manager earns between 20 % and 50% more than the average of their subordinates. We need to filter out managers who earn more and less than this policy.
2. The company also wants to avoid long reporting lines. We need to flag an employee if he has more than 4 managers between themselves and the CEO.

The project is developed using Java. 

## Input format
Below is the sample entry format for input data. The first line contains the column names. The first employee is the CEO and he has no reporting manager. All the other employees need to have a managerId.

- `Id,firstName,lastName,salary,managerId`
- `100,John,Jacob,100000,`
- `200,Martin,Michael,75000,100`

## Steps to run
1. Please pull this repository, and switch to master branch.
2. Complile and build the application using maven by following the below steps.
`mvn clean`
`mvn compile`
`mvn install`

3. Run using java command by specifying any custom input file.
`java -jar .\target\employee-1.0-SNAPSHOT.jar  <Path-to-input-data>\input-data.csv`

4. Run the following command for the default test cases.
`mvn test`

## Assumptions
- There are restrictions on the input data file - adhere to that format. Missing managerId leads to the entire row getting dropped.
- Given the max limit of 1000 rows, we walk through the entire organization structure for our analysis. We can optimize further by pre-computing certain parameters, or combining traversal to generate multiple outputs. To be added in future, if needed.
