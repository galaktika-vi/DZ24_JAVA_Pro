import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;

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

    public void addEvent(Event event) {
        events.add(event);
    }

    public void printEvents() {
        events.sort(Comparator.comparing(Event::getStart));
        for (Event event : events) {
            System.out.println(event);
        }
    }

    public void printEventsInReverseOrder() {
        events.sort(Comparator.comparing(Event::getStart).reversed());
        for (Event event : events) {
            System.out.println(event);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        do {
            System.out.println("\nВыберите задание для выполнения:");
            System.out.println("1. Создание и форматирование даты");
            System.out.println("2. Работа с текущим временем");
            System.out.println("3. Информация о текущей дате");
            System.out.println("4. Преобразование строки в дату и добавление дней");
            System.out.println("5. Преобразование строки в дату и добавление минут");
            System.out.println("6. Работа с календарем событий");
            System.out.println("0. Выход");
            System.out.print("Введите номер задания: ");

            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Пожалуйста, введите целое число.");
                scanner.next();
                continue;
            }

            switch (choice) {
                case 1:
                    performTask1();
                    break;
                case 2:
                    performTask2();
                    break;
                case 3:
                    performTask3();
                    break;
                case 4:
                    performTask4();
                    break;
                case 5:
                    performTask5();
                    break;
                case 6:
                    performTask6();
                    break;
            }
        } while (choice != 0);

        scanner.close();
    }



    private static void performTask1() {
        LocalDate date = LocalDate.of(2020, 1, 10);

        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("Формат dd/MM/yyyy: " + date.format(formatter1));

        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        System.out.println("Формат dd-MM-yyyy: " + date.format(formatter2));

        DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("yyyy MMMM dd");
        System.out.println("Формат yyyy MMMM dd: " + date.format(formatter3));
    }

    private static void performTask2() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("Текущее время: " + now);

        LocalDateTime tenHoursAgo = now.minusHours(10);
        System.out.println("Время 10 часов назад: " + tenHoursAgo);

        ZonedDateTime zonedNow = ZonedDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss Z");
        System.out.println("Текущее время с сдвигом: " + zonedNow.format(formatter));
    }

    private static void performTask3() {
        LocalDate localDate = LocalDate.now();
        System.out.println("LocalDate:");
        System.out.println("Текущий день недели: " + localDate.getDayOfWeek());
        System.out.println("Текущий день в году: " + localDate.getDayOfYear());
        System.out.println("Текущий месяц: " + localDate.getMonth());

        java.util.Calendar calendar = java.util.Calendar.getInstance();
        System.out.println("\nCalendar:");
        System.out.println("Текущий день недели: " + calendar.get(java.util.Calendar.DAY_OF_WEEK));
        System.out.println("Текущий день в году: " + calendar.get(java.util.Calendar.DAY_OF_YEAR));
        System.out.println("Текущая неделя в месяце: " + calendar.get(java.util.Calendar.WEEK_OF_MONTH));
        System.out.println("Текущий месяц: " + (calendar.get(java.util.Calendar.MONTH) + 1)); // +1 потому что январь = 0
    }

    private static void performTask4() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dateFromString = LocalDate.parse("10/01/2020", formatter);
        LocalDate tenDaysLater = dateFromString.plusDays(10);
        System.out.println("Дата после добавления 10 дней: " + tenDaysLater.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
    }

    private static void performTask5() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy HH:mm");
        LocalDateTime dateTimeFromString = LocalDateTime.parse("10 01 2020 10:00", formatter);
        LocalDateTime tenMinutesLater = dateTimeFromString.plusMinutes(10);
        System.out.println("Время после добавления 10 минут: " + tenMinutesLater.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
    }

    private static void performTask6() {
        EventCalendar myCalendar = new EventCalendar();
        myCalendar.addEvent(new Event(LocalDateTime.of(2020, 1, 10, 9, 30), LocalDateTime.of(2020, 1, 10, 10, 30), "Событие 1"));
        myCalendar.addEvent(new Event(LocalDateTime.of(2020, 1, 11, 11, 0), LocalDateTime.of(2020, 1, 11, 12, 0), "Событие 2"));
        myCalendar.addEvent(new Event(LocalDateTime.of(2020, 1, 12, 14, 0), LocalDateTime.of(2020, 1, 12, 15, 0), "Событие 3"));

        System.out.println("События по порядку:");
        myCalendar.printEvents();

        System.out.println("\nСобытия в обратном порядке:");
        myCalendar.printEventsInReverseOrder();
    }

}
