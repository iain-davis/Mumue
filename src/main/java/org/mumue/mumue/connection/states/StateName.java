package org.mumue.mumue.connection.states;

public enum StateName {
    EnterSpace(EnterSpace.class),
    EnterUniverse(EnterUniverse.class),
    LoginIdPrompt(LoginIdPrompt.class),
    LoginIdPromptHandler(LoginIdPromptHandler.class),
    NewPlayerPrompt(NewPlayerPrompt.class),
    PasswordPrompt(PasswordPrompt.class),
    PasswordPromptHandler(PasswordPromptHandler.class),
    PlayCharacter(PlayCharacter.class),
    PlayerAuthentication(PlayerAuthentication.class),
    WelcomeDisplay(WelcomeDisplay.class),
    WelcomeCommandsDisplay(WelcomeCommandsPrompt.class),
    WelcomeCommandsHandler(WelcomeCommandsHandler.class),
    NoOperationState(NoOperation.class)
    ;

    private final Class<? extends ConnectionState> stateClass;

    StateName(Class<? extends ConnectionState> stateClass) {
        this.stateClass = stateClass;
    }

    public Class<? extends ConnectionState> getStateClass() {
        return stateClass;
    }
}
