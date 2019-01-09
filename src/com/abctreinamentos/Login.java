package com.abctreinamentos;
// Generated 02/01/2019 23:10:23 by Hibernate Tools 4.3.5.Final


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Login generated by hbm2java
 */
@Entity
@Table(name = "LOGIN")
public class Login implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long cpf;
	private String senha;

	public Login() {
	}

	public Login(long cpf, String senha) {
		this.cpf = cpf;
		this.senha = senha;
	}

	@Id

	@Column(name = "CPF", unique = true, nullable = false, precision = 22, scale = 0)
	public long getCpf() {
		return this.cpf;
	}

	public void setCpf(long cpf) {
		this.cpf = cpf;
	}

	@Column(name = "SENHA", nullable = false, length = 20)
	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public String toString() {
		return "Login [cpf=" + cpf + ", senha=" + senha + "]";
	}

}