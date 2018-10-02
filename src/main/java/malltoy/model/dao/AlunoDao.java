package malltoy.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import malltoy.model.entity.Aluno;

@Repository
@Transactional
public class AlunoDao {
	
	@Autowired
	private EntityManager entityManager;
	
	public void salvar(Aluno aluno) {
		entityManager.persist(aluno);
	}
	
	public void atualizar(Aluno aluno) {
		entityManager.merge(aluno);
	}
	
	public void delete(Long ctCodigo) {
		entityManager.remove(entityManager.getReference(Aluno.class, ctCodigo));
	}
	
	public Aluno buscarPorId(Long ctCodigo) {
		return entityManager.find(Aluno.class, ctCodigo);		 
	}
	
	public List<Aluno> buscarTodos(){
		return entityManager.createQuery("select a from Aluno a", Aluno.class).getResultList();
	}
}
