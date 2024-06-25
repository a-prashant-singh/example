package com.hibernate;

import java.util.List;
import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class App {

    public static void main(String[] args) {
        Configuration con = new Configuration().configure();
        try (SessionFactory sf = con.buildSessionFactory();
             Session session = sf.openSession()) {
                System.out.println("Changes from main branch.");
            Scanner sc = new Scanner(System.in);
            String option;

            do {
                System.out.println("Select Operations:- ");
                System.out.println("1. Insert Details");
                System.out.println("2. Update Details");
                System.out.println("3. Delete Details");
                System.out.println("4. View Details");

                System.out.println("Enter your choice");
                int choice = sc.nextInt();
                sc.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        insertStudent(session, sc);
                        break;
                    case 2:
                        updateStudent(session, sc);
                        break;
                    case 3:
                        deleteStudent(session, sc);
                        break;
                    case 4:
                        viewStudents(session);
                        break;
                    default:
                        System.out.println("Enter a valid choice.");
                }

                System.out.println("Do you want to continue (Yes/no):-");
                option = sc.nextLine().toLowerCase();
            } while (option.equals("yes"));
        }
    }

    private static void insertStudent(Session session, Scanner sc) {
        Transaction tx = session.beginTransaction();
        Student s = new Student();
        System.out.println("Enter Name of the student:");
        s.setName(sc.nextLine());
        System.out.println("Enter College Name of the student:");
        s.setCllg(sc.nextLine());
        session.save(s);
        tx.commit();
        System.out.println("Details Inserted Successfully");
    }

    private static void updateStudent(Session session, Scanner sc) {
        Transaction tx = session.beginTransaction();
        System.out.println("Enter Id of the Student:");
        int id = sc.nextInt();
        sc.nextLine(); // Consume the newline character
        Student s = session.get(Student.class, id);
        if (s != null) {
            System.out.println("Enter Name of the student:");
            s.setName(sc.nextLine());
            System.out.println("Enter College Name of the student:");
            s.setCllg(sc.nextLine());
            session.update(s);
            tx.commit();
            System.out.println("Details Updated Successfully");
        } else {
            System.out.println("Student with ID " + id + " not found.");
        }
    }

    private static void deleteStudent(Session session, Scanner sc) {
        Transaction tx = session.beginTransaction();
        System.out.println("Enter Id of the Student:");
        int id = sc.nextInt();
        sc.nextLine(); // Consume the newline character
        Student s = session.get(Student.class, id);
        if (s != null) {
            session.delete(s);
            tx.commit();
            System.out.println("Details Deleted Successfully");
        } else {
            System.out.println("Student with ID " + id + " not found.");
        }
    }

    private static void viewStudents(Session session) {
        Transaction tx = session.beginTransaction();
        Query<Student> q = session.createQuery("from Student", Student.class);
        List<Student> students = q.list();
        for (Student st : students) {
            System.out.println("Id: " + st.getId() + "  Name : " + st.getName() + "  College : " + st.getCllg());
        }
        tx.commit();
    }
}
