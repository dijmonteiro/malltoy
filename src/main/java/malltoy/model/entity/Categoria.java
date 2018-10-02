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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="Categoria")
public class Categoria {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long ctCodigo;
	
	@NotBlank(message="O nome da categoria deve ser preenchido")
	private String ctDescricao;
	 
	@Column(columnDefinition="timestamp default CURRENT_TIMESTAMP()", insertable=false, updatable=false) 	
	@Temporal(TemporalType.TIMESTAMP)
	private Date ctDtCadastro;
		
	@Column(length=1, columnDefinition="int default 1", insertable=false)
	private int ctStatus;
	
	@OneToMany(mappedBy="categoria",fetch = FetchType.LAZY,cascade = CascadeType.ALL) //mapeada para uma variavel chamada 'categoria' que esta na classe Produto
	private List<Produto> produtos;

	//getters e setters
	public Long getCtCodigo() {
		return ctCodigo;
	}

	public void setCtCodigo(Long ctCodigo) {
		this.ctCodigo = ctCodigo;
	}

	public String getCtDescricao() {
		return ctDescricao;
	}

	public void setCtDescricao(String ctDescricao) {
		this.ctDescricao = ctDescricao;
	}

	public Date getCtDtCadastro() {
		return ctDtCadastro;
	}

	public void setCtDtCadastro(Date ctDtCadastro) {
		this.ctDtCadastro = ctDtCadastro;
	}
 

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public int getCtStatus() {
		return ctStatus;
	}

	public void setCtStatus(int ctStatus) {
		this.ctStatus = ctStatus;
	}

}
