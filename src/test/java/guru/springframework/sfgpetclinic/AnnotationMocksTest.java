package guru.springframework.sfgpetclinic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

/**
 * @author : eanani
 * @project : testing-junit5-mockito
 * @created : 01/03/2022,
 **/
public class AnnotationMocksTest {

    @Mock
    private Map<String, Object> mapMocks;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMock() {
        mapMocks.put("keyValue", "foo");
//        Assertions.assertEquals(1, mapMocks.size()); // Fail : Because it's not a real implinmatation of map
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }
}
