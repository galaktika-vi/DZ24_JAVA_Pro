import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

class EventOverlapException extends Exception {
    public EventOverlapException(String message) {
        super(message);
    }
}

class Event {
    LocalDateTime start;
    LocalDateTime end;
    String description;

    public Event(LocalDateTime start, LocalDateTime end, String description) {
        this.start = start;
        this.end = end;
        this.description = description;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "Событие{" +
                "начало=" + start +
                ", конец=" + end +
                ", описание='" + description + '\'' +
                '}';
    }
}

class EventCalendar {
    List<Event> events;

    public EventCalendar() {
        events = new ArrayList<>();
    }

    public void addEvent(Event newEvent) throws EventOverlapException {
        for (Event existingEvent : events) {
            if (isOverlapping(existingEvent, newEvent)) {
                throw new EventOverlapException("Событие " + newEvent.description + " не может быть добавлено, так как оно пересекается с существующим событием " + existingEvent.description + ".");
            }
        }
        events.add(newEvent);
    }

    private boolean isOverlapping(Event existingEvent, Event newEvent) {
        return !(newEvent.end.isBefore(existingEvent.start) || newEvent.start.isAfter(existingEvent.end));
    }

    public void printEvents() {
        events.sort(Comparator.comparing(Event::getStart));
        for (Event event : events) {
            System.out.println(event);
        }
    }
}

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static EventCalendar myCalendar = new EventCalendar();

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\nВыберите действие:");
            System.out.println("1. Добавить событие");
            System.out.println("2. Показать события");
            System.out.println("0. Выход");
            System.out.print("Введите номер действия: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addEventDialog();
                    break;
                case 2:
                    showEvents();
                    break;
            }
        } while (choice != 0);
    }

    private static void addEventDialog() {
        System.out.print("Введите описание события: ");
        scanner.nextLine(); // Очистка буфера
        String description = scanner.nextLine();

        System.out.print("Введите время начала события (формат yyyy-MM-dd HH:mm): ");
        String start = scanner.nextLine();
        LocalDateTime startTime = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        System.out.print("Введите время окончания события (формат yyyy-MM-dd HH:mm): ");
        String end = scanner.nextLine();
        LocalDateTime endTime = LocalDateTime.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        try {
            myCalendar.addEvent(new Event(startTime, endTime, description));
            System.out.println("Событие добавлено.");
        } catch (EventOverlapException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void showEvents() {
        System.out.println("Запланированные события:");
        myCalendar.printEvents();
    }
}
