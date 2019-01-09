package com.abctreinamentos;


import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class PagamentoApp {

	public static void main(String[] args) {
		try 	
		{	
			
			Scanner entrada = new Scanner(System.in);
			int opcao = 0;
			long cpf, cdcurso;
			String datainscricao;
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPATeste");
			EntityManager em = emf.createEntityManager();
			EntityTransaction tx = em.getTransaction();
			while(opcao != 6) {
				System.out.println("Sistema de Gerenciamento de Pagamentos");
				System.out.println("================================================");
				System.out.println("Digite 1 para Consultar Todos os Pagamentos");
				System.out.println("Digite 2 para Consultar um Pagamentos Especifico");
				System.out.println("Digite 3 para Cadastrar um Novo Pagamento");
				System.out.println("Digite 4 para Alterar um Pagamento");
				System.out.println("Digite 5 para Excluir um Pagamento");
				System.out.println("Digite 6 para Sair");
				System.out.println("================================================");
				opcao = entrada.nextInt();
				
				switch(opcao) {
				     case 1:{
				       System.out.println("Consultar todos os Pagamentos"); 
				       TypedQuery<Pagamento> query = em.createQuery(""
							   + "Select c from Pagamento c",Pagamento.class);
					   List<Pagamento> Pagamentos = query.getResultList();
					   Pagamentos.forEach(System.out::println);
					   break;
				   }
				     case 2:{
				    	 System.out.println("Consultar um Pagamento Especifico");
				    	 System.out.println("Informar o CPF");
						 cpf = entrada.nextLong(); 
						 System.out.println("Informar o CDCURSO");
						 cdcurso = entrada.nextLong(); 
				    	 PagamentoId pgtoid = new PagamentoId(cpf, cdcurso);
				    	 Pagamento pagamento = em.find(Pagamento.class, pgtoid);
				    	 System.out.println(pagamento);						 						 
				    	 break;
					   }
				     case 3:{
				    	 System.out.println("Criar um Novo Pagamento");
				    	 System.out.println("Informar o CPF");
						 cpf = entrada.nextLong(); 
						 System.out.println("Informar o CDCURSO");
						 cdcurso = entrada.nextLong(); 
						 PagamentoId pgtoid = new PagamentoId(cpf, cdcurso);
						 entrada.nextLine();
						 System.out.println("Infromar a DATA DE INSCRICAO");
						 datainscricao = entrada.nextLine();
				    	 Pagamento pagamento = new Pagamento(pgtoid,datainscricao);
				    	 tx.begin();
				    	 em.persist(pagamento);
				    	 tx.commit();
				    	 break;
						   
					   }
				     case 4:{
				    	 System.out.println("Criar um Novo Pagamento");
				    	 System.out.println("Informar o CPF");
						 cpf = entrada.nextLong(); 
						 System.out.println("Informar o CDCURSO");
						 cdcurso = entrada.nextLong(); 
						 PagamentoId pgtoid = new PagamentoId(cpf, cdcurso);
						 entrada.nextLine();
						 System.out.println("Infromar a DATA DE INSCRICAO");
						 datainscricao = entrada.nextLine();
				    	 Pagamento pagamento = new Pagamento(pgtoid,datainscricao);
				    	 tx.begin();
						 em.merge(pagamento);
						 tx.commit();
				    	 break;
						   
					   }
				     case 5:{
				    	 System.out.println("Consultar um Pagamento Especifico");
				    	 System.out.println("Informar o CPF");
						 cpf = entrada.nextLong(); 
						 System.out.println("Informar o CDCURSO");
						 cdcurso = entrada.nextLong(); 
				    	 PagamentoId pgtoid = new PagamentoId(cpf, cdcurso);
				    	 Pagamento pagamento = em.find(Pagamento.class, pgtoid);
				    	 tx.begin();
						 em.remove(pagamento);
						 tx.commit();					 
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
