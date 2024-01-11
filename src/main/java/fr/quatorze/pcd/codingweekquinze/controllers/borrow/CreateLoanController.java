package fr.quatorze.pcd.codingweekquinze.controllers.borrow;

import fr.quatorze.pcd.codingweekquinze.dao.LoanDAO;
import fr.quatorze.pcd.codingweekquinze.layout.LayoutManager;
import fr.quatorze.pcd.codingweekquinze.layout.RequiresAuth;
import fr.quatorze.pcd.codingweekquinze.model.Availability;
import fr.quatorze.pcd.codingweekquinze.model.Element;
import fr.quatorze.pcd.codingweekquinze.model.User;
import fr.quatorze.pcd.codingweekquinze.service.AuthService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.Pair;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiresAuth
public class CreateLoanController {
    private final Element element;

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    @FXML
    private Label name;

    @FXML
    private TextArea description;

    @FXML
    private Label price;

    @FXML
    private ListView<Pair<LocalDate, LocalDate>> reservationList;

    public CreateLoanController(Element element) {
        this.element = element;
    }

    @FXML
    private void initialize() {
        this.name.setText(this.element.getName());
        this.description.setText(this.element.getDescription());
        this.price.setText(this.element.getPrice().toString());

        this.reservationList.setItems(FXCollections.observableList(this.element.getAvailableDates()));

        this.reservationList.setCellFactory(new Callback<ListView<Pair<LocalDate,LocalDate>>, ListCell<Pair<LocalDate,LocalDate>>>() {
            @Override
            public ListCell<Pair<LocalDate,LocalDate>> call(ListView<Pair<LocalDate,LocalDate>> listView) {
                return new ListCell<Pair<LocalDate,LocalDate>>() {
                    @Override
                    protected void updateItem(Pair<LocalDate,LocalDate> period, boolean empty) {
                        super.updateItem(period, empty);
                        if (empty || period == null) {
                            setText(null);
                            setOnMouseClicked(null);
                        } else {
                            LocalDate start = period.getKey();
                            LocalDate end = period.getValue();
                            if (start.isEqual(end)) {
                                setText(start.toString());
                            } else {
                                setText(start.toString() + " - " + end.toString());
                            }
                            setOnMouseClicked(event -> {
                                startDate.setValue(period.getKey());
                                endDate.setValue(period.getValue());
                            });
                        }
                    }
                };

            }
        });
    }

    @FXML
    private void reserve() {
        Date startDate = Date.from(this.startDate.getValue().atStartOfDay().toInstant(java.time.ZoneOffset.UTC));
        Date endDate = Date.from(this.endDate.getValue().atStartOfDay().toInstant(java.time.ZoneOffset.UTC));

        if (startDate.after(endDate)) {
            LayoutManager.alert("La date de début doit être avant la date de fin");
            return;
        }
        if (!element.isAvailable(startDate, endDate)) {
            LayoutManager.alert("La période n'est pas disponible");
            return;
        }
        User user = AuthService.getInstance().getCurrentUser();

        LoanDAO.getInstance().createLoan(startDate, endDate, element, user);
        LayoutManager.success("Loan created");
        LayoutManager.setLayout("borrow/my-borrows.fxml", "Home");
    }
}
