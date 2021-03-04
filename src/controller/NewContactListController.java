package controller;

import com.gluonhq.charm.glisten.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;
import model.ContactList;

import java.util.List;

public class NewContactListController {

    private List<ContactList> contactLists;

    @FXML
    TextField listNameInput;

    public List<ContactList> getContactLists() {
        return contactLists;
    }

    public void setContactLists(List<ContactList> contactLists) {
        this.contactLists = contactLists;
    }

    public void cancelAction(ActionEvent actionEvent) {
        closeStage(actionEvent);
    }

    public void addContactList(ActionEvent actionEvent) {
        if(listNameInput.getText().isBlank()){
            listNameInput.setPromptText("* put a list name to proceed");
            return;
        }
        ContactList newList = new ContactList(listNameInput.getText());
        contactLists.add(newList);
        closeStage(actionEvent);
    }


    private void closeStage(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

}
