Feature: UserManagement

  Scenario: [UserManagement] Admin deletes a user account
    Given [UserManagement] admin is connected
    When [UserManagement] the admin try to delete a user account
    Then [UserManagement] the response is ok

  Scenario: [UserManagement] User deletes a user account
    Given [UserManagement] user is connected
    When [UserManagement] the user try to delete a user account
    Then [UserManagement] the response is ko