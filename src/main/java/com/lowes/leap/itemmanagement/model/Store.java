package com.lowes.leap.itemmanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "store")
public class Store {


		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		@Column(name = "id")
		private long id;

		@Column(name = "location")
		private String location;

		@Column(name = "quantity")
		private Integer quantity;

		// @OneToMany(mappedBy = "store")
    	// private Item item;

		public Store() {

		}

		public Store(String location, Integer quantity) {
			this.location= location;
			this.quantity = quantity;
		}

		@Override
		public String toString() {
			return "Store [id=" + id + ", location=" + location + ", quantity=" + quantity + "]";
		}

		public long getId() {
			return id;
		}

		public String getLocation() {
			return location;
		}

		public Integer getQuantity() {
			return quantity;
		}

		public void setId(long id) {
			this.id = id;
		}

		public void setLocation(String location) {
			this.location = location;
		}

		public void setQuantity(Integer quantity) {
			this.quantity = quantity;
		}

		

}
