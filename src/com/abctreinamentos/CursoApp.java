package com.abctreinamentos;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class CursoApp {

	public static void main(String[] args) {
		try 	
		{	
			
			Scanner entrada = new Scanner(System.in);
			int opcao = 0;
			long cdcurso, valor;
			String nome, url;
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPATeste");
			EntityManager em = emf.createEntityManager();
			EntityTransaction tx = em.getTransaction();
			
			while(opcao != 6) {
				System.out.println("Sistema de Gerenciamento de Cursos");
				System.out.println("================================================");
				System.out.println("Digite 1 para Consultar Todos os Cursos");
				System.out.println("Digite 2 para Consultar um Cursos Especifico");
				System.out.println("Digite 3 para Cadastrar um Novo Curso");
				System.out.println("Digite 4 para Alterar um Curso");
				System.out.println("Digite 5 para Excluir um Curso");
				System.out.println("Digite 6 para Sair");
				System.out.println("================================================");
				opcao = entrada.nextInt();
				
				switch(opcao) {
				     case 1:{
				    	 System.out.println("Consultar todos os Cursos"); 
				    	   TypedQuery<Curso> query = em.createQuery(""
								   + "Select c from Curso c",Curso.class);
						   List<Curso> cursos = query.getResultList();
						   cursos.forEach(System.out::println);
				    	 
					   break;
				   }
				     case 2:{
				    	 System.out.println("Consultar um Curso Especifico");
				    	 System.out.println("Informar o CDCruso");
				    	 cdcurso = entrada.nextLong();
				    	 Curso curso = em.find(Curso.class, cdcurso);
				    	 System.out.println(curso);
				    	 break;
					   }
				     case 3:{
				    	 System.out.println("Criar um Novo Curso");
				    	 System.out.println("Informar o CDCURSO:");
						 cdcurso = entrada.nextLong(); 
						 entrada.nextLine();
						 System.out.println("Informar o Nome:");
						 nome = entrada.nextLine();
						 System.out.println("Informar o valor:");
						 valor = entrada.nextLong();
						 entrada.nextLine();
						 System.out.println("Informar o url:");
						 url = entrada.nextLine();						 
						 Curso curso = new Curso(cdcurso,nome,valor,url);
						 tx.begin();
				    	 em.persist(curso);
				    	 tx.commit();	
				    	 break;
						   
					   }
				     case 4:{
				    	 System.out.println("Alterar um  Curso");
				    	 System.out.println("Informar o CDCURSO:");
						 cdcurso = entrada.nextLong(); 
						 entrada.nextLine();
						 System.out.println("Informar o Nome:");
						 nome = entrada.nextLine();
						 System.out.println("Informar o valor:");
						 valor = entrada.nextLong();
						 System.out.println("Informar o url:");
						 url = entrada.nextLine();
						 entrada.nextLine();
						 Curso curso = new Curso(cdcurso,nome,valor,url);
						 tx.begin();
						 em.merge(curso);
						 tx.commit();
				    	 break;
						   
					   }
				     case 5:{
				    	 System.out.println("Expcluir um Curso Especifico");
				    	 System.out.println("Informar o CDCURSO");
						 cdcurso = entrada.nextLong(); 
						 Curso curso = em.find(Curso.class, cdcurso);
						 tx.begin();
				    	 em.remove(curso);						 
				    	 break;
						   
					   }
				     case 6:{ 
						   System.out.println("Encerrando o Sistema....");
						   em.close();
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
