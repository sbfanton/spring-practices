package com.sbfanton.online_shop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbfanton.online_shop.model.Cliente;
import com.sbfanton.online_shop.repository.ClienteRepository;
import com.sbfanton.online_shop.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
    private ClienteRepository clienteRepository;

    public Cliente crearCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public List<Cliente> obtenerTodosLosClientes() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> obtenerClientePorId(String id) {
        return clienteRepository.findById(id);
    }

    public Optional<Cliente> obtenerClientePorEmail(String email) {
        return clienteRepository.findByEmail(email);
    }

    public Cliente actualizarCliente(String id, Cliente clienteActualizado) {
        return clienteRepository.findById(id).map(cliente -> {
            cliente.setNombre(clienteActualizado.getNombre());
            cliente.setEdad(clienteActualizado.getEdad());
            cliente.setEmail(clienteActualizado.getEmail());
            cliente.setTelefono(clienteActualizado.getTelefono());
            cliente.setDireccion(clienteActualizado.getDireccion());
            return clienteRepository.save(cliente);
        }).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    public void eliminarCliente(String id) {
        clienteRepository.deleteById(id);
    }

	@Override
	public List<Cliente> obtenerClientesPorNombreLike(String nombre) {
		return clienteRepository.findByNombreLike(nombre);
	}
}
