package controller;

import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import model.ContactList;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AddressBookService {
    private static final String CONTACTS_LISTS_DIRECTORY_NAME = "Contact Lists";

    private static AddressBookService addressBookService;
    private ObservableList<ContactList> contactLists;

    private AddressBookService() {
        try {
            contactLists = loadPersistedLists();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ObservableList<ContactList> loadPersistedLists() throws IOException {
        ObservableList<ContactList> contactLists = FXCollections.observableArrayList();
        Path directoryPath = Paths.get(AddressBookService.CONTACTS_LISTS_DIRECTORY_NAME);
        boolean isDir = Files.isDirectory(directoryPath);
        if (!isDir) {
            Files.createDirectory(directoryPath);
        }
        String[] lists = new File(AddressBookService.CONTACTS_LISTS_DIRECTORY_NAME).list();
        if (lists != null) {
            for (String listName : lists) {
                ContactList list = parsePersistedContactListFromFile(AddressBookService.CONTACTS_LISTS_DIRECTORY_NAME, listName);
                contactLists.add(list);
            }
        }
        return contactLists;
    }

    public ContactList parsePersistedContactListFromFile(String directoryPath, String listName) {
        ContactList contacts = new ContactList();
        contacts.setName(listName);
        try {
            Gson gson = new Gson();
            File listPath = new File(directoryPath + "/" + listName);
            if (listPath.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(listPath));
                while (br.ready()) {
                    String contactJson = br.readLine();
                    if(!contactJson.isBlank()) {
                        Contact contact = gson.fromJson(contactJson, Contact.class);
                        contacts.add(contact);
                    }
                }
                br.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contacts;
    }

    public void persistContactLists(ObservableList<ContactList> contactLists) {
        try {
            for (ContactList list : contactLists) {
                writeContactListToFile(list, CONTACTS_LISTS_DIRECTORY_NAME);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void persistContactLists() {
        persistContactLists(contactLists);
    }

    public void writeContactListToFile(ContactList contactList, String directoryPath) throws IOException {
        Gson gson = new Gson();
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(directoryPath + "/" + contactList.getName())));
        for (Contact contact : contactList) {
            String contactJson = gson.toJson(contact);
            bw.write(contactJson);
            bw.newLine();
        }
        bw.flush();
        bw.close();
    }

    public ContactList getContactListByName(String name) {
        return contactLists.stream().filter(list -> list.getName().equals(name)).findFirst().get();
    }

    public static AddressBookService getInstance() {
        if (addressBookService == null)
            addressBookService = new AddressBookService();
        return addressBookService;
    }

    public static AddressBookService getAddressBookService() {
        return addressBookService;
    }

    public static void setAddressBookService(AddressBookService addressBookService) {
        AddressBookService.addressBookService = addressBookService;
    }

    public ObservableList<ContactList> getContactLists() {
        return contactLists;
    }

    public void setContactLists(ObservableList<ContactList> contactLists) {
        this.contactLists = contactLists;
    }
}
