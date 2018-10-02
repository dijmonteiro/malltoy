package malltoy.controller;

import java.util.ArrayList;
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
@RequestMapping("/category")
public class CategoriaController {

	@Autowired
	private CategoriaDao daoCategoria;
	
	@Autowired
	private ProdutoDao daoProduto;
	
	@GetMapping("/{ctCodigo}")
	public String listar(@PathVariable("ctCodigo") Long ctCodigo, ModelMap model) {
		Categoria categoria = daoCategoria.buscarPorId(ctCodigo);
		List<Produto> produto = daoProduto.buscarTodos();
		List<Produto> produtoFiltrado = new ArrayList<>();
		
		for(int i = 0; i< produto.size();i++) {
			if(produto.get(i).getCategoria().getCtCodigo()==ctCodigo)produtoFiltrado.add(produto.get(i));
		}
		
		model.addAttribute("categoria", categoria);
		model.addAttribute("listaProduto",produtoFiltrado);
		
		return "/product-catalog";
	}
	
	@GetMapping("/admin/")
	public String listarAdmin() {
		return "/admin/category/list-category";
	}
	
	@GetMapping("/admin/save")
	public String preCadastrar(Categoria categoria) {		
		return "/admin/category/save-category"; 
	}
	
	@GetMapping("/admin/save/{ctCodigo}")//esse {id} pega o id do objeto clicado na lista pelo botão 'editar'
	public String preEditar(@PathVariable("ctCodigo") Long ctCodigo, ModelMap model) { //@PathVariable("id") esta indicando que o valor chamado 'id' vindo da view vai ser atribuido no Long id do parametro
		//busca o curso pelo id
		
		Categoria categoria = daoCategoria.buscarPorId(ctCodigo);		
		 
		model.addAttribute("categoria", categoria);
		
		return "/admin/category/save-category";
	}
	
	@PostMapping("/admin/insert")
	public String salvarCadastro(@Valid Categoria categoria, BindingResult brs, RedirectAttributes attr) {
		
		if(brs.hasErrors()) {	 
			return "/admin/category/save-category";	 
		}
		
		daoCategoria.salvar(categoria);	
		
		attr.addFlashAttribute("message","Categoria cadastrada com sucesso");
		return "redirect:/category/admin/"; //usando redirect, o metodo chamará uma nova view, se não usar, ela virá preenchida
	}
	
	@PostMapping("/admin/update")
	public String salvarEdicao(@Valid Categoria categoria, BindingResult brs, RedirectAttributes attr) {
		if(brs.hasErrors()) {	 
			return "/admin/category/save-category";	 
		}
		
		daoCategoria.atualizar(categoria);
		
		attr.addFlashAttribute("message","Categoria alterada com sucesso");
		return "redirect:/category/admin/";
	}
	
	@GetMapping("/admin/delete/{ctCodigo}")
	public String excluir(@PathVariable("ctCodigo") Long ctCodigo, RedirectAttributes attr) {
		
		daoCategoria.delete(ctCodigo);
		attr.addFlashAttribute("message","Categoria excluida com sucesso");
		return "redirect:/category/admin/";
	}
	
	@ModelAttribute("listaCategoria")
	public List<Categoria> getListaCategorias(){
		return daoCategoria.buscarTodos();
	}
}
