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
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
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

    @FXML
    private VBox contactDetails;
    @FXML
    private ContactDetailsController contactDetailsController;

    public MainController() {
    }

    @FXML
    private void initialize() {
        addressBookService = AddressBookService.getInstance();
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
        contactDetailsController.clearDisplay();
    }

    @FXML
    public void contactSelectedAction(MouseEvent mouseEvent) {
        Contact contact = contactsListView.getSelectionModel().getSelectedItem();
        contactDetailsController.displayContact(contact);
    }

    private boolean noContactSelected() {
        return Objects.isNull(contactsListView.getSelectionModel().getSelectedItem());
    }

    public EventHandler<WindowEvent> onCloseMethod = (WindowEvent windowEvent) -> {
        addressBookService.persistContactLists();
    };
}
