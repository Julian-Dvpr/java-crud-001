package com.cursojava.curso.controllers;
import com.cursojava.curso.dao.UsuarioDao;
import com.cursojava.curso.models.Usuario;
import com.cursojava.curso.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * ACA CREO LAS RUTAS Y LAS FUNCIONES
 * PORQUE ES UN CONTROLADOR jeje
 */

@RestController

public class UsuarioController {
    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "api/usuarios/{id}", method = RequestMethod.GET)
    public Usuario getUsuario(@PathVariable Long id){                   /*aca me traigo el Usuario y creo nueva funcion + el metodo GET getusuario()*/
        Usuario usuario = new Usuario();                 /*funcion Usuario guarda "res" en "var" usuario y produce un nuevo Usuario con new Usuario()*/
        usuario.setId(id);
        usuario.setNombre("Julian");
        usuario.setApellido("Brito");
        usuario.setEmail("andres@gmail.com");  /*con esto crea un usuario*/
        usuario.setTelefono("11253456");
        usuario.setPassword("1234");
        return usuario;                          /*devuelve los datos insertos en la variable "usuario"*/
    }
    @RequestMapping(value = "api/usuarios", method = RequestMethod.GET)  //para entrar aca tengo que entrar a /usuarios.html porque le valor 'usuarios' esta en el ID de la etiqueta TABLE
    public List<Usuario> getUsuarios(@RequestHeader(value="Authorization") String token){ //guardamos en token guardamos el tojen que nos pide la cabecera

        if(!validarToken(token)){return null;}
        return usuarioDao.getUsuarios();
    }

    private boolean validarToken(String token){
        String usuarioId = jwtUtil.getKey(token);  //esto me devuelve el ID del usuario
        return usuarioId != null;  //devuelve lista vacia si es NULL si es null esta
    }

    @RequestMapping(value = "api/usuarios", method = RequestMethod.POST)
    public void registrarUsuario(@RequestBody Usuario usuario){ //argon2 es para hashear
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1,2024,1, usuario.getPassword());
        usuario.setPassword(hash);
        usuarioDao.registrar(usuario);
    }
    @RequestMapping(value = "12")
    public Usuario editar(){
        Usuario usuario = new Usuario();
        usuario.setNombre("");
        usuario.setApellido("");
        usuario.setEmail("");
        usuario.setTelefono("");
        usuario.setPassword("");

        return usuario;
    }

    @RequestMapping(value = "api/usuarios/{id}", method = RequestMethod.DELETE)
    public void eliminar(@RequestHeader(value="Authorization") String token,
                         @PathVariable Long id){
        if(!validarToken(token)){
            return ;
        } //esta es la validacion del token
        usuarioDao.eliminar(id);
    }
}
