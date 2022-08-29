package com.cursojava.curso.dao;

import com.cursojava.curso.models.Usuario;

import java.util.List;

/**
 * DATA ACCES OBJET DAO.
 * ACA ESTÁN TODOS LOS METODOS
 * este es un componente de tipo INTERFACE que sirve para crear conexiones creo
 * a su vez crea un metodo llamado UsuarioDao para usar en los controladores
 */
public interface UsuarioDao {
    List<Usuario> getUsuarios();
    void eliminar(Long id);

    void registrar(Usuario usuario);

    Usuario obtenerUsuarioPorCredenciales(Usuario usuario);
}