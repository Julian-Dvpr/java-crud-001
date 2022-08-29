package com.cursojava.curso.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * CON ESTE MODELO LE DIGO QUE DATOS RECIBE
 */


@Table(name = "usuarios") /*con esto le decimos que va a ir a la tabla USUARIOS de SQLmyadmin*/
@Entity
@ToString @EqualsAndHashCode
public class Usuario {
    @Getter @Setter @Column(name = "id")
    @Id  //para que me tome el id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //hay que agregar esto para que pueda guardar los valores en base de datos
    private Long id;

    @Getter @Setter @Column(name = "nombre")
    private String nombre;

    @Getter @Setter @Column(name = "apellido")
    private String apellido;

    @Getter @Setter @Column(name = "email")
    private String email;

    @Getter @Setter @Column(name = "telefono")
    private String telefono;

    @Getter @Setter @Column(name = "password")
    private String password;

}
