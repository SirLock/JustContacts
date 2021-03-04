package controller;

import com.gluonhq.charm.glisten.control.TextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Contact;
import model.ContactList;

public class NewContactController {

    private ContactList contactList;
    private ListView<Contact> contactsListView;

    @FXML
    TextField givenNameInput;
    @FXML
    TextField surnameInput;
    @FXML
    TextField phoneInput;
    @FXML
    TextField addressInput;
    @FXML
    Label warningLabel;


    public NewContactController() {
    }

    public void addContact(ActionEvent actionEvent) {
        if (givenNameInput.getText().isBlank() && surnameInput.getText().isBlank()) {
            warningLabel.setText("Provide mandatory fields: *");
        } else {
            String givenName = givenNameInput.getText();
            String surname = surnameInput.getText();
            String phone = phoneInput.getText();
            String address = addressInput.getText();
            contactList.add(new Contact(givenName, surname, phone, address));
            contactsListView.setItems(FXCollections.observableArrayList(contactList));
            givenNameInput.setText("");
            surnameInput.setText("");
            phoneInput.setText("");
            addressInput.setText("");
            warningLabel.setText("");
        }
    }

    public void cancel(ActionEvent actionEvent) {
        closeStage(actionEvent);
    }

    public ContactList getContactList() {
        return contactList;
    }

    public void setContactList(ContactList contactList) {
        this.contactList = contactList;
    }

    public void setContactsListView(ListView<Contact> contactsListView){
        this.contactsListView = contactsListView;
    }

    private void closeStage(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
