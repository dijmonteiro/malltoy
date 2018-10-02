package malltoy.conversor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import malltoy.model.dao.ProdutoDao;
import malltoy.model.entity.Produto;

@Component
public class ProdutoConverter implements Converter<String,Produto>{

	@Autowired
	private ProdutoDao dao;
	
	@Override
	public Produto convert(String source) {
		System.out.println("Source produto = "+source);
		if(source.isEmpty()) return null;
		return dao.buscarPorId(Long.parseLong(source));
	}
	
}
