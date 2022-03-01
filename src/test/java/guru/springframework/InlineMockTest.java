package guru.springframework;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.mockito.Mockito.mock;

/**
 * @author : eanani
 * @project : testing-junit5-mockito
 * @created : 01/03/2022,
 **/
public class InlineMockTest {
    @Test
    void testInlineMock() {
        Map mapMock = mock(Map.class);
        Assertions.assertEquals(mapMock.size(), 0);
    }
}
