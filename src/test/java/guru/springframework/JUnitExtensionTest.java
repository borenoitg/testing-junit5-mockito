package guru.springframework;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

/**
 * @author : eanani
 * @project : testing-junit5-mockito
 * @created : 01/03/2022,
 **/
@ExtendWith(MockitoExtension.class)
public class JUnitExtensionTest {

    @Mock
    private Map<String, Object> mapMocks;

    @Test
    void testMock() {
        mapMocks.put("keyValue", "foo");
    }
}
