//package service;
//
//import com.brikton.lachacra.exceptions.DatabaseException;
//import com.brikton.lachacra.repositories.ClienteRepository;
//import com.brikton.lachacra.repositories.DevolucionRepository;
//import com.brikton.lachacra.repositories.TipoClienteRepository;
//import config.TestConfig;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.boot.test.mock.mockito.SpyBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.doReturn;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//@Import(TestConfig.class)
//@TestPropertySource(locations = "classpath:application-test.properties")
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//@Sql({"classpath:data-test.sql"})
//@MockBean(classes = { //clases que no interesa el comportamiento pero se las necesita para los constructores
//        DevolucionRepository.class
//})
//public class EjemploServiceTest {
//
//    @Autowired
//    private EjemploService ejemploService; //clase que importa el comportamiento
//    @SpyBean
//    private ClienteRepository clienteRepository; //clase que importa el comportamiento y se le quieren simular algún comportamiento
//    @MockBean
//    private TipoClienteRepository tipoClienteRepository; //clase que solo interesa simular su comportamiento
//
//
//    @BeforeEach
//    void setUp() {
//        doReturn(null).when(tipoClienteRepository).getById(2L);
//        doReturn(null).when(clienteRepository).getById(any());
//    }
//
//    //    @Test
//    public void probarAlgo() throws DatabaseException {
//        ejemploService.getEntityEjemplo("");
//    }
//
//}
