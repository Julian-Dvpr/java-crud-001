package com.cursojava.curso.dao;

import com.cursojava.curso.models.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * ESTAS SON LAS PETICIONES AL SERVIDOR CON ENTITY MANAGER
 */

@Repository
@Transactional /* esto sirve para hacer peticiones al servidor*/

public class UsuarioDaoImp implements UsuarioDao {

    @PersistenceContext
    EntityManager entityManager;
    /*entity es yna interfaz que tiene elementos para borrar pedir
     insertar porque es un gestor de persistencia
    tambien hace consultas a base de datos y tambien crear querys
    para pedir en lenguaje SQL */

    @Override
    @Transactional
    public List<Usuario> getUsuarios() {
        String query = "FROM Usuario";
        return entityManager.createQuery(query).getResultList();
    }


    @Override
    public void eliminar(Long id) { /*LONG se usa para ID de base de datos fin...*/
        Usuario usuario = entityManager.find(Usuario.class, id);
        entityManager.remove(usuario);   /* primero lo busca luego lo elimina por ID*/
    }

    @Override
    public void registrar(Usuario usuario) {
        entityManager.merge(usuario);
    }

    @Override
    public Usuario obtenerUsuarioPorCredenciales(Usuario usuario) {
        String query = "FROM Usuario WHERE email = :email"; /*aca se pone :email para evitar inyecciones sql*/
        List<Usuario> lista = entityManager.createQuery(query)
                .setParameter("email", usuario.getEmail())
                .getResultList();

        if (lista.isEmpty()) {
            return null;
        }

        String passwordHashed = lista.get(0).getPassword();

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        if (argon2.verify(passwordHashed, usuario.getPassword())) {
            return lista.get(0);
        }
        return null;
    }
}