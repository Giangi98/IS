package teammates.storage.api;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import teammates.common.datatransfer.attributes.StudentProfileAttributes;
import teammates.common.exception.InvalidParametersException;
import teammates.common.util.FieldValidator;
import teammates.test.BaseTestCaseWithLocalDatabaseAccess;

/**
 * SUT: {@link ProfilesDb}.
 */
public class ProfilesDbTest extends BaseTestCaseWithLocalDatabaseAccess {

    private final ProfilesDb profilesDb = ProfilesDb.inst();

    private StudentProfileAttributes typicalProfileWithPicture;
    private StudentProfileAttributes typicalProfileWithoutPicture;

    @BeforeMethod
    public void createTypicalData() throws Exception {
        // typical profiles
        profilesDb.createEntity(StudentProfileAttributes.builder("valid.googleId")
                .withInstitute("TEAMMATES Test Institute 1")
                .build());
        profilesDb.createEntity(StudentProfileAttributes.builder("valid.googleId2")
                .withInstitute("TEAMMATES Test Institute 1")
                .build());

        // save entity and picture
        typicalProfileWithPicture = profilesDb.getStudentProfile("valid.googleId");
        typicalProfileWithoutPicture = profilesDb.getStudentProfile("valid.googleId2");
    }

    @AfterMethod
    public void deleteTypicalData() {
        // delete entity
        profilesDb.deleteStudentProfile(typicalProfileWithPicture.getGoogleId());
        profilesDb.deleteStudentProfile(typicalProfileWithoutPicture.getGoogleId());
        verifyAbsentInDatabase(typicalProfileWithPicture);
        verifyAbsentInDatabase(typicalProfileWithoutPicture);
    }

    @Test
    public void testGetStudentProfile_nonExistentStudentProfile_shouldReturnNull() {
        assertNull(profilesDb.getStudentProfile("nonExistent"));
    }

    @Test
    public void testGetStudentProfile_existentStudentProfile_shouldNotReturnNull() {
        assertNotNull(profilesDb.getStudentProfile(typicalProfileWithPicture.getGoogleId()));
        assertNotNull(profilesDb.getStudentProfile(typicalProfileWithoutPicture.getGoogleId()));
    }

    @Test
    public void testUpdateOrCreateStudentProfile_nonExistentProfile_shouldCreateNewProfile()
            throws Exception {
        StudentProfileAttributes spa =
                StudentProfileAttributes.builder("non-ExIsTenT")
                        .withShortName("Test")
                        .build();
        StudentProfileAttributes createdSpa = profilesDb.updateOrCreateStudentProfile(
                StudentProfileAttributes.updateOptionsBuilder(spa.getGoogleId())
                        .withShortName(spa.getShortName())
                        .build());

        verifyPresentInDatabase(spa);
        assertEquals("non-ExIsTenT", createdSpa.getGoogleId());
        assertEquals("Test", createdSpa.getShortName());

        // tear down
        profilesDb.deleteStudentProfile(spa.getGoogleId());

        // create empty profile
        StudentProfileAttributes emptySpa =
                StudentProfileAttributes.builder("emptySpaGoogleId")
                        .build();
        profilesDb.updateOrCreateStudentProfile(
                StudentProfileAttributes.updateOptionsBuilder(emptySpa.getGoogleId())
                        .build());

        verifyPresentInDatabase(emptySpa);

        // tear down
        profilesDb.deleteStudentProfile(emptySpa.getGoogleId());
    }

    @Test
    public void testUpdateOrCreateStudentProfile_nullParameter_shouldThrowAssertionException() {
        assertThrows(AssertionError.class,
                () -> profilesDb.updateOrCreateStudentProfile(null));
    }

    // the test is to ensure that optimized saving policy is implemented without false negative
    @Test
    public void testUpdateOrCreateStudentProfile_updateSingleField_shouldUpdateCorrectly() throws Exception {
        assertNotEquals("testName", typicalProfileWithoutPicture.getShortName());
        StudentProfileAttributes updatedProfile =
                profilesDb.updateOrCreateStudentProfile(
                        StudentProfileAttributes.updateOptionsBuilder(typicalProfileWithoutPicture.getGoogleId())
                                .withShortName("testName")
                                .build());
        StudentProfileAttributes actualProfile = profilesDb.getStudentProfile(typicalProfileWithoutPicture.getGoogleId());
        assertEquals("testName", updatedProfile.getShortName());
        assertEquals("testName", actualProfile.getShortName());
        assertEquals(actualProfile.getModifiedDate(), updatedProfile.getModifiedDate());

        assertNotEquals("test@email.com", actualProfile.getEmail());
        updatedProfile =
                profilesDb.updateOrCreateStudentProfile(
                        StudentProfileAttributes.updateOptionsBuilder(typicalProfileWithoutPicture.getGoogleId())
                                .withEmail("test@email.com")
                                .build());
        actualProfile = profilesDb.getStudentProfile(typicalProfileWithoutPicture.getGoogleId());
        assertEquals("test@email.com", updatedProfile.getEmail());
        assertEquals("test@email.com", actualProfile.getEmail());
        assertEquals(actualProfile.getModifiedDate(), updatedProfile.getModifiedDate());

        assertNotEquals("NUS", actualProfile.getInstitute());
        updatedProfile =
                profilesDb.updateOrCreateStudentProfile(
                        StudentProfileAttributes.updateOptionsBuilder(typicalProfileWithoutPicture.getGoogleId())
                                .withInstitute("NUS")
                                .build());
        actualProfile = profilesDb.getStudentProfile(typicalProfileWithoutPicture.getGoogleId());
        assertEquals("NUS", updatedProfile.getInstitute());
        assertEquals("NUS", actualProfile.getInstitute());
        assertEquals(actualProfile.getModifiedDate(), updatedProfile.getModifiedDate());

        assertNotEquals("Singaporean", actualProfile.getNationality());
        updatedProfile =
                profilesDb.updateOrCreateStudentProfile(
                        StudentProfileAttributes.updateOptionsBuilder(typicalProfileWithoutPicture.getGoogleId())
                                .withNationality("Singaporean")
                                .build());
        actualProfile = profilesDb.getStudentProfile(typicalProfileWithoutPicture.getGoogleId());
        assertEquals("Singaporean", updatedProfile.getNationality());
        assertEquals("Singaporean", actualProfile.getNationality());
        assertEquals(actualProfile.getModifiedDate(), updatedProfile.getModifiedDate());

        assertNotEquals(StudentProfileAttributes.Gender.MALE, actualProfile.getGender());
        updatedProfile =
                profilesDb.updateOrCreateStudentProfile(
                        StudentProfileAttributes.updateOptionsBuilder(typicalProfileWithoutPicture.getGoogleId())
                                .withGender(StudentProfileAttributes.Gender.MALE)
                                .build());
        actualProfile = profilesDb.getStudentProfile(typicalProfileWithoutPicture.getGoogleId());
        assertEquals(StudentProfileAttributes.Gender.MALE, updatedProfile.getGender());
        assertEquals(StudentProfileAttributes.Gender.MALE, actualProfile.getGender());
        assertEquals(actualProfile.getModifiedDate(), updatedProfile.getModifiedDate());

        assertNotEquals("more info", actualProfile.getMoreInfo());
        updatedProfile =
                profilesDb.updateOrCreateStudentProfile(
                        StudentProfileAttributes.updateOptionsBuilder(typicalProfileWithoutPicture.getGoogleId())
                                .withMoreInfo("more info")
                                .build());
        actualProfile = profilesDb.getStudentProfile(typicalProfileWithoutPicture.getGoogleId());
        assertEquals("more info", updatedProfile.getMoreInfo());
        assertEquals("more info", actualProfile.getMoreInfo());
        assertEquals(actualProfile.getModifiedDate(), updatedProfile.getModifiedDate());
    }

    @Test
    public void testUpdateOrCreateStudentProfile_invalidParameter_shouldThrowInvalidParamException() throws Exception {
        // cannot access entity with empty googleId
        assertThrows(IllegalArgumentException.class,
                () -> profilesDb.updateOrCreateStudentProfile(
                        StudentProfileAttributes.updateOptionsBuilder("")
                                .build()));

        InvalidParametersException ipe = assertThrows(InvalidParametersException.class,
                () -> profilesDb.updateOrCreateStudentProfile(
                        StudentProfileAttributes.updateOptionsBuilder(typicalProfileWithPicture.getGoogleId())
                                .withEmail("invalid email")
                                .build()));

        assertEquals(getPopulatedErrorMessage(
                FieldValidator.EMAIL_ERROR_MESSAGE, "invalid email",
                FieldValidator.EMAIL_FIELD_NAME, FieldValidator.REASON_INCORRECT_FORMAT,
                FieldValidator.EMAIL_MAX_LENGTH), ipe.getMessage());
    }

    @Test
    public void testUpdateOrCreateStudentProfile_noChangesToProfile_shouldNotIssueSaveRequest()
            throws Exception {
        // update same profile
        profilesDb.updateOrCreateStudentProfile(
                StudentProfileAttributes.updateOptionsBuilder(typicalProfileWithPicture.getGoogleId())
                        .withShortName(typicalProfileWithPicture.getShortName())
                        .withGender(typicalProfileWithPicture.getGender())
                        .withMoreInfo(typicalProfileWithPicture.getMoreInfo())
                        .withInstitute(typicalProfileWithPicture.getInstitute())
                        .withEmail(typicalProfileWithPicture.getEmail())
                        .withNationality(typicalProfileWithPicture.getNationality())
                        .build());

        StudentProfileAttributes storedProfile = profilesDb.getStudentProfile(typicalProfileWithPicture.getGoogleId());
        // other fields remain
        verifyPresentInDatabase(typicalProfileWithPicture);
        // modifiedDate remains
        assertEquals(typicalProfileWithPicture.getModifiedDate(), storedProfile.getModifiedDate());

        // update nothing
        profilesDb.updateOrCreateStudentProfile(
                StudentProfileAttributes.updateOptionsBuilder(typicalProfileWithPicture.getGoogleId())
                        .build());

        storedProfile = profilesDb.getStudentProfile(typicalProfileWithPicture.getGoogleId());
        // other fields remain
        verifyPresentInDatabase(typicalProfileWithPicture);
        // modifiedDate remains
        assertEquals(typicalProfileWithPicture.getModifiedDate(), storedProfile.getModifiedDate());
    }

    @Test
    public void testDeleteStudentProfile_nonExistentEntity_shouldFailSilently() {
        profilesDb.deleteStudentProfile("test.non-existent");

        assertNull(profilesDb.getStudentProfile("test.non-existent"));
    }

    @Test
    public void testDeleteStudentProfile_profileWithoutPicture_shouldDeleteCorrectly() {
        profilesDb.deleteStudentProfile(typicalProfileWithoutPicture.getGoogleId());

        verifyAbsentInDatabase(typicalProfileWithoutPicture);
    }

    @Test
    public void testDeleteStudentProfile_profileWithPicture_shouldDeleteCorrectly() {
        profilesDb.deleteStudentProfile(typicalProfileWithPicture.getGoogleId());

        // check that profile get deleted and picture get deleted
        verifyAbsentInDatabase(typicalProfileWithPicture);
    }

}
