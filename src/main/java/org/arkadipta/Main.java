package org.arkadipta;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void startApp(){

        Configuration cfg = new Configuration().configure().addAnnotatedClass(Note.class).addAnnotatedClass(Users.class);
        SessionFactory sf = cfg.buildSessionFactory();
        Session session = sf.openSession();

        Scanner scanner = new Scanner(System.in);

        boolean isRunning = true;

        while (isRunning) {
            System.out.println("\nWelcome to Notable CLI !");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            scanner.nextLine();

            switch (choice) {
                case 1:
                    register(session);
                    break;
                case 2:
                    login(session);
                    break;
                case 3: {
                    isRunning = false;
                    System.out.println("Exiting... Thank you for using CLI Notepad!");
                    break;
                }
                default:
                    System.out.println("Invalid choice!");
            }
        }
        session.close();
        sf.close();

    }
    public static void grantAccess(Session session, String loggedInUserName) {

        Scanner scanner = new Scanner(System.in);

        boolean isRunning = true;

        while (isRunning) {
            System.out.println("1. Create Note");
            System.out.println("2. View Notes");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            scanner.nextLine(); // Consume the newline character left by nextInt()

            switch (choice) {
                case 1:
                    createNote(session, loggedInUserName);
                    break;
                case 2:
                    viewNotes(session, loggedInUserName);
                    break;
                case 3: {
                    isRunning = false;
//                    System.out.println("Exiting... Thank you for using CLI Notepad!");
                    break;
                }
                default:
                    System.out.println("Invalid choice! Please enter a number between 1-3.");
            }
        }
    }

    public static void register(Session session)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your name: ");
        String name = scanner.nextLine();
        System.out.println("Enter your username: ");
        String username = scanner.nextLine();
        System.out.println("Enter your password: ");
        String password = scanner.nextLine();

        Users user_data = new Users();
        user_data.setUsername(username);
        user_data.setPassword(password);
        user_data.setName(name);

        Transaction tx = session.beginTransaction();
        session.persist(user_data);
        tx.commit();
    }

    public static void login(Session session) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username: ");
        String username = scanner.nextLine();
        System.out.println("Enter your password: ");
        String password = scanner.nextLine();

        Users user = session.get(Users.class,username);

        if (user != null && user.getPassword().equals(password)) {
            grantAccess(session,username);
        } else {
            System.out.println("Invalid username or password! Please try again.");
        }

    }

    public static void createNote(Session session,String loggedInUserName) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter note title: ");
        String title = scanner.nextLine();
        System.out.println("Enter note content: ");
        String content = scanner.nextLine();

        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        note.setAuthor(loggedInUserName);
        System.out.println(loggedInUserName);

        Transaction tx = session.beginTransaction();
        session.persist(note);
        tx.commit();
    }

    public static void viewNotes(Session session, String loggedInUserName) {
        String hql = "FROM Note n WHERE n.author = :username";
        Query<Note> query = session.createQuery(hql, Note.class);
        query.setParameter("username", loggedInUserName);
        List<Note> notes = query.list();

        if (notes.isEmpty()) {
            System.out.println("No notes found.");
        } else {
            System.out.println("\nYour Notes:");
            for (Note note : notes) {
                System.out.println("NoteId: " + note.getId());
                System.out.println("Title: " + note.getTitle());
                System.out.println("Content: " + note.getContent());
                System.out.println("-----------------------------");
            }
        }

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Update Note");
            System.out.println("2. Delete Note");
            System.out.println("3. Exit");
            System.out.print("Enter an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    updateNote(session);
                    break;
                case 2:
                    deleteNote(session);
                    break;
                case 3:
                    System.out.println("Returning to main menu...");
                    return;  // Exits the method and goes back to the main menu
                default:
                    System.out.println("Invalid choice! Please enter a number between 1-3.");
            }
        }
    }



    public static void updateNote(Session session) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the note ID you want to update: ");
        int noteId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.println("Enter new title: ");
        String title = scanner.nextLine();
        System.out.println("Enter new content: ");
        String content = scanner.nextLine();

        // HQL Update Query
        String hql = "UPDATE Note n SET n.title = :title, n.content = :content WHERE n.id = :noteId";
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(hql);
        query.setParameter("title", title);
        query.setParameter("content", content);
        query.setParameter("noteId", noteId);

        int rowsAffected = query.executeUpdate();
        tx.commit();

        if (rowsAffected > 0) {
            System.out.println("Note updated successfully!");
        } else {
            System.out.println("No note found with the given ID.");
        }
    }


    public static void deleteNote(Session session) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the note ID you want to delete: ");
        int noteId = scanner.nextInt();

        // Begin transaction
        Transaction tx = session.beginTransaction();

        // Fetch the note by ID
        Note note = session.get(Note.class, noteId);

        if (note != null) {
                session.remove(note);
                tx.commit();
                System.out.println("Note deleted successfully!");
        } else {
            System.out.println("Error: No note found with the given ID.");
            tx.rollback();
        }
    }


    public static void main(String[] args) {
        startApp();
    }
}
