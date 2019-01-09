package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.abctreinamentos.Cliente;
import com.abctreinamentos.Curso;
import com.abctreinamentos.Pagamento;
import com.abctreinamentos.PagamentoId;

/**
 * Servlet implementation class Controlador
 */
@WebServlet("/Controlador")
public class Controlador extends HttpServlet {
	private static final long serialVersionUID = 1L;
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPATeste");
	EntityManager em = emf.createEntityManager();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controlador() {
        super();
        // TODO Auto-generated constructor stub
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
    		throws ServletException, IOException {
    	this.doPost(req, resp);
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		//criar as variaiveis
		int idformulario;//1-cliente; 2-cursos 3-pagamentos.
		int tipoformulario;//1.1- consulta...
		
		String nome,nomecurso,email,url,valorcurso;
		String cpfmascara,datainscricao;
		long cpf,cdcurso,valor;
		
		idformulario = Integer.parseInt(request.getParameter("idformulario"));
		tipoformulario = Integer.parseInt(request.getParameter("tipoformulario"));
		EntityTransaction tx = em.getTransaction();
		HttpSession session = request.getSession();
		if(idformulario == 1)//clientes
		{
			switch(tipoformulario)
			{
			
			case 11: //consultar Todos
			{
				TypedQuery<Cliente> query = em.createQuery(""
						+ "Select c from Cliente c",Cliente.class);
				List<Cliente> clientes = query.getResultList();
				
				session.setAttribute("mensagem", " Total de Cliente(s) "+clientes.size());
				session.setAttribute("cliente",clientes);		
			response.sendRedirect("clientes/consultaTodos.jsp");
				
				break;
			}
			
			case 12:// consultar
			{
				cpfmascara = request.getParameter("cpf");
				cpfmascara = cpfmascara.replaceAll("[.-]","");
				cpf = Long.parseLong(cpfmascara);
				Cliente cliente = em.find(Cliente.class,cpf);		
				
				if(cliente != null) {
					session.setAttribute("mensagem", "Cliente "+cpf+" Encontrado");
					session.setAttribute("cliente",cliente);
					
				}
				else {
					session.setAttribute("mensagem", "Cliente "+cpf+" Não Encontrado");
					session.setAttribute("cliente",null);
					
				}
			
				response.sendRedirect("clientes/resultado.jsp");
				break;
			}
			
			
			case 13:// Cadastar
			{
				cpfmascara = request.getParameter("cpf");
				cpfmascara = cpfmascara.replaceAll("[.-]","");
				cpf = Long.parseLong(cpfmascara);
				nome = request.getParameter("nome");
				email = request.getParameter("email");
				
				Cliente cliente = new Cliente(cpf,nome,email);			
				
				 tx.begin();
		    	 em.persist(cliente);
		    	 tx.commit();			
		    	 session.setAttribute("mensagem", "Cliente "+cpf+" Cadastrado");
			     session.setAttribute("cliente",cliente);
			     response.sendRedirect("clientes/resultado.jsp");		   
			     break;
			}
			case 14:// Alterar
			{
				cpfmascara = request.getParameter("cpf");
				cpfmascara = cpfmascara.replaceAll("[.-]","");
				cpf = Long.parseLong(cpfmascara);
				nome = request.getParameter("nome");
				email = request.getParameter("email");
				
                Cliente cliente = em.find(Cliente.class,cpf);		
				
				if(cliente != null) {
					
					cliente = new Cliente(cpf,nome,email);	
					 tx.begin();
			    	 em.merge(cliente);
			    	 tx.commit();	
					
					session.setAttribute("mensagem", "Cliente "+cpf+" Alterado");
					session.setAttribute("cliente",cliente);
					
				}
				else {
					session.setAttribute("mensagem", "Cliente "+cpf+" Não Encontrado");
					session.setAttribute("cliente",null);
					
				}
			
				response.sendRedirect("clientes/resultado.jsp");
				
				
				break;				
			}
			
			case 15:// Excluir
			{
				cpfmascara = request.getParameter("cpf");
				cpfmascara = cpfmascara.replaceAll("[.-]","");
				cpf = Long.parseLong(cpfmascara);
				out.println("<h2> CLientes => Excluir =>"+cpf);					
				
				Cliente cliente = em.find(Cliente.class,cpf);
				
				

				if(cliente != null) {
					tx.begin();
					em.remove(cliente);
					tx.commit();
					session.setAttribute("mensagem", "Cliente "+cpf+" Foi Excluido !");
					
					
				}
				else 
					session.setAttribute("mensagem", "Cliente "+cpf+" Não Encontrado !");
					session.setAttribute("cliente",null);
					response.sendRedirect("clientes/resultado.jsp");
		
				break;			
			}
			
			
			}
		}	
		else if(idformulario == 2)// cursos
		{
			switch(tipoformulario)
			{
			
			case 21: //consultar Todos
			{
				TypedQuery<Curso> query = em.createQuery(""
						+ "Select c from Curso c",Curso.class);
				List<Curso> cursos = query.getResultList();
				
				session.setAttribute("mensagem", " Total de Curso(s) "+cursos.size());
				session.setAttribute("curso",cursos);		
			response.sendRedirect("cursos/consultaTodos.jsp");
				
				break;
			}
			
			case 22:// consultar
			{
				cdcurso = Long.parseLong(request.getParameter("cdcurso"));
				Curso curso = em.find(Curso.class,cdcurso);
				
				
				if(curso != null) {
					
					session.setAttribute("mensagem", "Curso "+cdcurso+" Encontrado");
					session.setAttribute("curso",curso);
					
				}
				else {
					session.setAttribute("mensagem", "Curso "+cdcurso+" Não Encontrado");
					session.setAttribute("curso",null);
					
				}
			
				response.sendRedirect("cursos/resultado.jsp");
				break;
			}
			
			case 23:// Cadastar
			{
				cdcurso= Long.parseLong(request.getParameter("cdcurso"));
				nomecurso = request.getParameter("nome");
				valorcurso = request.getParameter("valor");
				valor = Long.parseLong(valorcurso);
				url = request.getParameter("site");
				
				Curso curso = new Curso (cdcurso, nomecurso, valor, url);			
				
				 tx.begin();
		    	 em.persist(curso);
		    	 tx.commit();			
		    	 session.setAttribute("mensagem", "Curso "+cdcurso+" Cadastrado");
			     session.setAttribute("curso",curso);
			     response.sendRedirect("cursos/resultado.jsp");	
				
				
				break;			
			}
			
			case 24:// Alterar
			{
				cdcurso= Long.parseLong(request.getParameter("cdcurso"));
				nomecurso = request.getParameter("nome");
				valorcurso = request.getParameter("valor");
				valor = Long.parseLong(valorcurso);
				url = request.getParameter("site");

                 Curso curso = em.find(Curso.class,cdcurso);		
				
				if(curso != null) {
					
					 curso = new Curso(cdcurso, nomecurso, valor, url);	
					 tx.begin();
			    	 em.merge(curso);
			    	 tx.commit();	
					
					session.setAttribute("mensagem", "Curso "+cdcurso+" Alterado");
					session.setAttribute("curso",curso);
					
				}
				else {
					session.setAttribute("mensagem", "Curso "+cdcurso+" Não Encontrado");
					session.setAttribute("curso",null);
					
				}
			
				response.sendRedirect("cursos/resultado.jsp");
				
				
				break;				
			}
			
			case 25:// Excluir
			{
				cdcurso = Long.parseLong(request.getParameter("cdcurso"));				
                Curso curso = em.find(Curso.class,cdcurso);		
				

				if(curso != null) {
					tx.begin();
					em.remove(curso);
					tx.commit();
					session.setAttribute("mensagem", "Curso "+cdcurso+" Foi Excluido !");
					
					
				}
				else 
					session.setAttribute("mensagem", "Curso "+cdcurso+" Não Encontrado !");
					session.setAttribute("curso",null);
					response.sendRedirect("cursos/resultado.jsp");
				
				
				break;								
			}
			
			}

			
		}
		else if (idformulario ==3)// pagamentos
		{
			
			switch(tipoformulario)
			{
			
			case 31: //consultar Todos
			{
				TypedQuery<Pagamento> query = em.createQuery(""
						+ "Select p from Pagamento p",Pagamento.class);
				List<Pagamento> pagamentos = query.getResultList();				
				session.setAttribute("mensagem", " Total de Pagamento(s) "+pagamentos.size());
				session.setAttribute("pagamento",pagamentos);		
			response.sendRedirect("pagamentos/consultaTodos.jsp");
				
				break;
			}
			
			case 32:// consultar
			{
				cpfmascara = request.getParameter("cpf");
				cpfmascara = cpfmascara.replaceAll("[.-]","");
				cpf = Long.parseLong(cpfmascara);
				cdcurso = Long.parseLong(request.getParameter("cdcurso"));
				out.println("<h2> Pagamentos => Consultar =>"+cpf+"-"+cdcurso);
				
				PagamentoId pgtoid = new PagamentoId(cpf, cdcurso);
				Pagamento pagamento = em.find(Pagamento.class, pgtoid);
				
                 if(pagamento!= null) {
					
					session.setAttribute("mensagem", "Pagamento "+cpf+""+cdcurso+" Encontrado");
					session.setAttribute("pagamento",pagamento);
					
				}
				else {
					session.setAttribute("mensagem", "Pagamento "+cpf+""+cdcurso+" Não Encontrado");
					session.setAttribute("pagamento",null);
					
				}
			
				response.sendRedirect("pagamentos/resultado.jsp");				
				break;			
			}
			
			case 33:// Cadastar
			{
				cpfmascara = request.getParameter("cpf");
				cpfmascara = cpfmascara.replaceAll("[.-]","");
				cpf = Long.parseLong(cpfmascara);
				cdcurso = Long.parseLong(request.getParameter("cdcurso"));
				datainscricao = (request.getParameter("datainscricao"));
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate date = LocalDate.parse(datainscricao, formatter);
				DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");				
				
				PagamentoId pgtoid = new PagamentoId(cpf, cdcurso);
				Pagamento pagamento = new Pagamento (pgtoid,datainscricao);
				tx.begin();
				em.persist(pagamento);
				tx.commit();
				session.setAttribute("mensagem", "Pagamento "+cpf+" "+cdcurso+" Cadastrado");
			    session.setAttribute("pagameto",pagamento);
			    response.sendRedirect("pagamentos/resultado.jsp");	
				break;			
			}
			
			case 34:// Alterar
			{
				cpfmascara = request.getParameter("cpf");
				cpfmascara = cpfmascara.replaceAll("[.-]","");
				cpf = Long.parseLong(cpfmascara);
				cdcurso = Long.parseLong(request.getParameter("cdcurso"));
				datainscricao = (request.getParameter("datainscricao"));
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate date = LocalDate.parse(datainscricao, formatter);
				DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");				
				
				PagamentoId pgtoid = new PagamentoId(cpf, cdcurso);
				Pagamento pagamento = new Pagamento(pgtoid,datainscricao);
				pagamento = em.find(Pagamento.class, pgtoid);
				
				if(pagamento != null) {
					
					 pagamento = new Pagamento(pgtoid, datainscricao);	
					 tx.begin();
			    	 em.merge(pagamento);
			    	 tx.commit();	
					
					session.setAttribute("mensagem", "Pagamento "+cpf+" "+cdcurso+" Alterado");
					session.setAttribute("pagamento",pagamento);
					
				}
				else {
					session.setAttribute("mensagem", "Pagamento "+cpf+" "+cdcurso+" Não Encontrado");
					session.setAttribute("pagamento",null);
					
				}
			
				response.sendRedirect("pagamentos/resultado.jsp");
						
				
				break;				
			}
			
			case 35:// Excluir
			{
				cpfmascara = request.getParameter("cpf");
				cpfmascara = cpfmascara.replaceAll("[.-]","");
				cpf = Long.parseLong(cpfmascara);
				cdcurso = Long.parseLong(request.getParameter("cdcurso"));
				
				PagamentoId pgtoid = new PagamentoId(cpf, cdcurso);
				Pagamento pagamento = em.find(Pagamento.class, pgtoid);
				
				if(pagamento != null) {
					tx.begin();
					em.remove(pagamento);
					tx.commit();
					session.setAttribute("mensagem", "Pagamento "+cpf+""+cdcurso+" Foi Excluido !");
					
					
				}
				else 
					session.setAttribute("mensagem", "Pagamento "+cpf+" "+cdcurso+" Não Encontrado !");
					session.setAttribute("Pagamento",null);
					response.sendRedirect("pagamentos/resultado.jsp");			
				
				break;		
					
			}
			
		}
	}

  }

}
