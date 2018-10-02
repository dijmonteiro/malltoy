package malltoy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import malltoy.model.dao.CategoriaDao;
import malltoy.model.dao.ProdutoDao;
import malltoy.model.entity.Categoria;
import malltoy.model.entity.Produto;

@Controller
public class IndexController {
	
	@Autowired
	private CategoriaDao daoCategory;
	
	@Autowired
	private ProdutoDao daoProduto;
	
	@RequestMapping("/")
	public String index(ModelMap model) {		
		return "/index";
	}
	
	@ModelAttribute("listaCategoria")
	public List<Categoria> getListaCategorias(){
		return daoCategory.buscarTodos();
	}
	
	@ModelAttribute("listaProduto")
	public List<Produto> getListaProdutos(){
		return daoProduto.buscarTodos();
	}
}
