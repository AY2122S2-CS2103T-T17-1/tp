package seedu.tinner.testutil;

import seedu.tinner.logic.commands.EditRoleCommand;
import seedu.tinner.model.role.Deadline;
import seedu.tinner.model.role.Description;
import seedu.tinner.model.role.Role;
import seedu.tinner.model.role.RoleName;
import seedu.tinner.model.role.Status;
import seedu.tinner.model.role.Stipend;

/**
 * A utility class to help with building EditRoleDescriptor objects.
 */
public class EditRoleDescriptorBuilder {
    private EditRoleCommand.EditRoleDescriptor descriptor;


    public EditRoleDescriptorBuilder() {
        this.descriptor = new EditRoleCommand.EditRoleDescriptor();
    }

    public EditRoleDescriptorBuilder(EditRoleCommand.EditRoleDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    /**
     * Returns an {@code EditRoleDescriptor} with fields containing {@code Role}'s details
     */
    public EditRoleDescriptorBuilder(Role role) {
        descriptor = new EditRoleCommand.EditRoleDescriptor();
        descriptor.setName(role.getName());
        descriptor.setStatus(role.getStatus());
        descriptor.setDeadline(role.getDeadline());
        descriptor.setDescription(role.getDescription());
        descriptor.setStipend(role.getStipend());
    }

    /**
     * Sets the {@code Name} of the {@code EditRoleDescriptor} that we are building.
     */
    public EditRoleDescriptorBuilder withName(String name) {
        descriptor.setName(new RoleName(name));
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code EditRoleDescriptor} that we are building.
     */
    public EditRoleDescriptorBuilder withStatus(String status) {
        descriptor.setStatus(new Status(status));
        return this;
    }

    /**
     * Sets the {@code Deadline} of the {@code EditRoleDescriptor} that we are building.
     */
    public EditRoleDescriptorBuilder withDeadline(String deadline) {
        descriptor.setDeadline(new Deadline(deadline));
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code EditRoleDescriptor} that we are building.
     */
    public EditRoleDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new Description(description));
        return this;
    }

    /**
     * Sets the {@code Stipend} of the {@code EditRoleDescriptor} that we are building.
     */
    public EditRoleDescriptorBuilder withStipend(String stipend) {
        descriptor.setStipend(new Stipend(stipend));
        return this;
    }

    public EditRoleCommand.EditRoleDescriptor build() {
        return descriptor;
    }
}
