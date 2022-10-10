Feature: UserManagement

  Scenario: User deletes a user account
    Given user is connected
    When the user try to delete a user account
    Then the user failed to delete the user account
    And the user account still present in database

  Scenario: Admin deletes a user account
    Given admin is connected
    When the admin try to delete a user account
    Then the admin succeed to delete the user account
    And the user account is successfully deleted from database

