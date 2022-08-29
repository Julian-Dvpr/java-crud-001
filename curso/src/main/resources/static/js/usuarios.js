    // Call the dataTables jQuery plugin
    $(document).ready(function() {
        cargarUsuarios();
      $('#usuarios').DataTable();
      actualizarEmailDelUsuario();
    });

    function actualizarEmailDelUsuario() {
        document.getElementById('txt-email-usuario').outerHTML = localStorage.email;
        //para cambiar el html usarmos outerRHTML
    }


    async function cargarUsuarios() {
      const request = await fetch('api/usuarios', {
        method: 'GET',
        headers: getHeaders()
      });
      const usuarios = await request.json();


      let listadoHtml = '';

      for (let usuario of usuarios) { /* aca saca una variable usuario de la manga de usuarios que es el ID del html*/
        let botonEliminar = '<a href="#" onclick="eliminarUsuario(' + usuario.id + ')" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>';

        let telefonoTexto = usuario.telefono == null ? '-' : usuario.telefono;

        let usuarioHtml = '<tr><td>'+usuario.id+'</td><td>' + usuario.nombre + ' ' + usuario.apellido + '</td><td>'
                        + usuario.email+'</td><td>'+telefonoTexto
                        + '</td><td>' + botonEliminar + '</td></tr>';

        listadoHtml += usuarioHtml;
      }

    document.querySelector('#usuarios tbody').outerHTML = listadoHtml;

    }

    function getHeaders() {
        return {
         'Accept': 'application/json',
         'Content-Type': 'application/json',
         'Authorization': localStorage.token //esto es la informacion del token
       };
    }

    async function eliminarUsuario(id) {
      if (!confirm('¿Desea eliminar este usuario?')) { //confirm muestra el cartel de alerta
        return;
      }
     const request = await fetch('api/usuarios/' + id, {
        method: 'DELETE',
        headers: getHeaders()
      });

      location.reload() //con esto si refresca la pagina se actualiza la pagina
    }