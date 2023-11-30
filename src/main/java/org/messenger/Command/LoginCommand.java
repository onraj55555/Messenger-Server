package org.messenger.Command;

import java.util.HashMap;

public class LoginCommand extends ACommand {

    public LoginCommand(HashMap<String, String> params) {
        super(params);
        type = CommandTypes.LOGIN;
    }
    public LoginCommand() {
        super();
        type = CommandTypes.LOGIN;
    }
}