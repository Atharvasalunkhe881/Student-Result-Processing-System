
import java.io.*;
import java.util.*;

class Student {

    int rollNo;
    String name;
    int[] marks = new int[5];
    int total;
    double average;
    String grade;

    Student(int rollNo, String name, int[] marks) {
        this.rollNo = rollNo;
        this.name = name;
        this.marks = marks;
        calculateResult();
    }

    private void calculateResult() {
        total = 0;
        boolean subjectFail = false;

        for (int m : marks) {
            total += m;
            if (m < 40) {
                subjectFail = true;
            }
        }

        average = total / 5.0;

        if (subjectFail) {
            grade = "F";
        } else if (average >= 90) {
            grade = "A";
        } else if (average >= 75) {
            grade = "B";
        } else if (average >= 60) {
            grade = "C";
        } else if (average >= 50) {
            grade = "D";
        } else {
            grade = "F";
        }
    }

    void display() {
        System.out.println(rollNo + " " + name
                + " Total: " + total
                + " Avg: " + average
                + " Grade: " + grade);
    }

    void display(String message) {   // Method Overloading
        System.out.println(message);
        display();
    }

    boolean isFailed() {
        return grade.equals("F");
    }
}

public class StudentResultSystem {

    static final String FILE_NAME = "students.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Add Student");
            System.out.println("2. Display All Students");
            System.out.println("3. Display Failed Students");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            int choice;
try {
    choice = sc.nextInt();
} catch (Exception e) {
    System.out.println("Invalid input! Please enter number only.");
    sc.nextLine();
    continue;
}


            switch (choice) {
                case 1:
                    addStudent(sc);
                    break;
                case 2:
                    readStudents(false);
                    break;
                case 3:
                    readStudents(true);
                    break;
                case 4:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    static void addStudent(Scanner sc) {
        try {
            System.out.print("Roll No: ");
            int roll = sc.nextInt();
            sc.nextLine();

            System.out.print("Name: ");
            String name = sc.nextLine();

            int[] marks = new int[5];
            for (int i = 0; i < 5; i++) {
                System.out.print("Mark " + (i + 1) + ": ");
                marks[i] = sc.nextInt();
            }

            Student s = new Student(roll, name, marks);

            FileWriter fw = new FileWriter(FILE_NAME, true);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(roll + "," + name + ","
                    + marks[0] + "," + marks[1] + ","
                    + marks[2] + "," + marks[3] + ","
                    + marks[4] + "," + s.total + ","
                    + s.average + "," + s.grade);
            bw.newLine();
            bw.close();

            System.out.println("Student saved successfully!");

        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }

    static void readStudents(boolean onlyFailed) {
        try {
            FileReader fr = new FileReader(FILE_NAME);
            BufferedReader br = new BufferedReader(fr);

            String line;
            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");
                String grade = data[9];

                if (!onlyFailed || grade.equals("F")) {
                    System.out.println("\nRoll No: " + data[0]);
                    System.out.println("Name: " + data[1]);
                    System.out.println("Total: " + data[7]);
                    System.out.println("Average: " + data[8]);
                    System.out.println("Grade: " + data[9]);
                    System.out.println("---------------------------");
                }
            }

            br.close();

        } catch (Exception e) {
            System.out.println("No records found.");
        }
    }
}
