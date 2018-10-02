package malltoy.model.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="Produto")
public class Produto {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long prCodigo;
	
	@NotBlank(message="O nome é obrigatório!")
	@Column(nullable=false)
	private String prNome;	
	
	@NotBlank(message="A descrição é obrigatório!")
	private String prDescricao;
	
	@Column(columnDefinition="timestamp default CURRENT_TIMESTAMP()",insertable=false,updatable=false) 	
	@Temporal(TemporalType.TIMESTAMP)
	private Date prDtCadastro;	
	
	@NotBlank(message="O valor do produto é obrigatório!")
	private String prValor;
	
	@Column(length=1, columnDefinition="int default 1", insertable=false)
	private int prStatus;
	
	@OneToMany(mappedBy="produto",fetch = FetchType.LAZY,cascade = CascadeType.ALL) //mapeada para uma variavel chamada 'imagem' que esta na classe Imagem
	private List<Imagem> images;

	@NotNull(message="Selecione uma categoria!")
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="prCtCodigo",nullable=false)//é a chave estrangeira na tabela aluno
	private Categoria categoria;
	
	//getters e setters
	public Long getPrCodigo() {
		return prCodigo;
	}

	public void setPrCodigo(Long prCodigo) {
		this.prCodigo = prCodigo;
	}

	public String getPrNome() {
		return prNome;
	}

	public void setPrNome(String prNome) {
		this.prNome = prNome;
	}

	public String getPrDescricao() {
		return prDescricao;
	}

	public void setPrDescricao(String prDescricao) {
		this.prDescricao = prDescricao;
	}

	public Date getPrDtCadastro() {
		return prDtCadastro;
	}

	public void setPrDtCadastro(Date prDtCadastro) {
		this.prDtCadastro = prDtCadastro;
	}

	public String getPrValor() {
		return prValor;
	}

	public void setPrValor(String prValor) {
		this.prValor = prValor;
	}

	public int getPrStatus() {
		return prStatus;
	}

	public void setPrStatus(int prStatus) {
		this.prStatus = prStatus;
	}

	public List<Imagem> getImages() {
		return images;
	}

	public void setImages(List<Imagem> images) {
		this.images = images;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}


}

