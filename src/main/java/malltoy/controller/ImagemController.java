package malltoy.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import malltoy.model.dao.ImagemDao;
import malltoy.model.dao.ProdutoDao;
import malltoy.model.entity.Imagem;
import malltoy.model.entity.Produto;


@Controller
@RequestMapping("/images")
public class ImagemController {
	
	public static final String TOKEN_SEPARATOR		= "\t";
	public static final String SEP					= System.getProperty("file.separator");
	
	private static final String DIRBASE				= System.getProperty("user.dir");
	private static final String DIRRES				= SEP+"src"+SEP+"main"+SEP+"resources"+SEP+"static"; 
	private static final String IMGPRODUTOS			= SEP+"images"+SEP+"produtos";

	@Autowired
	private ImagemDao dao;
	
	@Autowired
	private ProdutoDao produtoDao;
	
	@GetMapping("/admin/")
	public String listar(ModelMap model) {
		getDir();
		List<Imagem> imagem = dao.buscarTodasImagemPeloPrCodigo();
		
		model.addAttribute("images",imagem);
		return "/admin/images/list-image";
	}
	
	@GetMapping("/admin/save")
	public String preCadastrar(Imagem imagem) {
		getDir();
		return "/admin/images/save-image"; 
	}
	
	@GetMapping("/admin/save/{codigo}")
	public String preEditar(@PathVariable("codigo") Long codigo, ModelMap model) {
		getDir();
		Imagem imagem = dao.buscarPorId(codigo);
		model.addAttribute("imagem", imagem);
		
		return "/admin/images/save-image";
	}
	
	@PostMapping("/admin/insert")
	public String salvarImagem(@Valid Imagem imagem,
			BindingResult bs,
			@RequestParam("file") MultipartFile file,
			RedirectAttributes attr) {
		
		if(bs.hasErrors()) {
			return "/admin/images/save-image";
		}
		
		if(salveImg(file)) {
			imagem.setImHref(IMGPRODUTOS+SEP+file.getOriginalFilename());
		}
		
		dao.salvar(imagem);
		attr.addFlashAttribute("message", "Imagem cadastrada com sucesso!");
		
		return "redirect:/images/admin/";
	}
	
	@PostMapping("/admin/update")
	public String salvarEdicao(@Valid Imagem imagem,
			BindingResult bs,
			@RequestParam("imHref") String imHref,
			@RequestParam("file") MultipartFile file,
			RedirectAttributes attr) {
		
		if(bs.hasErrors()) {
			return "/admin/images/save-image";
		}

		if(!file.isEmpty()) {
			removeImg(imagem.getImHref());
			if(salveImg(file)) {
				imagem.setImHref(IMGPRODUTOS+SEP+file.getOriginalFilename());
			}
		}
		
		dao.atualizar(imagem);
		attr.addFlashAttribute("message","Imagem atualizada com sucesso!");
		
		return "redirect:/images/admin/";
	}
	
	@GetMapping("/admin/delete/{codigo}")
	public String excluir(@PathVariable("codigo") Long codigo) {
		Imagem imagem = dao.buscarPorId(codigo);
		removeImg(imagem.getImHref());
		dao.delete(codigo);			
		return "redirect:/images/admin/";
	}

	@ModelAttribute("listaProduto")
	public List<Produto> getListaProdutos(){
		return produtoDao.buscarTodos();
	}

	private boolean salveImg(MultipartFile file) {
		if(file.isEmpty()) {
			return false;
		}		
		try {
			byte[] bt = file.getBytes();
			Path path = Paths.get(DIRBASE+DIRRES+IMGPRODUTOS+SEP+file.getOriginalFilename());
			Files.write(path,bt);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return false;
	}
	
	private boolean removeImg(String file) {
		File arqFoto = new File(DIRBASE+DIRRES+file);
		if (arqFoto != null && arqFoto.exists()) {
			arqFoto.delete();
			return true;
		}
		return false;
	}
	
	private void getDir() {
		File diretorio = new File(DIRBASE+DIRRES+IMGPRODUTOS);
		if (!diretorio.exists()) {
		   diretorio.mkdirs();
		}
//		else {
//			System.out.println(DIRBASE+DIRRES+IMGPRODUTOS);
//		   System.out.println("Diretório já existente");
//		}
	}
}
