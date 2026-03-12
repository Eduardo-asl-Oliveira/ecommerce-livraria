package com.eduardo.ecommerce_livros.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

@Entity
public class Pedido {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private Instant momentoDoPedido;
    private double total;

    @Enumerated(EnumType.STRING)
    private StatusPedido status = StatusPedido.AGUARDANDO;
    
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List <ItemPedido> itensPedidos = new ArrayList<>();


    public Pedido() {}

    public Pedido(Long id, Instant momentoDoPedido, Cliente cliente, List<ItemPedido> itensPedidos) {
        this.id = id;
        this.momentoDoPedido = momentoDoPedido;
        this.cliente = cliente;
        this.itensPedidos = itensPedidos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getMomentoDoPedido() {
        return momentoDoPedido;
    }

    public void setMomentoDoPedido(Instant momentoDoPedido) {
        this.momentoDoPedido = momentoDoPedido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ItemPedido> getItensPedidos() {
        return itensPedidos;
    }

    public void setItensPedidos(List<ItemPedido> itensPedidos) {
        this.itensPedidos = itensPedidos;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

}


