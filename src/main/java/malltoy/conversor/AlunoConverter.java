package malltoy.conversor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import malltoy.model.dao.AlunoDao;
import malltoy.model.entity.Aluno;

@Component
public class AlunoConverter implements Converter<String,Aluno>{

	@Autowired
	private AlunoDao alunoDao;
	
	@Override
	public Aluno convert(String source) {
		System.out.println("Source aluno: "+source);
		if(source.isEmpty()) return null;
		return alunoDao.buscarPorId(Long.parseLong(source));
	}
}
