package malltoy.model.dao;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import malltoy.model.entity.Produto;

@Repository
@Transactional
public class ProdutoDao {
	
	@Autowired
	private EntityManager entityManager;
	
	public void salvar(Produto produto) {
		entityManager.persist(produto);
	}
	
	public void atualizar(Produto produto) {
		entityManager.merge(produto);
	}
	
	public void delete(Long id) {
		entityManager.remove(entityManager.getReference(Produto.class, id));
	}
	
	public Produto buscarPorId(Long prcodigo) {
		return entityManager.find(Produto.class, prcodigo);		 
	}
	
	public List<Produto> buscarTodos(){
		return entityManager.createQuery("select p from Produto p", Produto.class).getResultList();
	}
}
