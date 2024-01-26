package com.arakviel.localblog;

import static org.fusesource.jansi.Ansi.ansi;

import com.arakviel.localblog.appui.AuthView;
import com.arakviel.localblog.domain.contract.AuthService;
import com.arakviel.localblog.domain.impl.ServiceFactory;
import com.arakviel.localblog.persistence.repository.RepositoryFactory;
import java.io.IOException;
import jline.TerminalFactory;
import org.fusesource.jansi.AnsiConsole;

final class Startup {

    static void init() {
        RepositoryFactory jsonRepositoryFactory = RepositoryFactory
                .getRepositoryFactory(RepositoryFactory.JSON);
        ServiceFactory serviceFactory = ServiceFactory.getInstance(jsonRepositoryFactory);
        AuthService authService = serviceFactory.getAuthService();

        //===
        AnsiConsole.systemInstall();                                      // #1
        System.out.println(ansi().eraseScreen().render("Simple list example:"));

        try {
            AuthView authView = new AuthView(authService);
            authView.render();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                TerminalFactory.get().restore();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //===

        // Цей рядок, має бути обовязково в кінці метода main!!!!
        jsonRepositoryFactory.commit();
    }
}
