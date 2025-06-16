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
        //validamos user/pass y buscamos datos de ese usuario para agregar en la sesión

        LoginRequest loginRequest = context.bodyAsClass(LoginRequest.class);

        // Validación de los datos ingresados (algo simple)
        if (loginRequest.getUsername() == null) {
            throw new IllegalArgumentException("El nombre de usuario y la contraseña no pueden estar vacíos");
        }  // La excepcion se va mostrar en el archivo Application.java

        // Modifique esto para que reciba y de el nombre del dueño que se esta creando (pasado por parametro)
        Dueño dueño = new Dueño();
        dueño.setNombre(loginRequest.getUsername());
        dueño.setPassword(loginRequest.getPassword());

        // Devuelve el nombre del dueño creado y el objeto dueño con los parametros enviados
        System.out.println("Login: " + loginRequest.getUsername());
        System.out.println("Login: " + dueño);

        SesionManager sesionManager = SesionManager.get();
        String idSesion = sesionManager.crearSesion("dueño", dueño);

//        sesionManager.agregarAtributo(idSesion, "fechaInicio", new Date());
//        sesionManager.agregarAtributo(idSesion, "rol", repoRoles.getByUser(idUser));

        context.json(new LoginResponse(idSesion));

    }

}
