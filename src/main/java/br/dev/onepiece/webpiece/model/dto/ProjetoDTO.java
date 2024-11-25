package br.dev.onepiece.webpiece.model.dto;

import java.time.LocalDate;
import java.util.List;

import br.dev.onepiece.webpiece.enums.FollowUp;
import br.dev.onepiece.webpiece.enums.Material;
import br.dev.onepiece.webpiece.enums.StatusProjeto;
import br.dev.onepiece.webpiece.model.Orcamento;
import br.dev.onepiece.webpiece.model.Projeto;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class ProjetoDTO {
	private Long id;

	private float largura;

	private float comprimento;

	private float altura;

	private String descricao; 
	
	private String titulo;
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	private String caminhoArquivo;

	private LocalDate dataFinalizacao;

	private String imagem;
	
	

	@Enumerated(EnumType.STRING)
	private Material material;

	@Enumerated(EnumType.STRING)
	private FollowUp followup;

	@Enumerated(EnumType.STRING)
	private StatusProjeto statusprojeto;
	
	private UsuarioDTO usuario;
	
	private List<OrcamentoRespostaDTO> orcamento;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public UsuarioDTO getUsuario() {
		return usuario;
	}
	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}
	public List<OrcamentoRespostaDTO> getOrcamento() {
		return orcamento;
	}
	public void setOrcamento(List<OrcamentoRespostaDTO> orcamento) {
		this.orcamento = orcamento;
	}
	public ProjetoDTO(Projeto projeto, List<OrcamentoRespostaDTO> dtos) {
		super();
		this.id = projeto.getId();
		this.titulo = projeto.getTitulo();
		this.largura = projeto.getLargura();
		this.comprimento = projeto.getComprimento();
		this.altura = projeto.getAltura();
		this.descricao = projeto.getDescricao();
		this.caminhoArquivo = projeto.getCaminhoArquivo();
		this.dataFinalizacao = projeto.getDataFinalizacao();
		this.imagem = projeto.getImagem();
		this.material = projeto.getMaterial();
		this.followup = projeto.getFollowup();
		this.statusprojeto = projeto.getStatusprojeto();
		this.usuario = new UsuarioDTO(projeto.getUsuario());
		this.orcamento = dtos;
	}

	
	


}
