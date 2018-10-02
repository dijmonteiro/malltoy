package malltoy.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import malltoy.model.dao.CategoriaDao;
import malltoy.model.dao.ProdutoDao;
import malltoy.model.entity.Categoria;
import malltoy.model.entity.Produto;

@Controller
@RequestMapping("/product")
public class ProdutoController{

	@Autowired
	private ProdutoDao dao;
	
	@Autowired
	private CategoriaDao categoriaDao;
	
	@GetMapping("/{prCodigo}")
	public String exibir(@PathVariable("prCodigo") Long prCodigo, ModelMap model) {
		Produto produto = dao.buscarPorId(prCodigo);
		model.addAttribute("produto", produto);
		
		return "/product-description";
	}
	
	@GetMapping("/admin/")
	public String listar(ModelMap model) {
		List<Produto> produto = dao.buscarTodos();
		model.addAttribute("produtos",produto);
		
		return "/admin/product/list-product";
	}
	
	@GetMapping("/admin/save")
	public String preCadastrar(Produto produto) {
		return "/admin/product/save-product";
	}
	
	@GetMapping("/admin/save/{prCodigo}")
	public String preEditar(@PathVariable("prCodigo") Long prCodigo, ModelMap model) {
		Produto produto = dao.buscarPorId(prCodigo);
		model.addAttribute("produto", produto);
		
		return "/admin/product/save-product";
	}
	
	@PostMapping("/admin/insert")
	public String salvarCadastro(@Valid Produto produto, BindingResult bs, RedirectAttributes attr) {
		
		if(bs.hasErrors()) {	 
			return "/admin/product/save-product";	 
		}
		
		dao.salvar(produto);
		attr.addFlashAttribute("message", "Produto cadastrado com sucesso!");
		return "redirect:/product/admin/";
	}
	
	@PostMapping("/admin/update")
	public String salvarEdicao(@Valid Produto produto, BindingResult bs, RedirectAttributes attr) {
		if(bs.hasErrors()) {	 
			return "/admin/product/save-product";	 
		}
		
		dao.atualizar(produto);
		attr.addFlashAttribute("message", "Produto atualizado com sucesso!");
		return "redirect:/product/admin/";
	}
	
	@GetMapping("/admin/delete/{prCodigo}")
	public String excluir(@PathVariable("prCodigo") Long prCodigo, RedirectAttributes attr) {
		
		dao.delete(prCodigo);
		attr.addFlashAttribute("message", "Produto excluido com sucesso!");
		return "redirect:/product/admin/";
	}
	
	@ModelAttribute("listaCategoria")
	public List<Categoria> getListaCategorias(){
		return categoriaDao.buscarTodos();
	}
	
	@RequestMapping("/product-description")
	public String ProductDescription() {
		return "/product-description";
	}
}
