package com.customerdeal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class MtBullerResort {

    private ArrayList<Accommodation> accommodations;
    private ArrayList<Customer> customers;
    private ArrayList<TravelBundle> bundles;

    public MtBullerResort() {
        accommodations = new ArrayList<>();
        customers = new ArrayList<>();
        bundles = new ArrayList<>();
    }

    public void populateAccommodations() {
        if (!accommodations.isEmpty()) return;

        accommodations.add(new HotelRoom(1, "Hotel Alpine 1", 120, true));
        accommodations.add(new HotelRoom(2, "Hotel Alpine 2", 140, true));
        accommodations.add(new LodgeRoom(3, "Lodge Snow 1", 90, true));
        accommodations.add(new LodgeRoom(4, "Lodge Snow 2", 100, true));
        accommodations.add(new ApartmentUnit(5, "Apartment Ice 1", 180, true));
        accommodations.add(new ApartmentUnit(6, "Apartment Ice 2", 200, true));
        accommodations.add(new HotelRoom(7, "Hotel Peak 1", 130, true));
        accommodations.add(new LodgeRoom(8, "Lodge Peak 1", 95, true));
        accommodations.add(new ApartmentUnit(9, "Apartment Summit 1", 210, true));
        accommodations.add(new HotelRoom(10, "Hotel Summit 1", 150, true));
    }

    public void populateCustomers() {
        if (!customers.isEmpty()) return;

        customers.add(new Customer(101, "Paarth", "paarth@email.com", SkiLevel.BEGINNER));
        customers.add(new Customer(102, "John", "john@email.com", SkiLevel.INTERMEDIATE));
        customers.add(new Customer(103, "Sarah", "sarah@email.com", SkiLevel.EXPERT));
    }

    public int generateCustomerId() {
        int highestId = 100;

        for (Customer c : customers) {
            if (c.getCustomerId() > highestId) {
                highestId = c.getCustomerId();
            }
        }

        return highestId + 1;
    }

    public int generateBundleId() {
        int highestId = 200;

        for (TravelBundle b : bundles) {
            if (b.getBundleId() > highestId) {
                highestId = b.getBundleId();
            }
        }

        return highestId + 1;
    }

    public String displayAllAccommodations() {
        String output = "";

        for (Accommodation a : accommodations) {
            output += a + "\n";
        }

        return output;
    }

    public String displayAvailableAccommodations() {
        String output = "";

        for (Accommodation a : accommodations) {
            if (a.isAvailable()) {
                output += a + "\n";
            }
        }

        if (output.equals("")) {
            return "No available accommodations found.";
        }

        return output;
    }

    public String listCustomers() {
        String output = "";

        for (Customer c : customers) {
            output += c + "\n";
        }

        return output;
    }

    public String listBundles() {
        String output = "";

        for (TravelBundle b : bundles) {
            output += b + "\n";
            output += "----------------------------\n";
        }

        if (output.equals("")) {
            return "No travel bundles found.";
        }

        return output;
    }

    public Customer findCustomerById(int id) {
        for (Customer c : customers) {
            if (c.getCustomerId() == id) {
                return c;
            }
        }
        return null;
    }

    public Accommodation findAccommodationById(int id) {
        for (Accommodation a : accommodations) {
            if (a.getid() == id) {
                return a;
            }
        }
        return null;
    }

    public TravelBundle findBundleById(int id) {
        for (TravelBundle b : bundles) {
            if (b.getBundleId() == id) {
                return b;
            }
        }
        return null;
    }

    public Customer addCustomer(String name, String contact, SkiLevel level)
            throws InvalidBookingException {

        if (name == null || name.trim().isEmpty()) {
            throw new InvalidBookingException("Customer name cannot be empty.");
        }

        if (contact == null || contact.trim().isEmpty()) {
            throw new InvalidBookingException("Contact details cannot be empty.");
        }

        int id = generateCustomerId();

        Customer customer = new Customer(id, name, contact, level);
        customers.add(customer);

        return customer;
    }

    public TravelBundle createBundle(Customer customer, Accommodation accommodation,
                                     String startDate, int durationDays)
            throws InvalidBookingException {

        if (customer == null) {
            throw new InvalidBookingException("Please select a customer.");
        }

        if (accommodation == null) {
            throw new InvalidBookingException("Please select an accommodation.");
        }

        if (!accommodation.isAvailable()) {
            throw new InvalidBookingException("Accommodation is not available.");
        }

        validateDate(startDate);

        if (durationDays <= 0 || durationDays > 30) {
            throw new InvalidBookingException("Duration must be between 1 and 30 days.");
        }

        int bundleId = generateBundleId();

        TravelBundle bundle = new TravelBundle(bundleId, customer, startDate, durationDays);
        bundle.setAccommodation(accommodation);
        accommodation.setAvailable(false);

        bundles.add(bundle);

        return bundle;
    }

    public void addLiftPassToBundle(int bundleId, int days, boolean seasonPass)
            throws InvalidBookingException {

        TravelBundle bundle = findBundleById(bundleId);

        if (bundle == null) {
            throw new InvalidBookingException("Bundle not found.");
        }

        if (seasonPass) {
            bundle.setLiftPass(new LiftPass(30, true));
        } else {
            if (days <= 0 || days > 30) {
                throw new InvalidBookingException("Lift pass days must be between 1 and 30.");
            }

            bundle.setLiftPass(new LiftPass(days, false));
        }
    }

    public void addLessonToBundle(int bundleId, int lessonCount)
            throws InvalidBookingException {

        TravelBundle bundle = findBundleById(bundleId);

        if (bundle == null) {
            throw new InvalidBookingException("Bundle not found.");
        }

        if (lessonCount <= 0) {
            throw new InvalidBookingException("Number of lessons must be greater than 0.");
        }

        SkiLevel level = bundle.getCustomer().getLevel();
        Lesson lesson = new Lesson(lessonCount, level);
        bundle.setLesson(lesson);
    }

    private void validateDate(String startDate) throws InvalidBookingException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            LocalDate.parse(startDate, formatter);
        } catch (DateTimeParseException e) {
            throw new InvalidBookingException("Invalid date format. Use dd/MM/yyyy.");
        }
    }

    public String saveBundlesToFile() {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream("bundles.dat"))) {

            oos.writeObject(bundles);
            return "Bundles saved successfully.";

        } catch (IOException e) {
            return "Error saving bundles: " + e.getMessage();
        }
    }

    @SuppressWarnings("unchecked")
    public String readBundlesFromFile() {
        File file = new File("bundles.dat");

        if (!file.exists()) {
            return "No saved bundle file found.";
        }

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(file))) {

            bundles = (ArrayList<TravelBundle>) ois.readObject();
            updateAccommodationAvailabilityFromBundles();

            return "Bundles loaded successfully.\n\n" + listBundles();

        } catch (IOException | ClassNotFoundException e) {
            return "Error reading bundles: " + e.getMessage();
        }
    }

    private void updateAccommodationAvailabilityFromBundles() {
        for (TravelBundle b : bundles) {
            Accommodation bookedAccommodation = b.getAccommodation();

            if (bookedAccommodation != null) {
                Accommodation matchingAccommodation =
                        findAccommodationById(bookedAccommodation.getid());

                if (matchingAccommodation != null) {
                    matchingAccommodation.setAvailable(false);
                }
            }
        }
    }

    public ArrayList<Accommodation> getAccommodations() {
        return accommodations;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public ArrayList<TravelBundle> getBundles() {
        return bundles;
    }

    public String searchCustomerByName(String name) {
    String output = "";

    for (Customer c : customers) {
        if (c.getName().toLowerCase().contains(name.toLowerCase())) {
            output += c + "\n";
        }
    }

    if (output.equals("")) {
        return "No customers found.";
    }

    return output;
}
}

    