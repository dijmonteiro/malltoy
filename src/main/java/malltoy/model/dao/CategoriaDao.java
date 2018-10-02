package malltoy.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import malltoy.model.entity.Categoria;


@Repository
@Transactional
public class CategoriaDao {

	@Autowired
	private EntityManager entityManager;
	
	public void salvar(Categoria categoria) {
		entityManager.persist(categoria);
	}
	
	public void atualizar(Categoria categoria) {
		entityManager.merge(categoria);
	}
	
	public void delete(Long ctCodigo) {
		entityManager.remove(entityManager.getReference(Categoria.class, ctCodigo));
	}
	
	public Categoria buscarPorId(Long ctCodigo) {
		return entityManager.find(Categoria.class, ctCodigo);		 
	}
	
	public List<Categoria> buscarTodos(){
		return entityManager.createQuery("select c from Categoria c", Categoria.class).getResultList();
	}
}
