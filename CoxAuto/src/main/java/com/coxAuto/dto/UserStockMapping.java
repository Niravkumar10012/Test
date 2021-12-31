package com.coxAuto.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class UserStockMapping.
 */
@Entity
@Table(name = "userStockMapping")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserStockMapping implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@Id
	private String user;
	private String stockCode;
	
}
