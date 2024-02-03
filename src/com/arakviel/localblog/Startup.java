package com.arakviel.localblog;

import com.arakviel.localblog.domain.impl.ServiceFactory;
import com.arakviel.localblog.persistence.entity.impl.User;
import com.arakviel.localblog.persistence.entity.impl.User.Role.EntityName;
import com.arakviel.localblog.persistence.entity.impl.User.Role.Permission;
import com.arakviel.localblog.persistence.repository.RepositoryFactory;
import java.time.LocalDate;

final class Startup {

    static void init() {
        RepositoryFactory jsonRepositoryFactory = RepositoryFactory
                .getRepositoryFactory(RepositoryFactory.JSON);
        ServiceFactory serviceFactory = ServiceFactory.getInstance(jsonRepositoryFactory);
        var authService = serviceFactory.getAuthService();
        var userService = serviceFactory.getUserService();
        User user = authService.getUser();

        Permission permission = user.getRole().getPermissions().get(EntityName.POST);
        if (permission.canRead()) {

        }

        //===
                /*AnsiConsole.systemInstall();                                      // #1
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
                }*/
        //===

        userService.generateReport(
                u -> u.getBirthday().isAfter(LocalDate.of(1990, 1, 1)));

        // Цей рядок, має бути обовязково в кінці метода main!!!!
        jsonRepositoryFactory.commit();
    }
}
