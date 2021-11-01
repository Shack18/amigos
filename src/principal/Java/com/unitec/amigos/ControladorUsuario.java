package com.unitec.amigos;
//Comentario de githu
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")

public class ControladorUsuario {

    //Aqui va el metodoque representa cada uno de los estados que vamos a transferir
    //es decir, va un GET, POST , PUT y DELETE como minimo

    //Aqui viene ya el uso de la inversion de control
    @Autowired RepositorioUsuario repoUsuario;

    //Implementamos el codigo para guardar un usuario en mongodb
    @PostMapping("/usuario")
    public Estatus guardar(@RequestBody String json) throws Exception{
    //Primero leemos y convertimos un objeto json a objeto java
        ObjectMapper mapper = new ObjectMapper();
        Usuario u = mapper.readValue(json,Usuario.class);
        //Este usuario ya en formaro json lo guardamos en mongodb
        repoUsuario.save(u);
        //Creamos un objeto de tipo status este objeto lo retornamos al cliente (Android o postman)
        Estatus estatus = new Estatus();
        estatus.setSuccess(true);
        estatus.setMensaje("Tu usuario se guardo con exito!!!");
        return estatus;

    }

    @GetMapping("/usuario/{id}")
        public Usuario obtenerporId(@PathVariable String id){
        //leemos un usuario con el metodo findbyid pasandole como argumento el id(email)
        //que queremos apoyandonos del repositorio
            Usuario u = repoUsuario.findById(id).get();
            return u;
        }

        @GetMapping("/usuario")
    public List<Usuario> buscarTodos(){

        return repoUsuario.findAll();
        }

    //Metodo para actualizar
    @PutMapping("/usuario")
    public Estatus actualizar(@RequestBody String json)throws Exception {
        //Primero tenemos que verificar que exista por lo tanto primero buscamos
        ObjectMapper mapper = new ObjectMapper();
        Usuario u = mapper.readValue(json, Usuario.class);
        Estatus e = new Estatus();
        if (repoUsuario.findById(u.getEmail()).isPresent()) {

            //Lo volvemos a guardar
            repoUsuario.save(u);
            e.setMensaje("Usuario se actualizo con exito!!!");
            e.setSuccess(true);
        } else {
            e.setMensaje("Este registro no existe no se actualizara");
            e.setSuccess(false);
        }
        return e;
    }
    @DeleteMapping("/usuario/{id}")
    public Estatus borrar(@PathVariable String id){
        Estatus estatus = new Estatus();
        if(repoUsuario.findById(id).isPresent()){
        repoUsuario.deleteById(id);
        estatus.setSuccess(true);
        estatus.setMensaje("Usuario borrado con exito");
        }else{
            estatus.setSuccess(false);
            estatus.setMensaje("Usuario no existe");
        }
        return estatus;

    }
}
