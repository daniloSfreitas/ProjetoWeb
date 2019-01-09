package com.abctreinamentos;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class ClienteApp {

	public static void main(String[] args) {
		try 	
		{	
			
			Scanner entrada = new Scanner(System.in);
			int opcao = 0;
			long cpf;
			String nome, email;
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPATeste");
			EntityManager em = emf.createEntityManager();
			EntityTransaction tx = em.getTransaction();
			while(opcao != 6) {
				System.out.println("Sistema de Gerenciamento de Clientes");
				System.out.println("================================================");
				System.out.println("Digite 1 para Consultar Todos os Clientes");
				System.out.println("Digite 2 para Consultar um Clientes Especifico");
				System.out.println("Digite 3 para Cadastrar um Novo Cliente");
				System.out.println("Digite 4 para Alterar um Cliente");
				System.out.println("Digite 5 para Excluir um Cliente");
				System.out.println("Digite 6 para Sair");
				System.out.println("================================================");
				opcao = entrada.nextInt();
				
				switch(opcao) {
				     case 1:{
				       System.out.println("Consultar todos os Clientes"); 
					   TypedQuery<Cliente> query = em.createQuery(""
							   + "Select c from Cliente c",Cliente.class);
					   List<Cliente> clientes = query.getResultList();
					   clientes.forEach(System.out::println);
					   break;
				   }
				     case 2:{
				    	 System.out.println("Consultar um Cliente Especifico");
				    	 System.out.println("Informar o CPF");
						 cpf = entrada.nextLong(); 
				    	 Cliente cliente = em.find(Cliente.class, cpf);
				    	 System.out.println(cliente);
				    	 break;
					   }
				     case 3:{
				    	 System.out.println("Criar um Novo Cliente");
				    	 System.out.println("Informar o Cpf:");
						 cpf = entrada.nextLong(); 
						 entrada.nextLine();
						 System.out.println("Informar o Nome:");
						 nome = entrada.nextLine();
						 System.out.println("Informar o Email:");
						 email = entrada.nextLine();
						 Cliente cliente = new Cliente(cpf,nome,email);
						 tx.begin();
				    	 em.persist(cliente);
				    	 tx.commit();				    	 
				    	 break;
						   
					   }
				     case 4:{
				    	 System.out.println("Alterar um Cliente");
				    	 System.out.println("Informar o Cpf:");
						 cpf = entrada.nextLong(); 
						 entrada.nextLine();
						 System.out.println("Informar o Nome:");
						 nome = entrada.nextLine();
						 System.out.println("Informar o Email:");
						 email = entrada.nextLine();
						 Cliente cliente = new Cliente(cpf,nome,email);
						 tx.begin();
						 em.merge(cliente);
						 tx.commit();
				    	 break;
						   
					   }
				     case 5:{
				    	 System.out.println("Expcluir um Cliente Especifico");
				    	 System.out.println("Informar o CPF");
						 cpf = entrada.nextLong(); 
						 Cliente cliente = em.find(Cliente.class, cpf);
						 tx.begin();
				    	 em.remove(cliente);	
				    	 break;
						   
					   }
				     case 6:{ 
						   System.out.println("Encerrando o Sistema....");
						   tx.commit();
						   break;
					   }
				}
				
			}
			entrada.close();
					
		  }catch(Exception e) {
			  e.printStackTrace();
		  }
			
		}

	}


