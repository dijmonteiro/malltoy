package malltoy.conversor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import malltoy.model.dao.ImagemDao;
import malltoy.model.entity.Imagem;


@Component
public class ImagemConverter implements Converter<String, Imagem>{

	@Autowired
	private ImagemDao dao;
	
	@Override
	public Imagem convert(String source) {
		
		System.out.println(source);
		
		if(source.isEmpty() ) return null;		
		
		return dao.buscarPorId(Long.parseLong(source) );
	}
	
}
