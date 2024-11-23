package br.dev.onepiece.webpiece.model;

import java.time.LocalDate;
import java.util.List;

import br.dev.onepiece.webpiece.enums.FollowUp;
import br.dev.onepiece.webpiece.enums.Material;
import br.dev.onepiece.webpiece.enums.StatusProjeto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.transaction.Transactional;

@Entity
@Transactional
public class Projeto  {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String titulo;
	
	private float largura;

	private float comprimento;

	private float altura;

	private String descricao;

	private String caminhoArquivo;

	private LocalDate dataFinalizacao;

	private String imagem;

	@Enumerated(EnumType.STRING)
	private Material material;

	@Enumerated(EnumType.STRING)
	private FollowUp followup;

	@Enumerated(EnumType.STRING)
	private StatusProjeto statusprojeto;

	@ManyToOne
	@JoinColumn(name = "idUsuario")
	//@JsonBackReference
	private Usuario usuario;
	
	@OneToMany(mappedBy = "projeto",cascade = CascadeType.ALL ) //fetch = FetchType.LAZY
	//@JsonManagedReference
	private List<Orcamento> orcamentos;

	public Projeto() {
		
	}
	
	// Get Set--------------------------
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public float getLargura() {
		return largura;
	}

	public void setLargura(float largura) {
		this.largura = largura;
	}

	public float getComprimento() {
		return comprimento;
	}

	public void setComprimento(float comprimento) {
		this.comprimento = comprimento;
	}

	public float getAltura() {
		return altura;
	}

	public void setAltura(float altura) {
		this.altura = altura;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCaminhoArquivo() {
		return caminhoArquivo;
	}

	public void setCaminhoArquivo(String caminhoArquivo) {
		this.caminhoArquivo = caminhoArquivo;
	}

	public LocalDate getDataFinalizacao() {
		return dataFinalizacao;
	}

	public void setDataFinalizacao(LocalDate dataFinalizacao) {
		this.dataFinalizacao = dataFinalizacao;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public FollowUp getFollowup() {
		return followup;
	}

	public void setFollowup(FollowUp followup) {
		this.followup = followup;
	}

	public StatusProjeto getStatusprojeto() {
		return statusprojeto;
	}

	public void setStatusprojeto(StatusProjeto statusprojeto) {
		this.statusprojeto = statusprojeto;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Orcamento> getOrcamentos() {
		return orcamentos;
	}

	public void setOrcamentos(List<Orcamento> orcamentos) {
		this.orcamentos = orcamentos;
	}

	public Projeto(Long id, float largura, float comprimento, float altura, String descricao, String caminhoArquivo,
			LocalDate dataFinalizacao, String imagem, Material material, FollowUp followup, StatusProjeto statusprojeto,
			Usuario usuario, List<Orcamento> orcamentos) {
		super();
		this.id = id;
		this.largura = largura;
		this.comprimento = comprimento;
		this.altura = altura;
		this.descricao = descricao;
		this.caminhoArquivo = caminhoArquivo;
		this.dataFinalizacao = dataFinalizacao;
		this.imagem = imagem;
		this.material = material;
		this.followup = followup;
		this.statusprojeto = statusprojeto;
		this.usuario = usuario;
		this.orcamentos = orcamentos;
	}	

	
	
	
}
