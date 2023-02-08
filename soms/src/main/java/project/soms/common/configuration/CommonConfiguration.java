package project.soms.common.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import project.soms.common.repository.CommonRepository;
import project.soms.common.repository.CommonRepositoryImpl;
import project.soms.common.repository.mapper.CommonMapper;
import project.soms.common.service.CommonService;
import project.soms.common.service.CommonServiceImpl;

@Configuration
@RequiredArgsConstructor
public class CommonConfiguration {
	
	private final CommonMapper commonMapper;
	
	@Bean
	public CommonRepository commonRepository() {
		return new CommonRepositoryImpl(commonMapper);
	}
	
	@Bean
	public CommonService commonService() {
		return new CommonServiceImpl(commonRepository());
	}

}
