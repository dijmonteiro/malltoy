package malltoy.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import malltoy.model.entity.Imagem;

@Repository
@Transactional
public class ImagemDao {

	@Autowired
	private EntityManager entityManager;
	
	public void salvar(Imagem imagem) {
		entityManager.persist(imagem);
	}
	
	public void atualizar(Imagem imagem) {
		entityManager.merge(imagem);
	}
	
	public void delete(Long imCodigo) {
		entityManager.remove( entityManager.getReference(Imagem.class, imCodigo) );
	}
	
	public Imagem buscarPorId(Long imCodigo) {
		return entityManager.find(Imagem.class, imCodigo);
	}
	
	public Imagem buscarPorPrCodigo(Long imPrCodigo) {
		return entityManager.find(Imagem.class, imPrCodigo);
	}
		
	public List<Imagem> buscarTodasImagemPeloPrCodigo() {
		return entityManager
				.createQuery("select i from Imagem i", Imagem.class)
				.getResultList();				
	}
	
}
