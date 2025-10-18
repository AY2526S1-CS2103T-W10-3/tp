package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.getErrorMessageForDuplicatePrefixes;
import static seedu.address.logic.commands.CommandTestUtil.INDEX_DESC_FIRST;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INDEX_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NRIC_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PATIENT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.DeleteAppointmentCommand;
import seedu.address.model.patient.Nric;

public class DeleteAppointmentCommandParserTest {

    private DeleteAppointmentCommandParser parser = new DeleteAppointmentCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser, NRIC_DESC_AMY + INDEX_DESC_FIRST,
                new DeleteAppointmentCommand(VALID_NRIC_AMY, INDEX_FIRST_PATIENT));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validDeleteAppointmentString = NRIC_DESC_AMY + INDEX_DESC_FIRST;

        // multiple nrics
        assertParseFailure(parser, NRIC_DESC_AMY + NRIC_DESC_BOB + INDEX_DESC_FIRST,
                getErrorMessageForDuplicatePrefixes(PREFIX_NRIC));

        // multiple indexes
        assertParseFailure(parser, NRIC_DESC_AMY + INDEX_DESC_FIRST + INDEX_DESC_FIRST,
                getErrorMessageForDuplicatePrefixes(PREFIX_INDEX));


        // invalid value followed by valid value

        // invalid nric
        assertParseFailure(parser, INVALID_NRIC_DESC + validDeleteAppointmentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NRIC));

        // invalid index
        assertParseFailure(parser, INVALID_INDEX_DESC + validDeleteAppointmentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_INDEX));


        // valid value followed by invalid value

        // invalid nric
        assertParseFailure(parser, validDeleteAppointmentString + INVALID_NRIC_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NRIC));

        // invalid index
        assertParseFailure(parser, validDeleteAppointmentString + INVALID_INDEX_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_INDEX));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAppointmentCommand.MESSAGE_USAGE);

        // missing nric prefix
        assertParseFailure(parser, VALID_NRIC_BOB + INDEX_DESC_FIRST, expectedMessage);

        // missing index prefix
        assertParseFailure(parser, NRIC_DESC_BOB + INDEX_FIRST_PATIENT, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid nric
        assertParseFailure(parser, INVALID_NRIC_DESC + INDEX_DESC_FIRST, Nric.MESSAGE_CONSTRAINTS);

        // invalid index
        assertParseFailure(parser, NRIC_DESC_BOB + INVALID_INDEX_DESC, MESSAGE_INVALID_INDEX);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NRIC_DESC + INVALID_INDEX_DESC, Nric.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NRIC_DESC_BOB + INDEX_DESC_FIRST,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAppointmentCommand.MESSAGE_USAGE));
    }
}
