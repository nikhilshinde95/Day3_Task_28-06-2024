package com.org.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "subcategory")
public class Subcategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "subcategory_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "category_id", referencedColumnName = "category_id")
	private Category category;

	@Column(name = "subcategory_name")
	private String name;

	@Column(name = "subcategory_description")
	private String description;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Subcategory() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Subcategory(Long id, Category category, String name, String description) {
		super();
		this.id = id;
		this.category = category;
		this.name = name;
		this.description = description;
	}

	@Override
	public String toString() {
		return "Subcategory [id=" + id + ", category=" + category + ", name=" + name + ", description=" + description
				+ "]";
	}

	
}
