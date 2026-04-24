package hibernate;

public class modelStock {
	package com.hibernate.model;

	import jakarta.persistence.*;

	@Entity
	@Table(name="sin_stock")
	public class SinStock {
	    
	    @Id
	    @Column(name="idmedicamentos")
	    private int id;

	    public SinStock() {}

	    public SinStock(int id) {
	        this.id = id;
	    }

	    public int getId() { return id; }
	    public void setId(int id) { this.id = id; }
	}
}
