# Proyecto TDD – Registro de Personas

## Instrucciones para compilar y correr pruebas

Asegúrate de tener instalado **Maven** y **Java 17+**.

```bash
# Limpiar y correr todas las pruebas
mvn clean test
# Generar reporte
jacoco:report
```

Esto ejecutará los tests ubicados en `src/test/java/edu/unisabana/tyvs/tdd/domain/service`.

---

## Descripción del dominio

El dominio del proyecto consiste en un **sistema de registro de personas**.  
La clase principal (`Registry`) valida si una persona puede ser registrada con base en ciertas reglas.

### Reglas validadas

- La persona no puede estar muerta.  
- La edad debe estar entre 0 y 120 años.  
- La persona debe tener un nombre no vacío.  
- Solo se registran personas vivas y mayores de edad (≥ 18 años).  
- No se permiten duplicados (misma identificación).

---

## Explicación de TDD (Test Driven Development)

El proyecto se desarrolló aplicando el ciclo **TDD (Red → Green → Refactor)**:

1. **Red:** Escribir una prueba que falle (define el comportamiento esperado).  
2. **Green:** Escribir el código mínimo necesario para que la prueba pase.  
3. **Refactor:** Mejorar el código manteniendo todas las pruebas en verde.

Este proceso asegura que cada funcionalidad nueva esté validada desde su diseño.

---

## ⚙️ Patrón AAA en las pruebas

Las pruebas siguen la estructura **AAA (Arrange, Act, Assert):**

- **Arrange:** Se prepara el entorno o los datos (crear persona, inicializar Registry).  
- **Act:** Se ejecuta la acción a probar (por ejemplo, `registry.register(person)`).  
- **Assert:** Se verifican los resultados esperados (`assertTrue`, `assertFalse`, etc.).

Ejemplo:

```java
@Test
public void shouldNotRegisterPersonUnderAge() {
    // Arrange
    Person person = new Person("Ana", 15, Gender.FEMALE, true);
    Registry registry = new Registry();

    // Act
    RegisterResult result = registry.register(person);

    // Assert
    assertEquals(RegisterResult.UNDERAGE, result);
}
```

---

## 📜 Historia TDD

| Iteración | Estado | Descripción | Commit / Mensaje |
|:--:|:--:|:--|:--|
| 1 | **RED** | Se crea test para persona muerta (`shouldNotRegisterDeadPerson`) que falla inicialmente. | `test: add dead person rule (RED)` |
| 2 | **GREEN** | Se implementa la condición mínima en `Registry` para validar si la persona está viva. | `feat: minimal check alive (GREEN)` |
| 3 | **REFACTOR** | Se refactoriza el código extrayendo constantes y limpiando duplicación. | `refactor: extract constants and simplify validation` |
| 4 | **RED** | Nuevo test para edad fuera de rango (`shouldNotRegisterTooOldPerson`) que falla. | `test: add too old person rule (RED)` |
| 5 | **GREEN** | Implementación de la regla de edad máxima. | `feat: validate max age 120 (GREEN)` |
| 6 | **REFACTOR** | Se optimiza la validación de edad y se mejora legibilidad. | `refactor: consolidate age checks (REFACTOR)` |

---

## Tabla de Equivalencia 
| **ID** | **Entradas / Condición de entrada** | **Clase de Equivalencia** | **Valor de prueba (entrada)** | **Resultado esperado**                | **Test que lo cubre**                    |
| :----: | :---------------------------------- | :------------------------ | :---------------------------- | :------------------------------------ | :--------------------------------------- |
|   CE1  | Edad < 0                            | Inválida                  | `edad = -5`                   | Rechazar registro (false o excepción) | `shouldNotRegisterNegativeAge()`         |
|   CE2  | Edad entre 0 y 17                   | Inválida                  | `edad = 15`                   | Rechazar registro (false o excepción) | `shouldNotRegisterPersonUnderAge()`      |
|   CE3  | Edad entre 18 y 120                 | Válida                    | `edad = 25`                   | Registro exitoso (true)               | `shouldRegisterAdult()`                  |
|   CE4  | Edad > 120                          | Inválida                  | `edad = 130`                  | Rechazar registro (false o excepción) | `shouldNotRegisterTooOldPerson()`        |
|   CE5  | Nombre vacío o nulo                 | Inválida                  | `nombre = ""`                 | Rechazar registro (false o excepción) | `shouldNotRegisterPersonWithEmptyName()` |
|   CE6  | Nombre válido                       | Válida                    | `nombre = "Gabriela"`         | Registro exitoso (true)               | `shouldRegisterPersonWithValidName()`    |

## Valores Limite 
| **ID** | **Condición límite**                  | **Valor de prueba** | **Resultado esperado** | **Test que lo cubre**               |
| :----: | :------------------------------------ | :------------------ | :--------------------- | :---------------------------------- |
|   VL1  | Edad = 17 (límite inferior de adulto) | `edad = 17`         | Rechazado              | `shouldNotRegisterPersonUnderAge()` |
|   VL2  | Edad = 18 (mínimo válido)             | `edad = 18`         | Aceptado               | `shouldRegisterAdult()`             |
|   VL3  | Edad = 120 (máximo válido)            | `edad = 120`        | Aceptado               | `shouldRegisterOldestValidPerson()` |
|   VL4  | Edad = 121 (fuera de rango)           | `edad = 121`        | Rechazado              | `shouldNotRegisterTooOldPerson()`   |

## Cobertura 
Reporte JaCoCo 
<img width="1330" height="223" alt="image" src="https://github.com/user-attachments/assets/bb8dec79-4603-47a8-b778-125fe5db4ccd" />

## Tests realizados 
<img width="1693" height="335" alt="image" src="https://github.com/user-attachments/assets/3b0b8a2a-5115-45bc-b35c-6906d3ebbba7" />

## Escenarios no cubiertos

Según el reporte de cobertura  (90 % de instrucciones cubiertas, 85 % de ramas), hay partes del código que no se ejecutaron durante las pruebas.
Probablemente los escenarios no cubiertos sean:

Condiciones límite: valores justo en el borde de una regla (por ejemplo, edad = 18, puntaje = 0, etc.).

Casos excepcionales o errores: entradas nulas, vacías o con formato inválido.

Flujos alternativos: ramas de if o else que no se activan con los datos actuales.

Casos de validación negativa: cuando el registro no debería ser válido, pero el test no lo comprueba explícitamente.

## Cómo podria mejorar la clase Registry para facilitar su prueba

Inyección de dependencias:
Si Registry crea directamente objetos o depende de servicios externos, conviene inyectarlos por constructor o usar interfaces para poder sustituirlos con mocks en las pruebas.

Métodos más pequeños y puros:
Divide métodos grandes en funciones más simples que tengan una sola responsabilidad y sin efectos secundarios (por ejemplo, sin imprimir ni leer archivos directamente).

Eliminar dependencias estáticas o globales:
Evita usar variables estáticas o singletons, ya que dificultan el aislamiento de los tests.

Uso de valores de retorno claros:
En lugar de usar System.out o cambiar variables internas, haz que los métodos devuelvan resultados o códigos de estado que puedan ser verificados en los tests.

Validaciones explícitas:
Centraliza las reglas de negocio en métodos de validación fácilmente testeables, en lugar de dispersarlas en la lógica principal.
