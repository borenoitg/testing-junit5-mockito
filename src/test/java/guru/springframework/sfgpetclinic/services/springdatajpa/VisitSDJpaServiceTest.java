package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.VisitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalMatchers.and;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * @author : eanani
 * @project : testing-junit5-mockito
 * @created : 02/03/2022,
 **/
@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {

    @InjectMocks
    private VisitSDJpaService service;

    @Mock
    private VisitRepository visitRepository;

    @Nested
    class ReadPutDeleteVisitObjectTest {

        private Visit visit;

        @BeforeEach
        void setUp() {
            visit = new Visit();
        }

        @DisplayName("Test Find All")
        @Test
        void findAll() {

            Set<Visit> visits = new HashSet<>();
            visits.add(visit);

            when(visitRepository.findAll()).thenReturn(visits);

            Set<Visit> foundVisits = service.findAll();

            verify(visitRepository).findAll();

            assertThat(foundVisits)
                    .hasSize(1)
                    .containsAll(Set.of(visit));
        }

        @DisplayName("Test Find By Id")
        @Test
        void findById() {

            when(visitRepository.findById(anyLong())).thenReturn(Optional.of(visit));

            Visit foundVisit = service.findById(1L);

            verify(visitRepository).findById(anyLong());

            assertThat(foundVisit)
                    .isNotNull()
                    .hasAllNullFieldsOrProperties()
                    .isSameAs(visit);
        }

        @DisplayName("Test Save")
        @Test
        void save() {

            when(visitRepository.save(any(Visit.class))).thenReturn(visit);

            Visit savedVisit = service.save(new Visit());

            verify(visitRepository).save(any(Visit.class));
            assertThat(savedVisit)
                    .isNotNull()
                    .hasAllNullFieldsOrProperties()
                    .isInstanceOf(Visit.class);
        }

        @DisplayName("Test Delete")
        @Test
        void delete() {

            doNothing().when(visitRepository).delete(and(isNotNull(), isA(Visit.class)));

            service.delete(visit);

            verify(visitRepository).delete(any(Visit.class));
        }
    }

    @DisplayName("Test Delete By Id")
    @Test
    void deleteById() {

        doNothing().when(visitRepository).deleteById(and(isNotNull(), isA(Long.class)));

        service.deleteById(1L);

        verify(visitRepository).deleteById(anyLong());
    }
}