package com.example.javalin.presentacion;

import com.example.javalin.modelo.Dueño;
import com.example.javalin.persistencia.RepositorioDueños;
import com.example.javalin.presentacion.dto.LoginRequest;
import com.example.javalin.presentacion.dto.LoginResponse;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class LoginHandler implements Handler {

    private final RepositorioDueños repoDueños;

    public LoginHandler() {
        this.repoDueños = new RepositorioDueños();
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        LoginRequest loginRequest = context.bodyAsClass(LoginRequest.class);

        Dueño dueño = this.repoDueños.obtenerUsuario(loginRequest.getUsername(), loginRequest.getPassword());

        // Devuelve el nombre del dueño creado y el objeto dueño con los parametros enviados
        System.out.println("Login: " + loginRequest.getUsername());
        System.out.println("Login: " + dueño);

        SesionManager sesionManager = SesionManager.get();
        String idSesion = sesionManager.crearSesion("dueño", dueño);

        context.json(new LoginResponse(idSesion));

    }

}
