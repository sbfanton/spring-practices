package com.sbfanton.online_shop.service;

import java.util.List;
import java.util.Optional;

import com.sbfanton.online_shop.model.Cliente;

public interface ClienteService {
    public Cliente crearCliente(Cliente cliente);
    public List<Cliente> obtenerTodosLosClientes();
    public Optional<Cliente> obtenerClientePorId(String id);
    public Optional<Cliente> obtenerClientePorEmail(String email);
    public List<Cliente> obtenerClientesPorNombreLike(String nombre);
    public Cliente actualizarCliente(String id, Cliente clienteActualizado);
    public void eliminarCliente(String id);
}
