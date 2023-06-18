package com.lowes.leap.itemmanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;


@Builder
@Entity
@Table(name = "category")
public class Category {


		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		@Column(name = "id")
		private long id;

		@Column(name = "name")
		private String name;

		@Column(name = "description")
		private String description;


		public Category() {

		}

		public Category(String name, String description) {
			this.name= name;
			this.description = description;
		}

		@Override
		public String toString() {
			return "Category [id=" + id + ", name=" + name + ", description=" + description + "]";
		}

		public long getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public String getDescription() {
			return description;
		}

		public void setId(long id) {
			this.id = id;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		


}
