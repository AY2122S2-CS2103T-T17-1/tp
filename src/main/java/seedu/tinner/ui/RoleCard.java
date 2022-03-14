package seedu.tinner.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.tinner.model.role.Role;

/**
 * An UI component that displays information of a {@code Role}.
 */
public class RoleCard extends UiPart<Region> {

    private static final String FXML = "RoleCard.fxml";

    public final Role role;

    @FXML
    private HBox cardPane;
    @FXML
    private Label roleName;
    @FXML
    private Label id;
    @FXML
    private Label status;
    @FXML
    private Label deadline;
    @FXML
    private Label description;
    @FXML
    private Label stipend;

    /**
     * Creates a {@code RoleCard} with the given {@code Role}.
     */
    public RoleCard(Role role, int displayedIndex) {
        super(FXML);
        this.role = role;
        id.setText(displayedIndex + ". ");
        roleName.setText(role.getName().fullName);
        status.setText("Status: " + role.getStatus().value);
        deadline.setText("Deadline: " + role.getDeadline().toString());
        description.setText("Description: " + role.getDescription().value);
        stipend.setText("Stipend: $" + role.getStipend().value);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RoleCard)) {
            return false;
        }

        // state check
        RoleCard card = (RoleCard) other;
        return role.equals(card.role);
    }
}
