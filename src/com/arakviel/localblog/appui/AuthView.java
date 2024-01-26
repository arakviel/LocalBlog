package com.arakviel.localblog.appui;

import static com.arakviel.localblog.appui.AuthView.AuthMenu.EXIT;
import static com.arakviel.localblog.appui.AuthView.AuthMenu.SIGN_IN;
import static com.arakviel.localblog.appui.AuthView.AuthMenu.SIGN_UP;

import com.arakviel.localblog.domain.contract.AuthService;
import com.arakviel.localblog.domain.exception.AuthException;
import de.codeshelf.consoleui.prompt.ConsolePrompt;
import de.codeshelf.consoleui.prompt.InputResult;
import de.codeshelf.consoleui.prompt.ListResult;
import de.codeshelf.consoleui.prompt.builder.PromptBuilder;
import java.io.IOException;

public final class AuthView implements Renderable {

    private final AuthService authService;

    public AuthView(AuthService authService) {
        this.authService = authService;
    }

    private void process(AuthMenu selectedItem) throws IOException {
        ConsolePrompt prompt = new ConsolePrompt();
        PromptBuilder promptBuilder = prompt.getPromptBuilder();

        switch (selectedItem) {
            case SIGN_IN -> {
                promptBuilder.createInputPrompt()
                        .name("username")
                        .message("Впишіть ваш логін: ")
                        .addPrompt();
                promptBuilder.createInputPrompt()
                        .name("password")
                        .message("Впишіть ваш пароль: ")
                        .mask('*')
                        .addPrompt();

                var result = prompt.prompt(promptBuilder.build());
                var usernameInput = (InputResult) result.get("username");
                var passwordInput = (InputResult) result.get("password");

                try {
                    boolean authenticate = authService.authenticate(
                            usernameInput.getInput(),
                            passwordInput.getInput()
                    );
                    System.out.printf("%s %n", authenticate);
                } catch (AuthException e) {
                    System.err.println(e.getMessage());
                }


            }
            case SIGN_UP -> {
                System.out.println("Далі тут щось робим 2");
            }
            case EXIT -> {

            }
            default -> {

            }
        }
    }

    @Override
    public void render() throws IOException {
        ConsolePrompt prompt = new ConsolePrompt();
        PromptBuilder promptBuilder = prompt.getPromptBuilder();

        promptBuilder.createListPrompt()
                .name("auth-menu")
                .message("Локальний блог")
                .newItem(SIGN_IN.toString()).text(SIGN_IN.getName()).add()
                .newItem(SIGN_UP.toString()).text(SIGN_UP.getName()).add()
                .newItem(EXIT.toString()).text(EXIT.getName()).add()
                .addPrompt();

        var result = prompt.prompt(promptBuilder.build());
        ListResult resultItem = (ListResult) result.get("auth-menu");

        AuthMenu selectedItem = AuthMenu.valueOf(resultItem.getSelectedId());
        process(selectedItem);
    }

    enum AuthMenu {
        SIGN_IN("Авторизація"),
        SIGN_UP("Стоврити обліковий аккаунт"),
        EXIT("Вихід");

        private final String name;

        AuthMenu(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
