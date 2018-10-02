package malltoy.model.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="Imagem")
public class Imagem {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long imCodigo;
	
	@Column(nullable=true)
	private String imTitle;

//	@NotNull(message="Você deve selecionar uma imagem para UPLOAD!")
	@Column(nullable=true)
	private String imHref;
	
	@Column(length=1, columnDefinition="int default 1", insertable=false)
	private int imStatus;
	
	@Column(columnDefinition="timestamp default CURRENT_TIMESTAMP()",insertable=false,updatable=false) 	
	@Temporal(TemporalType.TIMESTAMP)
	private Date imDtCadastro;
	
	@NotNull(message="Selecione um produto!")
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="imPrCodigo")
	private Produto produto;

	public Long getImCodigo() {
		return imCodigo;
	}

	public void setImCodigo(Long imCodigo) {
		this.imCodigo = imCodigo;
	}

	public String getImTitle() {
		return imTitle;
	}

	public void setImTitle(String imTitle) {
		this.imTitle = imTitle;
	}

	public String getImHref() {
		return imHref;
	}

	public void setImHref(String imHref) {
		this.imHref = imHref;
	}

	public int getImStatus() {
		return imStatus;
	}

	public void setImStatus(int imStatus) {
		this.imStatus = imStatus;
	}

	public Date getImDtCadastro() {
		return imDtCadastro;
	}

	public void setImDtCadastro(Date imDtCadastro) {
		this.imDtCadastro = imDtCadastro;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

}
