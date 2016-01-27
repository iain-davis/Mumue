package org.mumue.mumue.text;

public enum TextName {
    Welcome("welcome screen"),
    LoginPrompt("login prompt"),
    PasswordPrompt("password prompt"),
    LoginFailed("login failed"),
    LoginSuccess("login success"),
    PlayerMainMenu("player main menu"),
    AdministratorMainMenu("administrator main menu"),
    InvalidOption("invalid option"),
    CharacterNamePrompt("character name prompt"),
    UniverseSelectionPrompt("universe selection prompt"),
    CharacterNameAlreadyExists("character name already exists"),
    CharacterNameTakenByOtherPlayer("character name taken by other player"),
    CharacterSelectionPrompt("character selection prompt"),
    CharacterNeeded("character needed"),
    EnterUniverse("enter universe"),
    MissingSayText("missing say text"),
    TargetBeingNotFound("target being not found"),
    UnknownCommand("unknown command"),
    AmbiguousCommand("ambiguous command"),
    NewPlayerPrompt("new player prompt"),
    AndText("and text"),
    Yes("yes"),
    WelcomeCommands("welcome screen commands"),
    MissingPassword("connect missing password"),
    MissingCharacterName("connect missing character name"),
    CharacterDoesNotExist("connect character does not exist"),
    CharacterNameInvalid("invalid character name")
    ;

    private final String name;

    TextName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
