package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.Contact;

public class ContactDetailsController {

    @FXML
    private TextField givenNameInput;
    @FXML
    private TextField surnameInput;
    @FXML
    private TextField phoneInput;
    @FXML
    private TextField addressInput;

    @FXML
    public void initialize(){

    }

    public void fillContactInfoFields(Contact contact) {
        givenNameInput.setText(contact.getGivenName());
        surnameInput.setText(contact.getSurname());
        phoneInput.setText(contact.getPhone());
        addressInput.setText(contact.getAddress());
    }

    public void toggleEdit(ActionEvent actionEvent) {
//        Contact selectedContact = contactsListView.getSelectionModel().getSelectedItem();
//        if (noContactSelected())
//            return;
//        if (contactInfoIsLocked) {
//            enableContactDetailsInputs();
//            editSaveButton.setText("save");
//        } else {
//            disableContactDetailsInputs();
//            updateContactByContactDetails(selectedContact);
//            editSaveButton.setText("edit");
//        }
//        contactInfoIsLocked = !contactInfoIsLocked;
    }
}
