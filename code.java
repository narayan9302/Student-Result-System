import java.io.*;
import java.util.*;

class Student {
    private int roll;
    private String name;
    private double marks;
    private char grade;

    public Student(int roll, String name, double marks) {
        this.roll = roll;
        this.name = name;
        this.marks = marks;
        calculateGrade();
    }

    private void calculateGrade() {
        if (marks >= 90) grade = 'A';
        else if (marks >= 75) grade = 'B';
        else if (marks >= 60) grade = 'C';
        else if (marks >= 50) grade = 'D';
        else grade = 'F';
    }

    public String toFileString() {
        return roll + "|" + name + "|" + marks + "|" + grade;
    }

    public static Student fromFileString(String line) {
        String[] parts = line.split("\\|");
        int roll = Integer.parseInt(parts[0]);
        String name = parts[1];
        double marks = Double.parseDouble(parts[2]);
        return new Student(roll, name, marks);
    }

    public void display() {
        System.out.println("Roll: " + roll +
                " | Name: " + name +
                " | Marks: " + marks +
                " | Grade: " + grade);
    }

    public int getRoll() {
        return roll;
    }
}

class ResultManager {
    private List<Student> students = new ArrayList<>();
    private final String fileName = "results.txt";

    public void load() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                students.add(Student.fromFileString(line));
            }
        } catch (IOException e) {
            System.out.println("No previous data found.");
        }
    }

    public void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (Student s : students) {
                bw.write(s.toFileString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving data.");
        }
    }

    public void addStudent() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Roll No: ");
        int roll = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Marks: ");
        double marks = sc.nextDouble();

        students.add(new Student(roll, name, marks));
        save();

        System.out.println("Student added successfully!");
    }

    public void viewAll() {
        if (students.isEmpty()) {
            System.out.println("No records found.");
            return;
        }

        for (Student s : students) {
            s.display();
        }
    }

    public void searchStudent() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Roll No: ");
        int roll = sc.nextInt();

        for (Student s : students) {
            if (s.getRoll() == roll) {
                s.display();
                return;
            }
        }
        System.out.println("Student not found.");
    }
}

public class Main {
    public static void main(String[] args) {
        ResultManager rm = new ResultManager();
        rm.load();

        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== Student Result System =====");
            System.out.println("1. Add Student");
            System.out.println("2. View All Results");
            System.out.println("3. Search Student");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();

            switch (choice) {
                case 1 -> rm.addStudent();
                case 2 -> rm.viewAll();
                case 3 -> rm.searchStudent();
                case 4 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice");
            }

        } while (choice != 4);
    }
}
