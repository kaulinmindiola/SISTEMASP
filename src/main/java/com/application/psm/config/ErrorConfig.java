package com.application.psm.config;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class ErrorConfig implements ErrorPageRegistrar {

    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        registry.addErrorPages(
            new ErrorPage(HttpStatus.FORBIDDEN, "/layout/error/403.html"), // Código de estado HTTP 403 (Solicitud prohibida)
            new ErrorPage(HttpStatus.BAD_REQUEST, "/layout/error/400.html"), // Código de estado HTTP 400 (Solicitud incorrecta)
            new ErrorPage(HttpStatus.UNAUTHORIZED, "/layout/error/401.html"), // Código de estado HTTP 401 (No autorizado)
            new ErrorPage(HttpStatus.NOT_FOUND, "/layout/error/404.html"), // Código de estado HTTP 404 (No encontrado)
            new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/layout/error/500.html") // Código de estado HTTP 500 (Error interno del servidor)
        );
    }
}
