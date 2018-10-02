package malltoy.conversor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import malltoy.model.dao.CategoriaDao;
import malltoy.model.entity.Categoria;


@Component
public class CategoriaConverter implements Converter<String, Categoria>{

	@Autowired
	private CategoriaDao dao;
	
	@Override
	public Categoria convert(String source) {
		
		System.out.println(source);
		
		if(source.isEmpty()) return null;		
		
		return dao.buscarPorId(Long.parseLong(source) );
	}
	
}
