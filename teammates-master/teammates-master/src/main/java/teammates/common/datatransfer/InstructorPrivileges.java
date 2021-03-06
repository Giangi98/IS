package teammates.common.datatransfer;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import teammates.common.util.Const;

/**
 * Representation of instructor privileges. Store the privileges of the instructor
 */
public final class InstructorPrivileges {

    private static final Map<String, Boolean> PRIVILEGES_COOWNER = new LinkedHashMap<>();
    private static final Map<String, Boolean> PRIVILEGES_MANAGER = new LinkedHashMap<>();
    private static final Map<String, Boolean> PRIVILEGES_OBSERVER = new LinkedHashMap<>();
    private static final Map<String, Boolean> PRIVILEGES_TUTOR = new LinkedHashMap<>();
    private static final Map<String, Boolean> PRIVILEGES_CUSTOM = new LinkedHashMap<>();

    static {
        PRIVILEGES_COOWNER.put(Const.InstructorPermissions.CAN_MODIFY_COURSE, true);
        PRIVILEGES_COOWNER.put(Const.InstructorPermissions.CAN_MODIFY_INSTRUCTOR, true);
        PRIVILEGES_COOWNER.put(Const.InstructorPermissions.CAN_MODIFY_SESSION, true);
        PRIVILEGES_COOWNER.put(Const.InstructorPermissions.CAN_MODIFY_STUDENT, true);
        PRIVILEGES_COOWNER.put(Const.InstructorPermissions.CAN_VIEW_STUDENT_IN_SECTIONS, true);
        PRIVILEGES_COOWNER.put(Const.InstructorPermissions.CAN_VIEW_SESSION_IN_SECTIONS, true);
        PRIVILEGES_COOWNER.put(Const.InstructorPermissions.CAN_SUBMIT_SESSION_IN_SECTIONS, true);
        PRIVILEGES_COOWNER.put(Const.InstructorPermissions.CAN_MODIFY_SESSION_COMMENT_IN_SECTIONS, true);

        PRIVILEGES_MANAGER.put(Const.InstructorPermissions.CAN_MODIFY_COURSE, false);
        PRIVILEGES_MANAGER.put(Const.InstructorPermissions.CAN_MODIFY_INSTRUCTOR, true);
        PRIVILEGES_MANAGER.put(Const.InstructorPermissions.CAN_MODIFY_SESSION, true);
        PRIVILEGES_MANAGER.put(Const.InstructorPermissions.CAN_MODIFY_STUDENT, true);
        PRIVILEGES_MANAGER.put(Const.InstructorPermissions.CAN_VIEW_STUDENT_IN_SECTIONS, true);
        PRIVILEGES_MANAGER.put(Const.InstructorPermissions.CAN_VIEW_SESSION_IN_SECTIONS, true);
        PRIVILEGES_MANAGER.put(Const.InstructorPermissions.CAN_SUBMIT_SESSION_IN_SECTIONS, true);
        PRIVILEGES_MANAGER.put(Const.InstructorPermissions.CAN_MODIFY_SESSION_COMMENT_IN_SECTIONS, true);

        PRIVILEGES_OBSERVER.put(Const.InstructorPermissions.CAN_MODIFY_COURSE, false);
        PRIVILEGES_OBSERVER.put(Const.InstructorPermissions.CAN_MODIFY_INSTRUCTOR, false);
        PRIVILEGES_OBSERVER.put(Const.InstructorPermissions.CAN_MODIFY_SESSION, false);
        PRIVILEGES_OBSERVER.put(Const.InstructorPermissions.CAN_MODIFY_STUDENT, false);
        PRIVILEGES_OBSERVER.put(Const.InstructorPermissions.CAN_VIEW_STUDENT_IN_SECTIONS, true);
        PRIVILEGES_OBSERVER.put(Const.InstructorPermissions.CAN_VIEW_SESSION_IN_SECTIONS, true);
        PRIVILEGES_OBSERVER.put(Const.InstructorPermissions.CAN_SUBMIT_SESSION_IN_SECTIONS, false);
        PRIVILEGES_OBSERVER.put(Const.InstructorPermissions.CAN_MODIFY_SESSION_COMMENT_IN_SECTIONS, false);

        PRIVILEGES_TUTOR.put(Const.InstructorPermissions.CAN_MODIFY_COURSE, false);
        PRIVILEGES_TUTOR.put(Const.InstructorPermissions.CAN_MODIFY_INSTRUCTOR, false);
        PRIVILEGES_TUTOR.put(Const.InstructorPermissions.CAN_MODIFY_SESSION, false);
        PRIVILEGES_TUTOR.put(Const.InstructorPermissions.CAN_MODIFY_STUDENT, false);
        PRIVILEGES_TUTOR.put(Const.InstructorPermissions.CAN_VIEW_STUDENT_IN_SECTIONS, true);
        PRIVILEGES_TUTOR.put(Const.InstructorPermissions.CAN_VIEW_SESSION_IN_SECTIONS, true);
        PRIVILEGES_TUTOR.put(Const.InstructorPermissions.CAN_SUBMIT_SESSION_IN_SECTIONS, true);
        PRIVILEGES_TUTOR.put(Const.InstructorPermissions.CAN_MODIFY_SESSION_COMMENT_IN_SECTIONS, false);

        PRIVILEGES_CUSTOM.put(Const.InstructorPermissions.CAN_MODIFY_COURSE, false);
        PRIVILEGES_CUSTOM.put(Const.InstructorPermissions.CAN_MODIFY_INSTRUCTOR, false);
        PRIVILEGES_CUSTOM.put(Const.InstructorPermissions.CAN_MODIFY_SESSION, false);
        PRIVILEGES_CUSTOM.put(Const.InstructorPermissions.CAN_MODIFY_STUDENT, false);
        PRIVILEGES_CUSTOM.put(Const.InstructorPermissions.CAN_VIEW_STUDENT_IN_SECTIONS, false);
        PRIVILEGES_CUSTOM.put(Const.InstructorPermissions.CAN_VIEW_SESSION_IN_SECTIONS, false);
        PRIVILEGES_CUSTOM.put(Const.InstructorPermissions.CAN_SUBMIT_SESSION_IN_SECTIONS, false);
        PRIVILEGES_CUSTOM.put(Const.InstructorPermissions.CAN_MODIFY_SESSION_COMMENT_IN_SECTIONS, false);
    }

    private static final String[] COURSE_LEVEL_ONLY_LIST = new String[] {
            Const.InstructorPermissions.CAN_MODIFY_COURSE,
            Const.InstructorPermissions.CAN_MODIFY_INSTRUCTOR,
            Const.InstructorPermissions.CAN_MODIFY_SESSION,
            Const.InstructorPermissions.CAN_MODIFY_STUDENT,
    };

    private static final String[] SECTION_LEVEL_ONLY_LIST = new String[] {
            Const.InstructorPermissions.CAN_VIEW_STUDENT_IN_SECTIONS,
    };

    private static final String[] SESSION_LEVEL_ONLY_LIST = new String[] {
            Const.InstructorPermissions.CAN_VIEW_SESSION_IN_SECTIONS,
            Const.InstructorPermissions.CAN_SUBMIT_SESSION_IN_SECTIONS,
            Const.InstructorPermissions.CAN_MODIFY_SESSION_COMMENT_IN_SECTIONS,
    };

    private static final Set<String> COURSE_LEVEL_ONLY_PRIVILEGES =
            new LinkedHashSet<>(Arrays.asList(COURSE_LEVEL_ONLY_LIST));
    private static final Set<String> SECTION_LEVEL_ONLY_PRIVILEGES =
            new LinkedHashSet<>(Arrays.asList(SECTION_LEVEL_ONLY_LIST));
    private static final Set<String> SESSION_LEVEL_ONLY_PRIVILEGES =
            new LinkedHashSet<>(Arrays.asList(SESSION_LEVEL_ONLY_LIST));

    private Map<String, Boolean> courseLevel;
    private Map<String, Map<String, Boolean>> sectionLevel;
    private Map<String, Map<String, Map<String, Boolean>>> sessionLevel;

    public InstructorPrivileges() {
        this.courseLevel = new LinkedHashMap<>();
        this.sectionLevel = new LinkedHashMap<>();
        this.sessionLevel = new LinkedHashMap<>();
    }

    public InstructorPrivileges(String instrRole) {
        this();
        switch (instrRole) {
        case Const.InstructorPermissionRoleNames.INSTRUCTOR_PERMISSION_ROLE_COOWNER:
            setDefaultPrivilegesForCoowner();
            break;
        case Const.InstructorPermissionRoleNames.INSTRUCTOR_PERMISSION_ROLE_MANAGER:
            setDefaultPrivilegesForManager();
            break;
        case Const.InstructorPermissionRoleNames.INSTRUCTOR_PERMISSION_ROLE_OBSERVER:
            setDefaultPrivilegesForObserver();
            break;
        case Const.InstructorPermissionRoleNames.INSTRUCTOR_PERMISSION_ROLE_TUTOR:
            setDefaultPrivilegesForTutor();
            break;
        case Const.InstructorPermissionRoleNames.INSTRUCTOR_PERMISSION_ROLE_CUSTOM:
            setDefaultPrivilegesForCustom();
            break;
        default:
            setDefaultPrivilegesForCustom();
            break;
        }
    }

    static boolean isPrivilegeNameValid(String privilegeName) {
        return COURSE_LEVEL_ONLY_PRIVILEGES.contains(privilegeName)
                || SECTION_LEVEL_ONLY_PRIVILEGES.contains(privilegeName)
                || SESSION_LEVEL_ONLY_PRIVILEGES.contains(privilegeName);
    }

    static boolean isPrivilegeNameValidForSectionLevel(String privilegeName) {
        return SECTION_LEVEL_ONLY_PRIVILEGES.contains(privilegeName)
                || SESSION_LEVEL_ONLY_PRIVILEGES.contains(privilegeName);
    }

    static boolean isPrivilegeNameValidForSessionLevel(String privilegeName) {
        return SESSION_LEVEL_ONLY_PRIVILEGES.contains(privilegeName);
    }

    void setDefaultPrivilegesForCoowner() {
        setDefaultPrivileges(PRIVILEGES_COOWNER);
    }

    void setDefaultPrivilegesForManager() {
        setDefaultPrivileges(PRIVILEGES_MANAGER);
    }

    void setDefaultPrivilegesForObserver() {
        setDefaultPrivileges(PRIVILEGES_OBSERVER);
    }

    void setDefaultPrivilegesForTutor() {
        setDefaultPrivileges(PRIVILEGES_TUTOR);
    }

    void setDefaultPrivilegesForCustom() {
        setDefaultPrivileges(PRIVILEGES_CUSTOM);
    }

    private void setDefaultPrivileges(Map<String, Boolean> defaultPrivileges) {
        defaultPrivileges.forEach((key, value) -> courseLevel.put(key, value));
    }

    private Map<String, Boolean> getOverallPrivilegesForSections() {
        Map<String, Boolean> privileges = new LinkedHashMap<>();

        privileges.put(Const.InstructorPermissions.CAN_VIEW_STUDENT_IN_SECTIONS,
                isAllowedInCourseLevel(Const.InstructorPermissions.CAN_VIEW_STUDENT_IN_SECTIONS));

        privileges.put(Const.InstructorPermissions.CAN_VIEW_SESSION_IN_SECTIONS,
                isAllowedInCourseLevel(Const.InstructorPermissions.CAN_VIEW_SESSION_IN_SECTIONS));
        privileges.put(Const.InstructorPermissions.CAN_SUBMIT_SESSION_IN_SECTIONS,
                isAllowedInCourseLevel(Const.InstructorPermissions.CAN_SUBMIT_SESSION_IN_SECTIONS));
        privileges.put(Const.InstructorPermissions.CAN_MODIFY_SESSION_COMMENT_IN_SECTIONS,
                isAllowedInCourseLevel(Const.InstructorPermissions.CAN_MODIFY_SESSION_COMMENT_IN_SECTIONS));

        return privileges;
    }

    /**
     * Sets privilege for the privilege specified by privilegeName.
     */
    public void updatePrivilege(String privilegeName, boolean isAllowed) {
        updatePrivilegeInCourseLevel(privilegeName, isAllowed);
    }

    /**
     * Sets privilege for the privilege specified by privilegeName for sectionName.
     */
    public void updatePrivilege(String sectionName, String privilegeName, boolean isAllowed) {
        updatePrivilegeInSectionLevel(sectionName, privilegeName, isAllowed);
    }

    /**
     * Sets privilege for the privilege specified by privilegeName for sessionName in sectionName.
     */
    public void updatePrivilege(String sectionName, String sessionName, String privilegeName, boolean isAllowed) {
        updatePrivilegeInSessionLevel(sectionName, sessionName, privilegeName, isAllowed);
    }

    private void updatePrivilegeInCourseLevel(String privilegeName, boolean isAllowed) {
        if (!isPrivilegeNameValid(privilegeName)) {
            return;
        }
        this.courseLevel.put(privilegeName, isAllowed);
    }

    private void updatePrivilegeInSectionLevel(String sectionName, String privilegeName, boolean isAllowed) {
        if (!isPrivilegeNameValidForSectionLevel(privilegeName)) {
            return;
        }
        addSectionWithDefaultPrivileges(sectionName);
        this.sectionLevel.get(sectionName).put(privilegeName, isAllowed);
    }

    private void updatePrivilegeInSessionLevel(String sectionName, String sessionName,
                                               String privilegeName, boolean isAllowed) {
        if (!isPrivilegeNameValidForSessionLevel(privilegeName)) {
            return;
        }
        verifyExistenceOfsectionName(sectionName);
        this.sessionLevel.get(sectionName).computeIfAbsent(sessionName, key -> new LinkedHashMap<>())
                                          .put(privilegeName, isAllowed);
    }

    private void verifyExistenceOfsectionName(String sectionName) {
        this.sessionLevel.computeIfAbsent(sectionName, key -> {
            addSectionWithDefaultPrivileges(sectionName);
            return new LinkedHashMap<>();
        });
    }

    void addSectionWithDefaultPrivileges(String sectionName) {
        this.sectionLevel.putIfAbsent(sectionName, getOverallPrivilegesForSections());
    }

    /**
     * Returns true if it is allowed for the privilege specified by privilegeName.
     */
    public boolean isAllowedForPrivilege(String privilegeName) {
        return isAllowedInCourseLevel(privilegeName);
    }

    /**
     * Returns true if it is allowed for the privilege specified by privilegeName in sectionName.
     */
    public boolean isAllowedForPrivilege(String sectionName, String privilegeName) {
        return isAllowedInSectionLevel(sectionName, privilegeName);
    }

    /**
     * Returns true if it is allowed for the privilege specified by privilegeName for sessionName in sectionName.
     */
    public boolean isAllowedForPrivilege(String sectionName, String sessionName, String privilegeName) {
        return isAllowedInSessionLevel(sectionName, sessionName, privilegeName);
    }

    /**
     * Returns true if privilege for session is present for any section.
     */
    public boolean isAllowedForPrivilegeAnySection(String sessionName, String privilegeName) {
        return isAllowedInSessionLevelAnySection(sessionName, privilegeName);
    }

    /**
     * Returns true if co-owner privilege exists.
     */
    public boolean hasCoownerPrivileges() {
        return hasSamePrivileges(PRIVILEGES_COOWNER);
    }

    /**
     * Returns true if manager privilege exists.
     */
    public boolean hasManagerPrivileges() {
        return hasSamePrivileges(PRIVILEGES_MANAGER);
    }

    /**
     * Returns true if observer privilege exists.
     */
    public boolean hasObserverPrivileges() {
        return hasSamePrivileges(PRIVILEGES_OBSERVER);
    }

    /**
     * Returns true if tutor privilege exists.
     */
    public boolean hasTutorPrivileges() {
        return hasSamePrivileges(PRIVILEGES_TUTOR);
    }

    private boolean hasSamePrivileges(Map<String, Boolean> defaultPrivileges) {

        for (Map.Entry<String, Boolean> entry : defaultPrivileges.entrySet()) {
            if (isAllowedForPrivilege(entry.getKey()) != entry.getValue()) {
                return false;
            }
        }

        return true;
    }

    private boolean isAllowedInCourseLevel(String privilegeName) {

        assert isPrivilegeNameValid(privilegeName);

        return this.courseLevel.getOrDefault(privilegeName, false);
    }

    private boolean isAllowedInSectionLevel(String sectionName, String privilegeName) {

        assert isPrivilegeNameValid(privilegeName);

        if (!this.sectionLevel.containsKey(sectionName)) {
            return isAllowedInCourseLevel(privilegeName);
        }

        return this.sectionLevel.get(sectionName).getOrDefault(privilegeName, false);
    }

    private boolean isAllowedInSessionLevel(String sectionName, String sessionName, String privilegeName) {

        assert isPrivilegeNameValid(privilegeName);

        if (!this.sessionLevel.containsKey(sectionName)
                || !this.sessionLevel.get(sectionName).containsKey(sessionName)) {
            return isAllowedInSectionLevel(sectionName, privilegeName);
        }

        return this.sessionLevel.get(sectionName).get(sessionName).getOrDefault(privilegeName, false);
    }

    private boolean isAllowedInSessionLevelAnySection(String sessionName, String privilegeName) {

        assert isPrivilegeNameValid(privilegeName);

        Set<String> sections = new LinkedHashSet<>(this.sessionLevel.keySet());
        sections.addAll(this.sectionLevel.keySet());
        for (String sectionName : sections) {
            if (isAllowedInSessionLevel(sectionName, sessionName, privilegeName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Validates the privileges in course level, section level and session level.
     *
     * <p>Makes sure there is nothing wrong with privileges hierarchy by adding the
     * prerequisite privileges if they have not been granted yet.
     */
    public void validatePrivileges() {
        if (this.courseLevel.getOrDefault(
                Const.InstructorPermissions.CAN_MODIFY_SESSION_COMMENT_IN_SECTIONS, false)) {
            this.courseLevel.put(Const.InstructorPermissions.CAN_VIEW_SESSION_IN_SECTIONS, true);
        }
        for (Map<String, Boolean> sectionMap : this.sectionLevel.values()) {
            if (sectionMap.getOrDefault(
                    Const.InstructorPermissions.CAN_MODIFY_SESSION_COMMENT_IN_SECTIONS, false)) {
                sectionMap.put(Const.InstructorPermissions.CAN_VIEW_SESSION_IN_SECTIONS, true);
            }
        }
        for (Map<String, Map<String, Boolean>> section : this.sessionLevel.values()) {
            for (Map<String, Boolean> sessionMap : section.values()) {
                if (sessionMap.getOrDefault(
                        Const.InstructorPermissions.CAN_MODIFY_SESSION_COMMENT_IN_SECTIONS, false)) {
                    sessionMap.put(Const.InstructorPermissions.CAN_VIEW_SESSION_IN_SECTIONS, true);
                }
            }
        }
    }

    public Map<String, Boolean> getCourseLevelPrivileges() {
        return new LinkedHashMap<>(courseLevel);
    }

    /**
     * Returns the section level privileges of the instructor.
     */
    public Map<String, Map<String, Boolean>> getSectionLevelPrivileges() {
        Map<String, Map<String, Boolean>> copy = new LinkedHashMap<>();
        sectionLevel.forEach((key, value) -> copy.put(key, new LinkedHashMap<>(value)));
        return copy;
    }

    /**
     * Returns the session level privileges of the instructor.
     */
    public Map<String, Map<String, Map<String, Boolean>>> getSessionLevelPrivileges() {
        Map<String, Map<String, Map<String, Boolean>>> copy = new LinkedHashMap<>();
        sessionLevel.forEach((sessionLevelKey, sessionLevelValue) -> {
            Map<String, Map<String, Boolean>> sectionCopy = new LinkedHashMap<>();
            sessionLevelValue.forEach((key, value) -> sectionCopy.put(key, new LinkedHashMap<>(value)));

            copy.put(sessionLevelKey, sectionCopy);
        });
        return copy;
    }

    @Override
    public boolean equals(Object another) {
        if (!(another instanceof InstructorPrivileges)) {
            return false;
        }
        if (another == this) {
            return true;
        }

        InstructorPrivileges rhs = (InstructorPrivileges) another;
        return this.getCourseLevelPrivileges().equals(rhs.getCourseLevelPrivileges())
               && this.getSectionLevelPrivileges().equals(rhs.getSectionLevelPrivileges())
               && this.getSessionLevelPrivileges().equals(rhs.getSessionLevelPrivileges());
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;

        result = prime * result + this.getCourseLevelPrivileges().hashCode();
        result = prime * result + this.getSectionLevelPrivileges().hashCode();
        result = prime * result + this.getSessionLevelPrivileges().hashCode();

        return result;
    }

}
