package com.lowes.leap.itemmanagement.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

//enum Status {
//    ACTIVE,
//    INACTIVE,
//    ONLINE,
//    STORE_ONLINE,
//    OUT_OF_STOCK
//}


@Entity
@Table(name = "item")
public class Item {
	 public enum Status {
	        ACTIVE,
	        INACTIVE,
	        ONLINE,
	        STORE_ONLINE,
	        OUT_OF_STOCK
	    }

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;

	@Column(name = "name")
	private String name;

	@Column(name = "price")
	private Integer price;

	@OneToOne()
	@JoinColumn(name = "store_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	// @JsonIgnore
	private Store store;

	@OneToOne()
	@JoinColumn(name = "category_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	// @JsonIgnore
	private Category category;

	@Enumerated(EnumType.STRING)
	private Status status;

	public Item() {

	}

	public Item(String name, Integer price, Status status) {
		this.name= name;
		this.price = price;
		this.status = status;
	}


	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", price=" + price + ", store=" + store + ", category=" + category
				+ ", status=" + status + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	
	

}
