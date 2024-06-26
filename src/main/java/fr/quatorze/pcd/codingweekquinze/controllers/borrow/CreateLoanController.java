package fr.quatorze.pcd.codingweekquinze.controllers.borrow;

import fr.quatorze.pcd.codingweekquinze.controllers.components.CustomDateTimePicker;
import fr.quatorze.pcd.codingweekquinze.dao.LoanDAO;
import fr.quatorze.pcd.codingweekquinze.layout.LayoutManager;
import fr.quatorze.pcd.codingweekquinze.layout.RequiresAuth;
import fr.quatorze.pcd.codingweekquinze.model.Element;
import fr.quatorze.pcd.codingweekquinze.model.Loan;
import fr.quatorze.pcd.codingweekquinze.model.User;
import fr.quatorze.pcd.codingweekquinze.service.AuthService;
import fr.quatorze.pcd.codingweekquinze.util.DateUtil;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.utils.others.FunctionalStringConverter;
import io.github.palexdev.mfxcore.controls.Label;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.util.Pair;
import javafx.util.StringConverter;

import java.time.LocalDateTime;

@RequiresAuth
public class CreateLoanController {
    private final Element element;

    @FXML
    private Label type;

    @FXML
    private CustomDateTimePicker startDate;

    @FXML
    private CustomDateTimePicker endDate;

    @FXML
    private Label name;

    @FXML
    private TextArea description;

    @FXML
    private Label price;

    @FXML
    private MFXListView<Pair<LocalDateTime, LocalDateTime>> reservationList;

    public CreateLoanController(Element element) {
        this.element = element;
    }

    @FXML
    private void initialize() {
        this.name.setText(this.element.getName());
        this.description.setText(this.element.getDescription());
        this.price.setText(this.element.getPrice().toString());
        this.type.setText(this.element.getIsService() ? "Service" : "Objet");


        StringConverter<Pair<LocalDateTime, LocalDateTime>> converter = FunctionalStringConverter.to(period -> {
            LocalDateTime start = period.getKey();
            LocalDateTime end = period.getValue();
            if (start.isEqual(end)) {
                return DateUtil.format(start);
            } else {
                return "Du " + DateUtil.format(start) + " au " + DateUtil.format(end);
            }

        });

        this.reservationList.setConverter(converter);

        this.reservationList.setItems(FXCollections.observableList(this.element.getAvailableDates()));

        /*this.reservationList.setCellFactory(new Callback<ListView<Pair<LocalDate,LocalDate>>, ListCell<Pair<LocalDate,LocalDate>>>() {
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
        });*/

        this.reservationList.setOnMouseClicked(event -> {
            // Obtenir l'élément sélectionné
            Pair<LocalDateTime, LocalDateTime> selectedItem = reservationList.getSelectionModel().getSelectedValue();

            if (selectedItem != null) {
                startDate.setValue(selectedItem.getKey());
                endDate.setValue(selectedItem.getValue());
            }
        });
    }

    @FXML
    private void reserve() {
        LocalDateTime startDate = this.startDate.getValue();
        LocalDateTime endDate = this.endDate.getValue();

        if (startDate.isAfter(endDate)) {
            LayoutManager.alert("La date de début doit être avant la date de fin");
            return;
        }
        if (!element.isAvailable(startDate, endDate)) {
            LayoutManager.alert("La période n'est pas disponible");
            return;
        }
        User user = AuthService.getInstance().getCurrentUser();

        Loan loan = LoanDAO.getInstance().createLoan(startDate, endDate, element, user);
        user.addLoansCalendar(loan);
        LayoutManager.success("Réservation effectuée");
        LayoutManager.setLayout("borrow/my-borrows.fxml", "Home");
    }
}
