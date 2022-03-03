package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.fauxspring.Model;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

/**
 * @author : eanani
 * @project : testing-junit5-mockito
 * @created : 03/03/2022,
 **/
@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    private static final String OWNERS_CREATE_OR_UPDATE_OWNER_FORM = "owners/createOrUpdateOwnerForm";
    private static final String REDIRECT_OWNERS_5 = "redirect:/owners/5";
    public static final String OWNERS_FIND_OWNERS = "owners/findOwners";
    public static final String OWNERS_OWNERS_LIST = "owners/ownersList";

    @Mock(lenient = true)
    OwnerService ownerService;

    @InjectMocks
    OwnerController controller;

    @Mock
    BindingResult bindingResult;

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    @BeforeEach
    void setUp() {

        given(ownerService.findAllByLastNameLike(stringArgumentCaptor.capture()))
                .willAnswer(invocation -> {
                    List<Owner> owners = new ArrayList<>();

                    /*
                     * Récupération de l'argument passé en paramètre de la méthode.
                     * L'argument est capter par le captor (stringArgumentCaptor).
                     * On a ici un seul argument
                     * */
                    String name = invocation.getArgument(0);

                    if (name.equals("%Buck%")) {
                        owners.add(new Owner(5L, "Joe", "Buck"));
                        return owners;
                    } else if (name.equals("%dontFindMe%")) {
                        return owners;
                    } else if (name.equals("%findMe%")) {
                        owners.add(new Owner(1L, "Joe", "Buck"));
                        owners.add(new Owner(5L, "Joel", "Bucket"));
                        return owners;
                    }

                    throw new RuntimeException("Invalid Argument");
                });
    }

    @Test
    void processFindFormWildCardStringAnnotation() {

        // Given
        Owner owner = new Owner(1L, "Joe", "Buck");
        List<Owner> ownerList = new ArrayList<>();

        // When
        String viewName = controller.processFindForm(owner, bindingResult, null);

        // Then
        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("%Buck%");
        assertThat(viewName).isEqualToIgnoringCase(REDIRECT_OWNERS_5);
    }

    @Test
    void processFindFormWildCardNotFound() {

        // Given
        Owner owner = new Owner(1L, "Joe", "dontFindMe");

        // When
        String viewName = controller.processFindForm(owner, bindingResult, null);

        // Then
        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("%dontFindMe%");
        assertThat(viewName).isEqualToIgnoringCase(OWNERS_FIND_OWNERS);
    }

    @Test
    void processFindFormWildCardFoundMany() {

        // Given
        Owner owner = new Owner(1L, "Joe", "findMe");

        // When
        String viewName = controller.processFindForm(owner, bindingResult, Mockito.mock(Model.class));

        // Then
        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("%findMe%");
        assertThat(viewName).isEqualToIgnoringCase(OWNERS_OWNERS_LIST);
    }

    @Test
    void processCreationForm() {

        // Given
        Owner owner = new Owner(1L, "Jim", "Bob");
        given(bindingResult.hasErrors()).willReturn(true);

        // When
        String viewName = controller.processCreationForm(owner, bindingResult);

        // Then
        assertThat(viewName).isEqualToIgnoringCase(OWNERS_CREATE_OR_UPDATE_OWNER_FORM);
    }

    @Test
    void processCreationFormNoErrors() {

        // Given
        Owner owner = new Owner(5L, "Jim", "Bob");
        given(ownerService.save(any(Owner.class))).willReturn(owner);
        given(bindingResult.hasErrors()).willReturn(false);

        // When
        String viewName = controller.processCreationForm(owner, bindingResult);

        // Then
        assertThat(viewName).isEqualToIgnoringCase(REDIRECT_OWNERS_5);
    }
}