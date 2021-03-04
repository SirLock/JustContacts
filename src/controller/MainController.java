package controller;

import com.gluonhq.charm.glisten.control.TextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Contact;
import model.ContactList;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class MainController {

    @FXML
    ComboBox<ContactList> contactListsBox;
    @FXML
    Label infoLabel;
    @FXML
    ListView<Contact> contactsListView;
    @FXML
    TextField givenNameInput;
    @FXML
    TextField surnameInput;
    @FXML
    TextField phoneInput;
    @FXML
    TextField addressInput;
    @FXML
    Button editSaveButton;

    private AddressBookService addressBookService;
    private boolean contactInfoIsLocked = true;
    private Stage primaryStage;

    public MainController() {
    }

    @FXML
    private void initialize() {
        // TEST DATA
//        Contact c1 = new Contact("contact1", "c1");
//        Contact c2 = new Contact("contact2", "c2");
//        Contact c3 = new Contact("contact3", "c3");
//        List<Contact> collection1 = List.of(c1, c2);
//        List<Contact> collection2 = List.of(c2, c3);
//        ContactList l1 = new ContactList("liste 1");
//        l1.addAll(collection1);
//        ContactList l2 = new ContactList("liste 2");
//        l2.addAll(collection2);

        addressBookService = AddressBookService.getInstance();
//        addressBookService.getContactLists().addAll(l1, l2); // TODO REMOVE
        contactListsBox.setItems(addressBookService.getContactLists());
    }

    public void contactsListBoxAction(ActionEvent actionEvent) {
        ContactList selectedItem = contactListsBox.getSelectionModel().getSelectedItem();
        contactsListView.setItems(FXCollections.observableArrayList(selectedItem));
    }

    public void addContactList(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/newContactList.fxml"));
        Parent parent = fxmlLoader.load();
        NewContactListController newContactListController = fxmlLoader.<NewContactListController>getController();
        newContactListController.setContactLists(addressBookService.getContactLists());

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void addNewContact(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/newContact.fxml"));
        Parent parent = fxmlLoader.load();
        NewContactController newContactController = fxmlLoader.<NewContactController>getController();
        ContactList contactList = contactListsBox.getValue();
        if (contactList == null) {
            infoLabel.setText("No contact list selected. Maybe one must be created first.");
            return;
        }
        newContactController.setContactList(contactList);
        newContactController.setContactsListView(contactsListView);
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void removeContact(ActionEvent actionEvent) {
        if (noContactSelected())
            return;
        Contact contact = contactsListView.getSelectionModel().getSelectedItem();
        ContactList list = contactListsBox.getValue();
        list.remove(contact);
        contactsListView.getItems().remove(contact);
    }

    @FXML
    public void contactSelectedAction(MouseEvent mouseEvent) {
        Contact contact = contactsListView.getSelectionModel().getSelectedItem();
        fillContactInfoFields(contact);
    }

    public void toggleEdit(ActionEvent actionEvent) {
        Contact selectedContact = contactsListView.getSelectionModel().getSelectedItem();
        if (noContactSelected())
            return;
        if (contactInfoIsLocked) {
            enableContactDetailsInputs();
            editSaveButton.setText("save");
        } else {
            disableContactDetailsInputs();
            updateContactByContactDetails(selectedContact);
            editSaveButton.setText("edit");
        }
        contactInfoIsLocked = !contactInfoIsLocked;
    }

    private void updateContactByContactDetails(Contact contact) {
        if (noContactSelected())
            return;
        contact.setGivenName(givenNameInput.getText());
        contact.setSurname(surnameInput.getText());
        contact.setPhone(phoneInput.getText());
        contact.setAddress(addressInput.getText());
    }

    private void fillContactInfoFields(Contact contact) {
        givenNameInput.setText(contact.getGivenName());
        surnameInput.setText(contact.getSurname());
        phoneInput.setText(contact.getPhone());
        addressInput.setText(contact.getAddress());
    }

    private void enableContactDetailsInputs() {
        givenNameInput.setDisable(false);
        surnameInput.setDisable(false);
        phoneInput.setDisable(false);
        addressInput.setDisable(false);
    }

    private void disableContactDetailsInputs() {
        givenNameInput.setDisable(true);
        surnameInput.setDisable(true);
        phoneInput.setDisable(true);
        addressInput.setDisable(true);
    }

    private boolean noContactSelected() {
        return Objects.isNull(contactsListView.getSelectionModel().getSelectedItem());
    }

    public EventHandler<WindowEvent> onCloseMethod = (WindowEvent windowEvent) -> {
        addressBookService.persistContactLists();
    };
}
