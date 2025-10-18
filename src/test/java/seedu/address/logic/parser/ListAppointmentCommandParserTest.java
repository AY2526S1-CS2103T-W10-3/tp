package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NRIC_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.ListAppointmentCommand;
import seedu.address.model.patient.Nric;

public class ListAppointmentCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT = String.format(
            MESSAGE_INVALID_COMMAND_FORMAT, ListAppointmentCommand.MESSAGE_USAGE);

    private ListAppointmentCommandParser parser = new ListAppointmentCommandParser();

    @Test
    public void parse_allFieldsSpecified_success() {
        assertParseSuccess(parser, NRIC_DESC_AMY, new ListAppointmentCommand(VALID_NRIC_AMY));
        assertParseSuccess(parser, NRIC_DESC_BOB, new ListAppointmentCommand(VALID_NRIC_BOB));
    }

    @Test
    public void parse_missingFields_failure() {
        assertParseFailure(parser, VALID_NRIC_AMY, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, INVALID_NRIC_DESC, Nric.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_repeatedFields_failure() {
        assertParseFailure(parser, NRIC_DESC_AMY + NRIC_DESC_AMY,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NRIC));
    }
}
