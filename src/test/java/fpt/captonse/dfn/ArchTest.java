package fpt.captonse.dfn;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("fpt.captonse.dfn");

        noClasses()
            .that()
                .resideInAnyPackage("fpt.captonse.dfn.service..")
            .or()
                .resideInAnyPackage("fpt.captonse.dfn.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..fpt.captonse.dfn.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
