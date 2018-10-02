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

import malltoy.model.dao.AlunoDao;
import malltoy.model.dao.CategoriaDao;
import malltoy.model.entity.Aluno;
import malltoy.model.entity.Categoria;

@Controller
@RequestMapping("/member")
public class AlunoController {

	public static final String TOKEN_SEPARATOR		= "\t";
	public static final String SEP					= System.getProperty("file.separator");
	
	private static final String DIRBASE				= System.getProperty("user.dir");
	private static final String DIRRES				= SEP+"src"+SEP+"main"+SEP+"resources"+SEP+"static"; 
	private static final String IMGALUNOS			= SEP+"images"+SEP+"alunos";

	@Autowired
	private AlunoDao alunoDao;
	
	@Autowired
	private CategoriaDao daoCategory;
	
	@GetMapping("/")
	public String aboutTeam(ModelMap model) {
		getDir();
		List<Aluno> aluno = alunoDao.buscarTodos();

		model.addAttribute("alunos",aluno);
		return "/about-team";
	}
	
	@GetMapping("/admin/")
	public String listar(ModelMap model) {
		getDir();
		return "/admin/member/list-member";
	}
	
	@GetMapping("/admin/save")
	public String preCadastrar(Aluno aluno) {
		getDir();
		return "/admin/member/save-member"; 
	}
	
	@GetMapping("/admin/save/{codigo}")
	public String preEditar(@PathVariable("codigo") Long codigo, ModelMap model) {
		getDir();
		Aluno aluno = alunoDao.buscarPorId(codigo);
		model.addAttribute("aluno", aluno);
		
		return "/admin/member/save-member";
	}
	
	@PostMapping("/admin/insert")
	public String salvarCadastro(@Valid Aluno aluno, 
			BindingResult bs, 
			@RequestParam("foto") MultipartFile file, 
			RedirectAttributes attr) {
		
		if(bs.hasErrors()) {
			return "/admin/member/save-member";
		}
		
		if(salveImg(file)) {
			aluno.setUsHref(IMGALUNOS+SEP+file.getOriginalFilename());
		}
		
		alunoDao.salvar(aluno);
		attr.addFlashAttribute("message", "Integrante cadastrado com sucesso!");
		
		return "redirect:/member/admin/";
	}
	
	@PostMapping("/admin/update")
	public String salvarEdicao(@Valid Aluno aluno, 
			BindingResult bs, 
			@RequestParam("usHref") String usHref, 
			@RequestParam("foto") MultipartFile file, 
			RedirectAttributes attr) {
		
		if(bs.hasErrors()) {
			return "/admin/member/save-member";
		}

		if(!file.isEmpty()) {
			removeImg(aluno.getUsHref());
			if(salveImg(file)) {
				aluno.setUsHref(IMGALUNOS+SEP+file.getOriginalFilename());
			}
		}
		
		alunoDao.atualizar(aluno);
		attr.addFlashAttribute("message","Integrante atualizado com sucesso!");
		
		return "redirect:/member/admin/";
	}
	
	@GetMapping("/admin/delete/{codigo}")
	public String excluir(@PathVariable("codigo") Long codigo, RedirectAttributes attr) {
		Aluno aluno = alunoDao.buscarPorId(codigo);
		removeImg(aluno.getUsHref());
		alunoDao.delete(codigo);
		attr.addFlashAttribute("message","Integrante excluido com sucesso!");
		return "redirect:/member/admin/";
	}
	
	@ModelAttribute("listMembers")
	public List<Aluno> getListMembers(){
		return alunoDao.buscarTodos();
	}
	
	@ModelAttribute("listaCategoria")
	public List<Categoria> getListaCategorias(){
		return daoCategory.buscarTodos();
	}
	
	private boolean salveImg(MultipartFile file) {
		if(file.isEmpty()) {
			return false;
		}		
		try {
			byte[] bt = file.getBytes();
			Path path = Paths.get(DIRBASE+DIRRES+IMGALUNOS+SEP+file.getOriginalFilename());
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
		File diretorio = new File(DIRBASE+DIRRES+IMGALUNOS);
		if (!diretorio.exists()) {
		   diretorio.mkdirs();
		}
//		else {
//			System.out.println(DIRBASE+DIRRES+IMGPRODUTOS);
//		   System.out.println("Diretório já existente");
//		}
	}
}