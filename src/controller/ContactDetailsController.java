package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Contact;

public class ContactDetailsController {

    private Contact displayedContact;
    private boolean contactInfoLocked = true;
    @FXML
    private TextField givenNameInput;
    @FXML
    private TextField surnameInput;
    @FXML
    private TextField phoneInput;
    @FXML
    private TextField addressInput;
    @FXML
    private Button editSaveButton;


    public void displayContact(Contact displayContact) {
        this.displayedContact = displayContact;
        fillContactInfoFields();
    }

    public void clearDisplay(){
        displayedContact = null;
        givenNameInput.setText("");
        surnameInput.setText("");
        phoneInput.setText("");
        addressInput.setText("");
        contactInfoLocked = true;
    }

    private void fillContactInfoFields() {
        givenNameInput.setText(displayedContact.getGivenName());
        surnameInput.setText(displayedContact.getSurname());
        phoneInput.setText(displayedContact.getPhone());
        addressInput.setText(displayedContact.getAddress());
    }

    @FXML
    private void editAndSave(ActionEvent actionEvent) {
        if(displayedContact == null)
            return;
        if (contactInfoLocked) {
            enableContactDetailsInputs();
            editSaveButton.setText("save");
        } else {
            disableContactDetailsInputs();
            updateContactByContactDetails();
            editSaveButton.setText("edit");
        }
        contactInfoLocked = !contactInfoLocked;
    }

    private void updateContactByContactDetails() {
        if(displayedContact == null)
            return;
        displayedContact.setGivenName(givenNameInput.getText());
        displayedContact.setSurname(surnameInput.getText());
        displayedContact.setPhone(phoneInput.getText());
        displayedContact.setAddress(addressInput.getText());
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

    public Contact getDisplayedContact() {
        return displayedContact;
    }
}
